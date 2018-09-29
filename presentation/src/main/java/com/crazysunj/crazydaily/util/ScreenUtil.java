package com.crazysunj.crazydaily.util;

import android.content.Context;

/**
 * author: sunjian
 * created on: 2018/9/25 下午2:54
 * description:
 */
public class ScreenUtil {
    private ScreenUtil() {
    }

    public static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
