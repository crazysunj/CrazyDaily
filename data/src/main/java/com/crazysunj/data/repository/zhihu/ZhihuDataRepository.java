package com.crazysunj.data.repository.zhihu;


import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.ZhihuService;
import com.crazysunj.data.util.RxTransformerUtil;
import com.crazysunj.domain.entity.ZhihuNewsDetailEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;
import com.crazysunj.domain.repository.zhihu.ZhihuRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/8/31 上午10:03
 * description:
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
