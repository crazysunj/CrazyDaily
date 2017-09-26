package com.crazysunj.domain.repository.zhihu;


import com.crazysunj.domain.entity.ZhihuNewsDetailEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2017/8/31 上午10:01
 * description:
 */

public interface ZhihuRepository {
    
    Flowable<ZhihuNewsEntity> getZhihuNewsList();

    Flowable<ZhihuNewsDetailEntity> getZhihuNewsDetail(long id);
}
