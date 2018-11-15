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

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

/**
 * @author: sunjian
 * created on: 2018/8/8 下午4:44
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class HandlerHelper {
    private HandlerHelper() {
    }

    public static Handler get() {
        return Holder.sHandler;
    }

    private static class Holder {
        private static Handler sHandler = new Handler(Looper.getMainLooper());
    }

    public static void post(Runnable r) {
        Holder.sHandler.post(r);
    }

    public static void post(Runnable r, long delayMillis) {
        Holder.sHandler.postDelayed(r, delayMillis);
    }

    /**
     * 一般用于主线程消息执行完成之后，初始化一些耗时的数据
     * 默认用的是主线程MessageQueue
     *
     * @param handler MessageQueue.IdleHandler
     */
    public static void addIdleHandler(MessageQueue.IdleHandler handler) {
        Looper.myQueue().addIdleHandler(handler);
    }

    /**
     * 成对出现，记得remove
     *
     * @param handler MessageQueue.IdleHandler
     */
    public static void removeIdleHandler(MessageQueue.IdleHandler handler) {
        Looper.myQueue().removeIdleHandler(handler);
    }
}
