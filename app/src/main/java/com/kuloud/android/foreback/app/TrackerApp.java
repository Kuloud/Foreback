package com.kuloud.android.foreback.app;

import android.app.Application;
import android.content.Intent;

import com.kuloud.android.foreback.ForebackTracker;

/**
 * @author kuloud
 * @date 6/26/17.
 */

public class TrackerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ForebackTracker.init(this);

        Intent mainService = new Intent(this, TrackerService.class);
        startService(mainService);
    }
}
