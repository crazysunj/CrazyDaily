package com.crazysunj.data.service;

import com.crazysunj.domain.entity.WeatherRemoteEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:36
 * description:
 */

public interface WeatherService {

    String HOST = "http://tj.nineton.cn/";

    @GET("Heart/index/all")
    Flowable<WeatherRemoteEntity> getWeatherList(@Query("city") String city, @Query("language") String language);
}
