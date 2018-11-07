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
package com.crazysunj.data.repository.zhihu;


import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.ZhihuService;
import com.crazysunj.data.util.RxTransformerUtil;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsDetailEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author: sunjian
 * created on: 2017/8/31 上午10:03
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ZhihuDataRepository implements ZhihuRepository {

    private ZhihuService mZhihuService;

    @Inject
    public ZhihuDataRepository(HttpHelper httpHelper) {
        mZhihuService = httpHelper.getZhihuService();
    }

    @Override
    public Flowable<ZhihuNewsEntity> getZhihuNewsList() {
        return mZhihuService.getZhihuNewsList()
                .compose(RxTransformerUtil.normalTransformer());
    }

    @Override
    public Flowable<ZhihuNewsDetailEntity> getZhihuNewsDetail(long id) {
        return mZhihuService.getZhihuNewsDetail(id)
                .compose(RxTransformerUtil.normalTransformer());
    }
}
