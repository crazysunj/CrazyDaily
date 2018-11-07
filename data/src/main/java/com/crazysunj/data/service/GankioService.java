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
package com.crazysunj.data.service;

import com.crazysunj.domain.entity.gankio.GankioEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午6:36
 * description: https://github.com/crazysunj/CrazyDaily
 */
public interface GankioService {

    String HOST = "http://gank.io/api/";

    @Headers("Cache-Control: public, max-age=300")//缓存时间为5分钟
    @GET("random/data/{type}/{count}")
    Flowable<GankioEntity> getGankio(@Path("type") String type, @Path("count") int count);
}
