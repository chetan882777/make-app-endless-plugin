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
    private void createChannel(String name, String description) {
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
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

    public Notification getNotification(String name, String description, int smallIcon,  String title, String contentText) {

        if (isAndroidOOrHigher()) {
            createChannel(name, description);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, PluginNotificationManager.CHANNEL_ID);
        builder
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }
}
