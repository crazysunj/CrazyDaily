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
package com.crazysunj.crazydaily.ui.scan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/7/2 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ScannerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.scanner_decorated_barcode)
    DecoratedBarcodeView mDecoratedBarcodeView;

    private CaptureManager mCaptureManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCaptureManager = new CaptureManager(this, mDecoratedBarcodeView);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        mCaptureManager.decode();
    }

    @Override
    protected void initView() {
        showBack(mToolbar);
        mToolbar.setTitle(R.string.page_name_scan);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_scanner;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCaptureManager.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDecoratedBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
