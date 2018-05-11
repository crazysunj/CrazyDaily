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

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.cardslideview.CardViewPager;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.entity.CityEntity;
import com.crazysunj.crazydaily.moudle.ImageLoader;
import com.crazysunj.crazydaily.presenter.HomePresenter;
import com.crazysunj.crazydaily.presenter.contract.HomeContract;
import com.crazysunj.crazydaily.ui.adapter.HomeAdapter;
import com.crazysunj.crazydaily.ui.adapter.helper.HomeAdapterHelper;
import com.crazysunj.crazydaily.ui.contact.ContactActivity;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.crazydaily.view.banner.BannerCardHandler;
import com.crazysunj.crazydaily.view.banner.WrapBannerView;
import com.crazysunj.crazydaily.view.threed.CubeReversalView;
import com.crazysunj.crazydaily.weex.WeexActivity;
import com.crazysunj.data.util.JsonUtil;
import com.crazysunj.data.util.LoggerUtil;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.entity.weather.WeatherRemoteEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.domain.util.NeihanManager;
import com.crazysunj.domain.util.ThreadManager;
import com.jaeger.library.StatusBarUtil;
import com.sunjian.android_pickview_lib.BaseOptionsPickerDialog;
import com.sunjian.android_pickview_lib.PhoneOptionsPickerDialog;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {

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

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucent(this);
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
        mHomeList.setLayoutManager(new LinearLayoutManager(this));
        mHomeList.setAdapter(mAdapter);
        mWrapBanner.setOnBannerSlideListener(this::handleWrapBanner);
    }

    @Override
    protected void initListener() {
        mRefresh.setOnRefreshListener(this::onRefresh);
        mAdapter.setOnHeaderClickListener(this::handleHeaderOptions);
        mHomeList.addOnChildAttachStateChangeListener(new HomeRecyclerViewStateChangeListener());
        mAppbar.addOnOffsetChangedListener(this::handleAppbarOffsetChangedListener);
        mBottomNavigation.setOnNavigationItemSelectedListener(this::handleNavigationItemClick);
        mCubeAnchor.setOnClickListener(v -> clickCubeAnchor());
        mCubeFirst.setOnClickListener(v -> clickCubeFirst());
        mCubeSecond.setOnClickListener(v -> clickCubeSecond());
    }

    @Override
    protected void initData() {
        mPresenter.getZhihuNewsList();
        mPresenter.getGankioList(GankioEntity.ResultsEntity.PARAMS_ANDROID);
        mPresenter.getWeather("CHZJ000000");
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
    public void showWeather(List<WeatherRemoteEntity.WeatherEntity> weatherList) {
        stopRefresh();
        mAdapter.notifyWeatherList(weatherList);
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
        initData();
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
        SnackbarUtil.show(this, "我是第三个");
    }

    private void clickCubeFirst() {
        SnackbarUtil.show(this, "我是第二个");
    }

    private boolean handleNavigationItemClick(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case R.id.navigation_contact:
                ContactActivity.start(this);
                break;
            case R.id.navigation_live:
                SnackbarUtil.show(this, "直播模块敬请期待!");
                break;
            case R.id.navigation_music:
                SnackbarUtil.show(this, "音乐模块敬请期待!");
                break;
            case R.id.navigation_about_me:
                AboutMeActivity.start(this);
                break;
            case R.id.navigation_more:
                SnackbarUtil.show(this, "更多模块敬请期待!");
                break;
            default:
                AboutMeActivity.start(this);
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
                    data.add(GankioEntity.ResultsEntity.PARAMS_ALL);
                    mGankioDialog = PhoneOptionsPickerDialog.newInstance(bundle, data);
                    mGankioDialog.setOnoptionsSelectListener((options1, option2, options3) -> {
                        final String selectOption = data.get(options1);
                        if (selectOption.equals(options)) {
                            return;
                        }
                        if (GankioEntity.ResultsEntity.PARAMS_ALL.equals(selectOption)) {
                            WeexActivity.start(HomeActivity.this);
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
                        final String selectOption = mCityList.get(options1).getTownID();
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
        mTitle.setTextColor((int) mArgbEvaluator.evaluate(percent, Color.WHITE, Color.BLACK));
    }

    private void stopRefresh() {
        if (mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
    }

    private class HomeRecyclerViewStateChangeListener implements RecyclerView.OnChildAttachStateChangeListener {

        @Override
        public void onChildViewAttachedToWindow(View view) {

        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            final int position = mHomeList.getChildAdapterPosition(view) - mAdapter.getHeaderLayoutCount();
            if (position < 0) {
                return;
            }
            final int itemViewType = mAdapter.getHelper().getItemViewType(position);
            if (itemViewType == GaoxiaoItemEntity.TYPE_GAOXIAO) {
                NiceVideoPlayer niceVideoPlayer = view.findViewById(R.id.item_neihan_video);
                if (niceVideoPlayer.isPlaying()) {
                    ThreadManager.single().execute(niceVideoPlayer::release);
                }
            }
        }
    }
}
