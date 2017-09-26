package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.ZhihuNewsDetailContract;
import com.crazysunj.domain.entity.ZhihuNewsDetailEntity;
import com.crazysunj.domain.interactor.zhihu.ZhihuNewsDetailUseCase;

import javax.inject.Inject;

@ActivityScope
public class ZhihuNewsDetailPresenter extends BasePresenter<ZhihuNewsDetailContract.View> implements ZhihuNewsDetailContract.Presenter {

    private ZhihuNewsDetailUseCase mZhihuNewsDetailUseCase;

    @Inject
    public ZhihuNewsDetailPresenter(ZhihuNewsDetailUseCase useCase) {
        mZhihuNewsDetailUseCase = useCase;
    }

    @Override
    public void getZhihuNewsDetail(long id) {
        mZhihuNewsDetailUseCase.execute(ZhihuNewsDetailUseCase.Params.get(id), new BaseSubscriber<ZhihuNewsDetailEntity>() {
            @Override
            public void onNext(ZhihuNewsDetailEntity zhihuNewsDetailEntity) {
                mView.showContent(zhihuNewsDetailEntity);
            }
        });
    }
}
