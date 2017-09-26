package com.crazysunj.domain.interactor.gankio;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.GankioEntity;
import com.crazysunj.domain.exception.ApiException;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.gankio.GankioRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/9/5 下午5:34
 * description:
 */

public class GankioUseCase extends UseCase<List<GankioEntity.ResultsEntity>, GankioUseCase.Params> {

    private final GankioRepository mGankioRepository;

    @Inject
    public GankioUseCase(GankioRepository gankioRepository) {
        mGankioRepository = gankioRepository;
    }

    @Override
    protected Flowable<List<GankioEntity.ResultsEntity>> buildUseCaseObservable(Params params) {
        return mGankioRepository.getGankio(params.type)
                .flatMap(gankioEntity -> {
                    if (gankioEntity == null) {
                        return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
                    }
                    if (gankioEntity.isError()) {
                        return Flowable.error(new ApiException(CodeConstant.CODE_DATA_ERROR, "数据错误，没法快乐玩耍！"));
                    }
                    List<GankioEntity.ResultsEntity> results = gankioEntity.getResults();
                    if (results == null || results.isEmpty()) {
                        return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
                    }
                    return Flowable.just(results);
                });
    }


    public static final class Params {

        private final String type;

        private Params(String type) {
            this.type = type;
        }

        public static Params get(String type) {
            return new Params(type);
        }
    }
}
