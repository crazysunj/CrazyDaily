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
package com.crazysunj.crazydaily.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.crazysunj.crazydaily.BuildConfig;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author: sunjian
 * created on: 2018/5/11 上午10:26
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class DeviceUtil {
    private DeviceUtil() {
    }

    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获得状态栏高度
     *
     * @param context Context
     * @return int
     */
    public static int getStatusBarHeight(Context context) {
        final Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    /**
     * 判断全面屏
     *
     * @param activity Activity
     * @return boolean
     */
    public static boolean isFullScreenDisplay(@NonNull Activity activity) {
        if (isSupportP()) {
            // 谷歌官方提供
            Window window = activity.getWindow();
            if (window != null) {
                WindowInsets windowInsets = window.getDecorView().getRootWindowInsets();
                if (windowInsets != null) {
                    DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                    if (displayCutout != null) {
                        List<Rect> rects = displayCutout.getBoundingRects();
                        // 一般为刘海屏即为全面屏
                        if (rects != null && rects.size() > 0) {
                            return true;
                        }
                    }
                }
            }
        }

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display defaultDisplay = wm.getDefaultDisplay();
            if (defaultDisplay != null) {
                DisplayMetrics dm = new DisplayMetrics();
                defaultDisplay.getRealMetrics(dm);
                float heightPixels = dm.heightPixels;
                float widthPixels = dm.widthPixels;
                // 通过分比率比例去判断
                return heightPixels / widthPixels >= 1.96f;
            }
        }
        return false;
    }

    /**
     * 是否支持Android9.0
     *
     * @return boolean
     */
    public static boolean isSupportP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 判断是否含有虚拟按键，此方法可能只适用于小米
     *
     * @param contextWrapper ContextWrapper
     * @return boolean
     */
    public static boolean isNoVirtualNavigation(@NonNull ContextWrapper contextWrapper) {
        return Settings.Global.getInt(contextWrapper.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }
}
