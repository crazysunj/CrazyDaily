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
package com.crazysunj.data.api;

import android.content.Context;
import android.text.TextUtils;

import com.crazysunj.data.logger.HttpLogger;
import com.crazysunj.data.service.DownloadService;
import com.crazysunj.data.service.GankioService;
import com.crazysunj.data.service.GaoxiaoService;
import com.crazysunj.data.service.NeihanService;
import com.crazysunj.data.service.WeatherService;
import com.crazysunj.data.service.ZhihuService;
import com.crazysunj.data.util.LoggerUtil;
import com.crazysunj.data.util.NetworkUtils;
import com.crazysunj.domain.bus.RxBus;
import com.crazysunj.domain.bus.event.DownloadEvent;
import com.crazysunj.domain.constant.CacheConstant;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: sunjian
 * created on: 2017/9/6 下午5:25
 * description: https://github.com/crazysunj/CrazyDaily
 */
@Singleton
public class HttpHelper {

    private volatile ZhihuService mZhihuService;
    private volatile GankioService mGankioService;
    private volatile WeatherService mWeatherService;
    private volatile NeihanService mNeihanService;
    private volatile GaoxiaoService mGaoxiaoService;
    private OkHttpClient mOkHttpClient;

    @Inject
    public HttpHelper(Context context) {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            // 设置缓存 20M
            Cache cache = new Cache(new File(context.getExternalCacheDir(), CacheConstant.CACHE_DIR_API), 20 * 1024 * 1024);
            builder.cache(cache);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(new CrazyDailyCacheInterceptor());
            builder.addNetworkInterceptor(new CrazyDailyCacheNetworkInterceptor());
            // 设置Cookie
            builder.cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)));
            // 设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            // 错误重连
            builder.retryOnConnectionFailure(true);
            mOkHttpClient = builder.build();
        }
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
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

    public GaoxiaoService getGaoxiaoService() {
        if (mGaoxiaoService == null) {
            synchronized (this) {
                if (mGaoxiaoService == null) {
                    mGaoxiaoService = new Retrofit.Builder()
                            .baseUrl(GaoxiaoService.HOST)
                            .client(mOkHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(GaoxiaoService.class);
                }
            }
        }
        return mGaoxiaoService;
    }

    public DownloadService getDownloadService(int taskId) {
        return new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor(new HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addNetworkInterceptor(new ProgressInterceptor(taskId))
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(DownloadService.class);
    }

    /**
     * 有网才会执行哦
     */
    private static class CrazyDailyCacheNetworkInterceptor implements Interceptor {

        private static final String CACHE_CONTROL = "Cache-Control";

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            final Response response = chain.proceed(request);
            final String requestHeader = request.header(CACHE_CONTROL);
            //判断条件最好加上TextUtils.isEmpty(response.header(CACHE_CONTROL))来判断服务器是否返回缓存策略，如果返回，就按服务器的来，我这里全部客户端控制了
            if (!TextUtils.isEmpty(requestHeader)) {
                LoggerUtil.i(LoggerUtil.MSG_HTTP, "CrazyDailyCacheNetworkInterceptor---cache---host:" + request.url().host());
                return response.newBuilder().header(CACHE_CONTROL, requestHeader).removeHeader("Pragma").build();
            }
            return response;
        }
    }

    private static class CrazyDailyCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            CacheControl cacheControl = request.cacheControl();
            //header可控制不走这个逻辑
            boolean noCache = cacheControl.noCache() || cacheControl.noStore() || cacheControl.maxAgeSeconds() == 0;
            if (!noCache && !NetworkUtils.isNetworkAvailable()) {
                Request.Builder builder = request.newBuilder();
                LoggerUtil.i(LoggerUtil.MSG_HTTP, "CrazyDailyCacheInterceptor---cache---host:" + request.url().host());
                CacheControl newCacheControl = new CacheControl.Builder().maxStale(1, TimeUnit.DAYS).build();
                request = builder.cacheControl(newCacheControl).build();
                return chain.proceed(request);
            }
            return chain.proceed(request);
        }
    }

    private static class ProgressInterceptor implements Interceptor {

        private final int taskId;

        private ProgressInterceptor(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(taskId, originalResponse.body()))
                    .build();
        }
    }

    private static class ProgressResponseBody extends ResponseBody {
        private ResponseBody responseBody;
        private final int taskId;
        private BufferedSource bufferedSource;

        private ProgressResponseBody(int taskId, ResponseBody responseBody) {
            this.taskId = taskId;
            this.responseBody = responseBody;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(contentLength(), responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(long contentLength, Source source) {
            return new ForwardingSource(source) {
                long bytesReaded = 0;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                    RxBus.getDefault().post(String.valueOf(taskId), new DownloadEvent(taskId, contentLength, bytesReaded));
                    return bytesRead;
                }
            };
        }
    }
}
