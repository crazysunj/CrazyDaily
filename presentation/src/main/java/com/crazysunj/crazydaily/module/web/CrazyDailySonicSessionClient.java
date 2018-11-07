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

import android.os.Bundle;

import com.crazysunj.crazydaily.view.web.CrazyDailyWebView;
import com.tencent.sonic.sdk.SonicSessionClient;

import java.util.HashMap;

/**
 * @author: sunjian
 * created on: 2018/6/1 上午11:33
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailySonicSessionClient extends SonicSessionClient {

    private CrazyDailyWebView mWebView;

    public CrazyDailySonicSessionClient(CrazyDailyWebView webView) {
        this.mWebView = webView;
    }

    @Override
    public void loadUrl(String url, Bundle extraData) {
        mWebView.loadUrl(url);
    }

    @Override
    public void loadDataWithBaseUrl(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        mWebView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override
    public void loadDataWithBaseUrlAndHeader(String baseUrl, String data, String mimeType, String encoding, String historyUrl, HashMap<String, String> headers) {
        loadDataWithBaseUrl(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public void onDestroy() {
        if (null != mWebView) {
            mWebView.onDestroy();
            mWebView = null;
        }
    }
}
