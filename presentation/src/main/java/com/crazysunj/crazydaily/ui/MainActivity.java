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
package com.crazysunj.crazydaily.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.presenter.MainPresenter;
import com.crazysunj.crazydaily.presenter.contract.MainContract;
import com.crazysunj.crazydaily.ui.photo.PhotoActivity;
import com.crazysunj.crazydaily.ui.splash.SplashActivity;
import com.crazysunj.crazydaily.view.threed.CubeReversalView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;

/**
 * @author: sunjian
 * created on: 2018/11/7 下午8:10
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private static final String RESTART = "restart";

    @BindView(R.id.main_navigition)
    BottomNavigationView mNavigation;

    @BindView(R.id.cube_anchor)
    CubeReversalView mCubeAnchor;
    @BindView(R.id.cube_first)
    CubeReversalView mCubeFirst;
    @BindView(R.id.cube_second)
    CubeReversalView mCubeSecond;
    @BindView(R.id.bottom_shadow)
    ImageView mShadow;

    private boolean isTop = true;
    private List<String> mMeinvList;

    public static void restart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(RESTART, RESTART);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        handleTranslucent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (RESTART.equals(intent.getStringExtra(RESTART))) {
            SplashActivity.start(this);
            finish();
        }
    }

    private void handleTranslucent() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setNavigationBarColor(Color.WHITE);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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

    @Override
    protected void initView() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_host_fragment);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(mNavigation, navHostFragment.getNavController());
        }
    }

    @Override
    protected void initListener() {
        mCubeAnchor.setOnClickListener(v -> clickCubeAnchor());
        mCubeFirst.setOnClickListener(v -> clickCubeFirst());
        mCubeSecond.setOnClickListener(v -> clickCubeSecond());
    }

    @Override
    protected void initData() {
        mPresenter.getMeinvList();
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
    public void showMeinv(List<String> meinvList) {
        ImageLoader.loadWithVignette(this, meinvList.get(0), R.drawable.img_default, mCubeAnchor.getForegroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(1), R.drawable.img_default, mCubeAnchor.getBackgroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(2), R.drawable.img_default, mCubeFirst.getForegroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(3), R.drawable.img_default, mCubeFirst.getBackgroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(4), R.drawable.img_default, mCubeSecond.getForegroundView());
        ImageLoader.loadWithVignette(this, meinvList.get(5), R.drawable.img_default, mCubeSecond.getBackgroundView());
        if (mShadow != null) {
            mShadow.setVisibility(View.VISIBLE);
        }
        mMeinvList = meinvList;
    }

    @Override
    public void errorMeinv() {
        mShadow.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
//        if (NiceVideoPlayerManager.instance().onBackPressd()) {
//            return;
//        }
        if (!isTop) {
            clickCubeAnchor();
            return;
        }
        showExitDialog();
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出CrazyDaily吗");
        builder.setNegativeButton("再玩玩", null);
        builder.setPositiveButton("忍住泪水离开", (dialogInterface, i) -> App.getInstance().exitApp());
        builder.show();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
