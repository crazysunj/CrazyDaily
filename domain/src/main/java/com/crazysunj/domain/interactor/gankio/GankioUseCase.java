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
package com.crazysunj.domain.interactor.gankio;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.exception.ApiException;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.gankio.GankioRepository;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author: sunjian
 * created on: 2017/9/5 下午5:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class GankioUseCase extends UseCase<List<GankioEntity.ResultsEntity>, GankioUseCase.Params> {

    private final GankioRepository mGankioRepository;

    @Inject
    public GankioUseCase(GankioRepository gankioRepository) {
        mGankioRepository = gankioRepository;
    }

    @Override
    protected Flowable<List<GankioEntity.ResultsEntity>> buildUseCaseObservable(Params params) {
        return mGankioRepository.getGankio(params.type, params.count)
                .flatMap(this::handleException);
    }

    private Publisher<List<GankioEntity.ResultsEntity>> handleException(GankioEntity gankioEntity) {
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
    }


    public static final class Params {

        private final String type;
        private final int count;

        private Params(String type, int count) {
            this.type = type;
            this.count = count;
        }

        public static Params get(String type, int count) {
            return new Params(type, count);
        }
    }
}
