import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const _channel =
      const MethodChannel('com.fabirt.flutter_foreground_service/channel');

  @override
  void initState() {
    super.initState();
    _channel.setMethodCallHandler(_onForegroundServiceCallback);
  }

  Future<void> _onForegroundServiceCallback(MethodCall call) async {
    switch (call.method) {
      case "onStarted":
        break;
      case "onStopped":
        break;
      case "onServiceMethodCallback":
        debugPrint('onServiceMethodCallback  WAS CALLED!!!!!');
        break;
      default:
        debugPrint('something  WAS CALLED!!!!!');
        break;
    }
  }

  void _showToast() {
    _channel.invokeMethod(
      'showToast',
      <dynamic, dynamic>{'message': 'Hi from Flutter!'},
    );
  }

  void _startService() {
    _channel.invokeMethod('startToastService');
  }

  void _stopService() {
    _channel.invokeMethod('stopToastService');
  }

  void _setupAlarm() {
    _channel.invokeMethod('setupAlarm');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter Foreground Service'),
      ),
      floatingActionButton: Builder(
        builder: (context) {
          return FloatingActionButton(
            onPressed: () {
              final snackbar = SnackBar(content: Text('Your alarm will fire in a moment'));
              Scaffold.of(context).showSnackBar(snackbar);
              _setupAlarm();
            },
            child: Icon(Icons.alarm),
          );
        },
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
