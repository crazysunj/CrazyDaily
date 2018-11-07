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
package com.crazysunj.data.util;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import androidx.annotation.Nullable;

/**
 * @author: sunjian
 * created on: 2017/9/22 下午3:58
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class LoggerUtil {

    public static final String TAG = "CrazyDailyLog";
    public static final String MSG_HTTP = "HttpLog";
    public static final String MSG_WEB = "WebLog";
    public static final String MSG_IMG = "ImageLog";
    public static final String MSG_JSON = "JsonLog";

    /**
     * 初始化log工具，在app入口处调用
     *
     * @param isLogEnable 是否打印log
     */
    public static void init(boolean isLogEnable) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isLogEnable;
            }
        });
    }

    public static void d(String message) {
        Logger.d(message);
    }

    public static void d(String tag, String message) {
        Logger.d(String.format("[%s]%s", tag, message));
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void i(String tag, String message) {
        Logger.i(String.format("[%s]%s", tag, message));
    }

    public static void e(String tag, String message) {
        Logger.e(String.format("[%s]%s", tag, message));
    }

    public static void e(String tag, Throwable e) {
        Logger.e(String.format("[%s]%s", tag, e.getMessage()));
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
