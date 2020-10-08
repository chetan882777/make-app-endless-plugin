import 'dart:async';

import 'package:flutter/services.dart';

// Method Channel for plugin
const MethodChannel _channel = const MethodChannel(_Constants.METHOD_CHANNEL_ID);

// Plugin class
class MakeAppEndlessPlugin {


  Future<void> extendLifeCycle(String channelName, String channelDescription,
      String contentTitle, String contentText) async {
    await _channel.invokeMethod(_Constants.METHOD_CALL_START_SERVICE,
        [channelName, channelDescription, contentTitle, contentText]);
  }

  Future<void> stopLifeCycleExtension() async {
    await _channel.invokeMethod(_Constants.METHOD_CALL_STOP_SERVICE);
  }
}

class _Constants {
  static const METHOD_CHANNEL_ID =
      "com.chetan.make_app_endless_plugin.method_channel";
  static const METHOD_CALL_START_SERVICE = "method_call_start_service";
  static const METHOD_CALL_STOP_SERVICE = "method_call_stop_service";
}
