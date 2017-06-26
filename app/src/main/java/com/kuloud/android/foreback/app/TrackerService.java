package com.kuloud.android.foreback.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.kuloud.android.foreback.ForebackTracker;

public class TrackerService extends Service implements ForebackTracker.ForebackListener {
    private static final String TAG = "TrackerService";

    public TrackerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ForebackTracker.getInstance().addTrackListener(this);
    }

    @Override
    public void onDestroy() {
        ForebackTracker.getInstance().removeTrackListener(this);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onEnterForeground() {
        Log.d(TAG, "App enter foreground");
    }

    @Override
    public void onEnterBackground() {
        Log.d(TAG, "App enter background");
    }
}
