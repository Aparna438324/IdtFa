package com.proj8.idt_fa.RfidReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.proj8.idt_fa.Basic.BaseActivity;
import com.proj8.idt_fa.R;
import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;

import java.util.HashMap;

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
//        ProgressDialog mypDialog;


        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

//            mypDialog.cancel();

            if (!result) {
                isConnected = false;
                Log.d("Rfid Reader: ","Reader Not Connected");
                if (listener != null) listener.onStatusChanged(false);
            }else{
                isConnected = true;
                mReader.setPower(Power);
                if (listener != null) listener.onStatusChanged(true);
                Log.d("Rfid Reader: ","Reader Connected");
            }
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Log.d("Rfid Reader: ","Trying to Connect Reader...");
            /*mypDialog = new ProgressDialog(context);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("Trying to Connect Reader...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();*/
        }

    }
    @Override
    public void destroy() {
        if (mReader!=null){
            mReader.free();
            mReader = null;
            isConnected = false;
            isInitialized = false;
            if (listener != null) listener.onStatusChanged(false);
            Log.d("Rfid Reader: ","Reader disconnected");
        }
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
    public void reconnect(int powerValue) {
        destroy();              // safely disconnect first
        initUhf(powerValue);    // then re-init
    }

}
