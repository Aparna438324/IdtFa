package com.proj8.idt_fa.RfidReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.proj8.idt_fa.Basic.BaseActivity;
import com.proj8.idt_fa.R;
import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.IUHF;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ReaderManager extends BaseActivity implements ReaderInterface {

    private static ReaderManager instance;
    Context context;
    private boolean isInitialized = false;
    private boolean isConnected = false;
    public RFIDWithUHFUART mReader;
    private int Power;
    private AudioManager am;
    private SoundPool soundPool;
    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private ReaderStatusListener listener;
    private boolean isReading = false;
    private TagReadCallback tagReadCallback;
    private ContinuousTagCallback continuousCallback;
    private volatile boolean isContinuousReading = false;
    private boolean isLocating = false;
    private LocationCallback locationCallback;
    private int reconnectAttempts = 0;
    private static final int MAX_RECONNECT_ATTEMPTS = 2;
    private ReaderManager(Context ctxt) {
        this.context = ctxt.getApplicationContext();
    }
    public static synchronized ReaderManager getInstance(Context ctxt) {
        if (instance == null) {
            instance = new ReaderManager(ctxt);
        }
        return instance;
    }
    public static ReaderManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ReaderManager not initialized. Call getInstance(Context) first.");
        }
        return instance;
    }

    @Override
    public void initUhf(int PowerVal) {
        if (isInitialized) {
            Log.d("Rfid Reader:", "Already initialized, skipping init");
            return;
        }
        Power=PowerVal;
        try {
            mReader = RFIDWithUHFUART.getInstance();
        } catch (Exception ex) {

            Log.e("Rfid Reader: ",ex.getMessage());
            return;
        }

        isInitialized = true;

        if (mReader != null) {
            if (!isConnected) {
                new InitTask().execute();
            }
        }
    }

    private class InitTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (!result) {
                Log.d("Rfid Reader: ","Reader Not Connected");
                updateStatus(false);
            }else{
                Log.d("Rfid Reader: ","Reader Connected");
                mReader.setPower(Power);
                updateStatus(true);
            }
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Log.d("Rfid Reader: ","Trying to Connect Reader...");
        }

    }

    @Override
    public void destroy() {

        Log.d("Rfid Reader", "Destroy called");

        try {
            if (mReader != null) {

                // Stop ANY active mode safely (no flag dependency)
                try { mReader.stopInventory(); } catch (Exception ignored) {}
                try { mReader.stopLocation(); } catch (Exception ignored) {}

                try {
                    mReader.free();
                    Log.d("Rfid Reader", "Reader freed");
                } catch (Exception e) {
                    Log.e("Rfid Reader", "Error freeing reader: " + e.getMessage());
                }
            }
        }
        finally {

            mReader = null;
            isConnected = false;
            isInitialized = false;
            isReading = false;
            isContinuousReading = false;
            isLocating = false;

            if (listener != null) {
                listener.onStatusChanged(false);
            }

            Log.d("Rfid Reader", "Reader fully destroyed");
        }
    }
    public void setReaderPower(int power) {
        Power = power;

        if (mReader != null) {
            try {
                mReader.setPower(power);
                Log.d("Rfid Reader", "Power updated to: " + power);
                int p=getPower();
                Log.d("Rfid Reader","Updated Power: "+p);
            } catch (Exception e) {
                Log.e("Rfid Reader", "Failed to set power: " + e.getMessage());
            }
        } else {
            Log.d("Rfid Reader", "Reader not initialized yet, saving power only");
        }
    }
    public int getPower() {
        return Power;
    }
    public void sound() {
        try {
            ToneGenerator toneG = new ToneGenerator( AudioManager.STREAM_ALARM, 100 );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                toneG.startTone( ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200 );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void initSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(context, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(context, R.raw.serror, 1));
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void setStatusListener(ReaderStatusListener listener) {
        this.listener = listener;
//        listener.onStatusChanged(isConnected);
    }

    private void updateStatus(boolean connected) {
        isConnected = connected;
        if (listener != null) listener.onStatusChanged(connected);
    }
    public boolean isConnected() {
        return isConnected;
    }

    public void readEPC(TagReadCallback callback) {
        this.tagReadCallback = callback;

        if (mReader.startInventoryTag()) {
            isReading = true;
            new TagReadingThread().start();
        } else {
            if (callback != null) callback.onTagReadFailure("Failed to start inventory");
        }
    }

    private class TagReadingThread extends Thread {
        @Override
        public void run() {
            while (isReading) {
                UHFTAGInfo tag = mReader.readTagFromBuffer();
                if (tag != null) {
                    String epc = tag.getEPC();
                    String tid = tag.getTid();

                    Log.d("TAG-FOUND", "EPC: " + epc);

                    if (epc != null && (!epc.isEmpty())) {
                        isReading = false;
                        mReader.stopInventory();
                        if (tagReadCallback != null) tagReadCallback.onTagReadSuccess(epc);
                        return;
                    }
                    else {
                        isReading = false;
                        mReader.stopInventory();
                        if (tagReadCallback != null) tagReadCallback.onTagReadFailure("Tag length is not Appropriate");
                        return;
                    }
                }
            }
        }
    }
    public boolean isInitialized() {
        return isInitialized;
    }
    public boolean isContinuousReading() {
        return isContinuousReading;
    }

    public void reconnect(int powerValue) {

//        reconnectAttempts = 0;
//        attemptReconnect(powerValue);
        destroy();              // safely disconnect first
        initUhf(powerValue);    // then re-init
    }
    private void attemptReconnect(int powerValue) {
        reconnectAttempts++;

        destroy();
        initUhf(powerValue);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isConnected) {
                if (reconnectAttempts <= MAX_RECONNECT_ATTEMPTS) {
                    Log.d("Rfid Reader", "Reconnect failed. Retrying... Attempt " + reconnectAttempts);
                    attemptReconnect(powerValue);
                } else {
                    Log.d("Rfid Reader", "Reconnect failed after max attempts");

                    if (listener != null) {
                        listener.onStatusChanged(false);
                    }
                }
            }
        }, 2500);
    }
    public void startContinuousRead(ContinuousTagCallback callback) {
        this.continuousCallback = callback;
        if (isContinuousReading) {
            Log.d("ReaderManager", "Continuous reading already running.");
            return;
        }
        if (!mReader.startInventoryTag()) {
            if (callback != null) callback.onError("Failed to start inventory");
            return;
        }
        isContinuousReading = true;

        new Thread(() -> {
            Log.d("ReaderManager", "Continuous reading thread started");
            while (isContinuousReading) {
                UHFTAGInfo tag = mReader.readTagFromBuffer();
                if (tag != null) {
                    String epc = tag.getEPC();
                    String rssi = tag.getRssi();

                    if (epc != null && epc.length() > 7) {
                        if (continuousCallback != null) {
                            continuousCallback.onTagRead(epc, rssi);
                        }
                    }
                }
            }

            Log.d("ReaderManager", "Continuous reading thread stopped");
        }).start();
    }

    public void stopContinuousRead() {
        if (!isContinuousReading) return;
        isContinuousReading = false;
        mReader.stopInventory();

        if (continuousCallback != null) {
            continuousCallback.onStopped();
        }
    }
    public void startLocation(String epc, LocationCallback callback) {
        this.locationCallback = callback;

        if (mReader == null || !isConnected) {
            callback.onError("Reader not connected");
            return;
        }

        isLocating = true;

        boolean result = false;

        try {
            result = mReader.startLocation(
                    context,
                    epc,
                    IUHF.Bank_EPC,
                    32,
                    (value, flag) -> {
                        if (locationCallback != null && isLocating) {
                            locationCallback.onLocationValue(value, flag);
                        }
                    }
            );
        } catch (Exception e) {
            callback.onError("Location start failed: " + e.getMessage());
            return;
        }

        if (!result) {
            callback.onError("Failed to start location");
        }
    }
    public void stopLocation() {
        isLocating = false;

        try {
            if (mReader != null) {
                mReader.stopLocation();
            }
        } catch (Exception e) {
            Log.e("Rfid Reader", "Error stopping location: " + e.getMessage());
        }
    }

}
