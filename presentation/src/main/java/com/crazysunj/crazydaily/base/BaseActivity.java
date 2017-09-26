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