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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    SwipeRefreshLayout mRefreshView;

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

    @SuppressLint("ClickableViewAccessibility")
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
                Log.e("CrazyDailyFlutter", "onListen---type:" + type);
            }

            @Override
            public void onCancel(Object arguments) {
                Log.e("CrazyDailyFlutter", "onCancel---type:" + type);
            }
        });

        FlutterRefreshCompleteEventPlugin.registerWith(pluginRegistry, type -> mRefreshView.setRefreshing(false));
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

    private static class FlutterRefreshCompleteEventPlugin implements MethodChannel.MethodCallHandler {

        static final String CHANNEL_NAME = "CrazyDaily/flutterRefreshComplete/Gankio";
        private static final String METHOD = "refreshComplete";
        private static final String TYPE = "type";

        private Callback mCallback;

        private FlutterRefreshCompleteEventPlugin(Callback callback) {
            mCallback = callback;
        }

        private static void registerWith(FlutterPluginRegistry pluginRegistry, Callback callback) {
            final MethodChannel channel = new MethodChannel(pluginRegistry.registrarFor(CHANNEL_NAME).messenger(), CHANNEL_NAME);
            channel.setMethodCallHandler(new FlutterRefreshCompleteEventPlugin(callback));
        }

        @Override
        public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
            if (METHOD.equals(methodCall.method)) {
                String type = methodCall.argument(TYPE);
                mCallback.complete(type);
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
        }
    }
}
