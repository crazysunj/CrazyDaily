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
package com.crazysunj.domain.interactor.gaoxiao;

import android.text.TextUtils;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.exception.ApiException;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.gaoxiao.GaoxiaoRepository;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: sunjian
 * created on: 2017/9/5 下午5:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class GaoxiaoUseCase extends UseCase<List<GaoxiaoItemEntity>, GaoxiaoUseCase.Params> {

    private final GaoxiaoRepository mGaoxiaoRepository;

    @Inject
    public GaoxiaoUseCase(GaoxiaoRepository gaoxiaoRepository) {
        mGaoxiaoRepository = gaoxiaoRepository;
    }

    @Override
    protected Flowable<List<GaoxiaoItemEntity>> buildUseCaseObservable(Params params) {
        return mGaoxiaoRepository.getGaoxiaoList(Params.TYPE, params.page)
                .observeOn(Schedulers.io())
                .flatMap(this::handleException)
                .filter(entity -> !TextUtils.isEmpty(entity.getVideouri()))
                .map(GaoxiaoItemEntity::get)
                .toList()
                .toFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Publisher<GaoxiaoEntity.DataEntity> handleException(GaoxiaoEntity gaoxiaoEntity) {
        if (gaoxiaoEntity == null) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        List<GaoxiaoEntity.DataEntity> data = gaoxiaoEntity.getData();
        if (data == null || data.isEmpty()) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        return Flowable.fromIterable(data);
    }


    public static final class Params {

        private final int page;
        private static final int TYPE = 4;

        private Params(int page) {
            this.page = page;
        }

        public static Params get(int page) {
            return new Params(page);
        }
    }
}
