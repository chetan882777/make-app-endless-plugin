package com.chetan.make_app_endless_plugin;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;

public class PluginService  extends Service {

    private static final String TAG = PluginService.class.getSimpleName();

    private FlutterEngine flutterEngine;
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: CALLED");

        if (!FlutterEngineCache.getInstance().contains(ConstantsOnlyForAndroid.CACHED_ENGINE_ID)) {
            Log.e(TAG, "onCreate: engine cannot null");
            throw new RuntimeException("Engine cannot be null");
        }
        flutterEngine = FlutterEngineCache.getInstance().get(ConstantsOnlyForAndroid.CACHED_ENGINE_ID);

        preferences = getSharedPreferences(ConstantsOnlyForAndroid.PREF_NAME, MODE_PRIVATE);
        preferences.edit().putBoolean(
                ConstantsOnlyForAndroid.PREF_IS_SERVICE_ACTIVE,
                ConstantsOnlyForAndroid.PREF_SERVICE_ACTIVE).apply();
    }

    @Override
    public void onDestroy() {

        preferences.edit().putBoolean(
                ConstantsOnlyForAndroid.PREF_IS_SERVICE_ACTIVE,
                ConstantsOnlyForAndroid.PREF_IS_NOT_SERVICE_ACTIVE).apply();

        if (!preferences.getBoolean(ConstantsOnlyForAndroid.PREF_IS_ACTIVITY_ACTIVE,
                ConstantsOnlyForAndroid.PREF_ACTIVITY_ACTIVE)) {
            Log.d(TAG, "onDestroy: destroy engine");
            FlutterEngineCache.getInstance().remove(ConstantsOnlyForAndroid.CACHED_ENGINE_ID);
            flutterEngine.destroy();
        }
        flutterEngine = null;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    boolean isServiceActive = false;
    private PluginNotificationManager notificationManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.hasExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_SHOULD_STOP_SERVICE)){
            boolean shouldStop = intent.getBooleanExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_SHOULD_STOP_SERVICE, false);
            if(isServiceActive && shouldStop){
                stopForeground(true);
                stopSelf();
            } else if(!isServiceActive) {
                isServiceActive = true;

                notificationManager = new PluginNotificationManager(this);
                notificationManager.buildNotificationChannel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PluginNotificationManager.CHANNEL_ID);
                builder.setSmallIcon(android.R.drawable.ic_media_play)
                        .setContentTitle("Throw")
                        .setContentText("Running...")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                startForeground(1, builder.build());
            }
        }
        return START_STICKY;
    }

}
