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
package com.crazysunj.crazydaily.view.web;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.crazysunj.crazydaily.util.DeviceUtils;
import com.crazysunj.domain.constant.CacheConstant;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

/**
 * author: sunjian
 * created on: 2018/5/11 上午9:43
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailyWebView extends WebView {

    private WebViewCallback mWebViewCallback;
    private WebViewSonicCallback mWebViewSonicCallback;

    public CrazyDailyWebView(Context context) {
        this(context, null);
    }

    public CrazyDailyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CrazyDailyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressWarnings("all")
    private void init(Context context) {
        WebSettings setttings = getSettings();
        setttings.setJavaScriptEnabled(true);//打开js
        setttings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置布局
        setttings.setDomStorageEnabled(true);//打开Dom Storage
        setttings.setDatabaseEnabled(true);//打开Database
        setttings.setAppCacheEnabled(true);//打开App Cache
        setttings.setAppCacheMaxSize(Long.MAX_VALUE);
        File cacheDir = new File(context.getExternalCacheDir(), CacheConstant.CACHE_DIR_WEB);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        setttings.setAppCachePath(cacheDir.getAbsolutePath());//设置App Cache缓存目录
        setttings.setSupportMultipleWindows(false);//不支持多窗口
        setttings.setJavaScriptCanOpenWindowsAutomatically(true);//支持js打开新窗口
        setttings.setAllowFileAccess(true);//启用WebView访问文件数据
        setttings.setSupportZoom(true);//支持缩放
        setttings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        setttings.setBuiltInZoomControls(true);//支持手势缩放
        setttings.setLoadWithOverviewMode(true);//缩放至屏幕大小
        setttings.setUseWideViewPort(true);//调整屏幕自适应
        setttings.setDefaultTextEncodingName("utf-8");//设置编码格式为utf-8
        setttings.setLoadsImagesAutomatically(true);//支持自动加载图片
        setttings.setSavePassword(false);//禁止密码保存在本地
        String ua = setttings.getUserAgentString();
        setttings.setUserAgentString(String.format("%s CrazyDaily %s", ua, DeviceUtils.getVersionName()));//重置ua
        setWebViewClient(new CrazyDailyWebViewClient());
        setWebChromeClient(new CrazyDailyWebChromeClient());
    }

    /**
     * 防止内存泄露
     */
    public void onDestroy() {
        loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        clearHistory();
        ((ViewGroup) getParent()).removeView(this);
        setWebViewClient(null);
        setWebChromeClient(null);
        destroy();
    }

    public class CrazyDailyWebViewClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            if (mWebViewSonicCallback != null) {
                WebResourceResponse webResourceResponse;
                try {
                    webResourceResponse = (WebResourceResponse) mWebViewSonicCallback.requestResource(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    webResourceResponse = super.shouldInterceptRequest(webView, s);
                }
                return webResourceResponse;
            }
            return super.shouldInterceptRequest(webView, s);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            if (mWebViewSonicCallback != null) {
                String s = webResourceRequest.getUrl().toString();
                WebResourceResponse webResourceResponse;
                try {
                    webResourceResponse = (WebResourceResponse) mWebViewSonicCallback.requestResource(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    webResourceResponse = super.shouldInterceptRequest(webView, webResourceRequest);
                }
                return webResourceResponse;
            }
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
            if (mWebViewSonicCallback != null) {
                String s = webResourceRequest.getUrl().toString();
                WebResourceResponse webResourceResponse;
                try {
                    webResourceResponse = (WebResourceResponse) mWebViewSonicCallback.requestResource(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    webResourceResponse = super.shouldInterceptRequest(webView, webResourceRequest, bundle);
                }
                return webResourceResponse;
            }
            return super.shouldInterceptRequest(webView, webResourceRequest, bundle);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            if (mWebViewSonicCallback != null) {
                mWebViewSonicCallback.pageFinish(s);
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler handler, SslError sslError) {
            handler.proceed();//处理证书
        }
    }

    public class CrazyDailyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mWebViewCallback != null) {
                mWebViewCallback.onReceivedTitle(title);
            }
        }
    }

    public void setWebViewSonicCallback(WebViewSonicCallback callback) {
        mWebViewSonicCallback = callback;
    }

    public interface WebViewSonicCallback {
        void pageFinish(String url);

        Object requestResource(String url);
    }

    public void setWebViewCallback(WebViewCallback callback) {
        mWebViewCallback = callback;
    }

    public interface WebViewCallback {
        /**
         * 回调title
         */
        void onReceivedTitle(String title);
    }
}
