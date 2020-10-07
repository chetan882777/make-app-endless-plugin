
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:make_app_endless_plugin/constants.dart';

class MakeAppEndlessPlugin {
  static const String TAG = "MzLifecycleExtensionPlugin";
  static const MethodChannel _channel =
  const MethodChannel(Constants.METHOD_CHANNEL_ID);

  Future<void> extendLifeCycle() async {
    await _channel.invokeMethod(Constants.METHOD_CALL_START_SERVICE, ["name", "des", "title", "des"]);
  }

  Future<void> stopLifeCycleExtension() async {
    await _channel.invokeMethod(Constants.METHOD_CALL_STOP_SERVICE);
  }
}
