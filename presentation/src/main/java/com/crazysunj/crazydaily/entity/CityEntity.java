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
package com.crazysunj.crazydaily.entity;

import com.sunjian.android_pickview_lib.model.IPickerViewData;

/**
 * @author: sunjian
 * created on: 2017/9/22 下午4:14
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CityEntity implements IPickerViewData {

    public static final String FILE_NAME = "CenterWeatherCityCode.json";

    /**
     * ID : 1
     * cityName : 北京
     * cityEN : Beijing
     * townID : CHBJ000000
     * townName : 北京
     * townEN : Beijing
     */

    private String ID;
    private String cityName;
    private String cityEN;
    private String townID;
    private String townName;
    private String townEN;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityEN() {
        return cityEN;
    }

    public void setCityEN(String cityEN) {
        this.cityEN = cityEN;
    }

    public String getTownID() {
        return townID;
    }

    public void setTownID(String townID) {
        this.townID = townID;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getTownEN() {
        return townEN;
    }

    public void setTownEN(String townEN) {
        this.townEN = townEN;
    }

    @Override
    public String getPickerViewText() {
        return townName;
    }
}
