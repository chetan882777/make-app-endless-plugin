package com.chetan.make_app_endless_plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Collections;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** MakeAppEndlessPlugin */
public class MakeAppEndlessPlugin implements FlutterPlugin, MethodCallHandler {
  private static final String TAG = MakeAppEndlessPlugin.class.getSimpleName();
  private MethodChannel channel;
  private Context mContext;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "make_app_endless_plugin");
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
        Log.d(TAG, "onMethodCall: Start service");
        serviceIntent(false);
        result.success(null);
        break;
      }
      case Constants.METHOD_CALL_STOP_SERVICE: {
        Log.d(TAG, "onMethodCall: Stop service");
        serviceIntent(true);
        result.success(null);
        break;
      }
      default:
        result.notImplemented();
    }
  }

  private void serviceIntent(boolean shouldStopService) {
    Intent intent = new Intent(mContext, PluginService.class);
    intent.putExtra(ConstantsOnlyForAndroid.INTENT_EXTRA_SHOULD_STOP_SERVICE, shouldStopService);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      mContext.startForegroundService(intent);
    } else {
      mContext.startService(intent);
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
