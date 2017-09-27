/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.crazydaily.base;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazysunj.multitypeadapter.entity.MultiTypeEntity;
import com.crazysunj.multitypeadapter.helper.RecyclerViewAdapterHelper;

/**
 * author: sunjian
 * created on: 2017/8/3 下午5:18
 * description:https://github.com/crazysunj/CrazyDaily
 */

public abstract class BaseAdapter<T extends MultiTypeEntity, K extends BaseViewHolder, H extends RecyclerViewAdapterHelper<T, BaseAdapter>> extends BaseQuickAdapter<T, K> {

    protected H mHelper;

    public BaseAdapter(H helper) {
        super(helper.getData());
        mHelper = helper;
        mHelper.bindAdapter(this);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mHelper.getLayoutId(viewType));
    }

    @Override
    protected int getDefItemViewType(int position) {
        return mHelper.getItemViewType(position);
    }

    public H getHelper() {
        return mHelper;
    }

}
