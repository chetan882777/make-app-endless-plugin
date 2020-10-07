package com.chetan.make_app_endless_plugin;

import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class PluginNotificationManager {


    private static final String TAG = PluginNotificationManager.class.getSimpleName();

    private Context mContext;
    private final android.app.NotificationManager mNotificationManager;
    public static final String CHANNEL_ID = "com.example.mz_lifecycle_extension_plugin.channel";


    public PluginNotificationManager(Context context) {
        mContext = context;
        mNotificationManager = (android.app.NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    public void clearNotification(int id) {
        mNotificationManager.cancel(id);
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
            Log.d(TAG, "createChannel: New channel created");
        } else {
            Log.d(TAG, "createChannel: Existing channel reused");
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
