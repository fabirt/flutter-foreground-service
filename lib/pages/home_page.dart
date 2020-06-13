import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const _platform =
      const MethodChannel('com.fabirt.flutter_foreground_service/channel');

  void _showToast() {
    _platform.invokeMethod(
      'showToast',
      <dynamic, dynamic>{'message': 'Hola desde Flutter'},
    );
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
          ],
        ),
      ),
    );
  }
}
