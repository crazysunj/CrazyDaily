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
package com.crazysunj.domain.interactor.zhihu;

import com.crazysunj.domain.constant.CodeConstant;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.domain.exception.ApiException;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;

import org.reactivestreams.Publisher;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author: sunjian
 * created on: 2017/9/5 下午5:34
 * description: https://github.com/crazysunj/CrazyDaily
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
                .flatMap(this::handleException);
    }

    private Publisher<ZhihuNewsEntity> handleException(ZhihuNewsEntity zhihuNewsList) {
        if (zhihuNewsList == null) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        return Flowable.just(zhihuNewsList);
    }
}
