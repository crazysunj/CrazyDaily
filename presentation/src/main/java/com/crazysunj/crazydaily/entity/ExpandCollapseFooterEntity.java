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
package com.crazysunj.crazydaily.entity;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.constant.Constant;
import com.crazysunj.domain.entity.common.CommonFooterEntity;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ExpandCollapseFooterEntity extends CommonFooterEntity {

    private static final String COLLAPSE = "收起";
    private static final String EXPAND = "收起";
    private static final int STATUS_EXPAND = 0;
    private static final int STATUS_COLLAPSE = 1;

    private int status;

    public ExpandCollapseFooterEntity(int level) {
        super(Constant.EMPTY, level, EXPAND);
        status = STATUS_COLLAPSE;
    }

    public boolean isFlod() {
        return status == STATUS_COLLAPSE;
    }

    public void switchStatus() {
        if (status == STATUS_EXPAND) {
            status = STATUS_COLLAPSE;
            return;
        }
        status = STATUS_EXPAND;
    }

    public void initStatus(String title) {
        this.title = title;
        status = STATUS_COLLAPSE;
    }

    public int getStatus() {
        return status;
    }

    public int getIconResId() {
        if (status == STATUS_EXPAND) {
            return R.mipmap.icon_collapse;
        }
        return R.mipmap.icon_expand;
    }

    @Override
    public String getStringId() {
        if (status == STATUS_EXPAND) {
            return COLLAPSE;
        }
        return title;
    }
}
