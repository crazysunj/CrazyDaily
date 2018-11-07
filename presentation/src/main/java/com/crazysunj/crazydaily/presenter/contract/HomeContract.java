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
package com.crazysunj.crazydaily.presenter.contract;


import com.crazysunj.crazydaily.base.IPresenter;
import com.crazysunj.crazydaily.base.IView;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.entity.weather.WeatherXinZhiEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
public interface HomeContract {

    interface View extends IView {
        void showZhihu(ZhihuNewsEntity zhihuNewsEntity);

        void showGankio(List<GankioEntity.ResultsEntity> gankioList);

        void showWeather(WeatherXinZhiEntity.FinalEntity weatherEntity);

        void showNeihan(List<NeihanItemEntity> neihanList);

        void showGaoxiao(List<GaoxiaoItemEntity> gaoxiaoList);

        void showMeinv(List<String> meinvList);

        void errorMeinv();

        void switchBanner();
    }

    interface Presenter extends IPresenter<View> {
        void getZhihuNewsList();

        void getGankioList(String type);

        void getWeather(String location);

        void getNeihanList(long am_loc_time, long min_time, int screen_width, String iid, String device_id, String ac, String version_code, String version_name,
                           String device_type, String device_brand, int os_api, String os_version, String uuid, String openudid, String manifest_version_code, String resolution,
                           String dpi, String update_version_code);

        void getGaoxiaoList(int page);

        void getMeinvList();

        void startBanner();

        void endBanner();
    }
}
