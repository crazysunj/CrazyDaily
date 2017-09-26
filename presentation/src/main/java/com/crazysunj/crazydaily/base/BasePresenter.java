package com.crazysunj.crazydaily.base;

/**
 * author: sunjian
 * created on: 2017/9/10 上午11:47
 * description:
 */

public class BasePresenter<T extends IView> implements IPresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
