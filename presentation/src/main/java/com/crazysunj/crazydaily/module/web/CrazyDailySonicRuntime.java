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
package com.crazysunj.crazydaily.module.web;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.crazysunj.crazydaily.util.FileUtil;
import com.crazysunj.data.util.LoggerUtil;
import com.crazysunj.domain.util.ThreadManager;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicRuntime;
import com.tencent.sonic.sdk.SonicSessionClient;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author: sunjian
 * created on: 2018/6/1 上午11:03
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailySonicRuntime extends SonicRuntime {

    public CrazyDailySonicRuntime(Context context) {
        super(context);
    }

    @Override
    public void log(String tag, int level, String message) {
        switch (level) {
            case Log.ERROR:
                LoggerUtil.e(tag, message);
                break;
            default:
                LoggerUtil.d(tag, message);
        }
    }

    @Override
    public String getCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }

    @Override
    public boolean setCookie(String url, List<String> cookies) {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size() > 0) {
            CookieManager cookieManager = CookieManager.getInstance();
            for (String cookie : cookies) {
                cookieManager.setCookie(url, cookie);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getUserAgent() {
        return null;
    }

    @Override
    public String getCurrentUserAccount() {
        return null;
    }

    @Override
    public boolean isSonicUrl(String url) {
        return true;
    }

    @Override
    public Object createWebResourceResponse(String mimeType, String encoding, InputStream data, Map<String, String> headers) {
        WebResourceResponse resourceResponse = new WebResourceResponse(mimeType, encoding, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.setResponseHeaders(headers);
        }
        return resourceResponse;
    }

    @Override
    public boolean isNetworkValid() {
        return true;
    }

    @Override
    public void showToast(CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    @Override
    public void postTaskToThread(Runnable task, long delayMillis) {
        ThreadManager.single().execute(task);
    }

    @Override
    public void notifyError(SonicSessionClient client, String url, int errorCode) {

    }

    @Override
    public File getSonicCacheDir() {
        File webCacheFile = FileUtil.getWebCacheFile(context);
        if (webCacheFile == null) {
            notifyError(null, "Web Cache", SonicConstants.ERROR_CODE_MAKE_DIR_ERROR);
        }
        return webCacheFile;
    }

    @Override
    public File getSonicResourceCacheDir() {
        File webResCacheFile = FileUtil.getWebResCacheFile(context);
        if (webResCacheFile == null) {
            notifyError(null, "WebRes Cache", SonicConstants.ERROR_CODE_MAKE_DIR_ERROR);
        }
        return webResCacheFile;
    }
}
