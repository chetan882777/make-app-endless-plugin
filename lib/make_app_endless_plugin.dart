
import 'dart:async';

import 'package:flutter/services.dart';

class MakeAppEndlessPlugin {
  static const MethodChannel _channel =
      const MethodChannel('make_app_endless_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
