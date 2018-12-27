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
import com.crazysunj.crazydaily.presenter.contract.HomeContract;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.entity.weather.WeatherXinZhiEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.domain.interactor.gankio.GankioUseCase;
import com.crazysunj.domain.interactor.gaoxiao.GaoxiaoUseCase;
import com.crazysunj.domain.interactor.neihan.NeihanUseCase;
import com.crazysunj.domain.interactor.weather.WeatherUseCase;
import com.crazysunj.domain.interactor.zhihu.ZhihuNewsListUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private ZhihuNewsListUseCase mZhihuUseCase;
    private GankioUseCase mGankioUseCase;
    private WeatherUseCase mWeatherUseCase;
    private NeihanUseCase mNeihanUseCase;
    private GaoxiaoUseCase mGaoxiaoUseCase;
    private Disposable mBannerDisposable;

    @Inject
    HomePresenter(ZhihuNewsListUseCase zhihuUseCase, GankioUseCase gankioUseCase, WeatherUseCase weatherUseCase, NeihanUseCase neihanUseCase, GaoxiaoUseCase gaoxiaoUseCase) {
        mZhihuUseCase = zhihuUseCase;
        mGankioUseCase = gankioUseCase;
        mWeatherUseCase = weatherUseCase;
        mNeihanUseCase = neihanUseCase;
        mGaoxiaoUseCase = gaoxiaoUseCase;
    }

    @Override
    public void getZhihuNewsList() {
        mZhihuUseCase.execute(new BaseSubscriber<ZhihuNewsEntity>() {
            @Override
            public void onNext(ZhihuNewsEntity zhihuNewsEntity) {
                if (mView != null) {
                    mView.showZhihu(zhihuNewsEntity);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getGankioList(String type) {
        mGankioUseCase.execute(GankioUseCase.Params.get(type, 10), new BaseSubscriber<List<GankioEntity.ResultsEntity>>() {
            @Override
            public void onNext(List<GankioEntity.ResultsEntity> resultsEntities) {
                if (mView != null) {
                    mView.showGankio(resultsEntities);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getWeather(String location) {
        mWeatherUseCase.execute(WeatherUseCase.Params.get(location), new BaseSubscriber<WeatherXinZhiEntity.FinalEntity>() {
            @Override
            public void onNext(WeatherXinZhiEntity.FinalEntity weatherEntity) {
                if (mView != null) {
                    mView.showWeather(weatherEntity);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getNeihanList(long am_loc_time, long min_time, int screen_width, String iid, String device_id, String ac, String version_code, String version_name, String device_type, String device_brand, int os_api, String os_version, String uuid, String openudid, String manifest_version_code, String resolution, String dpi, String update_version_code) {
        mNeihanUseCase.execute(NeihanUseCase.Params.get(am_loc_time, min_time, screen_width, iid, device_id, ac, version_code, version_name, device_type, device_brand, os_api, os_version, uuid, openudid, manifest_version_code, resolution, dpi, update_version_code), new BaseSubscriber<List<NeihanItemEntity>>() {
            @Override
            public void onNext(List<NeihanItemEntity> dataEntities) {
                if (mView != null) {
                    mView.showNeihan(dataEntities);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getGaoxiaoList(int page) {
        mGaoxiaoUseCase.execute(GaoxiaoUseCase.Params.get(page), new BaseSubscriber<List<GaoxiaoItemEntity>>() {
            @Override
            public void onNext(List<GaoxiaoItemEntity> gaoxiaoItemEntities) {
                if (mView != null) {
                    mView.showGaoxiao(gaoxiaoItemEntities);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        });
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
    public void startBanner() {
        if (mBannerDisposable == null) {
            mBannerDisposable = Flowable.interval(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .subscribeWith(new BaseSubscriber<Long>() {
                        @Override
                        public void onNext(Long aLong) {
                            if (mView != null) {
                                mView.switchBanner();
                            }
                        }
                    });
        }
    }

    @Override
    public void endBanner() {
        if (mBannerDisposable == null || mBannerDisposable.isDisposed()) {
            return;
        }
        mBannerDisposable.dispose();
        mBannerDisposable = null;
    }

    @Override
    public void detachView() {
        super.detachView();
        mZhihuUseCase.dispose();
        mGankioUseCase.dispose();
        mWeatherUseCase.dispose();
        mNeihanUseCase.dispose();
        mGaoxiaoUseCase.dispose();
    }
}
