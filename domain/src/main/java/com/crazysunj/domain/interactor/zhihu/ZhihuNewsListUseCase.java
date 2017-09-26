package com.crazysunj.domain.interactor.zhihu;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.ZhihuNewsEntity;
import com.crazysunj.domain.exception.ApiException;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/9/5 下午5:34
 * description:
 */

public class ZhihuNewsListUseCase extends UseCase<ZhihuNewsEntity, Void> {

    private final ZhihuRepository mZhihuRepository;

    @Inject
    public ZhihuNewsListUseCase(ZhihuRepository zhihuRepository) {
        mZhihuRepository = zhihuRepository;
    }

    @Override
    protected Flowable<ZhihuNewsEntity> buildUseCaseObservable(Void aVoid) {
        return mZhihuRepository.getZhihuNewsList()
                .flatMap(zhihuNewsList -> {
                    if (zhihuNewsList == null) {
                        return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
                    }
                    return Flowable.just(zhihuNewsList);
                });
    }
}
