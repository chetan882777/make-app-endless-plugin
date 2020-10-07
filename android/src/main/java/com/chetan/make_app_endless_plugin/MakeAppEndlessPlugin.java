package com.chetan.make_app_endless_plugin;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

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
        serviceIntent(false);
        result.success(null);
        break;
      }
      case Constants.METHOD_CALL_STOP_SERVICE: {
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
    mContext.startService(intent);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
