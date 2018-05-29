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
package com.crazysunj.crazydaily.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.moudle.ImageLoader;
import com.crazysunj.crazydaily.view.photo.PhotoDrawerLayout;
import com.github.chrisbanes.photoview.PhotoView;
import com.yalantis.ucrop.UCrop;

import java.util.Locale;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/4/28 下午1:41
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class PhotoActivity extends BaseActivity {

    private static final int DEFAULT_COMPRESS_QUALITY = 90;

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
    @BindView(R.id.photo_crop_quality_text)
    TextView mCropQualityText;
    @BindView(R.id.photo_crop_quality)
    SeekBar mCropQuality;
    @BindView(R.id.photo_crop_ui_setting)
    CheckBox mCropUISetting;


    private ActionBarDrawerToggle mDrawerToggle;

    public static void start(Activity activity, String url, View view) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(ActivityConstant.URL, url);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transition_photo));
        activity.startActivity(intent, options.toBundle());
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        handleStatusbarTransparent();
        mPhotoDrawer.setScrimColor(Color.TRANSPARENT);
        mDrawerToggle = new ActionBarDrawerToggle(this, mPhotoDrawer, mToolbar, R.string.about_me_blog, R.string.about_me_blog_text);


        mCropRadioRadioGroup.check(R.id.photo_crop_ratio_origin);
        mCropRatioX.addTextChangedListener(mCropRatioTextWatcher);
        mCropRatioY.addTextChangedListener(mCropRatioTextWatcher);
        mCropCompressRadioGroup.check(R.id.photo_crop_compress_jpg);
        mCropQuality.setProgress(DEFAULT_COMPRESS_QUALITY);
        mCropQualityText.setText(String.format(Locale.getDefault(), "百分比: %d", mCropQuality.getProgress()));
        mCropQuality.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mCropResolutionWidth.addTextChangedListener(mCropResolutionTextWatcher);
        mCropResolutionHeight.addTextChangedListener(mCropResolutionTextWatcher);
    }

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mCropQualityText.setText(String.format(Locale.getDefault(), "百分比: %d", progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

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

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra(ActivityConstant.URL);
        ImageLoader.load(this, url, mPhoto);
    }

    @Override
    protected void initListener() {
        mPhotoDrawer.addDrawerListener(new PhotoDrawerListener());
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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
        final Resources resources = getResources();
        params.topMargin = -resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
        mPhotoDrawer.setLayoutParams(params);
    }

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
