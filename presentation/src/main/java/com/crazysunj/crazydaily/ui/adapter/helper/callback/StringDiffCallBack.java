package com.crazysunj.crazydaily.ui.adapter.helper.callback;

import android.support.v7.util.DiffUtil;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;

import java.util.List;

/**
 * author: sunjian
 * created on: 2017/9/21 上午10:17
 * description:
 */

public class StringDiffCallBack extends DiffUtil.Callback {

    private List<MultiTypeIdEntity> mOldDatas;
    private List<MultiTypeIdEntity> mNewDatas;

    public StringDiffCallBack(List<MultiTypeIdEntity> oldDatas, List<MultiTypeIdEntity> newDatas) {
        this.mOldDatas = oldDatas;
        this.mNewDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas == null ? 0 : mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas == null ? 0 : mNewDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        MultiTypeIdEntity oldItem = mOldDatas.get(oldItemPosition);
        MultiTypeIdEntity newItem = mNewDatas.get(newItemPosition);
        return !(oldItem == null || newItem == null) && oldItem.getItemType() == newItem.getItemType();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        String oldId = mOldDatas.get(oldItemPosition).getStringId();
        String newId = mNewDatas.get(newItemPosition).getStringId();
        return !(oldId == null || newId == null) && oldId.equals(newId);
    }
}
