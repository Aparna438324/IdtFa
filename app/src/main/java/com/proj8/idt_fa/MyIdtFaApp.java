package com.proj8.idt_fa;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.proj8.idt_fa.RfidReader.ReaderManager;

public class MyIdtFaApp extends Application implements Application.ActivityLifecycleCallbacks {

    private int activityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        ReaderManager.getInstance(this);
        registerActivityLifecycleCallbacks(this);
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level == TRIM_MEMORY_UI_HIDDEN) {
            // App is fully in background (not visible)
            Log.d("APP", "App moved to background → freeing reader");
            ReaderManager.getInstance(this).destroy();
        }
    }
    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;

        /*if (activityCount == 0) {
            // APP is CLOSED (no activity is visible)
            ReaderManager.getInstance(this).destroy();
            Log.d("APP", "All activities closed → Reader destroyed");
        }*/
    }

    // Unused but required to implement
    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) { }
    @Override public void onActivityResumed(Activity activity) { }
    @Override public void onActivityPaused(Activity activity) { }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }
    @Override public void onActivityDestroyed(Activity activity) { }



}
