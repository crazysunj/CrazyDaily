package com.crazysunj.domain.entity.base;

import com.crazysunj.multitypeadapter.entity.MultiTypeEntity;

/**
 * author: sunjian
 * created on: 2017/9/19 下午3:59
 * description:
 */

public abstract class MultiTypeIdEntity implements MultiTypeEntity {

    public abstract String getStringId();
    
    @Override
    public long getId() {
        return 0;
    }
}
