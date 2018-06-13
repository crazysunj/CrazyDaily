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
package com.crazysunj.crazydaily.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.di.component.DaggerServiceComponent;
import com.crazysunj.crazydaily.di.module.ServiceModule;
import com.crazysunj.crazydaily.presenter.DownloadPresenter;
import com.crazysunj.crazydaily.presenter.contract.DownloadContract;
import com.crazysunj.crazydaily.util.FileUtil;
import com.crazysunj.domain.bus.RxBus;
import com.crazysunj.domain.bus.event.DownloadEvent;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author: sunjian
 * created on: 2018/6/13 下午2:51
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class DownloadService extends IntentService implements DownloadContract.View {

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(ActivityConstant.URL, url);
        context.startService(intent);
    }

    @Inject
    protected DownloadPresenter mPresenter;

    public DownloadService() {
        super("DownloadService");
        RxBus.getDefault().toFlowable(DownloadEvent.class)
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(DownloadEvent downloadEvent) throws Exception {
                        Log.d("DownloadService", "total:" + downloadEvent.total + " loaded:" + downloadEvent.loaded);
                    }
                });
        onPrepare();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("DownloadService", "onHandleIntent:" + Thread.currentThread().getName());
        if (intent == null) {
            return;
        }
        final String url = intent.getStringExtra(ActivityConstant.URL);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mPresenter.download(url, new File(FileUtil.getDownloadFile(this), FileUtil.getFileNameWithUrl(this, url)));
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onSuccess(File saveFile) {
        Log.d("DownloadService", "onSuccess:" + saveFile.getAbsolutePath());
    }

    @Override
    public void onFailed(Throwable e) {
        Log.d("DownloadService", "onFailed:" + e.getMessage());
    }

    private void onPrepare() {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void initInject() {
        DaggerServiceComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .serviceModule(getServiceModule())
                .build()
                .inject(this);
    }

    public ServiceModule getServiceModule() {
        return new ServiceModule(this);
    }
}
