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
package com.crazysunj.crazydaily.ui.photo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.module.permission.PermissionHelper;
import com.crazysunj.crazydaily.module.permission.PermissionStorage;
import com.crazysunj.crazydaily.util.DeviceUtil;
import com.crazysunj.crazydaily.util.FileUtil;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.crazydaily.view.photo.PhotoDrawerLayout;
import com.crazysunj.domain.constant.CacheConstant;
import com.github.chrisbanes.photoview.PhotoView;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author: sunjian
 * created on: 2018/4/28 下午1:41
 * description: https://github.com/crazysunj/CrazyDaily
 */
@RuntimePermissions
public class PhotoActivity extends BaseActivity implements PermissionStorage {

    private static final String DEFAULT_COMPRESS_QUALITY = "90";

    @BindView(R.id.photo)
    PhotoView mPhoto;
    @BindView(R.id.photo_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.photo_drawer)
    PhotoDrawerLayout mPhotoDrawer;


    @BindView(R.id.photo_crop_ratio_radio_group)
    RadioGroup mCropRadioRadioGroup;
    @BindView(R.id.photo_crop_ratio_freestyle)
    CheckBox mCropRatioFreestyle;
    @BindView(R.id.photo_crop_ratio_origin)
    RadioButton mCropRatioOrigin;
    @BindView(R.id.photo_crop_ratio_square)
    RadioButton mCropRatioSquare;
    @BindView(R.id.photo_crop_ratio_x)
    EditText mCropRatioX;
    @BindView(R.id.photo_crop_ratio_y)
    EditText mCropRatioY;
    @BindView(R.id.photo_crop_resolution)
    CheckBox mCropResolution;
    @BindView(R.id.photo_crop_resolution_width)
    EditText mCropResolutionWidth;
    @BindView(R.id.photo_crop_resolution_height)
    EditText mCropResolutionHeight;
    @BindView(R.id.photo_crop_compress_radio_group)
    RadioGroup mCropCompressRadioGroup;
    @BindView(R.id.photo_crop_compress_jpg)
    RadioButton mCropCompressJPG;
    @BindView(R.id.photo_crop_compress_png)
    RadioButton mCropCompressPNG;
    @BindView(R.id.photo_crop_quality)
    EditText mCropQuality;
    @BindView(R.id.photo_crop_ui_setting)
    CheckBox mCropUISetting;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mUrl;

    public static void start(Activity activity, String url, View view) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(ActivityConstant.URL, url);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transition_photo));
        activity.startActivity(intent, options.toBundle());
    }

    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(ActivityConstant.URL, url);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        handleStatusbarTransparent();
        mPhotoDrawer.setScrimColor(Color.TRANSPARENT);
        mDrawerToggle = new ActionBarDrawerToggle(this, mPhotoDrawer, mToolbar, R.string.open, R.string.close);

        mCropRadioRadioGroup.check(R.id.photo_crop_ratio_origin);
        mCropRatioX.addTextChangedListener(mCropRatioTextWatcher);
        mCropRatioY.addTextChangedListener(mCropRatioTextWatcher);
        mCropCompressRadioGroup.check(R.id.photo_crop_compress_jpg);
        mCropQuality.setText(DEFAULT_COMPRESS_QUALITY);
        mCropQuality.addTextChangedListener(mCropQualityTextWatcher);
        mCropResolutionWidth.addTextChangedListener(mCropResolutionTextWatcher);
        mCropResolutionHeight.addTextChangedListener(mCropResolutionTextWatcher);
    }

    @Override
    protected void initData() {
        mUrl = getIntent().getStringExtra(ActivityConstant.URL);
        ImageLoader.loadNoCrop(this, mUrl, mPhoto);
    }

    @Override
    protected void initListener() {
        mPhotoDrawer.addDrawerListener(new PhotoDrawerListener());
        mToolbar.setOnMenuItemClickListener(this::handleMenuItemClick);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * 处理状态栏透明
     */
    private void handleStatusbarTransparent() {
        final Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup content = findViewById(android.R.id.content);
        for (int i = 0, count = content.getChildCount(); i < count; i++) {
            View child = content.getChildAt(i);
            if (child instanceof ViewGroup) {
                child.setFitsSystemWindows(true);
                ((ViewGroup) child).setClipToPadding(false);
            }
        }

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mPhotoDrawer.getLayoutParams();
        params.topMargin = -DeviceUtil.getStatusBarHeight(this);
        mPhotoDrawer.setLayoutParams(params);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void startCrop(@NonNull Uri uri) {
        String destinationFileName = FileUtil.getFileName(this);
        switch (mCropCompressRadioGroup.getCheckedRadioButtonId()) {
            case R.id.photo_crop_compress_png:
                destinationFileName += ".png";
                break;
            case R.id.photo_crop_compress_jpg:
            default:
                destinationFileName += ".jpg";
                break;
        }
        File parent = new File(getExternalCacheDir(), CacheConstant.CACHE_DIR_IMG);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        Uri destUri = Uri.fromFile(new File(parent, destinationFileName));
        UCrop uCrop = UCrop.of(uri, destUri);
        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
        uCrop.start(this);
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            mUrl = resultUri.toString();
            ImageLoader.loadNoCrop(this, mUrl, mPhoto);
        } else {
            SnackbarUtil.show(this, "不好意思，裁剪开小差了！");
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            SnackbarUtil.show(this, cropError.getMessage());
        } else {
            SnackbarUtil.show(this, "不好意思，裁剪开小差了！");
        }
    }

    /**
     * 基础配置
     */
    private UCrop basisConfig(@NonNull UCrop uCrop) {
        switch (mCropRadioRadioGroup.getCheckedRadioButtonId()) {
            case R.id.photo_crop_ratio_origin:
                break;
            case R.id.photo_crop_ratio_square:
                uCrop = uCrop.withAspectRatio(1, 1);
                break;
            default:
                try {
                    float ratioX = Float.valueOf(mCropRatioX.getText().toString().trim());
                    float ratioY = Float.valueOf(mCropRatioY.getText().toString().trim());
                    if (ratioX > 0 && ratioY > 0) {
                        uCrop = uCrop.withAspectRatio(ratioX, ratioY);
                    }
                } catch (NumberFormatException e) {
                    SnackbarUtil.show(this, "要输入数字哦！");
                }
                break;
        }
        if (mCropResolution.isChecked()) {
            try {
                int maxWidth = Integer.valueOf(mCropResolutionWidth.getText().toString().trim());
                int maxHeight = Integer.valueOf(mCropResolutionHeight.getText().toString().trim());
                if (maxWidth > UCrop.MIN_SIZE && maxHeight > UCrop.MIN_SIZE) {
                    uCrop = uCrop.withMaxResultSize(maxWidth, maxHeight);
                }
            } catch (NumberFormatException e) {
                SnackbarUtil.show(this, "要输入数字哦！");
            }
        }
        return uCrop;
    }

    /**
     * 其它配置
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        switch (mCropCompressRadioGroup.getCheckedRadioButtonId()) {
            case R.id.photo_crop_compress_png:
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                break;
            case R.id.photo_crop_compress_jpg:
            default:
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                break;
        }
        options.setCompressionQuality(getCompressionQuality());
        options.setHideBottomControls(mCropUISetting.isChecked());
        options.setFreeStyleCropEnabled(mCropRatioFreestyle.isChecked());

        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.color_white));
        return uCrop.withOptions(options);
    }

    private int getCompressionQuality() {
        String qualityStr = mCropQuality.getText().toString().trim();
        if (TextUtils.isEmpty(qualityStr)) {
            return 10;
        }
        int quality = Integer.parseInt(qualityStr);
        if (quality < 10) {
            return 10;
        }
        return quality;
    }

    private boolean handleMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_photo_edit:
                if (TextUtils.isEmpty(mUrl)) {
                    return true;
                }
                startCrop(Uri.parse(mUrl));
                break;
            case R.id.menu_photo_save:
                PhotoActivityPermissionsDispatcher.saveImageWithPermissionCheck(this);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveImage() {
        if (TextUtils.isEmpty(mUrl)) {
            SnackbarUtil.show(this, "好可怜，保存失败！");
            return;
        }
        File downloadFile = FileUtil.getDownloadFile(this);
        if (downloadFile == null) {
            SnackbarUtil.show(this, "好可怜，保存失败！");
            return;
        }
        final String fileName = FileUtil.getFileName(this) + mUrl.substring(mUrl.lastIndexOf("."));
        final File saveFile = new File(downloadFile, fileName);
        ImageLoader.downloadFile(this, mUrl, saveFile, isSuccess -> {
            if (isSuccess) {
                SnackbarUtil.showLong(this, "保存成功！路径：" + saveFile.getAbsolutePath());
            } else {
                SnackbarUtil.show(this, "好可怜，保存失败！");
            }
        });
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

    private TextWatcher mCropRatioTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mCropRadioRadioGroup.clearCheck();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher mCropQualityTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable s) {
            if (s == null) {
                return;
            }
            String qualityStr = s.toString().trim();
            if (TextUtils.isEmpty(qualityStr)) {
                return;
            }
            int quality = Integer.parseInt(qualityStr);
            if (quality > 100) {
                String text = "100";
                mCropQuality.setText(text);
                mCropQuality.setSelection(text.length());
            }

            if (quality < 10) {
                Toast.makeText(PhotoActivity.this, "百分比不能小于10哦！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private TextWatcher mCropResolutionTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && !s.toString().trim().isEmpty()) {
                if (Integer.valueOf(s.toString()) < UCrop.MIN_SIZE) {
                    Toast.makeText(PhotoActivity.this, String.format(Locale.getDefault(), "分辨率大小不能小于%d哦！", UCrop.MIN_SIZE), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private class PhotoDrawerListener implements DrawerLayout.DrawerListener {

        private static final float SCALE_RATIO = 0.7f;
        private static final float ALPHA_RATIO = 0.6f;
        private static final float SCROLL_RATIO = 0.4f;

        @SuppressWarnings({"SuspiciousNameCombination", "UnnecessaryLocalVariable"})
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            final View content = mPhotoDrawer.getChildAt(0);
            final View menu = drawerView;
            final float leftScale = SCALE_RATIO + (1 - SCALE_RATIO) * slideOffset;
            final float rightScale = 1 - (1 - SCALE_RATIO) * slideOffset;
            final int slideWidth = menu.getMeasuredWidth();
            //设置缩放基点
            menu.setPivotX(menu.getMeasuredWidth());
            menu.setScaleX(leftScale);
            menu.setScaleY(leftScale);
            menu.setAlpha(ALPHA_RATIO + (1 - ALPHA_RATIO) * slideOffset);
            //setTranslationX会超出区域，不美观，但scrollTo也存在缺点
            menu.scrollTo((int) (-slideWidth * SCROLL_RATIO * (1 - slideOffset)), 0);
            content.setTranslationX(slideWidth * slideOffset);
            content.setPivotX(0);
            content.setScaleX(rightScale);
            content.setScaleY(rightScale);
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }
}
