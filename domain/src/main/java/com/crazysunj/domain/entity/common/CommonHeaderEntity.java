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
package com.crazysunj.domain.entity.common;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.crazysunj.multitypeadapter.helper.RecyclerViewAdapterHelper;

/**
 * @author: sunjian
 * created on: 2017/9/19 下午3:58
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CommonHeaderEntity extends MultiTypeIdEntity {

    private String id;
    private int type;
    private String title;
    private String options;

    public CommonHeaderEntity(String id, int level, String title, String options) {
        this.id = id;
        this.type = level - RecyclerViewAdapterHelper.HEADER_TYPE_DIFFER;
        this.title = title;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public String getOptions() {
        return options;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOptions(String options) {
        this.options = options;
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
