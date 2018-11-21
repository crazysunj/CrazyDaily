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
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

/**
 * @author: sunjian
 * created on: 2018/11/21 上午11:13
 * description: 今日头条适配方案
 * https://github.com/crazysunj/CrazyDaily
 */
public class ScreenAdapterManager {

    /**
     * 适配类型，分为宽度适配、高度适配和同时适配宽高
     */
    public static final int ADAPTER_TYPE_WIDTH = 0;
    public static final int ADAPTER_TYPE_HEIGHT = 1;
    public static final int ADAPTER_TYPE_WIDTH_HEIGHT = 2;

    @IntDef({ADAPTER_TYPE_WIDTH, ADAPTER_TYPE_HEIGHT, ADAPTER_TYPE_WIDTH_HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AdapterType {
    }

    /**
     * 适配单位，分为dp和pt，主流为dp，dp主要用于宽度，pt用于高度，不统一用dp是避免宽度和高度同时适配时冲突
     */
    public static final int ADAPTER_UNIT_DP = 0;
    public static final int ADAPTER_UNIT_PT = 1;

    /**
     * 适配用的DisplayMetrics
     */
    private DisplayMetrics adapterMetrics;
    /**
     * activity生命周期监听
     */
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;

    private ScreenAdapterManager() {
    }

    private static class Holder {
        private static ScreenAdapterManager sManager = new ScreenAdapterManager();
    }

    public static ScreenAdapterManager get() {
        return Holder.sManager;
    }

    /**
     * 初始化 static
     *
     * @param application Application
     */
    public static void register(@NonNull Application application, @AdapterType int adapterType, float adapterWidthSize, float adapterHeightSize) {
        get().registerApp(application, adapterType, adapterWidthSize, adapterHeightSize);
    }

    /**
     * 初始化 static
     *
     * @param application Application
     */
    private void registerApp(@NonNull Application application, @AdapterType int adapterType, float adapterWidthSize, float adapterHeightSize) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (adapterMetrics == null) {
            adapterMetrics = new DisplayMetrics();
            adapterMetrics.setTo(displayMetrics);
        }
        // 系统设置修改后会回调
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    adapterMetrics.scaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {
            }
        });
        if (activityLifecycleCallbacks == null) {
            activityLifecycleCallbacks = new CrazyDailyActivityLifecycleCallbacks(adapterType, adapterWidthSize, adapterHeightSize);
        }
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    /**
     * 注销
     *
     * @param application Application
     */
    public static void unregister(@NonNull Application application) {
        get().unregisterApp(application);
    }

    private void unregisterApp(@NonNull Application application) {
        if (activityLifecycleCallbacks != null) {
            application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
            activityLifecycleCallbacks = null;
        }
        application.getResources().getDisplayMetrics().setTo(adapterMetrics);
    }

    /**
     * 用于宽度
     *
     * @param activity         Activity
     * @param adapterWidthSize float 基于适配的值，用于宽度
     */
    private void adapterWidth(@NonNull Activity activity, float adapterWidthSize) {
        final float targetDensity = adapterMetrics.widthPixels * 1f / adapterWidthSize;
        final int targetDensityDpi = (int) (targetDensity * 160);
        final float targetScaledDensity = targetDensity * (adapterMetrics.scaledDensity / adapterMetrics.density);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        displayMetrics.scaledDensity = targetScaledDensity;
    }

    /**
     * 用于高度
     *
     * @param activity          Activity
     * @param adapterHeightSize float 基于适配的值，用于高度
     */
    private void adapterHeight(@NonNull Activity activity, float adapterHeightSize) {
        final float targetXdpi = adapterMetrics.heightPixels * 72f / adapterHeightSize;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        displayMetrics.xdpi = targetXdpi;
    }

    /**
     * 用于同时适配宽度和高度
     *
     * @param activity          Activity
     * @param adapterWidthSize  float 基于适配的值，用于宽度
     * @param adapterHeightSize float 基于适配的值，用于高度
     */
    private void adapterBothWidthAndHeight(@NonNull Activity activity, float adapterWidthSize, float adapterHeightSize) {
        final float targetDensity = adapterMetrics.widthPixels * 1f / adapterWidthSize;
        final int targetDensityDpi = (int) (targetDensity * 160);
        final float targetScaledDensity = targetDensity * (adapterMetrics.scaledDensity / adapterMetrics.density);

        final float targetXdpi = adapterMetrics.heightPixels * 72f / adapterHeightSize;

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();

        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        displayMetrics.scaledDensity = targetScaledDensity;

        displayMetrics.xdpi = targetXdpi;
    }

    private class CrazyDailyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        int adapterType;
        float adapterWidthSize;
        float adapterHeightSize;

        private CrazyDailyActivityLifecycleCallbacks(int adapterType, float adapterWidthSize, float adapterHeightSize) {
            this.adapterType = adapterType;
            this.adapterWidthSize = adapterWidthSize;
            this.adapterHeightSize = adapterHeightSize;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (adapterType == ADAPTER_TYPE_WIDTH) {
                adapterWidth(activity, adapterWidthSize);
            } else if (adapterType == ADAPTER_TYPE_HEIGHT) {
                adapterHeight(activity, adapterHeightSize);
            } else if (adapterType == ADAPTER_TYPE_WIDTH_HEIGHT) {
                adapterBothWidthAndHeight(activity, adapterWidthSize, adapterHeightSize);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
