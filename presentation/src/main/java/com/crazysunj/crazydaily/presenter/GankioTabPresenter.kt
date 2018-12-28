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
package com.crazysunj.crazydaily.presenter

import com.crazysunj.crazydaily.base.BasePresenter
import com.crazysunj.crazydaily.base.BaseSubscriber
import com.crazysunj.crazydaily.presenter.contract.GankioTabContract
import com.crazysunj.domain.entity.gankio.GankioEntity
import com.crazysunj.domain.interactor.gankio.GankioUseCase
import javax.inject.Inject

/**
 * @author: sunjian
 * created on: 2018/12/28 下午2:40
 * description: https://github.com/crazysunj/CrazyDaily
 */
class GankioTabPresenter @Inject constructor(private val mGankioUseCase: GankioUseCase) : BasePresenter<GankioTabContract.View>(), GankioTabContract.Presenter {

    override fun getGankioList(type: String) {
        mGankioUseCase.execute(GankioUseCase.Params.get(type, 10), object : BaseSubscriber<List<GankioEntity.ResultsEntity>>() {
            override fun onNext(resultsEntities: List<GankioEntity.ResultsEntity>) {
                mView?.showGankio(resultsEntities)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView?.showError(e.message)
            }
        })
    }
}