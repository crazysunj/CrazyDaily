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
package com.crazysunj.crazydaily.presenter;

import android.util.Log;

import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ServiceScope;
import com.crazysunj.crazydaily.presenter.contract.DownloadContract;
import com.crazysunj.domain.bus.RxBus;
import com.crazysunj.domain.bus.event.DownloadEvent;
import com.crazysunj.domain.interactor.download.DownloadUseCase;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author: sunjian
 * created on: 2017/9/19 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ServiceScope
public class DownloadPresenter implements DownloadContract.Presenter {

    private DownloadUseCase mDownloadUseCase;
    private DownloadContract.View mView;

    @Inject
    public DownloadPresenter(DownloadUseCase downloadUseCase) {
        mDownloadUseCase = downloadUseCase;
        RxBus.getDefault().toFlowable(DownloadEvent.class)
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(DownloadEvent downloadEvent) throws Exception {
                        Log.d("DownloadPresenter", "loaded:" + downloadEvent.loaded + "  total:" + downloadEvent.total);
                        final int progress = (int) (downloadEvent.loaded * 100f / downloadEvent.total + 0.5f);
                        mView.onProgress(progress);
                    }
                });
    }

    @Override
    public void download(String url, File saveFile) {
        mDownloadUseCase.execute(DownloadUseCase.Params.get(url, saveFile), new BaseSubscriber<File>() {
            @Override
            public void onNext(File file) {
                mView.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.onFailed(e);
            }

            @Override
            public void onComplete() {
                mView.onComplete();
            }
        });
    }

    @Override
    public void attachView(DownloadContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mDownloadUseCase.dispose();
    }
}
