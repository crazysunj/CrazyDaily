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
package com.crazysunj.domain.entity.neihan;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.xiao.nicevideoplayer.Clarity;

import java.util.List;

/**
 * author: sunjian
 * created on: 2017/9/24 下午4:13
 * description: https://github.com/crazysunj/CrazyDaily
 */

public class NeihanItemEntity extends MultiTypeIdEntity {

    public static final int TYPE_NEIHAN = 3;
    public static final String HEADER_TITLE = "内涵段子";
    public static final String HEADER_OPTIONS = "天王盖地虎";

    private String id;
    private String avatar;
    private String name;
    private CharSequence title;
    private String thumbnail;
    private long duration;
    private List<Clarity> clarityList;

    public NeihanItemEntity(String id, String avatar, String name, CharSequence title, String thumbnail, long duration, List<Clarity> clarityList) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.title = title;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.clarityList = clarityList;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public CharSequence getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public long getDuration() {
        return duration;
    }

    public List<Clarity> getClarityList() {
        return clarityList;
    }

    @Override
    public String getStringId() {
        return id;
    }

    @Override
    public int getItemType() {
        return TYPE_NEIHAN;
    }
}
