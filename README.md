# CrazyDaily
学习Android的开源项目，基于Material Design + MVP-Clean + Weex + Flutter +  RxJava2 + Retrofit + Dagger2 + Glide + OkHttp + MTRVA + 炫酷控件 + 炫酷动画 + greenDAO

## 效果图
### 首页
文章：[带你领略Clean架构的魅力](http://crazysunj.com/2017/09/25/%E5%B8%A6%E4%BD%A0%E9%A2%86%E7%95%A5Clean%E6%9E%B6%E6%9E%84%E7%9A%84%E9%AD%85%E5%8A%9B/)

![](https://github.com/crazysunj/crazysunj.github.io/blob/master/img/app_crazydaily.gif)

### Weex
文章：[一起来玩Weex](http://crazysunj.com/2018/03/16/%E4%B8%80%E8%B5%B7%E6%9D%A5%E7%8E%A9Weex/)

![](https://github.com/crazysunj/crazysunj.github.io/blob/master/img/demo_weex.gif)

### Flutter
文章：[一起来玩Flutter](http://crazysunj.com/2019/01/22/%E4%B8%80%E8%B5%B7%E6%9D%A5%E7%8E%A9Flutter/)

![](https://github.com/crazysunj/crazysunj.github.io/blob/master/img/ic_app_flutter.gif)

### 好友列表

![](https://github.com/crazysunj/crazysunj.github.io/blob/master/img/crazydaily_anim.gif)

### 妹子

![](https://github.com/crazysunj/crazysunj.github.io/blob/master/img/crazydaily_photo.gif)

### 笔记

![](https://github.com/crazysunj/crazysunj.github.io/blob/master/img/gif_note.gif)

## 简介
* 知乎日报
* 干货集中营
* 天气
* 搞笑视频
* 好友列表
* 妹子
* 笔记(仿微信朋友圈)

## 技术点
* 架构采用MVP-Clean，项目简洁、易维护、易测试、高内聚、低耦合
* UI风格采用Material Design，清新、简洁和直观
* 跨端采用Weex和Flutter，支持Kotlin，同时支持其扩展运用
* 支持今日头条适配方案
* Dagger2与Butterknife实现依赖注解，解耦，方便测试等
* Retrofit和Okhttp实现网络架构，实现多种扩展，例如常见的日志规范打印以及缓存配置等等
* MTRVA轻松处理RecyclerViewAdapter的数据，实现多列表效果更佳，可封装实现一个RecyclerView走天下
* 大量自定义控件，如Camera、Matrix、贝塞尔曲线、高斯模糊、Behavior、事件拦截、沉浸式、QQ侧滑菜单等等运用
* 大量动画，如lottie、补间动画、3D动画、属性动画（ObjectAnimator、ValueAnimator、View.animate和PropertyValuesHolder）和转场动画（自定义Transition）运用
* 视频列表滑动支持小窗口播放，本地保存\[自定义\]
* 支持图片预览(支持长图)、编辑(如裁剪、压缩)和保存本地\[自定义\]
* 支持图片和视频选择，交互仿微信，支持分页加载\[自定义\]
* 支持下载机制，通知栏回显(兼容8.0)，下载完成可打开\[自定义\]
* 支持扫码，扫码支持访问链接和下载，界面仿微信，支持本地图片扫码\[自定义\]
* 支持在线更新、在线反馈和Crash监控
* webview支持x5及常见用法，例如缓存、预加载、内存优化、增量更新、并行加载、下载等
* 兼容Android7.0，8.0，加入部分Jetpack
* 支持ConstraintLayout(约束布局，很好地解决层级问题，同时支持百分比)

尽量保持各种开源库最新状态，我踩坑，我骄傲！

尽量不使用第三方库，自己写！

当前版本采用AS3.3.1开发，完全采用AndroidX开发

有意见或者建议的同学可以联系我哦，持续更新，祝你生活愉快！

喜欢的朋友点个star关注一下我吧！

[下载地址](https://www.pgyer.com/EbHS "https://www.pgyer.com/EbHS")

想干的事（暂时先罗列这么多，版本迭代陆续实现，不分先后，可在develop/develop-x分支提前预览）：
* 加上引导页
* 添加微信精选，稀土掘金模块
* 添加音乐模块
* 添加阅读模块
* 添加分享
* 添加调试页面，如查看当前网络请求，webview也一样
* 使用AAC+kotlin重构项目(繁重，同时维护两套代码)

    ...

`[注]`本项目为开源项目，项目中涉及api若有侵犯产品权益，请告知，立刻删除。
## License

> ```
> Copyright 2017 Sun Jian
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```