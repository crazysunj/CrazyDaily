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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.pgyersdk.crash.PgyerCrashObservable;

/**
 * @author: sunjian
 * created on: 2019/2/15 上午9:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailyCrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    private CrazyDailyCrashHandler(Context context) {
        this.context = context;
    }

    private static volatile CrazyDailyCrashHandler sCrashHandler;

    public static CrazyDailyCrashHandler newInstance(Context context) {
        if (sCrashHandler == null) {
            synchronized (CrazyDailyCrashHandler.class) {
                if (sCrashHandler == null) {
                    sCrashHandler = new CrazyDailyCrashHandler(context);
                }
            }
        }
        return sCrashHandler;
    }

    static void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(newInstance(context));
    }

    /**
     * crash退出程序，0.5s后重启
     * 如果在系统onCreate前写的代码产生crash，需要特殊处理
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        PgyerCrashObservable.get().notifyObservers(t, e);
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent restartIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 500, restartIntent);
        }
        App.getInstance().abortedApp();
    }
}
