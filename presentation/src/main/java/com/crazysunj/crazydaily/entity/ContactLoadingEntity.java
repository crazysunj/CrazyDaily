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

import com.crazysunj.domain.entity.contact.MultiTypeIndexEntity;

/**
 * @author: sunjian
 * created on: 2018/4/28 下午5:25
 * description: https://github.com/crazysunj/CrazyDaily
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
