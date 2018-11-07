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

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: sunjian
 * created on: 2017/9/22 下午3:58
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class RxTransformerUtil {

    public static <T> FlowableTransformer<T, T> normalTransformer() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .onErrorResumeNext(e -> {  可能会卡顿，舍弃了
//                    Throwable throwable;
//                    if (NetworkUtils.isNetworkAvailable()) {
//                        throwable = e;
//                    } else {
//                        throwable = new ApiException(CodeConstant.CODE_NO_NET, e.getMessage());
//                    }
//                    return Flowable.error(throwable);
//                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
