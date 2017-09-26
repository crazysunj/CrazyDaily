package com.crazysunj.data.repository.weather;

import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.WeatherService;
import com.crazysunj.data.util.RxTransformerUtil;
import com.crazysunj.domain.entity.WeatherRemoteEntity;
import com.crazysunj.domain.repository.weather.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:58
 * description:
 */

public class WeatherDataRepository implements WeatherRepository {

    private WeatherService mWeatherService;

    @Inject
    public WeatherDataRepository(HttpHelper httpHelper) {
        mWeatherService = httpHelper.getWeatherService();
    }

    @Override
    public Flowable<WeatherRemoteEntity> getWeatherList(String city, String language) {
        return mWeatherService.getWeatherList(city, language)
                .compose(RxTransformerUtil.normalTransformer());
    }
}
