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
package com.crazysunj.crazydaily.ui.home;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.cardslideview.CardViewPager;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.WeexConstant;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.entity.CityEntity;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.module.permission.PermissionCamera;
import com.crazysunj.crazydaily.module.permission.PermissionHelper;
import com.crazysunj.crazydaily.module.permission.PermissionStorage;
import com.crazysunj.crazydaily.presenter.HomePresenter;
import com.crazysunj.crazydaily.presenter.contract.HomeContract;
import com.crazysunj.crazydaily.service.DownloadService;
import com.crazysunj.crazydaily.ui.MainActivity;
import com.crazysunj.crazydaily.ui.adapter.HomeAdapter;
import com.crazysunj.crazydaily.ui.adapter.helper.HomeAdapterHelper;
import com.crazysunj.crazydaily.ui.browser.BrowserActivity;
import com.crazysunj.crazydaily.ui.contact.ContactActivity;
import com.crazysunj.crazydaily.ui.me.MeActivity;
import com.crazysunj.crazydaily.ui.note.NoteActivity;
import com.crazysunj.crazydaily.ui.photo.PhotoActivity;
import com.crazysunj.crazydaily.ui.scan.ScannerActivity;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.crazydaily.view.banner.BannerCardHandler;
import com.crazysunj.crazydaily.view.banner.WrapBannerView;
import com.crazysunj.crazydaily.view.threed.CubeReversalView;
import com.crazysunj.crazydaily.weex.WeexActivity;
import com.crazysunj.data.util.LoggerUtil;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.entity.weather.WeatherXinZhiEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.domain.util.JsonUtil;
import com.crazysunj.domain.util.NeihanManager;
import com.crazysunj.domain.util.ThreadManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sunjian.android_pickview_lib.BaseOptionsPickerDialog;
import com.sunjian.android_pickview_lib.PhoneOptionsPickerDialog;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View, PermissionCamera, PermissionStorage {

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.home_list)
    RecyclerView mHomeList;
    @BindView(R.id.home_vp)
    CardViewPager mHomeBanner;
    @BindView(R.id.wrap_banner)
    WrapBannerView mWrapBanner;
    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.home_appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.home_title)
    TextView mTitle;
    @BindView(R.id.home_navigition)
    BottomNavigationView mBottomNavigation;

    @BindView(R.id.cube_anchor)
    CubeReversalView mCubeAnchor;
    @BindView(R.id.cube_first)
    CubeReversalView mCubeFirst;
    @BindView(R.id.cube_second)
    CubeReversalView mCubeSecond;
    @BindView(R.id.bottom_shadow)
    ImageView mShadow;

    @Inject
    HomeAdapter mAdapter;

    @Inject
    NeihanManager mNeihanManager;

    private PhoneOptionsPickerDialog mGankioDialog;
    private PhoneOptionsPickerDialog mWeatherDialog;
    private ArrayList<CityEntity> mCityList;
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    private int gaoxiaoIndex = 1;
    private boolean isTop = true;
    private List<String> mMeinvList;
    private Drawable mNavigationIcon;

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        handleTranslucent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.startBanner();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.endBanner();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        ThreadManager.shutdown();
        super.onDestroy();
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.mipmap.ic_scan);
        mNavigationIcon = mToolbar.getNavigationIcon();
        mHomeList.setLayoutManager(new LinearLayoutManager(this));
        mHomeList.setAdapter(mAdapter);
        mWrapBanner.setOnBannerSlideListener(this::handleWrapBanner);
    }

    @Override
    protected void initListener() {
        mRefresh.setOnRefreshListener(this::onRefresh);
        mAdapter.setOnHeaderClickListener(this::handleHeaderOptions);
        mAppbar.addOnOffsetChangedListener(this::handleAppbarOffsetChangedListener);
        mToolbar.setNavigationOnClickListener(v -> HomeActivityPermissionsDispatcher.openQRCodeWithPermissionCheck(this));
        mBottomNavigation.setOnNavigationItemSelectedListener(this::handleNavigationItemClick);
        mCubeAnchor.setOnClickListener(v -> clickCubeAnchor());
        mCubeFirst.setOnClickListener(v -> clickCubeFirst());
        mCubeSecond.setOnClickListener(v -> clickCubeSecond());
        mAdapter.setDownloadCallback(url -> HomeActivityPermissionsDispatcher.downloadWithPermissionCheck(this, url));
    }

    @Override
    protected void initData() {
        mPresenter.getZhihuNewsList();
        mPresenter.getGankioList(GankioEntity.ResultsEntity.PARAMS_ANDROID);
        // 天气功能api有点问题，暂时先停止
//        mPresenter.getWeather("CHZJ000000");
        mPresenter.getWeather("杭州");
        mPresenter.getGaoxiaoList(gaoxiaoIndex);
        mPresenter.getMeinvList();
//        mPresenter.getNeihanList(mNeihanManager.getAmLocTime(), mNeihanManager.getMinTime(), mNeihanManager.getSceenWidth(),
//                mNeihanManager.getIid(), mNeihanManager.getDeviceId(), mNeihanManager.getAc(), mNeihanManager.getVersionCode(),
//                mNeihanManager.getVersionName(), Build.MODEL, Build.BRAND, Build.VERSION.SDK_INT, Build.VERSION.RELEASE, mNeihanManager.getUuid(),
//                mNeihanManager.getOpenudid(), mNeihanManager.getManifestVersionCode(), mNeihanManager.getResolution(),
//                String.valueOf(getResources().getDisplayMetrics().densityDpi), mNeihanManager.getUpdateVersionCode());
    }

    @Override
    public void showZhihu(ZhihuNewsEntity zhihuNewsEntity) {
        stopRefresh();
        List<ZhihuNewsEntity.StoriesEntity> stories = zhihuNewsEntity.getStories();
        if (stories != null && !stories.isEmpty()) {
            mAdapter.notifyZhihuNewsList(stories);
        }

        List<ZhihuNewsEntity.TopStoriesEntity> topStories = zhihuNewsEntity.getTop_stories();
        if (topStories == null || topStories.isEmpty()) {
            mHomeBanner.setVisibility(View.GONE);
        } else {
            mHomeBanner.setVisibility(View.VISIBLE);
            mHomeBanner.bind(getSupportFragmentManager(), new BannerCardHandler(), topStories);
        }
    }

    @Override
    public void showGankio(List<GankioEntity.ResultsEntity> gankioList) {
        stopRefresh();
        mAdapter.notifyGankioList(gankioList);
    }

    @Override
    public void showWeather(WeatherXinZhiEntity.FinalEntity weatherEntity) {
        stopRefresh();
        mAdapter.notifyWeatherEntity(weatherEntity);
    }

    @Override
    public void showNeihan(List<NeihanItemEntity> neihanList) {
        stopRefresh();
        mAdapter.notifyNeihanList(neihanList);
    }

    @Override
    public void showGaoxiao(List<GaoxiaoItemEntity> gaoxiaoList) {
        stopRefresh();
        mAdapter.notifyGaoxiaoList(gaoxiaoList);
    }

    @Override
    public void showMeinv(List<String> meinvList) {
        stopRefresh();
        ImageLoader.loadWithVignette(this, meinvList.get(0), R.drawable.img_default, mCubeAnchor.getForegroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(1), R.drawable.img_default, mCubeAnchor.getBackgroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(2), R.drawable.img_default, mCubeFirst.getForegroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(3), R.drawable.img_default, mCubeFirst.getBackgroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(4), R.drawable.img_default, mCubeSecond.getForegroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(5), R.drawable.img_default, mCubeSecond.getBackgroundView());
        mShadow.setVisibility(View.VISIBLE);
        mMeinvList = meinvList;
    }

    @Override
    public void errorMeinv() {
        stopRefresh();
        mShadow.setVisibility(View.GONE);
    }

    @Override
    public void switchBanner() {
        mHomeBanner.setCurrentItem(mHomeBanner.getCurrentItem() + 1, true);
    }

    @Override
    public void showError(String msg) {
        stopRefresh();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInject() {
        getActivityComponent(new EntityModule())
                .inject(this);
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        if (!isTop) {
            clickCubeAnchor();
            return;
        }
        showExitDialog();
    }

    private void onRefresh() {
        mPresenter.endBanner();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        initData();
    }

    private void handleTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setNavigationBarColor(Color.WHITE);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup parent = (ViewGroup) findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    private void handleWrapBanner(boolean isCanSlide) {
        if (isCanSlide) {
            mPresenter.startBanner();
        } else {
            mPresenter.endBanner();
        }
    }

    private void clickCubeAnchor() {
        isTop = !isTop;
        mCubeSecond.start(isTop, 2);
        mCubeFirst.start(isTop, 1);
        mCubeAnchor.start(isTop);
    }

    private void clickCubeSecond() {
        if (mMeinvList == null) {
            return;
        }
        String url;
        View view;
        if (isTop) {
            url = mMeinvList.get(4);
            view = mCubeSecond.getForegroundView();
        } else {
            url = mMeinvList.get(5);
            view = mCubeSecond.getBackgroundView();
        }
        PhotoActivity.start(this, url, view);
    }

    private void clickCubeFirst() {
        if (mMeinvList == null) {
            return;
        }
        String url;
        View view;
        if (isTop) {
            url = mMeinvList.get(2);
            view = mCubeFirst.getForegroundView();
        } else {
            url = mMeinvList.get(3);
            view = mCubeFirst.getBackgroundView();
        }
        PhotoActivity.start(this, url, view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        String scanResult = result.getContents();
        if (scanResult != null) {
            BrowserActivity.start(this, scanResult);
        }
    }

    private boolean handleNavigationItemClick(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case R.id.navigation_contact:
                ContactActivity.start(this);
                break;
            case R.id.navigation_note:
                NoteActivity.start(this);
                break;
//            case R.id.navigation_live:
//                SnackbarUtil.show(this, "直播模块敬请期待!");
//                break;
            case R.id.navigation_music:
//                WeexActivity.start(HomeActivity.this, WeexConstant.PAGE_NAME_ABOUT_ME, WeexConstant.PATH_ABOUT_ME);
                SnackbarUtil.show(this, "音乐模块敬请期待!");
                break;
            case R.id.navigation_me:
                MeActivity.start(this);
                break;
            case R.id.navigation_more:
//                SnackbarUtil.show(this, "更多模块敬请期待!");
                MainActivity.start(this);
//                PhotoPickerActivity.start(this);
//                NoteEditActivity.start(this);
                break;
            default:
                MeActivity.start(this);
                break;
        }
        return true;
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出CrazyDaily吗");
        builder.setNegativeButton("再玩玩", null);
        builder.setPositiveButton("忍住泪水离开", (dialogInterface, i) -> App.getInstance().exitApp());
        builder.show();
    }

    private void handleHeaderOptions(int level, String options) {
        switch (level) {
            case HomeAdapterHelper.LEVEL_GANK_IO:
                if (mGankioDialog == null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(BaseOptionsPickerDialog.CYCLIC_FIRST, true);
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(GankioEntity.ResultsEntity.PARAMS_ANDROID);
                    data.add(GankioEntity.ResultsEntity.PARAMS_IOS);
                    data.add(GankioEntity.ResultsEntity.PARAMS_H5);
                    data.add(GankioEntity.ResultsEntity.PARAMS_ALL_WEEX);
                    mGankioDialog = PhoneOptionsPickerDialog.newInstance(bundle, data);
                    mGankioDialog.setOnoptionsSelectListener((options1, option2, options3) -> {
                        final String selectOption = data.get(options1);
                        if (selectOption.equals(options)) {
                            return;
                        }
                        if (GankioEntity.ResultsEntity.PARAMS_ALL_WEEX.equals(selectOption)) {
                            WeexActivity.start(HomeActivity.this, WeexConstant.PAGE_NAME_GANK_IO, WeexConstant.PATH_GANK_IO);
                            return;
                        }
                        mPresenter.getGankioList(selectOption);
                    });
                }
                mGankioDialog.show(getFragmentManager(), "GankioDialog");
                break;

            case HomeAdapterHelper.LEVEL_WEATHER:
                if (mCityList == null) {
                    String json = JsonUtil.readLocalJson(this, CityEntity.FILE_NAME);
                    if (TextUtils.isEmpty(json)) {
                        LoggerUtil.d("城市Json数据读取失败！");
                        return;
                    }
                    mCityList = JsonUtil.fromJsonList(json, CityEntity.class);
                }
                if (mWeatherDialog == null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(BaseOptionsPickerDialog.CYCLIC_FIRST, true);
                    mWeatherDialog = PhoneOptionsPickerDialog.newInstance(bundle, mCityList);
                    mWeatherDialog.setOnoptionsSelectListener((options1, option2, options3) -> {
                        final String selectOption = mCityList.get(options1).getCityName();
                        if (selectOption.equals(options)) {
                            return;
                        }
                        mPresenter.getWeather(selectOption);
                    });
                }
                mWeatherDialog.show(getFragmentManager(), "WeatherDialog");
                break;
            case HomeAdapterHelper.LEVEL_ZHIHU:
                SnackbarUtil.show(this, "已经最新了，别点了！");
                break;
            case HomeAdapterHelper.LEVEL_GAOXIAO:
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                mPresenter.getGaoxiaoList(++gaoxiaoIndex);
                break;
            default:
                break;
        }
    }

    private void handleAppbarOffsetChangedListener(AppBarLayout appBarLayout, int verticalOffset) {
        final int totalScrollRange = appBarLayout.getTotalScrollRange();
        final float percent = Math.abs(verticalOffset * 1.0f / totalScrollRange);
        mRefresh.setEnabled(verticalOffset == 0);
        final int color = ContextCompat.getColor(this, R.color.colorPrimary);
        mTitle.setTextColor((int) mArgbEvaluator.evaluate(percent, color, Color.BLACK));
        if (mNavigationIcon != null) {
            mNavigationIcon.setColorFilter((int) mArgbEvaluator.evaluate(percent, color, Color.BLACK), PorterDuff.Mode.SRC_IN);
        }
    }

    private void stopRefresh() {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        HomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA})
    void openQRCode() {
        new IntentIntegrator(this)
                .setCaptureActivity(ScannerActivity.class).initiateScan();
    }

    @OnShowRationale({Manifest.permission.CAMERA})
    @Override
    public void showRationaleForCamera(PermissionRequest request) {
        PermissionHelper.cameraShowRationale(this, request);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA})
    @Override
    public void showDeniedForCamera() {
        PermissionHelper.cameraPermissionDenied(this);
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA})
    @Override
    public void showNeverAskForCamera() {
        PermissionHelper.cameraNeverAskAgain(this);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void download(String url) {
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
