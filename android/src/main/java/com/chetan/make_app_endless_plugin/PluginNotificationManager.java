package com.chetan.make_app_endless_plugin;

import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class PluginNotificationManager {

    private final android.app.NotificationManager mNotificationManager;
    public static final String CHANNEL_ID = "com.chetan.make_app_endless_plugin.channel";


    public PluginNotificationManager(Context context) {
        mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    // Does nothing on versions of Android earlier than O.
    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            CharSequence name = "MzLifecycleExtension";
            String description = "Extend flutter life cycle";
            int importance = android.app.NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(description);
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
        }
    }

    private boolean isAndroidOOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }


    public void buildNotificationChannel() {
        // Create the (mandatory) notification channel when running on Android Oreo.
        if (isAndroidOOrHigher()) {
            createChannel();
        }
    }

    public android.app.NotificationManager getNotificationManager() {
        return mNotificationManager;
    }
}
