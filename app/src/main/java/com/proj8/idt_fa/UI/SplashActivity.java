package com.proj8.idt_fa.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.proj8.idt_fa.R;

public class SplashActivity extends AppCompatActivity {

    ActionBar actionBar;
    private static int SPLASH_TIME_OUT = 4000;
    Boolean UserLoggedin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        actionBar = getSupportActionBar();
//        actionBar.hide();
        new Handler().postDelayed(new Runnable()
                                  {
                                      @Override
                                      public void run()
                                      {
                                          SharedPreferences prefs = getSharedPreferences("IdtUser",
                                                  MODE_PRIVATE);
                                          UserLoggedin = prefs.getBoolean("LoggedIn",false);
                                          if (UserLoggedin){
                                              Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
                                              startActivity(i);
                                              finish();
                                          }else{
                                              Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                              startActivity(i);
                                              finish();
                                          }
                                      }
                                  }
                ,SPLASH_TIME_OUT);
    }
}