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
package com.crazysunj.crazydaily.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.di.component.ActivityComponent;
import com.crazysunj.crazydaily.di.component.DaggerActivityComponent;
import com.crazysunj.crazydaily.di.module.ActivityModule;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.util.SnackbarUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: sunjian
 * created on: 2017/9/6 下午5:25
 * description:基类
 * https://github.com/crazysunj/CrazyDaily
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    @Inject
    protected T mPresenter;
    private Unbinder mUnBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResId());
        mUnBinder = ButterKnife.bind(this);
        App.getInstance().addActivity(this);
        onPrepare();
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
        App.getInstance().removeActivity(this);
        mUnBinder.unbind();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.show(this, msg);
    }


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

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityComponent getActivityComponent(EntityModule entityModule) {
        return DaggerActivityComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .activityModule(getActivityModule())
                .entityModule(entityModule)
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract int getContentResId();

    protected abstract void initInject();
}