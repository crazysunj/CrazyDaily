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
package com.crazysunj.crazydaily.ui.browser;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.module.permission.PermissionHelper;
import com.crazysunj.crazydaily.module.permission.PermissionStorage;
import com.crazysunj.crazydaily.module.web.CrazyDailySonicSessionClient;
import com.crazysunj.crazydaily.service.DownloadService;
import com.crazysunj.crazydaily.util.StorageUtil;
import com.crazysunj.crazydaily.view.web.CrazyDailyWebView;
import com.crazysunj.data.util.LoggerUtil;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
@RuntimePermissions
public class BrowserActivity extends BaseActivity implements CrazyDailyWebView.WebViewCallback, PermissionStorage {

    @BindView(R.id.browser_web_container)
    FrameLayout mWebContainer;
    private CrazyDailyWebView mWebView;
    private CrazyDailySonicSessionClient mSessionClient;
    private SonicSession mSonicSession;
    private String mUrl;

    public static void start(Context context, String url) {
        boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, new SonicSessionConfig.Builder().setSupportLocalServer(true).build());
        LoggerUtil.d(LoggerUtil.MSG_WEB, preloadSuccess ? "预加载成功！" : "预加载失败！");
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(ActivityConstant.URL, url);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers(); //暂停所有布局、解析、JS
    }

    @Override
    protected void onDestroy() {
        if (mSonicSession != null) {
            mSessionClient.onDestroy();
            mSonicSession.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        showBack();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //引用getApplicationContext可以防止webview中弹窗，例如长按弹出菜单等
        mWebView = new CrazyDailyWebView(this);
        mWebContainer.addView(mWebView, params);
    }

    @Override
    protected void initListener() {
        mWebView.setWebViewCallback(this);
        mWebView.setDownloadCallback((url, contentLength) ->
                new AlertDialog.Builder(this, R.style.NormalDialog)
                        .setTitle("提示")
                        .setCancelable(false)
                        .setMessage(String.format("下载链接：%s\n下载大小：%sMB", url, StorageUtil.byteToMB(contentLength)))
                        .setNegativeButton("不下", null)
                        .setPositiveButton("下载", (dialogInterface, i) -> BrowserActivityPermissionsDispatcher.startDownloadWithPermissionCheck(this, url))
                        .show());
    }

    @Override
    protected void initData() {
        mUrl = getIntent().getStringExtra(ActivityConstant.URL);
        mSonicSession = SonicEngine.getInstance().createSession(mUrl, new SonicSessionConfig.Builder().setSupportLocalServer(true).build());
        if (mSonicSession == null) {
            mWebView.loadUrl(mUrl);
            Toast.makeText(this, "Sonic加载失败", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_browser_open) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "该url无法解析", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_browser;
    }

    @Override
    public void onReceivedTitle(String title) {
        setTitle(title);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BrowserActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void startDownload(String url) {
        DownloadService.start(this, url);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showRationaleForStorage(PermissionRequest request) {
        PermissionHelper.storageShowRationale(this, request);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showDeniedForStorage() {
        PermissionHelper.storagePermissionDenied(this);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showNeverAskForStorage() {
        PermissionHelper.storageNeverAskAgain(this);
    }
}
