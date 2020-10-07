import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:make_app_endless_plugin/make_app_endless_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('make_app_endless_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await MakeAppEndlessPlugin.platformVersion, '42');
  });
}
