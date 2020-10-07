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
    private final NotificationCompat.Builder builder;

    public PluginNotificationManager(PluginService context, String channelName) {
        mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        if (isAndroidOOrHigher()) {
            createChannel(channelName);
        }
        builder = new NotificationCompat.Builder(context, PluginNotificationManager.CHANNEL_ID);
    }

    public PluginNotificationManager(PluginService context, String channelName, String channelDescription) {
        this(context, channelName);
        if (isAndroidOOrHigher()) {
            createChannel(channelName, channelDescription);
        }
    }

    public void setNotificationSmallIcon(int id) {
        builder.setSmallIcon(id);
    }

    public void setNotificationTitle(String title) {
        builder.setContentTitle(title);
    }

    public void setContentText(String contentText) {
        builder.setContentText(contentText);
    }

    public Notification build() {
        return builder.build();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel(String name) {
        if (mNotificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            int importance = android.app.NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
        }
    }

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
}
