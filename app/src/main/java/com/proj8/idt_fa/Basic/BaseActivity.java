package com.proj8.idt_fa.Basic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.proj8.idt_fa.R;
import com.proj8.idt_fa.RfidReader.ReaderInterface;
import com.proj8.idt_fa.RfidReader.ReaderManager;
import com.proj8.idt_fa.UI.DashboardActivity;

public class BaseActivity extends AppCompatActivity {

    protected TextView headerTitle;
    protected ImageView connectedBtn, disConnectedBtn, reconnectBtn, backBtn;
    ProgressBar progressReconnect;
    private boolean isReconnectInProgress = false;
    private boolean pendingReconnectSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout); // use super here to avoid override

        headerTitle = findViewById(R.id.header_title);
        backBtn = findViewById(R.id.backBtn);
        connectedBtn = findViewById(R.id.connectedBtn);
        disConnectedBtn = findViewById(R.id.disConnectedBtn);
        reconnectBtn = findViewById(R.id.reconnectBtn);
        progressReconnect = findViewById(R.id.progressReconnect);

        reconnectBtn.setOnClickListener(v->{
            Log.d("IdtFa: ","Reconnect");
            isReconnectInProgress = true;
            pendingReconnectSuccess = false;
            showReconnectLoading(true);

            ReaderManager.getInstance().reconnect(10);
        });
        /*ReaderManager readerManager = ReaderManager.getInstance(this);
        isReconnectInProgress = false;
        showReconnectLoading(false);
        readerManager.setStatusListener(isConnected -> {
            runOnUiThread(() -> {
                updateConnectionStatus(isConnected);

                if (isReconnectInProgress && isConnected) {
                    showReconnectLoading(false);
                    isReconnectInProgress = false;
                }
            });
        });*/

    }
    @Override
    protected void onResume() {
        super.onResume();

        ReaderManager rm = ReaderManager.getInstance(this);
        updateConnectionStatus(rm.isConnected());

        rm.setStatusListener(isConnected -> {
            runOnUiThread(() -> {
                updateConnectionStatus(isConnected);

                if (isReconnectInProgress) {
                    if (isConnected) {
                        pendingReconnectSuccess = true;
                        showReconnectLoading(false);
                        isReconnectInProgress = false;
                    } else {
                        pendingReconnectSuccess = false;
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            if (!pendingReconnectSuccess) {
                                showReconnectLoading(false);
                                isReconnectInProgress = false;
                                HelperFunctions.showToastMessage(BaseActivity.this,
                                        "Reconnect failed, please try again");
                            }
                        }, 1200);
                    }
                }
            });
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        LayoutInflater.from(this).inflate(layoutResID, contentFrame, true);
    }
    private void showReconnectLoading(boolean isLoading) {
        if (isLoading) {
            reconnectBtn.setVisibility(View.GONE);
            progressReconnect.setVisibility(View.VISIBLE);
        } else {
            progressReconnect.setVisibility(View.GONE);
            reconnectBtn.setVisibility(View.VISIBLE);
        }
    }
    protected void setHeaderTitle(boolean showBackButton,String title) {
        headerTitle.setText(title);
        backBtn.setVisibility(showBackButton ? View.VISIBLE : View.GONE);

        backBtn.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }
    protected void updateConnectionStatus(boolean isConnected) {
        if (connectedBtn != null && disConnectedBtn != null) {
            connectedBtn.setVisibility(isConnected ? View.VISIBLE : View.GONE);
            disConnectedBtn.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        }
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Log.d("APP", "App moved to background → destroy reader");
            ReaderManager.getInstance(this).destroy();
        }
    }

}
