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
package com.crazysunj.crazydaily.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.di.component.DaggerFragmentComponent;
import com.crazysunj.crazydaily.di.component.FragmentComponent;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.di.module.FragmentModule;
import com.crazysunj.crazydaily.util.DeviceUtil;
import com.crazysunj.crazydaily.util.SnackbarUtil;

import javax.inject.Inject;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: sunjian
 * created on: 2018/11/7 下午10:15
 * description: Fragment基类
 * https://github.com/crazysunj/CrazyDaily
 */
public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    @Inject
    protected T mPresenter;
    private Unbinder mUnBinder;
    protected AppCompatActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (AppCompatActivity) getActivity();
        onPrepare();
        View rootView = inflater.inflate(getContentResId(), container, false);
        mUnBinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.show(mActivity, msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @SuppressWarnings("unchecked")
    private void onPrepare() {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected void initListener() {

    }

    protected void initData() {

    }

    protected void initView() {

    }

    protected int getColor(@ColorRes int color) {
        return ContextCompat.getColor(mActivity, color);
    }

    protected void setSupportActionBar(Toolbar toolbar) {
        mActivity.setSupportActionBar(toolbar);
    }

    protected void finish() {
        mActivity.finish();
    }

    protected void showBack() {
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back);
        }
    }

    protected void showBack(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(v -> mActivity.finish());
    }

    /**
     * 添加一个假的statusbar
     *
     * @param color 颜色
     */
    protected void addStatusBar(LinearLayout linearLayout, @ColorInt int color) {
        View statusBarView = new View(mActivity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtil.getStatusBarHeight(mActivity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        linearLayout.addView(statusBarView, 0);
    }

    /**
     * 返回布局资源id
     *
     * @return int
     */
    protected abstract int getContentResId();

    /**
     * 注入
     */
    protected void initInject() {
    }

    protected FragmentComponent getFragmentComponent(EntityModule entityModule) {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .fragmentModule(getFragmentModule())
                .entityModule(entityModule)
                .build();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    private FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }
}
