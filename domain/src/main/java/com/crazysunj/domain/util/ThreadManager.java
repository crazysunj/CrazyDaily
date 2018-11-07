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
package com.crazysunj.domain.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: sunjian
 * created on: 2018/5/10 上午9:59
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ThreadManager {
    private static volatile ThreadManager sThreadManager;

    private ExecutorService mExecutor;

    private ThreadManager() {
    }

    public static ThreadManager get() {
        if (sThreadManager == null) {
            synchronized (ThreadManager.class) {
                if (sThreadManager == null) {
                    sThreadManager = new ThreadManager();
                }
            }
        }
        return sThreadManager;
    }

    public static ExecutorService single() {
        return get().singleInternal();
    }

    public static void shutdown() {
        get().shutdownInternal();
    }

    private ExecutorService singleInternal() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.isShutdown();
        return mExecutor;
    }

    private void shutdownInternal() {
        if (mExecutor != null && !mExecutor.isShutdown()) {
            mExecutor.shutdown();
        }
        mExecutor = null;
    }
}
