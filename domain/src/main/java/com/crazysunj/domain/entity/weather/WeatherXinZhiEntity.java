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
 * created on: 2018/10/29 下午1:51
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WeatherXinZhiEntity {

    public static class FinalEntity extends MultiTypeIdEntity {

        public static final int TYPE_WEATHER = 2;

        /**
         * id
         */
        private String id;
        /**
         * 地名
         */
        private String location;
        /**
         * 天气名
         */
        private String text;
        /**
         * 温度
         */
        private String temperature;
        /**
         * 上次更新的时间
         */
        private String lastUpdate;
        /**
         * 天气代码，用于获取天气图标
         */
        private String code;

        public FinalEntity(String id, String location, String text, String temperature, String lastUpdate, String code) {
            this.id = id;
            this.location = location;
            this.text = text;
            this.temperature = temperature;
            this.lastUpdate = lastUpdate;
            this.code = code;
        }

        public String getLocation() {
            return location;
        }

        public String getText() {
            return text;
        }

        public String getTemperature() {
            return temperature;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String getStringId() {
            return id;
        }

        @Override
        public int getItemType() {
            return TYPE_WEATHER;
        }
    }

    private List<ResultsEntity> results;

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        /**
         * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * now : {"text":"晴","code":"0","temperature":"15"}
         * last_update : 2018-10-29T13:15:00+08:00
         */

        private LocationEntity location;
        private NowEntity now;
        private String last_update;

        public LocationEntity getLocation() {
            return location;
        }

        public void setLocation(LocationEntity location) {
            this.location = location;
        }

        public NowEntity getNow() {
            return now;
        }

        public void setNow(NowEntity now) {
            this.now = now;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        @Override
        public String toString() {
            return "ResultsEntity{" +
                    "location=" + location +
                    ", now=" + now +
                    ", last_update='" + last_update + '\'' +
                    '}';
        }

        public static class LocationEntity {
            /**
             * id : WX4FBXXFKE4F
             * name : 北京
             * country : CN
             * path : 北京,北京,中国
             * timezone : Asia/Shanghai
             * timezone_offset : +08:00
             */

            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }

            @Override
            public String toString() {
                return "LocationEntity{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", country='" + country + '\'' +
                        ", path='" + path + '\'' +
                        ", timezone='" + timezone + '\'' +
                        ", timezone_offset='" + timezone_offset + '\'' +
                        '}';
            }
        }

        public static class NowEntity {
            /**
             * text : 晴
             * code : 0
             * temperature : 15
             */

            private String text;
            private String code;
            private String temperature;

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

            @Override
            public String toString() {
                return "NowEntity{" +
                        "text='" + text + '\'' +
                        ", code='" + code + '\'' +
                        ", temperature='" + temperature + '\'' +
                        '}';
            }
        }
    }
}
