package com.crazysunj.domain.util;

import androidx.annotation.ColorInt;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: sunjian
 * created on: 2018/9/21 下午2:32
 * description:
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
