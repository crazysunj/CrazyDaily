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
package com.crazysunj.domain.entity.weather;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2017/9/22 上午10:11
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WeatherRemoteEntity {

    /**
     * status : OK
     * weather : [{"city_name":"上海","city_id":"CHSH000000","last_update":"2017-08-30T02:04:00+08:00","now":{"text":"多云","code":"4","temperature":"26","feels_like":"25","wind_direction":"东南","wind_speed":"4.32","wind_scale":"1","humidity":"62","visibility":"29.80","pressure":"1013","pressure_rising":"未知","air_quality":{"city":{"aqi":"33","pm25":"9","pm10":"33","so2":"7","no2":"14","co":"0.480","o3":"84","last_update":"2017-08-30T00:00:00+08:00","quality":"优"},"stations":null},"alarms":[]},"today":{"sunrise":"","sunset":"","suggestion":{"dressing":{"brief":"热","details":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"},"uv":{"brief":"弱","details":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"},"car_washing":{"brief":"不宜","details":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"travel":{"brief":"适宜","details":"天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。"},"flu":{"brief":"少发","details":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"},"sport":{"brief":"较不宜","details":"阴天，且天气较热，请减少运动时间并降低运动强度。"}}},"future":[{"date":"2017-08-29","high":"33","low":"26","day":"周二","text":"大雨/小雨","code1":"15","code2":"13","cop":"","wind":"风力3级"},{"date":"2017-08-30","high":"29","low":"24","day":"周三","text":"阴/中雨","code1":"9","code2":"14","cop":"","wind":"风力2级"},{"date":"2017-08-31","high":"29","low":"24","day":"周四","text":"中雨/中雨","code1":"14","code2":"14","cop":"","wind":"风力2级"},{"date":"2017-09-01","high":"28","low":"23","day":"周五","text":"中雨/中雨","code1":"14","code2":"14","cop":"","wind":"风力2级"},{"date":"2017-09-02","high":"28","low":"23","day":"周六","text":"中雨/小雨","code1":"14","code2":"13","cop":"","wind":"风力3级"},{"date":"2017-09-03","high":"30","low":"25","day":"周日","text":"小雨/小雨","code1":"13","code2":"13","cop":"","wind":"风力2级"},{"date":"2017-09-04","high":"30","low":"26","day":"周一","text":"小雨/多云","code1":"13","code2":"4","cop":"","wind":"风力2级"},{"date":"2017-09-05","high":"30","low":"25","day":"周二","text":"小雨/阴","code1":"13","code2":"9","cop":"","wind":"风力2级"},{"date":"2017-09-06","high":"32","low":"26","day":"周三","text":"多云/多云","code1":"4","code2":"4","cop":"","wind":"风力3级"},{"date":"2017-09-07","high":"31","low":"26","day":"周四","text":"小雨/阴","code1":"13","code2":"9","cop":"","wind":"风力2级"}]}]
     */

    private String status;
    private List<WeatherEntity> weather;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WeatherEntity> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherEntity> weather) {
        this.weather = weather;
    }

    public static class WeatherEntity extends MultiTypeIdEntity {

        public static final int TYPE_WEATHER = 2;

        /**
         * city_name : 上海
         * city_id : CHSH000000
         * last_update : 2017-08-30T02:04:00+08:00
         * now : {"text":"多云","code":"4","temperature":"26","feels_like":"25","wind_direction":"东南","wind_speed":"4.32","wind_scale":"1","humidity":"62","visibility":"29.80","pressure":"1013","pressure_rising":"未知","air_quality":{"city":{"aqi":"33","pm25":"9","pm10":"33","so2":"7","no2":"14","co":"0.480","o3":"84","last_update":"2017-08-30T00:00:00+08:00","quality":"优"},"stations":null},"alarms":[]}
         * today : {"sunrise":"","sunset":"","suggestion":{"dressing":{"brief":"热","details":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。"},"uv":{"brief":"弱","details":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"},"car_washing":{"brief":"不宜","details":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"travel":{"brief":"适宜","details":"天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。"},"flu":{"brief":"少发","details":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"},"sport":{"brief":"较不宜","details":"阴天，且天气较热，请减少运动时间并降低运动强度。"}}}
         * future : [{"date":"2017-08-29","high":"33","low":"26","day":"周二","text":"大雨/小雨","code1":"15","code2":"13","cop":"","wind":"风力3级"},{"date":"2017-08-30","high":"29","low":"24","day":"周三","text":"阴/中雨","code1":"9","code2":"14","cop":"","wind":"风力2级"},{"date":"2017-08-31","high":"29","low":"24","day":"周四","text":"中雨/中雨","code1":"14","code2":"14","cop":"","wind":"风力2级"},{"date":"2017-09-01","high":"28","low":"23","day":"周五","text":"中雨/中雨","code1":"14","code2":"14","cop":"","wind":"风力2级"},{"date":"2017-09-02","high":"28","low":"23","day":"周六","text":"中雨/小雨","code1":"14","code2":"13","cop":"","wind":"风力3级"},{"date":"2017-09-03","high":"30","low":"25","day":"周日","text":"小雨/小雨","code1":"13","code2":"13","cop":"","wind":"风力2级"},{"date":"2017-09-04","high":"30","low":"26","day":"周一","text":"小雨/多云","code1":"13","code2":"4","cop":"","wind":"风力2级"},{"date":"2017-09-05","high":"30","low":"25","day":"周二","text":"小雨/阴","code1":"13","code2":"9","cop":"","wind":"风力2级"},{"date":"2017-09-06","high":"32","low":"26","day":"周三","text":"多云/多云","code1":"4","code2":"4","cop":"","wind":"风力3级"},{"date":"2017-09-07","high":"31","low":"26","day":"周四","text":"小雨/阴","code1":"13","code2":"9","cop":"","wind":"风力2级"}]
         */

        private String city_name;
        private String city_id;
        private String last_update;
        private NowEntity now;
        private List<FutureEntity> future;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public NowEntity getNow() {
            return now;
        }

        public void setNow(NowEntity now) {
            this.now = now;
        }

        public List<FutureEntity> getFuture() {
            return future;
        }

        public void setFuture(List<FutureEntity> future) {
            this.future = future;
        }

        @Override
        public String getStringId() {
            return city_id;
        }

        @Override
        public int getItemType() {
            return TYPE_WEATHER;
        }

        public static class NowEntity {
            /**
             * text : 多云
             * code : 4
             * temperature : 26
             * feels_like : 25
             * wind_direction : 东南
             * wind_speed : 4.32
             * wind_scale : 1
             * humidity : 62
             * visibility : 29.80
             * pressure : 1013
             * pressure_rising : 未知
             * air_quality : {"city":{"aqi":"33","pm25":"9","pm10":"33","so2":"7","no2":"14","co":"0.480","o3":"84","last_update":"2017-08-30T00:00:00+08:00","quality":"优"},"stations":null}
             * alarms : []
             */

            private String text;
            private String code;
            private String temperature;
            private String feels_like;
            private String wind_direction;
            private String wind_speed;
            private String wind_scale;
            private String humidity;
            private String visibility;
            private String pressure;
            private String pressure_rising;
            private AirQualityEntity air_quality;
            private List<?> alarms;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getFeels_like() {
                return feels_like;
            }

            public void setFeels_like(String feels_like) {
                this.feels_like = feels_like;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_speed() {
                return wind_speed;
            }

            public void setWind_speed(String wind_speed) {
                this.wind_speed = wind_speed;
            }

            public String getWind_scale() {
                return wind_scale;
            }

            public void setWind_scale(String wind_scale) {
                this.wind_scale = wind_scale;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getVisibility() {
                return visibility;
            }

            public void setVisibility(String visibility) {
                this.visibility = visibility;
            }

            public String getPressure() {
                return pressure;
            }

            public void setPressure(String pressure) {
                this.pressure = pressure;
            }

            public String getPressure_rising() {
                return pressure_rising;
            }

            public void setPressure_rising(String pressure_rising) {
                this.pressure_rising = pressure_rising;
            }

            public AirQualityEntity getAir_quality() {
                return air_quality;
            }

            public void setAir_quality(AirQualityEntity air_quality) {
                this.air_quality = air_quality;
            }

            public List<?> getAlarms() {
                return alarms;
            }

            public void setAlarms(List<?> alarms) {
                this.alarms = alarms;
            }

            public static class AirQualityEntity {
                /**
                 * city : {"aqi":"33","pm25":"9","pm10":"33","so2":"7","no2":"14","co":"0.480","o3":"84","last_update":"2017-08-30T00:00:00+08:00","quality":"优"}
                 * stations : null
                 */

                private CityEntity city;
                private Object stations;

                public CityEntity getCity() {
                    return city;
                }

                public void setCity(CityEntity city) {
                    this.city = city;
                }

                public Object getStations() {
                    return stations;
                }

                public void setStations(Object stations) {
                    this.stations = stations;
                }

                public static class CityEntity {
                    /**
                     * aqi : 33
                     * pm25 : 9
                     * pm10 : 33
                     * so2 : 7
                     * no2 : 14
                     * co : 0.480
                     * o3 : 84
                     * last_update : 2017-08-30T00:00:00+08:00
                     * quality : 优
                     */

                    private String aqi;
                    private String pm25;
                    private String pm10;
                    private String so2;
                    private String no2;
                    private String co;
                    private String o3;
                    private String last_update;
                    private String quality;

                    public String getAqi() {
                        return aqi;
                    }

                    public void setAqi(String aqi) {
                        this.aqi = aqi;
                    }

                    public String getPm25() {
                        return pm25;
                    }

                    public void setPm25(String pm25) {
                        this.pm25 = pm25;
                    }

                    public String getPm10() {
                        return pm10;
                    }

                    public void setPm10(String pm10) {
                        this.pm10 = pm10;
                    }

                    public String getSo2() {
                        return so2;
                    }

                    public void setSo2(String so2) {
                        this.so2 = so2;
                    }

                    public String getNo2() {
                        return no2;
                    }

                    public void setNo2(String no2) {
                        this.no2 = no2;
                    }

                    public String getCo() {
                        return co;
                    }

                    public void setCo(String co) {
                        this.co = co;
                    }

                    public String getO3() {
                        return o3;
                    }

                    public void setO3(String o3) {
                        this.o3 = o3;
                    }

                    public String getLast_update() {
                        return last_update;
                    }

                    public void setLast_update(String last_update) {
                        this.last_update = last_update;
                    }

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }
                }
            }
        }

        public static class FutureEntity {
            /**
             * date : 2017-08-29
             * high : 33
             * low : 26
             * day : 周二
             * text : 大雨/小雨
             * code1 : 15
             * code2 : 13
             * cop :
             * wind : 风力3级
             */

            private String date;
            private String high;
            private String low;
            private String day;
            private String text;
            private String code1;
            private String code2;
            private String cop;
            private String wind;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode1() {
                return code1;
            }

            public void setCode1(String code1) {
                this.code1 = code1;
            }

            public String getCode2() {
                return code2;
            }

            public void setCode2(String code2) {
                this.code2 = code2;
            }

            public String getCop() {
                return cop;
            }

            public void setCop(String cop) {
                this.cop = cop;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }
        }
    }
}
