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
package com.crazysunj.crazydaily.base;

import android.widget.Toast;

import com.crazysunj.crazydaily.app.App;
import com.crazysunj.data.util.LoggerUtil;
import com.crazysunj.data.util.NetworkUtils;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author: sunjian
 * created on: 2017/9/10 上午11:53
 * description:统一封装处理
 * https://github.com/crazysunj/CrazyDaily
 */
public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    public void onError(Throwable e) {
        NetworkUtils.isNetworkAvailable(isAvailable -> Toast.makeText(App.getInstance(), isAvailable ? e.getMessage() : "请检测你的网络是否畅通", Toast.LENGTH_SHORT).show());
        LoggerUtil.e(LoggerUtil.MSG_HTTP, e);
    }

    @Override
    public void onComplete() {

    }
}
