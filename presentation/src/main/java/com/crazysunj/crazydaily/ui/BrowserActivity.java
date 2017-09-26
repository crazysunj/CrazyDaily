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
