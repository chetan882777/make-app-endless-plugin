package com.chetan.make_app_endless_plugin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class PluginNotificationManager {

    private final android.app.NotificationManager mNotificationManager;
    public static final String CHANNEL_ID = "com.chetan.make_app_endless_plugin.channel";
    private final Context mContext;


    public PluginNotificationManager(Context context) {
        mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        mContext = context;
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


    private void buildNotificationChannel() {
        // Create the (mandatory) notification channel when running on Android Oreo.
        if (isAndroidOOrHigher()) {
            createChannel();
        }
    }

    public Notification getNotification() {

        buildNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, PluginNotificationManager.CHANNEL_ID);
        builder.setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Throw")
                .setContentText("Running...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }
}
