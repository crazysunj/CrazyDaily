package com.crazysunj.crazydaily.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bumptech.glide.request.target.ViewTarget;
import com.crazysunj.crazydaily.BuildConfig;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.di.component.AppComponent;
import com.crazysunj.crazydaily.di.component.DaggerAppComponent;
import com.crazysunj.crazydaily.di.module.AppModule;
import com.crazysunj.data.util.LoggerUtil;

import java.util.HashSet;
import java.util.Set;

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
    }

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
                for (Activity activity : mActivities) {
                    activity.finish();
                }
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
