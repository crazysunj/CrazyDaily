package com.crazysunj.crazydaily.entity;

import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;

/**
 * author: sunjian
 * created on: 2018/4/28 下午5:25
 * description:
 */
public class ContactLoadingEntity extends MultiTypeIndexEntity {

    private int type;
    private String id;

    public ContactLoadingEntity(int type) {
        this.type = type;
        id = String.valueOf(type);
    }

    @Override
    public String getIndex() {
        return null;
    }

    @Override
    public String getStringId() {
        return id;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
