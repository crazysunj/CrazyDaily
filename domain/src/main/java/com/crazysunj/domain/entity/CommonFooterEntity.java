package com.crazysunj.domain.entity;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.crazysunj.multitypeadapter.helper.RecyclerViewAdapterHelper;

/**
 * author: sunjian
 * created on: 2017/9/19 下午3:58
 * description:
 */

public class CommonFooterEntity extends MultiTypeIdEntity {

    private String id;
    private int type;
    protected String title;

    public CommonFooterEntity(String id, int type, String title) {
        this.id = id;
        this.type = type - RecyclerViewAdapterHelper.FOOTER_TYPE_DIFFER;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
