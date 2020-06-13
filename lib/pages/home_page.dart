import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const _platform =
      const MethodChannel('com.fabirt.flutter_foreground_service/channel');

  @override
  void initState() {
    super.initState();
    _platform.setMethodCallHandler(_onForegroundServiceCallback);
  }

  Future<void> _onForegroundServiceCallback(MethodCall call) async {
    switch (call.method) {
      case "onStarted":
        break;
      case "onStopped":
        break;
      case "onServiceMethodCallback":
        break;
      default:
        break;
    }
  }

  void _showToast() {
    _platform.invokeMethod(
      'showToast',
      <dynamic, dynamic>{'message': 'Hola desde Flutter'},
    );
  }

  void _startService() {
    _platform.invokeMethod('startToastService');
  }

  void _stopService() {
    _platform.invokeMethod('stopToastService');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Foreground Service'),
      ),
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            MaterialButton(
              onPressed: _showToast,
              textColor: Theme.of(context).accentColor,
              child: Text(
                'Show Toast'.toUpperCase(),
              ),
            ),
            RaisedButton(
              onPressed: _startService,
              child: Text('Start service'.toUpperCase()),
            ),
            RaisedButton(
              onPressed: _stopService,
              child: Text('Stop service'.toUpperCase()),
            ),
          ],
        ),
      ),
    );
  }
}
