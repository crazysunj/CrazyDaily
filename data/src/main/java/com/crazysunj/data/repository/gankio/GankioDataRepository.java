package com.crazysunj.data.repository.gankio;

import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.GankioService;
import com.crazysunj.data.util.RxTransformerUtil;
import com.crazysunj.domain.entity.GankioEntity;
import com.crazysunj.domain.repository.gankio.GankioRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:58
 * description:
 */

public class GankioDataRepository implements GankioRepository {

    private GankioService mGankioService;

    @Inject
    public GankioDataRepository(HttpHelper httpHelper) {
        mGankioService = httpHelper.getGankioService();
    }

    @Override
    public Flowable<GankioEntity> getGankio(String type) {
        return mGankioService.getGankio(type)
                .compose(RxTransformerUtil.normalTransformer());
    }
}
