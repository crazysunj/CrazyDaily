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
package com.crazysunj.crazydaily.util;

import com.crazysunj.crazydaily.R;

/**
 * @author: sunjian
 * created on: 2017/9/22 下午1:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WeatherUtil {
    /**
     * 晴
     */
    private static final String SUNNY = "0";
    private static final String CLEAR = "1";
    private static final String FAIR1 = "2";
    private static final String FAIR2 = "3";

    /**
     * 多云
     */
    private static final String CLOUDY = "4";
    /**
     * 晴间多云
     */
    private static final String PARTLYCLOUDY1 = "5";
    private static final String PARTLYCLOUDY2 = "6";
    /**
     * 大部多云
     */
    private static final String MOSTLYCLOUDY1 = "7";
    private static final String MOSTLYCLOUDY2 = "8";
    /**
     * 阴
     */
    private static final String OVERCAST = "9";

    /**
     * 阵雨
     */
    private static final String SHOWER = "10";

    /**
     * 雷阵雨
     */
    private static final String THUNDERSHOWER = "11";
    /**
     * 雷阵雨伴有冰雹
     */
    private static final String THUNDERSHOWERWITHHAIL = "12";

    /**
     * 小雨
     */
    private static final String LIGHTRAIN = "13";
    /**
     * 中雨
     */
    private static final String MODERATERAIN = "14";
    /**
     * 大雨
     */
    private static final String HEAVYRAIN = "15";
    /**
     * 冻雨
     */
    private static final String ICERAIN = "19";
    /**
     * 雨夹雪
     */
    private static final String SLEET = "20";

    /**
     * 大雨
     */
    private static final String STORM = "16";
    private static final String HEAVYSTORM = "17";
    private static final String SEVERESTORM = "18";

    /**
     * 阵雪
     */
    private static final String SNOWFLURRY = "21";
    /**
     * 小雪
     */
    private static final String LIGHTSNOW = "22";
    /**
     * 中雪
     */
    private static final String MODERATESNOW = "23";

    /**
     * 大雪
     */
    private static final String HEAVYSNOW = "24";
    /**
     * 暴雪
     */
    private static final String SNOWSTORM = "25";

    /**
     * 浮尘
     */
    private static final String DUST = "26";
    /**
     * 扬尘
     */
    private static final String SAND = "27";
    /**
     * 沙尘暴
     */
    private static final String DUSTSTORM = "28";
    /**
     * 强沙尘暴
     */
    private static final String SANDSTORM = "29";
    /**
     * 雾
     */
    private static final String FOGGY = "30";
    /**
     * 霾
     */
    private static final String HAZE = "31";
    /**
     * 风
     */
    private static final String WINDY = "32";
    /**
     * 大风
     */
    private static final String BLUSTERY = "33";
    /**
     * 飓风
     */
    private static final String HURRICANE = "34";
    /**
     * 热带风暴
     */
    private static final String TROPICALSTORM = "35";
    /**
     * 龙卷风
     */
    private static final String TORNADO = "36";
    /**
     * 冷
     */
    private static final String COLD = "37";
    /**
     * 热
     */
    private static final String HOT = "38";
    /**
     * 未知
     */
    private static final String UNKNOWN = "99";

    private WeatherUtil() {
    }

    public static int getWeatherIcon(String code) {
        switch (code) {
            case SUNNY:
            case CLEAR:
            case FAIR1:
            case FAIR2:
            case HOT:
                return R.mipmap.ic_sunny;
            case CLOUDY:
            case PARTLYCLOUDY1:
            case PARTLYCLOUDY2:
            case MOSTLYCLOUDY1:
            case MOSTLYCLOUDY2:
            case OVERCAST:
                return R.mipmap.ic_cloudy;
            case SHOWER:
                return R.mipmap.ic_shower;
            case THUNDERSHOWER:
            case THUNDERSHOWERWITHHAIL:
                return R.mipmap.ic_thunder;
            case LIGHTRAIN:
            case MODERATERAIN:
            case HEAVYRAIN:
            case ICERAIN:
            case SLEET:
                return R.mipmap.ic_rain;
            case STORM:
            case HEAVYSTORM:
            case SEVERESTORM:
                return R.mipmap.ic_storm;
            case SNOWFLURRY:
            case LIGHTSNOW:
            case MODERATESNOW:
                return R.mipmap.ic_snow;
            case HEAVYSNOW:
            case SNOWSTORM:
            case COLD:
                return R.mipmap.ic_snow_storm;
            case DUST:
            case SAND:
            case DUSTSTORM:
            case SANDSTORM:
            case FOGGY:
            case HAZE:
            case WINDY:
            case BLUSTERY:
            case HURRICANE:
            case TROPICALSTORM:
            case TORNADO:
                return R.mipmap.ic_wind;
            default:
                return R.mipmap.ic_huaji;
        }
    }
}
