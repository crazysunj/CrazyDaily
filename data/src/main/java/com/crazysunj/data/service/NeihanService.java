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

import com.crazysunj.domain.entity.neihan.NeihanEntity;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午6:36
 * description: https://github.com/crazysunj/CrazyDaily
 */
public interface NeihanService {

    String HOST = "http://iu.snssdk.com/";

    @Headers("Cache-Control: public, max-age=300")//缓存时间为5分钟
    @GET("neihan/stream/mix/v1/")
    Flowable<NeihanEntity> getNeihanList(@Query("webp") int webp, @Query("essence") int essence, @Query("content_type") int content_type, @Query("message_cursor") int message_cursor, @Query("am_longitude") String am_longitude,
                                         @Query("am_latitude") String am_latitude, @Query("am_city") String am_city, @Query("am_loc_time") long am_loc_time, @Query("count") int count,
                                         @Query("min_time") long min_time, @Query("screen_width") int screen_width, @Query("double_col_mode") int double_col_mode, @Query("iid") String iid,
                                         @Query("device_id") String device_id, @Query("ac") String ac, @Query("channel") String channel, @Query("aid") int aid,
                                         @Query("app_name") String app_name, @Query("version_code") String version_code, @Query("version_name") String version_name, @Query("device_platform") String device_platform,
                                         @Query("ssmix") String ssmix, @Query("device_type") String device_type, @Query("device_brand") String device_brand, @Query("os_api") int os_api,
                                         @Query("os_version") String os_version, @Query("uuid") String uuid, @Query("openudid") String openudid, @Query("manifest_version_code") String manifest_version_code,
                                         @Query("resolution") String resolution, @Query("dpi") String dpi, @Query("update_version_code") String update_version_code);
}
