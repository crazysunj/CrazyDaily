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
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.crazysunj.cardslideview.CardViewPager;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.entity.CityEntity;
import com.crazysunj.crazydaily.presenter.HomePresenter;
import com.crazysunj.crazydaily.presenter.contract.HomeContract;
import com.crazysunj.crazydaily.ui.adapter.HomeAdapter;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.crazydaily.view.banner.BannerCardHandler;
import com.crazysunj.data.util.JsonUtil;
import com.crazysunj.data.util.LoggerUtil;
import com.crazysunj.domain.entity.GankioEntity;
import com.crazysunj.domain.entity.NeihanItemEntity;
import com.crazysunj.domain.entity.WeatherRemoteEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;
import com.crazysunj.domain.util.NeihanManager;
import com.jaeger.library.StatusBarUtil;
import com.sunjian.android_pickview_lib.BaseOptionsPickerDialog;
import com.sunjian.android_pickview_lib.PhoneOptionsPickerDialog;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

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
    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.home_appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.home_title)
    TextView mTitle;
    @BindView(R.id.home_bottom)
    TextView mBottom;

    @Inject
    HomeAdapter mAdapter;

    @Inject
    NeihanManager mNeihanManager;

    private PhoneOptionsPickerDialog mGankioDialog;
    private PhoneOptionsPickerDialog mWeatherDialog;
    private ArrayList<CityEntity> mCityList;
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucent(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
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
    }

    @Override
    protected void initListener() {
        mRefresh.setOnRefreshListener(this::initData);
        mAdapter.setOnHeaderClickListener(this::handleHeaderOptions);
        mHomeList.addOnChildAttachStateChangeListener(new HomeRecyclerViewStateChangeListener());
        mAppbar.addOnOffsetChangedListener(this::handleAppbarOffsetChangedListener);
        mBottom.setOnClickListener(v -> AboutMeActivity.start(this));
    }

    @Override
    protected void initData() {
        mPresenter.getZhihuNewsList();
        mPresenter.getGankioList(GankioEntity.ResultsEntity.PARAMS_ANDROID);
        mPresenter.getWeather("CHZJ000000");
        mPresenter.getNeihanList(mNeihanManager.getAmLocTime(), mNeihanManager.getMinTime(), mNeihanManager.getSceenWidth(),
                mNeihanManager.getIid(), mNeihanManager.getDeviceId(), mNeihanManager.getAc(), mNeihanManager.getVersionCode(),
                mNeihanManager.getVersionName(), Build.MODEL, Build.BRAND, Build.VERSION.SDK_INT, Build.VERSION.RELEASE, mNeihanManager.getUuid(),
                mNeihanManager.getOpenudid(), mNeihanManager.getManifestVersionCode(), mNeihanManager.getResolution(),
                String.valueOf(getResources().getDisplayMetrics().densityDpi), mNeihanManager.getUpdateVersionCode());
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
    protected int getContentResId() {
        return R.layout.activity_main;
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
        showExitDialog();
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出CrazyDaily吗");
        builder.setNegativeButton("留下来", null);
        builder.setPositiveButton("残忍地弄死", (DialogInterface dialogInterface, int i) -> App.getInstance().exitApp());
        builder.show();
    }

    private void handleHeaderOptions(int type, String options) {
        switch (type) {
            case GankioEntity.ResultsEntity.TYPE_GANK_IO:
                if (mGankioDialog == null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(BaseOptionsPickerDialog.CYCLIC_FIRST, true);
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(GankioEntity.ResultsEntity.PARAMS_ANDROID);
                    data.add(GankioEntity.ResultsEntity.PARAMS_IOS);
                    data.add(GankioEntity.ResultsEntity.PARAMS_H5);
                    mGankioDialog = PhoneOptionsPickerDialog.newInstance(bundle, data);
                    mGankioDialog.setOnoptionsSelectListener((int options1, int option2, int options3) -> {
                        final String selectOption = data.get(options1);
                        if (selectOption.equals(options)) {
                            return;
                        }
                        mPresenter.getGankioList(selectOption);
                    });
                }
                mGankioDialog.show(getFragmentManager(), "GankioDialog");
                break;

            case WeatherRemoteEntity.WeatherEntity.TYPE_WEATHER:
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
                    mWeatherDialog.setOnoptionsSelectListener((int options1, int option2, int options3) -> {
                        final String selectOption = mCityList.get(options1).getTownID();
                        if (selectOption.equals(options)) {
                            return;
                        }
                        mPresenter.getWeather(selectOption);
                    });
                }
                mWeatherDialog.show(getFragmentManager(), "WeatherDialog");
                break;
            case ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS:
                SnackbarUtil.show(this, "已经最新了，别点了！");
                break;
            case NeihanItemEntity.TYPE_NEIHAN:
                SnackbarUtil.show(this, "小鸡炖蘑菇");
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
            if (itemViewType == NeihanItemEntity.TYPE_NEIHAN) {
                NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) view.findViewById(R.id.item_neihan_video);
                if (niceVideoPlayer.isPlaying()) {
                    Executors.newSingleThreadExecutor().execute(niceVideoPlayer::release);
                }
            }
        }
    }
}
