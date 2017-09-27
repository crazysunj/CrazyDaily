/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.data.util;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * author: sunjian
 * created on: 2017/9/22 下午3:58
 * description:https://github.com/crazysunj/CrazyDaily
 */

public class LoggerUtil {

    /**
     * 初始化log工具，在app入口处调用
     *
     * @param isLogEnable 是否打印log
     */
    public static void init(boolean isLogEnable) {
        Logger.init("CrazyDailyHttp")
                .hideThreadInfo()
                .logLevel(isLogEnable ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(2);
    }

    public static void d(String message) {
        Logger.d(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void w(String message, Throwable e) {
        String info = e != null ? e.toString() : "null";
        Logger.w(message + "：" + info);
    }

    public static void e(String message, Throwable e) {
        Logger.e(e, message);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
