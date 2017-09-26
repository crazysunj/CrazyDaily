package com.crazysunj.data.service;


import com.crazysunj.domain.entity.ZhihuNewsDetailEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by codeest on 2016/8/2.
 * 知乎APIs
 */
public interface ZhihuService {

    String HOST = "http://news-at.zhihu.com/api/4/";

    @GET("news/latest")
    Flowable<ZhihuNewsEntity> getZhihuNewsList();

    @GET("news/{id}")
    Flowable<ZhihuNewsDetailEntity> getZhihuNewsDetail(@Path("id") long id);

}
