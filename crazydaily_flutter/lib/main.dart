import 'dart:ui';
import 'src/gankio.dart';
import 'package:flutter/material.dart';

void main() => runApp(crazyDailyWidgetRouter(window.defaultRouteName));

Widget crazyDailyWidgetRouter(String url) {
  switch (url) {
    case 'CrazyDailyGankioFlutter':
      return GankioFragment();
    default:
      return Center(
        child: Text('Unknown route: $url', textDirection: TextDirection.ltr),
      );
  }
}
