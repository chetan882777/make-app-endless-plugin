import 'dart:async';

import 'package:flutter/services.dart';

/// Method Channel for plugin
const MethodChannel _channel =
    const MethodChannel(_Constants.METHOD_CHANNEL_ID);

/// Plugin class
class MakeAppEndlessPlugin {
  ///  Makes Application endless even if removed from background with persisting UI so on restart of application same UI gets loaded which was before removing from background
  ///
  ///  All Parameters are for notification which is shown for foreground service.
  ///  [channelName] - required type string and can be null - Gives name to notification channel
  ///  [channelDescription] - required type string and can be null - Gives description to notification channel
  ///
  ///   below two params will work only if [setSmallIcon] is called in [onCreate] of launcher activity like
  ///
  ///   ```java
  ///   public class MainActivity extends EndlessActivity {
  ///
  ///      @Override
  ///      protected void onCreate(@Nullable Bundle savedInstanceState) {
  ///         super.onCreate(savedInstanceState);
  ///         setSmallIcon(android.R.drawable.ic_media_play);
  ///      }
  ///   }
  ///  ```
  ///  [contentTitle] - required type string and can be null - set content title of notification
  ///  [contentText] - required type string and can be null - set content text of notification
  ///
  Future<void> extendLifeCycle(String channelName, String channelDescription,
      String contentTitle, String contentText) async {
    await _channel.invokeMethod(_Constants.METHOD_CALL_START_SERVICE,
        [channelName, channelDescription, contentTitle, contentText]);
  }

  /// Makes application life cycle normal and removes foreground notification. This will not make any effect if called before [extendLifeCycle]
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
