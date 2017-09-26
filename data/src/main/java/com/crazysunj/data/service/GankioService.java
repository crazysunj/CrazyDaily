package com.crazysunj.data.service;

import com.crazysunj.domain.entity.GankioEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:36
 * description:
 */

public interface GankioService {

    String HOST = "http://gank.io/api/";

    @GET("random/data/{type}/10")
    Flowable<GankioEntity> getGankio(@Path("type") String type);
}
