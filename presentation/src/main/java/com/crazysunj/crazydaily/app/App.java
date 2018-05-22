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
package com.crazysunj.crazydaily.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bumptech.glide.request.target.ViewTarget;
import com.crazysunj.crazydaily.BuildConfig;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.di.component.AppComponent;
import com.crazysunj.crazydaily.di.component.DaggerAppComponent;
import com.crazysunj.crazydaily.di.module.AppModule;
import com.crazysunj.crazydaily.weex.RichTextView;
import com.crazysunj.crazydaily.weex.RouterModule;
import com.crazysunj.crazydaily.weex.WXHttpAdapter;
import com.crazysunj.crazydaily.weex.WXImageAdapter;
import com.crazysunj.crazydaily.weex.WXTabPagerComponent;
import com.crazysunj.data.util.LoggerUtil;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashSet;
import java.util.Set;

/**
 * author: sunjian
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
        ViewTarget.setTagId(R.id.glide_tag);
        LoggerUtil.init(BuildConfig.DEBUG);
        initWeex();
        initX5WebView();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
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
            WXSDKEngine.registerComponent("richText", RichTextView.class);
            WXSDKEngine.registerModule("RouterModule", RouterModule.class);
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
            mActivities = new HashSet<Activity>();
        }
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (mActivities != null) {
            mActivities.remove(activity);
        }
    }

    public void exitApp() {
        if (mActivities != null) {
            synchronized (this) {
                mActivities.forEach(Activity::finish);
            }
        }
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
