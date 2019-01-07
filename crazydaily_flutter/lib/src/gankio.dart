import 'package:flutter/material.dart';

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
        color: Colors.deepPurpleAccent,
        child: Center(
          child: Text(
            "My Flutter View: $counter",
            style: TextStyle(color: Colors.white, fontSize: 25.0),
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.pinkAccent,
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

class TabbedAppBarSample extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      theme: new ThemeData(bottomAppBarColor: Colors.white),
      home: new DefaultTabController(
        length: mGankioTitle.length,
        child: new Scaffold(
//          appBar: new AppBar(
//            bottom: new TabBar(
//              isScrollable: true,
//              tabs: choices.map((Choice choice) {
//                return new Tab(
//                  text: choice.title,
//                  icon: new Icon(choice.icon),
//                );
//              }).toList(),
//            ),
//          ),
        backgroundColor: Colors.white,
          appBar: new TabBar(
            unselectedLabelColor: Color(0xFF999999),
            labelColor: Color(0xFF000000),
            indicatorColor: Color(0xFFFF4081),
            tabs: mGankioTitle.map((String title) {
              return new Tab(
                text: title,
              );
            }).toList(),
          ),
          body: new TabBarView(
            children: mGankioTitle.map((String title) {
              return new ChoiceCard(choice: title);
            }).toList(),
          ),
        ),
      ),
    );
  }
}

const List<String> mGankioTitle = const <String>["Android", "iOS", "前端"];

class ChoiceCard extends StatelessWidget {
  const ChoiceCard({Key key, this.choice}) : super(key: key);

  final String choice;

  @override
  Widget build(BuildContext context) {
    final TextStyle textStyle = Theme.of(context).textTheme.display1;
    return new Card(
      margin: EdgeInsets.all(0),
      color: Colors.red,
      child: new Center(
        child: new Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            new Text(choice, style: textStyle),
          ],
        ),
      ),
    );
  }
}
