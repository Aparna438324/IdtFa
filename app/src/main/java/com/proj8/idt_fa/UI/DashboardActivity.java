package com.proj8.idt_fa.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.proj8.idt_fa.Basic.BaseActivity;
import com.proj8.idt_fa.R;
import com.proj8.idt_fa.RfidReader.ReaderInterface;
import com.proj8.idt_fa.RfidReader.ReaderManager;

public class DashboardActivity extends BaseActivity {

    ReaderInterface reader;
    Button identifyBtn,inventoryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        identifyBtn=findViewById(R.id.identifyBtn);
        inventoryBtn=findViewById(R.id.inventoryBtn);

        reader = ReaderManager.getInstance(DashboardActivity.this);
        reader.initUhf(10);
        setHeaderTitle("Dashboard");

//        updateConnectionStatus(ReaderManager.getInstance().isConnected());

        ReaderManager.getInstance().setStatusListener(isConnected -> {
            Log.d("IdtFa: ","First connect");
            runOnUiThread(() -> updateConnectionStatus(isConnected));
        });

        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idtbtn = new Intent(getApplicationContext(),IdentifyActivity.class);
                startActivity(idtbtn);
            }
        });
        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idtbtn = new Intent(getApplicationContext(),InventoryActivity.class);
                startActivity(idtbtn);
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        ReaderManager reader = ReaderManager.getInstance(this);

        if (!reader.isInitialized()) {
            // Initialize only once
            reader.initUhf(10);
        } else {
            if (!reader.isConnected()) {
                reader.reconnect(10);
            }
        }
    }


}