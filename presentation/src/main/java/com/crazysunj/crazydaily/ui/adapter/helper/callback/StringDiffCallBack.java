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
package com.crazysunj.crazydaily.ui.adapter.helper.callback;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

/**
 * @author: sunjian
 * created on: 2017/9/21 上午10:17
 * description: https://github.com/crazysunj/CrazyDaily
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
