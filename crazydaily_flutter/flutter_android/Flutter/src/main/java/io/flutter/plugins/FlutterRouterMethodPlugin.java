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
package io.flutter.plugins;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.lang.ref.SoftReference;
import java.util.Set;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

/**
 * @author: sunjian
 * created on: 2019/1/9 下午12:32
 * description: https://github.com/crazysunj/CrazyDaily
 * flutter路由module
 */
public class FlutterRouterMethodPlugin implements MethodChannel.MethodCallHandler {

    static final String CHANNEL_NAME = "CrazyDaily/flutterRouter";
    private static final String METHOD = "openUrl";
    private static final String URL = "url";
    private SoftReference<Activity> activity;

    private FlutterRouterMethodPlugin(Activity activity) {
        this.activity = new SoftReference<>(activity);
    }

    static void registerWith(PluginRegistry.Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL_NAME);
        channel.setMethodCallHandler(new FlutterRouterMethodPlugin(registrar.activity()));
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        if (METHOD.equals(methodCall.method)) {
            String url = methodCall.argument(URL);
            if (TextUtils.isEmpty(url)) {
                result.error("url不能为空", null, null);
                return;
            }
            try {
                Uri uri = Uri.parse(url);
                Set<String> parameterNames = uri.getQueryParameterNames();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                for (String parameterName : parameterNames) {
                    String queryParameter = uri.getQueryParameter(parameterName);
                    String decode = Uri.decode(queryParameter);
                    intent.putExtra(parameterName, decode);
                }
                Activity activity = this.activity.get();
                if (activity == null) {
                    result.error("请重新打开页面", null, null);
                    return;
                }
                activity.startActivity(intent);
            } catch (Exception e) {
                result.error(e.getMessage(), null, null);
            }
            return;
        }
        result.notImplemented();
    }
}
