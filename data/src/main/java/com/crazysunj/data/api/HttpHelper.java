/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.data.api;

import com.crazysunj.data.logger.HttpLogger;
import com.crazysunj.data.service.GankioService;
import com.crazysunj.data.service.NeihanService;
import com.crazysunj.data.service.WeatherService;
import com.crazysunj.data.service.ZhihuService;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: sunjian
 * created on: 2017/9/6 下午5:25
 * description:https://github.com/crazysunj/CrazyDaily
 */
@Singleton
public class HttpHelper {

    private ZhihuService mZhihuService;
    private GankioService mGankioService;
    private WeatherService mWeatherService;
    private NeihanService mNeihanService;
    private OkHttpClient mOkHttpClient;

    @Inject
    public HttpHelper() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            mOkHttpClient = builder.build();
        }
    }

    public ZhihuService getZhihuService() {
        if (mZhihuService == null) {
            synchronized (this) {
                if (mZhihuService == null) {
                    mZhihuService = new Retrofit.Builder()
                            .baseUrl(ZhihuService.HOST)
                            .client(mOkHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ZhihuService.class);
                }
            }
        }
        return mZhihuService;
    }

    public GankioService getGankioService() {
        if (mGankioService == null) {
            synchronized (this) {
                if (mGankioService == null) {
                    mGankioService = new Retrofit.Builder()
                            .baseUrl(GankioService.HOST)
                            .client(mOkHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(GankioService.class);
                }
            }
        }
        return mGankioService;
    }

    public WeatherService getWeatherService() {
        if (mWeatherService == null) {
            synchronized (this) {
                if (mWeatherService == null) {
                    mWeatherService = new Retrofit.Builder()
                            .baseUrl(WeatherService.HOST)
                            .client(mOkHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(WeatherService.class);
                }
            }
        }
        return mWeatherService;
    }

    public NeihanService getNeihanService() {
        if (mNeihanService == null) {
            synchronized (this) {
                if (mNeihanService == null) {
                    mNeihanService = new Retrofit.Builder()
                            .baseUrl(NeihanService.HOST)
                            .client(mOkHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(NeihanService.class);
                }
            }
        }
        return mNeihanService;
    }
}
