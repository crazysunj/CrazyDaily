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

import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午6:36
 * description: https://github.com/crazysunj/CrazyDaily
 */
public interface WeatherService {

    String HOST = "https://api.seniverse.com/";

    /**
     * 缓存时间为1分钟
     *
     * @param key      API密钥
     * @param location 所查询的位置
     *                 参数值范围：
     *                 城市ID 例如：location=WX4FBXXFKE4F
     *                 城市中文名 例如：location=北京
     *                 省市名称组合 例如：location=辽宁朝阳、location=北京朝阳
     *                 城市拼音/英文名 例如：location=beijing（如拼音相同城市，可在之前加省份和空格，例：shanxi yulin）
     *                 经纬度 例如：location=39.93:116.40（格式是 纬度:经度，英文冒号分隔）
     *                 IP地址 例如：location=220.181.111.86（某些IP地址可能无法定位到城市）
     *                 “ip”两个字母 自动识别请求IP地址，例如：location=ip
     * @param language 语言 (可选)
     *                 参数值范围：
     *                 zh-Hans 简体中文
     *                 zh-Hant 繁体中文
     *                 en 英文
     *                 ja 日语
     *                 de 德语
     *                 fr 法语
     *                 es 西班牙语
     *                 pt 葡萄牙语
     *                 hi 印地语（印度官方语言之一）
     *                 id 印度尼西亚语
     *                 ru 俄语
     *                 th 泰语
     *                 ar 阿拉伯语
     *                 默认值：zh-Hans
     * @param unit     单位 (可选)
     *                 参数值范围：
     *                 c 当参数为c时，温度c、风速km/h、能见度km、气压mb
     *                 f 当参数为f时，温度f、风速mph、能见度mile、气压inch
     *                 默认值：c
     * @return WeatherRemoteEntity
     */
    @Headers("Cache-Control: public, max-age=60")
    @GET("v3/weather/now.json")
    Flowable<Response<JsonObject>> getWeatherList(@Query("key") String key, @Query("location") String location, @Query("language") String language, @Query("unit") String unit);
}
