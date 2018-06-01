/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.crazydaily.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.crazysunj.crazydaily.R;
import com.crazysunj.domain.constant.CacheConstant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: sunjian
 * created on: 2017/9/20 下午2:37
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class FileUtil {

    private FileUtil() {
    }

    public static String getText(String text, String defaultText) {
        if (TextUtils.isEmpty(text)) {
            return defaultText;
        }
        return text;
    }

    public static String getFileName(Context context) {
        final String appName = context.getResources().getString(R.string.app_name);
        SimpleDateFormat formatter = new SimpleDateFormat("_yyyyMMdd_HHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        return appName + formatter.format(curDate);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File getDownloadFile(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return null;
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), context.getResources().getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File getWebCacheFile(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return null;
        }
        File file = new File(context.getExternalCacheDir(), CacheConstant.CACHE_DIR_WEB);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File getWebResCacheFile(Context context) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return null;
        }
        File file = new File(context.getExternalCacheDir(), CacheConstant.CACHE_DIR_WEB_RES);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
