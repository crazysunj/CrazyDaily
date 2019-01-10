import 'dart:async';
import 'package:flutter/services.dart';

class FlutterRouter {
  static const MethodChannel _channel =
      const MethodChannel('CrazyDaily/flutterRouter');

  static Future<String> openUrl(String url) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'url': url,
    };

    String res = await _channel.invokeMethod('openUrl', params);
    return res;
  }
}
