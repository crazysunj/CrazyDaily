package com.crazysunj.crazydaily.util;

import android.text.TextUtils;

/**
 * author: sunjian
 * created on: 2017/9/20 下午2:37
 * description:
 */

public class StringUtil {

    private StringUtil() {
    }

    public static String getText(String text, String defaultText) {
        if (TextUtils.isEmpty(text)) {
            return defaultText;
        }
        return text;
    }
}
