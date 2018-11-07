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
package com.crazysunj.domain.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.ColorInt;

/**
 * @author: sunjian
 * created on: 2018/9/21 下午2:32
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ColorUtil {
    private ColorUtil() {
    }

    public static CharSequence handleKeyWordHighLight
            (String originStr, String keyWord, @ColorInt int hightLightColor) {
        SpannableString ss = new SpannableString(originStr);
        Pattern p = Pattern.compile(keyWord);
        Matcher m = p.matcher(ss);
        while (m.find()) {
            ss.setSpan(new ForegroundColorSpan(hightLightColor), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
}
