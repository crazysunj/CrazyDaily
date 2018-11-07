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
package com.crazysunj.data.util;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: sunjian
 * created on: 2018/5/8 下午5:51
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NetworkUtils {

    private static final String DEFAULT_IP = "www.baidu.com";

    /**
     * 真正判断网络是否可用
     */
    public static boolean isNetworkAvailable() {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 100 " + DEFAULT_IP);
            int status = process.waitFor();
            return status == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Runtime在主线程执行会阻塞，最好采用异步
     * 若调用线程本身非主线程可直接调用isNetworkAvailable方法
     *
     * @param callback NetworkAvailableCallback
     */
    public static void isNetworkAvailable(NetworkAvailableCallback callback) {
        Single.just(DEFAULT_IP)
                .observeOn(Schedulers.io())
                .map(ip -> {
                    Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 -w 100 " + ip);
                    int status = process.waitFor();
                    return status == 0;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        this.disposable = disposable;
                    }

                    @Override
                    public void onSuccess(Boolean isAvailable) {
                        if (callback != null) {
                            callback.isAvailable(isAvailable);
                        }
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null) {
                            callback.isAvailable(false);
                        }
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }
                });
    }

    public interface NetworkAvailableCallback {
        /**
         * 是否可用
         *
         * @param isAvailable boolean
         */
        void isAvailable(boolean isAvailable);
    }
}
