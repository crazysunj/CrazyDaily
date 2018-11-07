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
package com.crazysunj.domain.entity.contact;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2018/4/17 下午4:18
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ContactHeader extends MultiTypeIndexEntity {

    public static final int TYPE = 1;
    private static final int STATUS_EXPAND = 0;
    private static final int STATUS_COLLAPSE = 1;

    private String title;
    private int status;
    private List<MultiTypeIndexEntity> childs;

    public ContactHeader(String title) {
        this.title = title;
        status = STATUS_EXPAND;
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

    public List<MultiTypeIndexEntity> getChilds() {
        return childs;
    }

    public void setChilds(List<MultiTypeIndexEntity> childs) {
        this.childs = childs;
    }

    @Override
    public String getStringId() {
        return title;
    }

    @Override
    public String getIndex() {
        return title;
    }

    @Override
    public int getItemType() {
        return TYPE;
    }
}
