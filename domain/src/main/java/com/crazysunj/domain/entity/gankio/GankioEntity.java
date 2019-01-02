/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.domain.entity.gankio;


import com.crazysunj.domain.entity.base.MultiTypeIdEntity;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午6:38
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class GankioEntity {

    /**
     * error : false
     * results : [{"_id":"56cc6d22421aa95caa7078fe","createdAt":"2015-10-07T13:00:31.548Z","desc":"设置购物数量的view","publishedAt":"2015-10-08T04:29:48.0Z","type":"Android","url":"https://github.com/himanshu-soni/QuantityView","used":true,"who":"Jason"},{"_id":"58e59282421aa90d6f211e38","createdAt":"2017-04-06T08:57:38.454Z","desc":"敲酷炫的ViewPager切换效果和Q弹指示器","publishedAt":"2017-04-06T12:06:10.17Z","source":"web","type":"Android","url":"http://blog.csdn.net/qian520ao/article/details/68952079","used":true,"who":null},{"_id":"56cc6d26421aa95caa707fd0","createdAt":"2015-12-14T01:57:31.515Z","desc":"10个Android开源个性化View控件","publishedAt":"2015-12-14T04:19:59.746Z","type":"Android","url":"http://www.jianshu.com/p/cb705d582859#","used":true,"who":"有时放纵"},{"_id":"574bc8ea421aa910abe2bf67","createdAt":"2016-05-30T13:00:26.875Z","desc":"Android Binder 完全解析（一）概述","publishedAt":"2016-05-31T12:12:06.424Z","source":"chrome","type":"Android","url":"http://www.jianshu.com/p/c11333e77910","used":true,"who":"MVP"},{"_id":"56cc6d23421aa95caa7079e3","createdAt":"2015-06-17T06:42:06.319Z","desc":"快来打飞机！Android PlaneGame！","publishedAt":"2015-06-19T03:31:16.403Z","type":"Android","url":"https://github.com/nuptboyzhb/newplanegame","used":true,"who":"大城小黄"},{"_id":"56cc6d26421aa95caa707eeb","createdAt":"2015-11-18T08:22:23.214Z","desc":"Android快捷方式解密","publishedAt":"2015-11-25T03:57:07.85Z","type":"Android","url":"http://blog.csdn.net/eclipsexys/article/details/49868881","used":true,"who":"鲍永章"},{"_id":"570ef1686776590f57c4e0f8","createdAt":"2016-04-14T09:24:56.679Z","desc":"狂拽酷炫叼的翻转折叠布局效果","publishedAt":"2016-04-14T11:43:31.514Z","source":"web","type":"Android","url":"https://github.com/Ramotion/folding-cell-android","used":true,"who":"liuzheng"},{"_id":"56f9d0e667765933d9b0a982","createdAt":"2016-03-29T08:48:38.488Z","desc":"MVP+Retrofit+Material Design的Demo","publishedAt":"2016-03-29T11:56:01.215Z","source":"chrome","type":"Android","url":"https://github.com/Rtsunoath/Sunoath","used":true,"who":"MVP"},{"_id":"5785ba82421aa90dea11e9d7","createdAt":"2016-07-13T11:50:26.736Z","desc":"目前做的最漂亮的一款脉搏效果，真的超级好看！","images":["https://github.com/booncol/Pulsator4Droid/raw/master/demo.gif"],"publishedAt":"2016-07-13T12:10:33.380Z","source":"chrome","type":"Android","url":"https://github.com/booncol/Pulsator4Droid","used":true,"who":"代码家"},{"_id":"578c3ac3421aa90df33fe7be","createdAt":"2016-07-18T10:11:15.401Z","desc":"Reminder：辅助自定义 View 实现状态恢复","publishedAt":"2016-07-18T11:49:19.322Z","source":"chrome","type":"Android","url":"https://github.com/OneCodeLabs/reminder","used":true,"who":"代码家"},{"_id":"56cc6d23421aa95caa707bed","createdAt":"2015-08-11T14:32:44.40Z","desc":"用Android Studio 快捷键和活动模板提高开发效率","publishedAt":"2015-08-12T02:37:50.871Z","type":"Android","url":"http://android.jobbole.com/81169/","used":true,"who":"有时放纵"},{"_id":"56cc6d1d421aa95caa707796","createdAt":"2015-08-17T09:15:51.966Z","desc":"基于openCV开源库的车牌号识别，支持android，Linux，ios，mac，java","publishedAt":"2015-08-18T03:58:47.765Z","type":"Android","url":"https://github.com/liuruoze/EasyPR","used":true,"who":"有时放纵"},{"_id":"586f0566421aa9315bfbe827","createdAt":"2017-01-06T10:48:06.166Z","desc":"一键接入Tinker","publishedAt":"2017-01-06T13:20:19.591Z","source":"web","type":"Android","url":"http://w4lle.github.io/2017/01/05/one-key-for-tinker/","used":true,"who":"王令龙"},{"_id":"58080e33421aa91369f95950","createdAt":"2016-10-20T08:22:11.221Z","desc":"以组件的方式维护列表加载状态","images":["http://img.gank.io/fb48a6eb-7d39-4e9e-a04a-4af780e44e64"],"publishedAt":"2016-10-20T11:39:59.546Z","source":"chrome","type":"Android","url":"https://github.com/gitsindonesia/baso","used":true,"who":"gitsindonesia"},{"_id":"56cc6d1d421aa95caa70786d","createdAt":"2015-09-08T10:37:54.97Z","desc":"Android Hook 神器！！！","publishedAt":"2015-09-09T04:10:30.714Z","type":"Android","url":"https://github.com/rovo89/Xposed","used":true,"who":"andyiac"},{"_id":"56cc6d23421aa95caa707bd4","createdAt":"2015-08-11T16:51:07.520Z","desc":"google的官方应用测试模板demo","publishedAt":"2015-08-12T02:37:50.566Z","type":"Android","url":"https://github.com/googlesamples/android-testing-templates/tree/master/AndroidTestingBlueprint","used":true,"who":"有时放纵"},{"_id":"5962e177421aa90c9203d358","createdAt":"2017-07-10T10:07:51.286Z","desc":"懂得智能配色的ImageView,还能给自己设置多彩的阴影哦。","images":["http://img.gank.io/4cc10e1d-fb75-4c22-9df9-091706b55c82"],"publishedAt":"2017-07-10T12:48:49.297Z","source":"web","type":"Android","url":"https://github.com/DingMouRen/PaletteImageView-","used":true,"who":null},{"_id":"56eb558c67765933d9b0a8fb","createdAt":"2016-03-18T09:10:36.804Z","desc":" Http的会话跟踪和跨站攻击（xss）","publishedAt":"2016-03-24T12:21:54.835Z","source":"chrome","type":"Android","url":"http://blog.csdn.net/lpjishu/article/details/50917092","used":true,"who":"刘朋"},{"_id":"56cc6d23421aa95caa7079f3","createdAt":"2015-06-24T08:26:01.77Z","desc":"通过拖拽重排View位置的一个库","publishedAt":"2015-06-26T04:31:36.580Z","type":"Android","url":"https://github.com/rajasharan/RearrangeableLayout","used":true,"who":"mthli"},{"_id":"56cc6d22421aa95caa70791f","createdAt":"2015-10-13T02:31:50.271Z","desc":"灵活多样的Page Indicator","publishedAt":"2015-10-13T03:48:04.411Z","type":"Android","url":"https://github.com/H07000223/FlycoPageIndicator","used":true,"who":"鲍永章"}]
     */

    private boolean error;
    private List<ResultsEntity> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity extends MultiTypeIdEntity {

        public static final String PARAMS_ANDROID = "Android";
        public static final String PARAMS_IOS = "iOS";
        public static final String PARAMS_H5 = "前端";
        public static final String PARAMS_FULI = "福利";
        public static final String PARAMS_ALL_WEEX = "全部(weex版)";
        public static final String PARAMS_ALL_KOTLIN = "全部(kotlin版)";
        public static final String PARAMS_ALL_FLUTTER = "全部(flutter版)";

        public static final int TYPE_GANK_IO = 1;
        public static final String HEADER_TITLE = "Gankio";
        /**
         * _id : 56cc6d22421aa95caa7078fe
         * createdAt : 2015-10-07T13:00:31.548Z
         * desc : 设置购物数量的view
         * publishedAt : 2015-10-08T04:29:48.0Z
         * type : Android
         * url : https://github.com/himanshu-soni/QuantityView
         * used : true
         * who : Jason
         * source : web
         * images : ["https://github.com/booncol/Pulsator4Droid/raw/master/demo.gif"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private String source;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Override
        public String getStringId() {
            return _id;
        }

        @Override
        public int getItemType() {
            return TYPE_GANK_IO;
        }
    }
}
