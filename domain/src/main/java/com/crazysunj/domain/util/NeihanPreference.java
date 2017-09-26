package com.crazysunj.domain.util;

import android.content.Context;
import android.support.annotation.NonNull;

import net.grandcentrix.tray.TrayPreferences;

import javax.inject.Inject;


/**
 * author: sunjian
 * created on: 2017/9/23 下午9:56
 * description:
 */
class NeihanPreference extends TrayPreferences {

    private static final String MODEL_NAME = "Neihan";

    @Inject
    NeihanPreference(@NonNull Context context) {
        super(context.getApplicationContext(), MODEL_NAME, 1);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
