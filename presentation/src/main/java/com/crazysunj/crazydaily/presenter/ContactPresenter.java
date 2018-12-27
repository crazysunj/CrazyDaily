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
import com.crazysunj.crazydaily.presenter.contract.ContactContract;
import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;
import com.crazysunj.domain.interactor.contact.ContactUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ContactPresenter extends BasePresenter<ContactContract.View> implements ContactContract.Presenter {

    private ContactUseCase mContactUseCase;

    @Inject
    ContactPresenter(ContactUseCase contactUseCase) {
        mContactUseCase = contactUseCase;
    }

    @Override
    public void getConactList() {
        mContactUseCase.execute(ContactUseCase.Params.get(0), new BaseSubscriber<List<MultiTypeIndexEntity>>() {
            @Override
            protected void onStart() {
                super.onStart();
                if (mView != null) {
                    mView.showLoading();
                }
            }

            @Override
            public void onNext(List<MultiTypeIndexEntity> data) {
                if (mView != null) {
                    mView.showContent(data);
                }
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        mContactUseCase.dispose();
    }
}
