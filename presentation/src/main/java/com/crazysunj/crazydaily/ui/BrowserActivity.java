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
package com.crazysunj.crazydaily.ui;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class BrowserActivity extends BaseActivity {

    @BindView(R.id.browser_web)
    WebView mWeb;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(ActivityConstant.URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        final WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
    }

    @Override
    protected void initListener() {
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        mWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra(ActivityConstant.URL);
        mWeb.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWeb.canGoBack()) {
            mWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_browser;
    }

    @Override
    protected void initInject() {
    }
}
