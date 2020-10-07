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
                    child: const Text('Make Endless'),
                    onPressed: () async {
                      await plugin.extendLifeCycle(
                          "mke App Endless",          // Notification Channel Name
                          null,                       // Notification Channel Description
                          "Title",                    // Notification Content title
                          "No description required"); // Notification Content description
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
