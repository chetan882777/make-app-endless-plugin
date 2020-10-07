package com.chetan.make_app_endless_plugin;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * MakeAppEndlessPlugin
 */
public class MakeAppEndlessPlugin implements FlutterPlugin, MethodCallHandler {
    private static final String TAG = MakeAppEndlessPlugin.class.getSimpleName();
    private MethodChannel channel;
    private Context mContext;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), Constants.METHOD_CHANNEL_ID);
        channel.setMethodCallHandler(this);
        mContext = flutterPluginBinding.getApplicationContext();
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), Constants.METHOD_CHANNEL_ID);
        channel.setMethodCallHandler(new MakeAppEndlessPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case Constants.METHOD_CALL_START_SERVICE: {
                startServiceIntent(call);
                result.success(null);
                break;
            }
            case Constants.METHOD_CALL_STOP_SERVICE: {
                stopServiceIntent();
                result.success(null);
                break;
            }
            default:
                result.notImplemented();
        }
    }

    private void startServiceIntent(MethodCall call) {
        /**
         *
         *  call args :-
         *   0 - nullable - channel name
         *   1 - nullable - channel description
         *   2 - non null - notification title
         *   3 - non null - notification content text
         *
         */

        ArrayList list = call.arguments();
        String name = (String) list.get(0);
        String description = (String) list.get(1);
        String title = (String) list.get(2);
        String contentText = (String) list.get(3);

        Intent intent = new Intent(mContext, PluginService.class);
        intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_SHOULD_STOP_SERVICE, false);
        intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_NAME , name);

        if(!description.isEmpty())
            intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_DESCRIPTION , description);
        else
            intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_DESCRIPTION , ConstantsOnlyForAndroid.DEF_INTENT_EXTRA_NOTIFICATION);

        if(!title.isEmpty())
            intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_TITLE , title);
        else
            intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_TITLE , ConstantsOnlyForAndroid.DEF_INTENT_EXTRA_NOTIFICATION);

        if(!contentText.isEmpty())
            intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_CONTENT_TEXT , contentText);
        else
            intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_NOTIFICATION_CONTENT_TEXT , ConstantsOnlyForAndroid.DEF_INTENT_EXTRA_NOTIFICATION);

        mContext.startService(intent);
    }

    private void stopServiceIntent() {
        Intent intent = new Intent(mContext, PluginService.class);
        intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_SHOULD_STOP_SERVICE, true);
        mContext.startService(intent);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
