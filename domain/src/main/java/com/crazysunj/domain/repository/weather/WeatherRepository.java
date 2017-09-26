package com.crazysunj.domain.repository.weather;

import com.crazysunj.domain.entity.WeatherRemoteEntity;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:40
 * description:
 */

public interface WeatherRepository {
    Flowable<WeatherRemoteEntity> getWeatherList(String city, String language);
}
