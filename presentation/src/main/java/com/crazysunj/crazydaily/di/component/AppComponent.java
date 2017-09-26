package com.crazysunj.crazydaily.di.component;


import android.content.Context;

import com.crazysunj.crazydaily.di.module.AppModule;
import com.crazysunj.domain.repository.gankio.GankioRepository;
import com.crazysunj.domain.repository.neihan.NeihanRepository;
import com.crazysunj.domain.repository.weather.WeatherRepository;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context provideContext();

    ZhihuRepository provideZhihuRepository();

    GankioRepository provideGankioRepository();

    WeatherRepository provideWeatherRepository();

    NeihanRepository provideNeihanRepository();

}
