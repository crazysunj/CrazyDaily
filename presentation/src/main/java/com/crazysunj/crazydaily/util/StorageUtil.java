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

import java.math.BigDecimal;

/**
 * @author: sunjian
 * created on: 2018/7/4 上午10:57
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class StorageUtil {
    private StorageUtil() {
    }

    public static String byteToMB(long contentLength) {
        return new BigDecimal(contentLength).divide(new BigDecimal(1024 * 1024), 2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }
}
