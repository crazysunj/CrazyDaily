package com.crazysunj.crazydaily.base;

public interface IPresenter<T extends IView> {
    void attachView(T view);

    void detachView();
}
