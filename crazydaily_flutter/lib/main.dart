import 'dart:ui';
import 'package:flutter/material.dart';

void main() => runApp(crazyDailyWidgetRouter(window.defaultRouteName));

Widget crazyDailyWidgetRouter(String url) {
  switch (url) {
    case 'CrazyDailyFlutter':
      return MyFlutterView();
    default:
      return Center(
        child: Text('Unknown route: $url', textDirection: TextDirection.ltr),
      );
  }
}

class MyFlutterView extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  @override
  HomePageState createState() {
    return new HomePageState();
  }
}

class HomePageState extends State<HomePage> {
  var counter = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Card(
        color: Colors.tealAccent,
        child: Center(
          child: Text(
            "My Flutter View: $counter",
            style: TextStyle(color: Colors.white, fontSize: 25.0),
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.limeAccent,
        onPressed: () {
          setState(() {
            counter++;
          });
        },
        child: Icon(
          Icons.add,
          color: Colors.black,
        ),
      ),
    );
  }
}
