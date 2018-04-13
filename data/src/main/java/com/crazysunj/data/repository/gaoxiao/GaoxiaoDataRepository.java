/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.data.repository.gaoxiao;

import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.GaoxiaoService;
import com.crazysunj.data.util.RxTransformerUtil;
import com.crazysunj.domain.entity.GaoxiaoEntity;
import com.crazysunj.domain.repository.gaoxiao.GaoxiaoRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:58
 * description: https://github.com/crazysunj/CrazyDaily
 */

public class GaoxiaoDataRepository implements GaoxiaoRepository {

    private GaoxiaoService mGaoxiaoService;

    @Inject
    public GaoxiaoDataRepository(HttpHelper httpHelper) {
        mGaoxiaoService = httpHelper.getGaoxiaoService();
    }

    @Override
    public Flowable<GaoxiaoEntity> getGaoxiaoList(int type, int page) {
        return mGaoxiaoService.getGaoxiaoList(type, page)
                .compose(RxTransformerUtil.normalTransformer());
    }
}