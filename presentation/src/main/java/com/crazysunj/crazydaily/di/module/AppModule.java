package com.crazysunj.crazydaily.di.module;

import android.content.Context;

import com.crazysunj.crazydaily.app.App;
import com.crazysunj.data.repository.gankio.GankioDataRepository;
import com.crazysunj.data.repository.neihan.NeihanDataRepository;
import com.crazysunj.data.repository.weather.WeatherDataRepository;
import com.crazysunj.data.repository.zhihu.ZhihuDataRepository;
import com.crazysunj.domain.repository.gankio.GankioRepository;
import com.crazysunj.domain.repository.neihan.NeihanRepository;
import com.crazysunj.domain.repository.weather.WeatherRepository;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;
import com.crazysunj.domain.util.NeihanManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    NeihanManager provideNeihanManager(NeihanManager neihanManager) {
        return neihanManager;
    }

    @Provides
    @Singleton
    ZhihuRepository provideZhihuRepository(ZhihuDataRepository zhihuRepository) {
        return zhihuRepository;
    }

    @Provides
    @Singleton
    GankioRepository provideGankioRepository(GankioDataRepository gankioRepository) {
        return gankioRepository;
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherDataRepository weatherRepository) {
        return weatherRepository;
    }

    @Provides
    @Singleton
    NeihanRepository provideNeihanRepository(NeihanDataRepository neihanRepository) {
        return neihanRepository;
    }
}
