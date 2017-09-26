package com.crazysunj.crazydaily.presenter.contract;

import com.crazysunj.crazydaily.base.IView;
import com.crazysunj.crazydaily.base.IPresenter;
import com.crazysunj.domain.entity.ZhihuNewsDetailEntity;

/**
 * Created by codeest on 16/8/13.
 */

public interface ZhihuNewsDetailContract {

    interface View extends IView {

        void showContent(ZhihuNewsDetailEntity zhihuNewsDetailEntity);
    }

    interface  Presenter extends IPresenter<View> {

        void getZhihuNewsDetail(long id);
    }
}
