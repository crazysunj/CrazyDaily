package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.HomeContract;
import com.crazysunj.domain.entity.GankioEntity;
import com.crazysunj.domain.entity.NeihanItemEntity;
import com.crazysunj.domain.entity.WeatherRemoteEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;
import com.crazysunj.domain.interactor.gankio.GankioUseCase;
import com.crazysunj.domain.interactor.neihan.NeihanUseCase;
import com.crazysunj.domain.interactor.weather.WeatherUseCase;
import com.crazysunj.domain.interactor.zhihu.ZhihuNewsListUseCase;

import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private ZhihuNewsListUseCase mZhihuUseCase;
    private GankioUseCase mGankioUseCase;
    private WeatherUseCase mWeatherUseCase;
    private NeihanUseCase mNeihanUseCase;

    @Inject
    public HomePresenter(ZhihuNewsListUseCase zhihuUseCase, GankioUseCase gankioUseCase, WeatherUseCase weatherUseCase, NeihanUseCase neihanUseCase) {
        mZhihuUseCase = zhihuUseCase;
        mGankioUseCase = gankioUseCase;
        mWeatherUseCase = weatherUseCase;
        mNeihanUseCase = neihanUseCase;
    }

    @Override
    public void getZhihuNewsList() {
        mZhihuUseCase.execute(new BaseSubscriber<ZhihuNewsEntity>() {
            @Override
            public void onNext(ZhihuNewsEntity zhihuNewsEntity) {
                mView.showZhihu(zhihuNewsEntity);
            }
        });
    }

    @Override
    public void getGankioList(String type) {
        mGankioUseCase.execute(GankioUseCase.Params.get(type), new BaseSubscriber<List<GankioEntity.ResultsEntity>>() {
            @Override
            public void onNext(List<GankioEntity.ResultsEntity> resultsEntities) {
                mView.showGankio(resultsEntities);
            }
        });
    }

    @Override
    public void getWeather(String city) {
        mWeatherUseCase.execute(WeatherUseCase.Params.get(city), new BaseSubscriber<List<WeatherRemoteEntity.WeatherEntity>>() {
            @Override
            public void onNext(List<WeatherRemoteEntity.WeatherEntity> weatherEntities) {
                mView.showWeather(weatherEntities);
            }
        });
    }

    @Override
    public void getNeihanList(long am_loc_time, long min_time, int screen_width, String iid, String device_id, String ac, String version_code, String version_name, String device_type, String device_brand, int os_api, String os_version, String uuid, String openudid, String manifest_version_code, String resolution, String dpi, String update_version_code) {
        mNeihanUseCase.execute(NeihanUseCase.Params.get(am_loc_time, min_time, screen_width, iid, device_id, ac, version_code, version_name, device_type, device_brand, os_api, os_version, uuid, openudid, manifest_version_code, resolution, dpi, update_version_code), new BaseSubscriber<List<NeihanItemEntity>>() {
            @Override
            public void onNext(List<NeihanItemEntity> dataEntities) {
                mView.showNeihan(dataEntities);
            }
        });
    }

    @Override
    public void detachView() {
        super.detachView();
        mZhihuUseCase.dispose();
        mGankioUseCase.dispose();
        mWeatherUseCase.dispose();
        mNeihanUseCase.dispose();
    }
}
