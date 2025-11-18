package com.proj8.idt_fa.Basic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.proj8.idt_fa.R;
import com.proj8.idt_fa.RfidReader.ReaderInterface;
import com.proj8.idt_fa.RfidReader.ReaderManager;
import com.proj8.idt_fa.UI.DashboardActivity;

public class BaseActivity extends AppCompatActivity {

    protected TextView headerTitle;
    protected ImageView connectedBtn, disConnectedBtn, reconnectBtn;
    ReaderInterface reader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_layout); // use super here to avoid override

        headerTitle = findViewById(R.id.header_title);
        connectedBtn = findViewById(R.id.connectedBtn);
        disConnectedBtn = findViewById(R.id.disConnectedBtn);
        reconnectBtn = findViewById(R.id.reconnectBtn);

        reconnectBtn.setOnClickListener(v->{
            Log.d("IdtFa: ","Reconnect");

            ReaderManager.getInstance().reconnect(10);
            /*reader = ReaderManager.getInstance(BaseActivity.this);
            reader.initUhf(10);
            ReaderManager.getInstance().setStatusListener(isConnected -> {
                runOnUiThread(() -> updateConnectionStatus(isConnected));
            });*/
        });
        // Load status icons globally from HeaderStatusManager
//        HeaderStatusManager.updateStatusIcons(this, connectedBtn, disConnectedBtn, reconnectBtn);
    }

    // Override setContentView to inject child layout inside content_frame
    @Override
    public void setContentView(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        LayoutInflater.from(this).inflate(layoutResID, contentFrame, true);
    }

    // Allow child activities to set title
    protected void setHeaderTitle(String title) {
        if (headerTitle != null) {
            headerTitle.setText(title);
        }
    }
    protected void updateConnectionStatus(boolean isConnected) {
        if (connectedBtn != null && disConnectedBtn != null) {
            connectedBtn.setVisibility(isConnected ? View.VISIBLE : View.GONE);
            disConnectedBtn.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        }
    }
}
