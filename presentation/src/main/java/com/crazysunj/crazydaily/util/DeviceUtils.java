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
package com.crazysunj.crazydaily.util;

import android.content.Context;
import android.content.res.Resources;

import com.crazysunj.crazydaily.BuildConfig;

/**
 * author: sunjian
 * created on: 2018/5/11 上午10:26
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class DeviceUtils {
    private DeviceUtils() {
    }

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        final Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }
}
