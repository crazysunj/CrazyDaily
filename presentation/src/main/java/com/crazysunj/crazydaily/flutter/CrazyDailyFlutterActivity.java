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
package com.crazysunj.crazydaily.flutter;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;

import butterknife.BindView;
import io.flutter.app.FlutterPluginRegistry;
import io.flutter.facade.Flutter;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.FlutterRefreshEventPlugin;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

/**
 * @author: sunjian
 * created on: 2018/12/12 上午11:14
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailyFlutterActivity extends BaseActivity {

    @BindView(R.id.flutter_refresh)
    FlutterGankioRefreshLayout mRefreshView;

    @BindView(R.id.flutter_container)
    FrameLayout mContainer;

    private String type;
    private EventChannel.EventSink mEventSink;

    public static void start(Context context) {
        Intent intent = new Intent(context, CrazyDailyFlutterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_flutter;
    }

    @Override
    protected void initView() {
        FlutterView gankioFlutterView = Flutter.createView(this, getLifecycle(), "CrazyDailyGankioFlutter");
        FlutterPluginRegistry pluginRegistry = gankioFlutterView.getPluginRegistry();
        GeneratedPluginRegistrant.registerWith(pluginRegistry);
        FlutterRefreshEventPlugin.registerWith(pluginRegistry, "Gankio", new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink eventSink) {
                type = arguments == null ? "Android" : arguments.toString();
                mEventSink = eventSink;
            }

            @Override
            public void onCancel(Object arguments) {
            }
        });

        FlutterGankioMethodPlugin.registerWith(pluginRegistry, new FlutterGankioMethodPlugin.Callback() {
            @Override
            public void complete(String type) {
                mRefreshView.setRefreshing(false);
            }

            @Override
            public void scroller(String type, double pixels, double minScrollExtent, double maxScrollExtent) {
                mRefreshView.setIntercept(pixels <= minScrollExtent);
            }
        });
        mContainer.addView(gankioFlutterView);
    }

    @Override
    protected void initListener() {
        mRefreshView.setOnRefreshListener(() -> {
            if (mEventSink != null) {
                mEventSink.success(type);
            }
        });
    }

    private static class FlutterGankioMethodPlugin implements MethodChannel.MethodCallHandler {

        static final String CHANNEL_NAME = "CrazyDaily/flutterGankioEvent";
        private static final String METHOD_REFRESH_COMPLETE = "refreshComplete";
        private static final String METHOD_SCROLLER = "scroller";
        private static final String TYPE = "type";
        /**
         * 最小滑动距离
         */
        private static final String MIN_SCROLL_EXTENT = "minScrollExtent";
        /**
         * 最大滑动距离
         */
        private static final String MAX_SCROLL_EXTENT = "maxScrollExtent";
        /**
         * 滑动距离
         */
        private static final String PIXELS = "pixels";

        private Callback mCallback;

        private FlutterGankioMethodPlugin(Callback callback) {
            mCallback = callback;
        }

        private static void registerWith(FlutterPluginRegistry pluginRegistry, Callback callback) {
            final MethodChannel channel = new MethodChannel(pluginRegistry.registrarFor(CHANNEL_NAME).messenger(), CHANNEL_NAME);
            channel.setMethodCallHandler(new FlutterGankioMethodPlugin(callback));
        }

        @Override
        public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
            if (METHOD_REFRESH_COMPLETE.equals(methodCall.method)) {
                String type = methodCall.argument(TYPE);
                mCallback.complete(type);
                return;
            }
            if (METHOD_SCROLLER.equals(methodCall.method)) {
                String type = methodCall.argument(TYPE);
                Double minScrollExtent = methodCall.argument(MIN_SCROLL_EXTENT);
                Double maxScrollExtent = methodCall.argument(MAX_SCROLL_EXTENT);
                Double pixels = methodCall.argument(PIXELS);
                if (minScrollExtent == null || maxScrollExtent == null || pixels == null) {
                    result.error("minScrollExtent,maxScrollExtent,pixels不能为空", null, null);
                    return;
                }
                mCallback.scroller(type, pixels, minScrollExtent, maxScrollExtent);
                return;
            }
            result.notImplemented();
        }

        private interface Callback {
            /**
             * 刷新完成回调
             *
             * @param type String
             */
            void complete(String type);

            /**
             * listview滚动距离回调
             *
             * @param type            String
             * @param pixels          滚动距离
             * @param minScrollExtent 滚动最小距离
             * @param maxScrollExtent 滚动最大距离
             */
            void scroller(String type, double pixels, double minScrollExtent, double maxScrollExtent);
        }
    }
}
