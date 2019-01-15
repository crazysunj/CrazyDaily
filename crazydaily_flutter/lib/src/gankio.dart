import 'dart:async';
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
  static const sRefreshEvent =
      const EventChannel('CrazyDaily/flutterRefresh/Gankio');
  /// 用于flutter的gankio页与原生通信
  static const sGankioEvent =
      const MethodChannel('CrazyDaily/flutterGankioEvent');
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
    if (mRefreshSubscription == null) {
      mRefreshSubscription =
          sRefreshEvent.receiveBroadcastStream().listen(onRefreshEvent);
    }
    mTabWidgetMap = Map.fromIterable(sGankioType,
        key: (type) => type,
        value: (type) => new GankioItemView(type, refreshComplete, scroller));
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
    await sGankioEvent.invokeMethod('refreshComplete', params);
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
    await sGankioEvent.invokeMethod('scroller', params);
  }

  @override
  void dispose() {
    super.dispose();
    if (mRefreshSubscription != null) {
      mRefreshSubscription.cancel();
    }
    if (mTabController != null) {
      mTabController.dispose();
    }
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

  void refresh() {
    state.getGankioList();
  }

  ScrollController getScrollController() {
    return state.getScrollController();
  }

  @override
  State<StatefulWidget> createState() {
    return state;
  }
}

class GankioItemState extends State<GankioItemView>
    with AutomaticKeepAliveClientMixin {
  final String type;
  List<ResultsEntity> mGankioList;

  var refreshComplete;
  var scroller;

  ScrollController controller;

  GankioItemState(this.type, this.refreshComplete, this.scroller);

  @override
  void initState() {
    super.initState();
    controller = new ScrollController();
    /// 监听滑动事件
    controller.addListener(() {
      var minScrollExtent = controller.position.minScrollExtent;
      var maxScrollExtent = controller.position.maxScrollExtent;
      var pixels = controller.position.pixels;
      scroller(type, pixels, minScrollExtent, maxScrollExtent);
    });
    getGankioList();
  }

  ScrollController getScrollController() {
    return controller;
  }

  /// 开始请求数据
  void getGankioList() async {
    final String url = "http://gank.io/api/random/data/$type/20";
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
      refreshComplete(type);
    });
  }

  @override
  Widget build(BuildContext context) {
    return new ListView.builder(
      controller: controller,
      padding: new EdgeInsets.fromLTRB(0, 5, 0, 5),
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

  /// 缓存页面，防止切换重新加载
  @override
  bool get wantKeepAlive => true;
}
