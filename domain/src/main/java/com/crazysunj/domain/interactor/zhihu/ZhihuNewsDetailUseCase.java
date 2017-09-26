package com.crazysunj.domain.interactor.zhihu;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.ZhihuNewsDetailEntity;
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

public class ZhihuNewsDetailUseCase extends UseCase<ZhihuNewsDetailEntity, ZhihuNewsDetailUseCase.Params> {

    private final ZhihuRepository mZhihuRepository;

    @Inject
    public ZhihuNewsDetailUseCase(ZhihuRepository zhihuRepository) {
        mZhihuRepository = zhihuRepository;
    }

    @Override
    protected Flowable<ZhihuNewsDetailEntity> buildUseCaseObservable(Params params) {
        return mZhihuRepository.getZhihuNewsDetail(params.id)
                .flatMap(zhihuNewsDetailEntity -> {
                    if (zhihuNewsDetailEntity == null) {
                        return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
                    }
                    return Flowable.just(zhihuNewsDetailEntity);
                });
    }

    public static final class Params {

        private final long id;

        private Params(long id) {
            this.id = id;
        }

        public static Params get(long id) {
            return new Params(id);
        }
    }
}
