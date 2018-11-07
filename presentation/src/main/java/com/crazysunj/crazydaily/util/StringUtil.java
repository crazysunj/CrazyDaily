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

/**
 * @author: sunjian
 * created on: 2018/9/21 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class StringUtil {
    private StringUtil() {
    }

    public static String handleTimeStringByMilli(long milli) {
        final long oneSecond = 1000;
        final long oneMinute = 60 * oneSecond;
        final long oneHour = 60 * oneMinute;
        String hourStr = null;
        if (milli >= oneHour) {
            final int hour = (int) (milli / oneHour);
            if (hour > 0 && hour < 10) {
                hourStr = "0" + hour;
            } else {
                hourStr = String.valueOf(hour);
            }
            milli = milli - hour * oneHour;
        }
        String minuteStr;
        if (milli >= oneMinute) {
            final int minute = (int) (milli / oneMinute);
            if (minute > 0 && minute < 10) {
                minuteStr = "0" + minute;
            } else {
                minuteStr = String.valueOf(minute);
            }
            milli = milli - minute * oneMinute;
        } else {
            minuteStr = "00";
        }
        String secondStr;
        if (milli >= oneSecond) {
            final int second = (int) (milli / oneSecond);
            if (second > 0 && second < 10) {
                secondStr = "0" + second;
            } else {
                secondStr = String.valueOf(second);
            }
        } else {
            secondStr = "00";
        }
        return hourStr == null ? minuteStr + ":" + secondStr : hourStr + ":" + minuteStr + ":" + secondStr;
    }
}
