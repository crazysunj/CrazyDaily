import 'package:fluttertoast/fluttertoast.dart';
import 'package:crazydaily_flutter/src/gankio_entity.dart';
import 'package:dio/dio.dart';
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
      home: new DefaultTabController(
        length: mGankioType.length,
        child: new Scaffold(
          backgroundColor: Colors.white,
          appBar: new TabBar(
            unselectedLabelColor: Color(0xFF999999),
            labelColor: Color(0xFF333333),
            indicatorColor: Color(0xFFFF4081),
            tabs: mGankioType.map((String type) {
              return new Tab(
                text: type,
              );
            }).toList(),
          ),
          body: new TabBarView(
            children: mGankioType.map((String type) {
              return new GankioItemView(type);
            }).toList(),
          ),
        ),
      ),
    );
  }
}

const List<String> mGankioType = const <String>["Android", "iOS", "前端"];

class GankioItemView extends StatefulWidget {
  final String type;

  GankioItemView(this.type);

  @override
  State<StatefulWidget> createState() {
    return new GankioItemState(type);
  }
}

class GankioItemState extends State<GankioItemView> {
  final String type;
  List<ResultsEntity> mGankioList;

  GankioItemState(this.type);

  @override
  void initState() {
    getGankioList();
  }

  getGankioList() async {
    final String url = "http://gank.io/api/random/data/$type/10";
    List<ResultsEntity> list;
    try {
      var dio = new Dio();
      Response<Map<String, dynamic>> response = await dio.get(url);
      list = GankioEntity.fromJson(response.data).results;
      Fluttertoast.showToast(msg: list.toString());
    } catch (e) {
      Fluttertoast.showToast(msg: e.toString());
    }

    setState(() {
      mGankioList = list;
    });
  }

  @override
  Widget build(BuildContext context) {
    return new ListView.builder(
      itemBuilder: (BuildContext context, int index) => new GestureDetector(
            child: new Padding(
                padding: new EdgeInsets.all(10.0),
                child: new Card(
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(8.0))),
                  child: new Padding(
                    padding: new EdgeInsets.fromLTRB(20, 10, 20, 10),
                    child: new Row(
                      children: <Widget>[
                        Text("123"),
                        Container(
                          width: 20,
                          alignment: Alignment.center,
                          child: new Image.asset("images/ic_go.png"),
                        )
                      ],
                    ),
                  ),
                )),
            onTap: () {
              Fluttertoast.showToast(msg: "index:$index");
            },
          ),
      itemCount: mGankioList == null ? 0 : mGankioList.length,
    );
  }
}

class ChoiceCard extends StatelessWidget {
  const ChoiceCard({Key key, this.choice}) : super(key: key);

  final String choice;

  @override
  Widget build(BuildContext context) {
    final TextStyle textStyle = Theme.of(context).textTheme.display1;
    return new Card(
      margin: EdgeInsets.all(0),
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
