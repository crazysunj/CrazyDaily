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
import android.os.Bundle;
import android.util.Log;

import com.crazysunj.crazydaily.ui.MainActivity;

/**
 * @author: sunjian
 * created on: 2018/12/27 下午3:20
 * description: https://github.com/crazysunj/CrazyDaily
 */
class CrazyDailyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "AppLifecycleCallbacks";
    /**
     * 前台Activity的数量
     */
    private int mForegroundActivityCount = 0;
    /**
     * Activity的configChanges变动需要排除
     */
    private int mActivityConfigChangesCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        App.getInstance().addActivity(activity);
        if (App.sAppState == -1 && !(activity instanceof MainActivity)) {
            // 进程被杀了
            restartApp(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (mForegroundActivityCount <= 0) {
            // 后台到前台
            Log.e(TAG, "后台到前台");
        }
        if (mActivityConfigChangesCount < 0) {
            mActivityConfigChangesCount++;
        } else {
            mForegroundActivityCount++;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            mActivityConfigChangesCount--;
        } else {
            mForegroundActivityCount--;
            if (mForegroundActivityCount <= 0) {
                // 前台到后台
                Log.e(TAG, "前台到后台");
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        App.getInstance().removeActivity(activity);
    }

    private void restartApp(Activity activity) {
        MainActivity.restart(activity);
        activity.finish();
    }
}
