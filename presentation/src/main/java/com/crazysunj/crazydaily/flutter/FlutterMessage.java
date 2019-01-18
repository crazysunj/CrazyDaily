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
package com.crazysunj.crazydaily.flutter;

import com.crazysunj.domain.util.JsonUtil;

import java.util.Map;

/**
 * @author: sunjian
 * created on: 2019/1/18 上午10:03
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class FlutterMessage {
    /**
     * 消息类型
     */
    private String type;

    /**
     * 附带参数，key-value形式
     */
    private Map<String, String> params;

    public static FlutterMessage get(String type) {
        return new FlutterMessage(type, null);
    }

    public static FlutterMessage get(String type, Map<String, String> params) {
        return new FlutterMessage(type, params);
    }

    private FlutterMessage(String type, Map<String, String> params) {
        this.type = type;
        this.params = params;
    }

    public String toJson() {
        return JsonUtil.objectToString(this);
    }
}
