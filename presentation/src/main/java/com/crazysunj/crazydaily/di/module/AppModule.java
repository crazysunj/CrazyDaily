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
package com.crazysunj.crazydaily.di.module;

import android.content.Context;

import com.crazysunj.crazydaily.app.App;
import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.repository.contact.ContactDataRepository;
import com.crazysunj.data.repository.download.DownloadDataRepository;
import com.crazysunj.data.repository.gankio.GankioDataRepository;
import com.crazysunj.data.repository.gaoxiao.GaoxiaoDataRepository;
import com.crazysunj.data.repository.neihan.NeihanDataRepository;
import com.crazysunj.data.repository.note.NoteDataRepository;
import com.crazysunj.data.repository.photo.PhotoPickerDataRepository;
import com.crazysunj.data.repository.weather.WeatherDataRepository;
import com.crazysunj.data.repository.zhihu.ZhihuDataRepository;
import com.crazysunj.domain.repository.contact.ContactRepository;
import com.crazysunj.domain.repository.download.DownloadRepository;
import com.crazysunj.domain.repository.gankio.GankioRepository;
import com.crazysunj.domain.repository.gaoxiao.GaoxiaoRepository;
import com.crazysunj.domain.repository.neihan.NeihanRepository;
import com.crazysunj.domain.repository.note.NoteRepository;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;
import com.crazysunj.domain.repository.weather.WeatherRepository;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;
import com.crazysunj.domain.util.NeihanManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:11
 * description: https://github.com/crazysunj/CrazyDaily
 */
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
    OkHttpClient provideOkhttpClient(HttpHelper httpHelper) {
        return httpHelper.getOkHttpClient();
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

    @Provides
    @Singleton
    GaoxiaoRepository provideGaoxiaoRepository(GaoxiaoDataRepository gaoxiaoRepository) {
        return gaoxiaoRepository;
    }

    @Provides
    @Singleton
    ContactRepository provideContactRepository(ContactDataRepository contactRepository) {
        return contactRepository;
    }

    @Provides
    @Singleton
    DownloadRepository provideDownloadRepository(DownloadDataRepository downloadRepository) {
        return downloadRepository;
    }

    @Provides
    @Singleton
    PhotoPickerRepository providePhotoPickerRepository(PhotoPickerDataRepository photoPickerRepository) {
        return photoPickerRepository;
    }

    @Provides
    @Singleton
    NoteRepository provideNoteRepository(NoteDataRepository noteRepository) {
        return noteRepository;
    }
}
