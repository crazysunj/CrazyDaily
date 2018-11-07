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
package com.crazysunj.crazydaily.view.contact;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

/**
 * @author: sunjian
 * created on: 2018/4/18 下午5:17
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ImgResEntity {
    int res;
    int color;

    public ImgResEntity(@DrawableRes int res, @ColorInt int color) {
        this.res = res;
        this.color = color;
    }
}
