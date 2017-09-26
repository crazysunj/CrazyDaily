package com.crazysunj.crazydaily.presenter.contract;


import com.crazysunj.crazydaily.base.IPresenter;
import com.crazysunj.crazydaily.base.IView;
import com.crazysunj.domain.entity.GankioEntity;
import com.crazysunj.domain.entity.NeihanItemEntity;
import com.crazysunj.domain.entity.WeatherRemoteEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;

import java.util.List;

public interface HomeContract {

    interface View extends IView {

        void showZhihu(ZhihuNewsEntity zhihuNewsEntity);

        void showGankio(List<GankioEntity.ResultsEntity> gankioList);

        void showWeather(List<WeatherRemoteEntity.WeatherEntity> weatherList);

        void showNeihan(List<NeihanItemEntity> neihanList);
    }

    interface Presenter extends IPresenter<View> {

        void getZhihuNewsList();

        void getGankioList(String type);

        void getWeather(String city);

        void getNeihanList(long am_loc_time, long min_time, int screen_width, String iid, String device_id, String ac, String version_code, String version_name,
                           String device_type, String device_brand, int os_api, String os_version, String uuid, String openudid, String manifest_version_code, String resolution,
                           String dpi, String update_version_code);
    }
}
