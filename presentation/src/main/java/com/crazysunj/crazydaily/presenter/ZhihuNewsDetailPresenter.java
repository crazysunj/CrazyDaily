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
import com.crazysunj.crazydaily.presenter.contract.ZhihuNewsDetailContract;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsDetailEntity;
import com.crazysunj.domain.interactor.zhihu.ZhihuNewsDetailUseCase;

import javax.inject.Inject;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ActivityScope
public class ZhihuNewsDetailPresenter extends BasePresenter<ZhihuNewsDetailContract.View> implements ZhihuNewsDetailContract.Presenter {

    private ZhihuNewsDetailUseCase mZhihuNewsDetailUseCase;

    @Inject
    ZhihuNewsDetailPresenter(ZhihuNewsDetailUseCase useCase) {
        mZhihuNewsDetailUseCase = useCase;
    }

    @Override
    public void getZhihuNewsDetail(long id) {
        mZhihuNewsDetailUseCase.execute(ZhihuNewsDetailUseCase.Params.get(id), new BaseSubscriber<ZhihuNewsDetailEntity>() {
            @Override
            public void onNext(ZhihuNewsDetailEntity zhihuNewsDetailEntity) {
                if (mView != null) {
                    mView.showContent(zhihuNewsDetailEntity);
                }
            }
        });
    }
}
