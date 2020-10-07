import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:make_app_endless_plugin/make_app_endless_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  MakeAppEndlessPlugin plugin;

  @override
  void initState() {
    super.initState();
    plugin = MakeAppEndlessPlugin();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
            child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  RaisedButton(
                    child: const Text('Start'),
                    onPressed: () async {
                      await plugin.extendLifeCycle(null, null, null, null);
                    },
                  ),
                  RaisedButton(
                    child: const Text('Stop'),
                    onPressed: () async {
                      await plugin.stopLifeCycleExtension();
                    },
                  )
                ]
            )
        ),
      ),
    );
  }
}
