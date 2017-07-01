# Foreback
Tracks app's foreback status.

## How do I use Foreback?

**1、Copy "ForebackTracker.java" into your project**

**2、Init when app launched**
````
public class TrackerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ForebackTracker.init(this);

        Intent mainService = new Intent(this, TrackerService.class);
        startService(mainService);
    }
}
````

**2、Handle with the callback event**
````
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
````


## LICENSE
````
Copyright 2017 Kuloud

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
