package com.chetan.make_app_endless_plugin;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;

public class PluginService extends Service {

    public static final int NOTIFICATION_ID = 1;
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
        if (intent.hasExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_SHOULD_STOP_SERVICE)) {
            boolean shouldStop = intent.getBooleanExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_SHOULD_STOP_SERVICE, false);

            if (isServiceActive && shouldStop) {
                stopForeground(true);
                stopSelf();
            } else if (!isServiceActive && !shouldStop) {
                isServiceActive = true;
                setNotification(intent);
            }
        }
        return START_STICKY;
    }

    private void setNotification(Intent intent) {
        String name = intent.getStringExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_NAME);
        String description = intent.getStringExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_DESCRIPTION);
        String title = intent.getStringExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_TITLE);
        String content_text = intent.getStringExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_CONTENT_TEXT);

        if(description.equals(ConstantsOnlyForAndroid.DEF_INTENT_EXTRA_NOTIFICATION)) {
            notificationManager = new PluginNotificationManager(this, name);
        } else {
            notificationManager = new PluginNotificationManager(this, name, description);
        }

        if(ConstantsOnlyForAndroid.SMALL_ICON != 0 ) {
            notificationManager.setNotificationSmallIcon(ConstantsOnlyForAndroid.SMALL_ICON);
        }

        if(!title.equals(ConstantsOnlyForAndroid.DEF_INTENT_EXTRA_NOTIFICATION))
            notificationManager.setNotificationTitle(title);

        if(!content_text.equals(ConstantsOnlyForAndroid.DEF_INTENT_EXTRA_NOTIFICATION))
            notificationManager.setContentText(content_text);

        startForeground(NOTIFICATION_ID, notificationManager.build());
    }

}
