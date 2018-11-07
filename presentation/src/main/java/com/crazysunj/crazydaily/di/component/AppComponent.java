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
package com.crazysunj.crazydaily.di.component;


import android.content.Context;

import com.crazysunj.crazydaily.di.module.AppModule;
import com.crazysunj.domain.repository.contact.ContactRepository;
import com.crazysunj.domain.repository.download.DownloadRepository;
import com.crazysunj.domain.repository.gankio.GankioRepository;
import com.crazysunj.domain.repository.gaoxiao.GaoxiaoRepository;
import com.crazysunj.domain.repository.neihan.NeihanRepository;
import com.crazysunj.domain.repository.note.NoteRepository;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;
import com.crazysunj.domain.repository.weather.WeatherRepository;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:11
 * description: https://github.com/crazysunj/CrazyDaily
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context provideContext();

    OkHttpClient provideOkhttpClient();

    ZhihuRepository provideZhihuRepository();

    GankioRepository provideGankioRepository();

    WeatherRepository provideWeatherRepository();

    NeihanRepository provideNeihanRepository();

    GaoxiaoRepository provideGaoxiaoRepository();

    ContactRepository provideContactRepository();

    DownloadRepository provideDownloadRepository();

    PhotoPickerRepository providePhotoPickerRepository();

    NoteRepository provideNoteRepository();
}
