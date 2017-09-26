package com.crazysunj.crazydaily.base;

import android.widget.Toast;

import com.crazysunj.crazydaily.app.App;
import com.crazysunj.data.util.LoggerUtil;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * author: sunjian
 * created on: 2017/9/10 上午11:53
 * description:统一封装处理
 */

public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    public void onError(Throwable e) {
        Toast.makeText(App.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
        LoggerUtil.w("HttpError", e);
    }

    @Override
    public void onComplete() {

    }
}
