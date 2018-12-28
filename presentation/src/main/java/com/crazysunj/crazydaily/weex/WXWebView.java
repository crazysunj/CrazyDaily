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
package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.crazysunj.crazydaily.module.web.CrazyDailySonicSessionClient;
import com.crazysunj.crazydaily.view.web.CrazyDailyWebView;
import com.taobao.weex.ui.view.IWebView;
import com.taobao.weex.utils.WXLogUtils;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

/**
 * @author: sunjian
 * created on: 2018/6/11 下午4:32
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WXWebView implements IWebView {

    private String mOrigin;
    private Context mContext;
    private CrazyDailyWebView mWebView;
    private ProgressBar mProgressBar;
    private boolean mShowLoading = true;
    private Handler mMessageHandler;
    private static final int POST_MESSAGE = 1;
    private static final String BRIDGE_NAME = "__WEEX_WEB_VIEW_BRIDGE";
    private static final int SDK_VERSION = Build.VERSION.SDK_INT;
    // downgraded by CVE-2012-6636(https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2012-6636)
    private static final boolean DOWNGRADE_JS_INTERFACE = SDK_VERSION < Build.VERSION_CODES.JELLY_BEAN_MR1;

    private OnErrorListener mOnErrorListener;
    private OnPageListener mOnPageListener;
    private OnMessageListener mOnMessageListener;

    private SonicSession mSonicSession;
    private CrazyDailySonicSessionClient mSessionClient;

    public WXWebView(Context context, String origin) {
        mContext = context;
        mOrigin = origin;
    }

    @Override
    public View getView() {
        FrameLayout root = new FrameLayout(mContext);
        root.setBackgroundColor(Color.WHITE);

        mWebView = new CrazyDailyWebView(mContext);
        FrameLayout.LayoutParams wvLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
        wvLayoutParams.gravity = Gravity.CENTER;
        mWebView.setLayoutParams(wvLayoutParams);
        root.addView(mWebView);
        initWebView(mWebView);

        mProgressBar = new ProgressBar(mContext);
        showProgressBar(false);
        FrameLayout.LayoutParams pLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
        mProgressBar.setLayoutParams(pLayoutParams);
        pLayoutParams.gravity = Gravity.CENTER;
        root.addView(mProgressBar);
        mMessageHandler = new MessageHandler(this);
        return root;
    }

    @Override
    public void destroy() {
        if (mSonicSession != null) {
            mSessionClient.onDestroy();
            mSonicSession.destroy();
        }
    }

    @Override
    public void loadUrl(String url) {
        if (getWebView() == null) {
            return;
        }
        mSonicSession = SonicEngine.getInstance().createSession(url, new SonicSessionConfig.Builder().setSupportLocalServer(true).build());
        if (mSonicSession == null) {
            getWebView().loadUrl(url);
        } else {
            mSonicSession.bindClient(mSessionClient = new CrazyDailySonicSessionClient(mWebView));
            mWebView.setWebViewSonicCallback(new CrazyDailyWebView.WebViewSonicCallback() {
                @Override
                public void pageFinish(String url) {
                    mSessionClient.pageFinish(url);
                }

                @Override
                public Object requestResource(String url) {
                    return mSessionClient.requestResource(url);
                }
            });
            mSessionClient.clientReady();
        }
    }

    @Override
    public void loadDataWithBaseURL(String source) {
        if (getWebView() == null) {
            return;
        }
        getWebView().loadDataWithBaseURL(mOrigin, source, "text/html", "utf-8", null);

    }

    @Override
    public void reload() {
        if (getWebView() == null) {
            return;
        }
        getWebView().reload();
    }

    @Override
    public void goBack() {
        if (getWebView() == null) {
            return;
        }
        getWebView().goBack();
    }

    @Override
    public void goForward() {
        if (getWebView() == null) {
            return;
        }
        getWebView().goForward();
    }

    @Override
    public void postMessage(Object msg) {
        if (getWebView() == null) {
            return;
        }

        try {
            JSONObject initData = new JSONObject();
            initData.put("type", "message");
            initData.put("data", msg);
            initData.put("origin", mOrigin);
            evaluateJS("javascript:(function () {"
                    + "var initData = " + initData.toString() + ";"
                    + "try {"
                    + "var event = new MessageEvent('message', initData);"
                    + "window.dispatchEvent(event);"
                    + "} catch (e) {}"
                    + "})();");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setShowLoading(boolean shown) {
        mShowLoading = shown;
    }

    @Override
    public void setOnErrorListener(OnErrorListener listener) {
        mOnErrorListener = listener;
    }

    @Override
    public void setOnPageListener(OnPageListener listener) {
        mOnPageListener = listener;
    }

    @Override
    public void setOnMessageListener(OnMessageListener listener) {
        mOnMessageListener = listener;
    }

    private void showProgressBar(boolean shown) {
        if (mShowLoading) {
            mProgressBar.setVisibility(shown ? View.VISIBLE : View.GONE);
        }
    }

    private void showWebView(boolean shown) {
        mWebView.setVisibility(shown ? View.VISIBLE : View.INVISIBLE);
    }

    @Nullable
    private WebView getWebView() {
        return mWebView;
    }

    private void initWebView(WebView wv) {
        wv.setWebViewClient(mWebView.new CrazyDailyWebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WXLogUtils.v("tag", "onPageStarted " + url);
                if (mOnPageListener != null) {
                    mOnPageListener.onPageStart(url);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WXLogUtils.v("tag", "onPageFinished " + url);
                if (mOnPageListener != null) {
                    mOnPageListener.onPageFinish(url, view.canGoBack(), view.canGoForward());
                }
                if (mOnMessageListener != null) {
                    evaluateJS("javascript:(window.postMessage = function(message, targetOrigin) {"
                            + "if (message == null || !targetOrigin) return;"
                            + (DOWNGRADE_JS_INTERFACE
                            ? "prompt('" + BRIDGE_NAME + "://postMessage?message=' + JSON.stringify(message) + '&targetOrigin=' + targetOrigin)"
                            : BRIDGE_NAME + ".postMessage(JSON.stringify(message), targetOrigin);")
                            + "})");
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (mOnErrorListener != null) {
                    mOnErrorListener.onError("error", "page error");
                }
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                if (mOnErrorListener != null) {
                    mOnErrorListener.onError("error", "http error");
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                if (mOnErrorListener != null) {
                    mOnErrorListener.onError("error", "ssl error");
                }
            }

        });
        wv.setWebChromeClient(mWebView.new CrazyDailyWebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                showWebView(newProgress == 100);
//                showProgressBar(newProgress != 100);
                WXLogUtils.v("tag", "onPageProgressChanged " + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mOnPageListener != null) {
                    mOnPageListener.onReceivedTitle(title);
                }
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String text, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(text);
                String scheme = uri.getScheme();
                if (TextUtils.equals(scheme, BRIDGE_NAME)) {
                    if (TextUtils.equals(uri.getAuthority(), "postMessage")) {
                        String message = uri.getQueryParameter("message");
                        String targetOrigin = uri.getQueryParameter("targetOrigin");
                        onMessage(message, targetOrigin);
                        result.confirm("success");
                    } else {
                        result.confirm("fail");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, text, defaultValue, result);
            }
        });

        if (!DOWNGRADE_JS_INTERFACE) {
            wv.addJavascriptInterface(new Object() {
                @JavascriptInterface
                public void postMessage(String message, String targetOrigin) {
                    onMessage(message, targetOrigin);
                }
            }, BRIDGE_NAME);
        }
    }

    private void onMessage(String message, String targetOrigin) {
        if (message != null && targetOrigin != null && mOnMessageListener != null) {
            try {
                Map<String, Object> initData = new HashMap<>();
                initData.put("data", JSON.parse(message));
                initData.put("origin", targetOrigin);
                initData.put("type", "message");
                Message threadMessage = new Message();
                threadMessage.what = POST_MESSAGE;
                threadMessage.obj = initData;
                mMessageHandler.sendMessage(threadMessage);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void evaluateJS(String jsStr) {
        if (SDK_VERSION < Build.VERSION_CODES.KITKAT) {
            mWebView.loadUrl(jsStr);
        } else {
            mWebView.evaluateJavascript(jsStr, null);
        }
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<WXWebView> mWv;

        private MessageHandler(WXWebView wv) {
            mWv = new WeakReference<>(wv);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case POST_MESSAGE:
                    if (mWv.get() != null && mWv.get().mOnMessageListener != null) {
                        mWv.get().mOnMessageListener.onMessage((Map<String, Object>) msg.obj);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
