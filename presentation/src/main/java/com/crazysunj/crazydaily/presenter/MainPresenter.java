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
package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.MainContract;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.interactor.gankio.GankioUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * @author: sunjian
 * created on: 2018/11/7 下午8:10
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private GankioUseCase mGankioUseCase;

    @Inject
    MainPresenter(GankioUseCase gankioUseCase) {
        mGankioUseCase = gankioUseCase;
    }

    @Override
    public void getMeinvList() {
        mGankioUseCase.execute(GankioUseCase.Params.get(GankioEntity.ResultsEntity.PARAMS_FULI, 10), new BaseSubscriber<List<GankioEntity.ResultsEntity>>() {
            @Override
            public void onNext(List<GankioEntity.ResultsEntity> resultsEntities) {
                if (mView != null) {
                    List<String> urls = new ArrayList<>();
                    Random random = new Random();
                    for (int i = 0; i < 6; i++) {
                        urls.add(resultsEntities.remove(random.nextInt(resultsEntities.size())).getUrl());
                    }
                    mView.showMeinv(urls);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.errorMeinv();
                    mView.showError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        mGankioUseCase.dispose();
    }
}
