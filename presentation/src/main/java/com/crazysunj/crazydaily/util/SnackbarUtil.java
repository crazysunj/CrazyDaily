package com.crazysunj.crazydaily.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * author: sunjian
 * created on: 2017/9/6 下午6:26
 * description:
 */

public class SnackbarUtil {

    private SnackbarUtil() {
    }

    public static void show(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void show(Activity activity, String msg) {
        Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }
}
