import 'package:fluttertoast/fluttertoast.dart';
import 'package:crazydaily_flutter/src/gankio_entity.dart';
import 'package:crazydaily_flutter/src/plugin/flutterrouter.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class TabbedAppBarSample extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      debugShowCheckedModeBanner: false,
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
              return new Padding(
                padding: new EdgeInsets.fromLTRB(0, 5, 0, 5),
                child: new GankioItemView(type),
              );
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
    super.initState();
    getGankioList();
  }

  getGankioList() async {
    final String url = "http://gank.io/api/random/data/$type/10";
    List<ResultsEntity> list;
    try {
      var dio = new Dio();
      Response<Map<String, dynamic>> response = await dio.get(url);
      list = GankioEntity.fromJson(response.data).results;
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
                padding: new EdgeInsets.fromLTRB(10, 5, 10, 5),
                child: new Card(
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(8.0))),
                  child: new Padding(
                    padding: new EdgeInsets.fromLTRB(20, 10, 20, 10),
                    child: new Row(
                      children: <Widget>[
                        Flexible(
                          child: new Align(
                            alignment: Alignment.centerLeft,
                            child: new Column(
                              children: <Widget>[
                                Align(
                                  alignment: Alignment.centerLeft,
                                  child: new Text(
                                    "${mGankioList[index].desc ?? "神秘标题"}",
                                    maxLines: 1,
                                    overflow: TextOverflow.ellipsis,
                                    style: TextStyle(
                                      color: Color(0xFF333333),
                                      fontSize: 16,
                                    ),
                                  ),
                                ),
                                Padding(
                                  padding: new EdgeInsets.fromLTRB(0, 6, 0, 0),
                                  child: new Align(
                                    alignment: Alignment.centerLeft,
                                    child: new Text(
                                        "作者：${mGankioList[index].who ?? "神秘大佬"}",
                                        maxLines: 1,
                                        overflow: TextOverflow.ellipsis,
                                        style: TextStyle(
                                          color: Color(0xFF666666),
                                          fontSize: 14,
                                        )),
                                  ),
                                ),
                                Padding(
                                  padding: new EdgeInsets.fromLTRB(0, 12, 0, 0),
                                  child: new Align(
                                    alignment: Alignment.centerLeft,
                                    child: new Text(
                                        "发布时间：${mGankioList[index].publishedAt ?? "未知"}",
                                        maxLines: 1,
                                        overflow: TextOverflow.ellipsis,
                                        style: TextStyle(
                                          color: Color(0xFFCCCCCC),
                                          fontSize: 12,
                                        )),
                                  ),
                                )
                              ],
                            ),
                          ),
                        ),
                        Container(
                          width: 20,
                          alignment: Alignment.centerRight,
                          child: new Image.asset(
                            "images/ic_go.png",
                            width: 12,
                            height: 12,
                          ),
                        )
                      ],
                    ),
                  ),
                )),
            onTap: () {
              FlutterRouter.openUrl(
                  "crazydaily://crazysunj.com/browser?url=${Uri.encodeComponent(mGankioList[index].url)}");
            },
          ),
      itemCount: mGankioList == null ? 0 : mGankioList.length,
    );
  }
}
