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
package com.crazysunj.crazydaily.ui.scan;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.module.permission.PermissionHelper;
import com.crazysunj.crazydaily.module.permission.PermissionStorage;
import com.crazysunj.crazydaily.ui.browser.BrowserActivity;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.Decoder;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author: sunjian
 * created on: 2018/7/2 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
@RuntimePermissions
public class ScannerActivity extends BaseActivity implements PermissionStorage {

    private static final int REQUEST_SELECT_PICTURE = 0x01;

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
        setSupportActionBar(mToolbar);
        showBack(mToolbar);
    }

    @Override
    protected void initListener() {
        mToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_scan_open) {
                ScannerActivityPermissionsDispatcher.handleImgWithPermissionCheck(this);
                return true;
            }
            return false;
        });
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
        ScannerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDecoratedBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedUri);
                    if (bitmap != null) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        int[] dataArr = new int[width * height];
                        bitmap.getPixels(dataArr, 0, width, 0, 0, width, height);
                        Decoder decoder = new Decoder(new MultiFormatReader());
                        Result result = decoder.decode(new RGBLuminanceSource(width, height, dataArr));
                        if (result != null) {
                            BrowserActivity.start(this, result.getText());
                        } else {
                            SnackbarUtil.show(this, "这个码真扫不了");
                        }
                        bitmap.recycle();
                    } else {
                        SnackbarUtil.show(this, "这个码真扫不了");
                    }
                } catch (Exception e) {
                    SnackbarUtil.show(this, "这个码真扫不了");
                }
            } else {
                SnackbarUtil.show(this, "这个码真扫不了");
            }
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void handleImg() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }
}
