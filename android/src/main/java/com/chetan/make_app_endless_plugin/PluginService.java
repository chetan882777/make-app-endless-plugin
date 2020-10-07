package com.chetan.make_app_endless_plugin;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;

public class PluginService  extends Service {

    private FlutterEngine flutterEngine;
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!FlutterEngineCache.getInstance().contains(ConstantsOnlyForAndroid.CACHED_ENGINE_ID)) {
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
            } else if(!isServiceActive && !shouldStop) {
                isServiceActive = true;

                notificationManager = new PluginNotificationManager(this);
                startForeground(1, notificationManager.getNotification());
            }
        }
        return START_STICKY;
    }

}
