package com.crazysunj.domain.repository.gankio;

import com.crazysunj.domain.entity.GankioEntity;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:40
 * description:
 */

public interface GankioRepository {
    Flowable<GankioEntity> getGankio(String type);
}
