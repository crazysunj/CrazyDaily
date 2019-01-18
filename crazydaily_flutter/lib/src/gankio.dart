import 'dart:async';
import 'dart:convert';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:crazydaily_flutter/src/gankio_entity.dart';
import 'package:crazydaily_flutter/src/plugin/flutterrouter.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class GankioFragment extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new GankioFragmentState();
  }
}

class GankioFragmentState extends State<GankioFragment>
    with SingleTickerProviderStateMixin {
  static const List<String> sGankioType = const <String>[
    "Android",
    "iOS",
    "前端"
  ];

  /// 用于通知flutter开始刷新
  final mRefreshEvent = const EventChannel('CrazyDaily/flutterRefresh/Gankio');

  /// 用于flutter的gankio页与原生通信
  final mGankioMethod = const MethodChannel('CrazyDaily/flutterGankioMethod');

  /// 用于flutter与原生通信
  final mGankioMessage = const BasicMessageChannel<Object>(
      'CrazyDaily/flutterGankioMessage', StandardMessageCodec());
  TabController mTabController;
  StreamSubscription mRefreshSubscription;
  Map<String, GankioItemView> mTabWidgetMap;

  @override
  void initState() {
    super.initState();
    mTabController = TabController(vsync: this, length: sGankioType.length);
    mTabController.addListener(() {
      final int index = mTabController.index;
      final String type = sGankioType[index];
      final ScrollPosition scrollPosition =
          mTabWidgetMap[type].getScrollController().position;
      // 处理每次切换后的滑动的位置
      scroller(type, scrollPosition.pixels, scrollPosition.minScrollExtent,
          scrollPosition.maxScrollExtent);
    });
    mRefreshSubscription ??
        mRefreshEvent.receiveBroadcastStream("refresh").listen(onRefreshEvent);
    mTabWidgetMap = Map.fromIterable(sGankioType,
        key: (type) => type,
        value: (type) => new GankioItemView(type, refreshComplete, scroller));
    mGankioMessage.setMessageHandler((message) async {
      if (message == null) {
        return null;
      }
      Map<String, dynamic> messageMap = json.decode(message);
      if (messageMap["type"] == "refresh") {
        final type =
            await mTabWidgetMap[sGankioType[mTabController.index]].refresh();
        final Map<String, dynamic> params = <String, dynamic>{'type': type};
        final Map<String, dynamic> messageParams = <String, dynamic>{
          'type': "refreshComplete",
          "params": params
        };
        return json.encode(messageParams);
      }
      return null;
    });
  }

  /// 通知子组件刷新
  void onRefreshEvent(Object event) {
    setState(() {
      mTabWidgetMap[sGankioType[mTabController.index]].refresh();
    });
  }

  /// 通知原生刷新完成
  void refreshComplete(String type) async {
    final Map<String, dynamic> params = <String, dynamic>{'type': type};
    await mGankioMethod.invokeMethod('refreshComplete', params);
  }

  /// 回调原生滑动进度
  void scroller(String type, double pixels, double minScrollExtent,
      double maxScrollExtent) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'type': type,
      'pixels': pixels,
      'minScrollExtent': minScrollExtent,
      'maxScrollExtent': maxScrollExtent,
    };
    await mGankioMethod.invokeMethod('scroller', params);
  }

  @override
  void dispose() {
    super.dispose();
    mRefreshSubscription?.cancel();
    mTabController?.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      debugShowCheckedModeBanner: false,
      home: new Scaffold(
        backgroundColor: Colors.white,
        appBar: new TabBar(
          controller: mTabController,
          unselectedLabelColor: Color(0xFF999999),
          labelColor: Color(0xFF333333),
          indicatorColor: Color(0xFFFF4081),
          tabs: sGankioType.map((String type) {
            return new Tab(
              text: type,
            );
          }).toList(),
        ),
        body: new TabBarView(
          controller: mTabController,
          children: sGankioType.map((String type) {
            return mTabWidgetMap[type];
          }).toList(),
        ),
      ),
    );
  }
}

class GankioItemView extends StatefulWidget {
  final GankioItemState state;

  GankioItemView(
      String type,
      void Function(String type) refreshComplete,
      void Function(String type, double pixels, double minScrollExtent,
              double maxScrollExtent)
          scroller)
      : state = new GankioItemState(type, refreshComplete, scroller);

  Future<String> refresh() => state.getGankioList();

  ScrollController getScrollController() => state.getScrollController();

  @override
  State<StatefulWidget> createState() {
    return state;
  }
}

class GankioItemState extends State<GankioItemView>
    with AutomaticKeepAliveClientMixin {
  final String type;
  List<ResultsEntity> mGankioList;

  final refreshComplete;
  final scroller;

  ScrollController mScrollController;

  GankioItemState(this.type, this.refreshComplete, this.scroller);

  @override
  void initState() {
    super.initState();
    mScrollController = new ScrollController();

    /// 监听滑动事件
    mScrollController.addListener(() {
      final minScrollExtent = mScrollController.position.minScrollExtent;
      final maxScrollExtent = mScrollController.position.maxScrollExtent;
      final pixels = mScrollController.position.pixels;
      scroller(type, pixels, minScrollExtent, maxScrollExtent);
    });
    getGankioList();
  }

  ScrollController getScrollController() => mScrollController;

  /// 开始请求数据
  Future<String> getGankioList() async {
    final String url = "http://gank.io/api/random/data/$type/20";
    List<ResultsEntity> list;
    try {
      final dio = new Dio();
      Response<Map<String, dynamic>> response = await dio.get(url);
      list = GankioEntity.fromJson(response.data).results;
    } catch (e) {
      Fluttertoast.showToast(msg: e.toString());
    }

    setState(() {
//      refreshComplete(type);
      mGankioList = list;
    });
    return type;
  }

  @override
  Widget build(BuildContext context) {
    return new ListView.builder(
      controller: mScrollController,
      padding: new EdgeInsets.fromLTRB(0, 5, 0, 5),
      itemBuilder: (BuildContext context, int index) => new GestureDetector(
            key: new Key(mGankioList[index].id),
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

  /// 缓存页面，防止切换重新加载
  @override
  bool get wantKeepAlive => true;
}
