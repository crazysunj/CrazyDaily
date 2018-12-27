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
package com.crazysunj.crazydaily.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.bumptech.glide.request.target.ViewTarget;
import com.crazysunj.crazydaily.BuildConfig;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.di.component.AppComponent;
import com.crazysunj.crazydaily.di.component.DaggerAppComponent;
import com.crazysunj.crazydaily.di.module.AppModule;
import com.crazysunj.crazydaily.module.web.CrazyDailySonicRuntime;
import com.crazysunj.crazydaily.util.ScreenAdapterManager;
import com.crazysunj.crazydaily.util.ScreenUtil;
import com.crazysunj.crazydaily.weex.CrazyDailyModule;
import com.crazysunj.crazydaily.weex.LogModule;
import com.crazysunj.crazydaily.weex.WXCustomTextDomObject;
import com.crazysunj.crazydaily.weex.WXHttpAdapter;
import com.crazysunj.crazydaily.weex.WXImageAdapter;
import com.crazysunj.crazydaily.weex.WXRichTextComponent;
import com.crazysunj.crazydaily.weex.WXTabPagerComponent;
import com.crazysunj.crazydaily.weex.WXWebComponent;
import com.crazysunj.crazydaily.weex.WXWebViewModule;
import com.crazysunj.data.util.LoggerUtil;
import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.ui.component.WXBasicComponentType;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;

import java.util.HashSet;
import java.util.Set;

import androidx.multidex.MultiDex;
import io.flutter.view.FlutterMain;

/**
 * @author: sunjian
 * created on: 2017/9/10 上午11:47
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class App extends Application {

    private static App sInstance;
    public AppComponent mAppComponent;
    private Set<Activity> mActivities;

    public static synchronized App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent();
        registerActivityLifecycleCallbacks(new CrazyDailyActivityLifecycleCallbacks());
        initScreenAdapter();
        ViewTarget.setTagId(R.id.glide_tag);
        LoggerUtil.init(BuildConfig.DEBUG);
        initFlutter();
        initWeex();
        initX5WebView();
        initSonic();
        initPgyer();
        initLeakCanary();
    }

    /**
     * 初始化flutter
     */
    private void initFlutter() {
        FlutterMain.startInitialization(this);
    }

    /**
     * 初始化屏幕适配
     */
    private void initScreenAdapter() {
        // 设置适配的那台手机分辨率，宽度是dp转换，高度是pt转换
        int width = ScreenUtil.px2dp(this, 1080);
        int height = ScreenUtil.px2pt(this, 2160);
        ScreenAdapterManager.register(this, ScreenAdapterManager.ADAPTER_TYPE_WIDTH, width, height);
    }

    private void initPgyer() {
        PgyCrashManager.register();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void initSonic() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new CrazyDailySonicRuntime(this), new SonicConfig.Builder().build());
        }
    }

    /**
     * 初始化Weex
     */
    private void initWeex() {
        InitConfig config = new InitConfig.Builder()
                .setImgAdapter(new WXImageAdapter())
                .setHttpAdapter(new WXHttpAdapter(mAppComponent.provideOkhttpClient()))
                .build();
        WXSDKEngine.initialize(this, config);
        try {
            WXSDKEngine.registerComponent("tabPager", WXTabPagerComponent.class);
            WXSDKEngine.registerComponent("richText", WXRichTextComponent.class);
            WXSDKEngine.registerComponent(WXBasicComponentType.WEB, WXWebComponent.class);
            WXSDKEngine.registerDomObject("richText", WXCustomTextDomObject.class);
            WXSDKEngine.registerModule("crazyDaily", CrazyDailyModule.class);
            WXSDKEngine.registerModule("webview", WXWebViewModule.class, true);
            WXSDKEngine.registerModule("log", LogModule.class);
        } catch (WXException e) {
            LoggerUtil.d(e.getMessage());
        }
    }

    /**
     * 初始化x5内核
     */
    private void initX5WebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean isLoadSuccess) {
                LoggerUtil.i(LoggerUtil.MSG_WEB, String.format("X5内核加载%s", isLoadSuccess ? "成功" : "失败"));
            }

            @Override
            public void onCoreInitFinished() {
                LoggerUtil.i(LoggerUtil.MSG_WEB, "X5内核加载完成");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
        MultiDex.install(this);
    }

    public void addActivity(Activity activity) {
        if (mActivities == null) {
            mActivities = new HashSet<>();
        }
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (mActivities != null) {
            mActivities.remove(activity);
        }
    }

    /**
     * 退出app
     */
    public void exitApp() {
        if (mActivities != null) {
            synchronized (this) {
                for (Activity activity : mActivities) {
                    activity.finish();
                }
            }
        }
        ScreenAdapterManager.unregister(this);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(sInstance))
                    .build();
        }
        return mAppComponent;
    }
}
