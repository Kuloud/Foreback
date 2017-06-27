package com.kuloud.android.foreback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 应用前后台监听，监听App内Activity的生命状态，维护队列，由此判断App是否在前台，并通知监听者。
 *
 * @author kuloud
 * @date 6/26/17.
 */

public class ForebackTracker implements Application.ActivityLifecycleCallbacks {
    private static ForebackTracker sTracker;

    private List<String> activities = new CopyOnWriteArrayList();

    private RegistDelegate<ForebackListener> listeners = new RegistDelegate<>();

    private ForebackTracker() {
    }

    public static void init(Application app) {
        if (sTracker == null) {
            sTracker = new ForebackTracker();
            app.registerActivityLifecycleCallbacks(sTracker);
        }
    }

    public static ForebackTracker getInstance() {
        if (sTracker == null) {
            throw new IllegalStateException("ForebackTracker should be initialized by [init] method.");
        }
        return sTracker;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!activities.contains(getActivityTag(activity))) {
            if (!isAppForeground()) {
                // 从后台状态进入前台
                for (ForebackListener listener : listeners.getRegedit()) {
                    listener.onEnterForeground();
                }
            }
            activities.add(getActivityTag(activity));
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activities.contains(getActivityTag(activity))) {
            activities.remove(getActivityTag(activity));
            if (!isAppForeground()) {
                // 从前台状态进入后台
                for (ForebackListener listener : listeners.getRegedit()) {
                    listener.onEnterBackground();
                }
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public boolean isAppForeground() {
        return !activities.isEmpty();
    }

    public void addTrackListener(ForebackListener listener) {
        listeners.add(listener);
    }

    public void removeTrackListener(ForebackListener listener) {
        listeners.remove(listener);
    }

    private String getActivityTag(@NonNull Activity activity) {
        return activity.getLocalClassName() + activity.hashCode();
    }

    /**
     * 供外部监听前后台切换事件
     */
    public interface ForebackListener {
        void onEnterForeground();

        void onEnterBackground();
    }
}
