package com.proj8.idt_fa;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.proj8.idt_fa.RfidReader.ReaderManager;

public class MyIdtFaApp extends Application implements Application.ActivityLifecycleCallbacks {

    private int activityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;

        if (activityCount == 0) {
            // APP is CLOSED (no activity is visible)
            ReaderManager.getInstance(this).destroy();
            Log.d("APP", "All activities closed → Reader destroyed");
        }
    }

    // Unused but required to implement
    @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) { }
    @Override public void onActivityResumed(Activity activity) { }
    @Override public void onActivityPaused(Activity activity) { }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }
    @Override public void onActivityDestroyed(Activity activity) { }
}
