package com.proj8.idt_fa.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.proj8.idt_fa.Basic.BaseActivity;
import com.proj8.idt_fa.Basic.CircleProgress;
import com.proj8.idt_fa.Basic.HelperFunctions;
import com.proj8.idt_fa.R;
import com.proj8.idt_fa.RfidReader.LocationCallback;
import com.proj8.idt_fa.RfidReader.ReaderManager;

public class SearchActivity extends BaseActivity {

    private CircleProgress mCircleProgress;
    String tagID="";
    HelperFunctions helperFunctions;
    EditText tagIdEt;
    TextView scantv;
    ImageView tagScanImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setHeaderTitle(true,"Search");
//        updateConnectionStatus(ReaderManager.getInstance().isConnected());

       /* ReaderManager.getInstance().setStatusListener(isConnected -> {
            runOnUiThread(() -> updateConnectionStatus(isConnected));
        });*/

        mCircleProgress=findViewById(R.id.circle_progress);

        ReaderManager.getInstance().setReaderPower(15);
        helperFunctions=new HelperFunctions();
        tagScanImg=findViewById(R.id.tagScanImg);
        tagIdEt=findViewById(R.id.tagIdEt);
        scantv=findViewById(R.id.scantv);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocation();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 293) {
            if (event.getRepeatCount() == 0) {
                readTag();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCircleProgress.setValue(0.00f);
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopLocation();
    }

    private void readTag() {
        String input = tagIdEt.getText().toString().trim();
        if (!isValidEpc(input)) {
            HelperFunctions.showToastMessage(SearchActivity.this,
                    "Enter valid EPC (Hex only, multiple of 4, max 24 digits)");
            return;
        }
        tagID = input;
        if (scantv.getText().equals(getString(R.string.scanToSearch))) {
            startLocation();
        } else {
            stopLocation();
        }
    }
    private void startLocation() {


        scantv.setText(R.string.scanningSearch);

        ReaderManager.getInstance(this).startLocation(tagID, new LocationCallback() {
            @Override
            public void onLocationValue(int value, boolean flag) {
                runOnUiThread(() -> mCircleProgress.setValue((float) value));
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    helperFunctions.showToastMessage(getApplicationContext(), message);
                    stopLocation();
                });
            }
        });
    }
    private void stopLocation() {
        ReaderManager.getInstance().stopLocation();

        mCircleProgress.setValue(0);
        scantv.setText(R.string.scanToSearch);
    }
    private boolean isValidEpc(String epc) {
        if (epc == null || epc.isEmpty()) {
            return false;
        }

        if (epc.length() > 24) {
            return false;
        }

        if (epc.length() % 4 != 0) {
            return false;
        }

        return epc.matches("^[0-9A-Fa-f]+$");
    }


}