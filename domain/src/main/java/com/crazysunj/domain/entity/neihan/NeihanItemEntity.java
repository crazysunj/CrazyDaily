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
package com.crazysunj.domain.entity.neihan;

import android.graphics.Color;

import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.crazysunj.domain.util.ColorUtil;
import com.xiao.nicevideoplayer.Clarity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunjian
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

    public static NeihanItemEntity get(NeihanEntity.DataEntityX.DataEntity entity) {
        NeihanEntity.DataEntityX.DataEntity.GroupEntity group = entity.getGroup();
        NeihanEntity.DataEntityX.DataEntity.GroupEntity.UserEntity user = group.getUser();
        final String avatar = user.getAvatar_url();
        final String name = user.getName();
        final String id = String.valueOf(group.getId());
        long duration = (long) (group.getDuration() * 1000);
        String categoryName = group.getCategory_name();
        String title = group.getContent();
        String temTitle = String.format("[%s] %s", categoryName, title);
        CharSequence realTitle = ColorUtil.handleKeyWordHighLight(temTitle, String.format("\\[%s\\]", categoryName), Color.parseColor("#FF5C8D"));
        List<Clarity> clarityList = new ArrayList<Clarity>();
        NeihanEntity.DataEntityX.DataEntity.GroupEntity.Video360pEntity video360P = group.getVideo_360p();
        if (video360P != null) {
            List<NeihanEntity.DataEntityX.DataEntity.GroupEntity.Video360pEntity.UrlListEntity> urlList = video360P.getUrl_list();
            if (urlList != null && urlList.size() > 0) {
                clarityList.add(new Clarity("标清", "360P", urlList.get(0).getUrl()));
            }
        }

        NeihanEntity.DataEntityX.DataEntity.GroupEntity.Video480pEntity video480P = group.getVideo480pEntity();
        if (video480P != null) {
            List<NeihanEntity.DataEntityX.DataEntity.GroupEntity.Video480pEntity.UrlListEntityXX> urlList = video480P.getUrl_list();
            if (urlList != null && urlList.size() > 0) {
                clarityList.add(new Clarity("高清", "480P", urlList.get(0).getUrl()));
            }
        }

        NeihanEntity.DataEntityX.DataEntity.GroupEntity.Video720pEntity video720P = group.getVideo720pEntity();
        if (video720P != null) {
            List<NeihanEntity.DataEntityX.DataEntity.GroupEntity.Video720pEntity.UrlListEntityX> urlList = video720P.getUrl_list();
            if (urlList != null && urlList.size() > 0) {
                clarityList.add(new Clarity("超清", "720P", urlList.get(0).getUrl()));
            }
        }

        NeihanEntity.DataEntityX.DataEntity.GroupEntity.OriginVideoEntity videoOrigin = group.getOrigin_video();
        if (videoOrigin != null) {
            List<NeihanEntity.DataEntityX.DataEntity.GroupEntity.OriginVideoEntity.UrlListEntityXXXXX> urlList = videoOrigin.getUrl_list();
            if (urlList != null && urlList.size() > 0) {
                clarityList.add(new Clarity("天然", "1080P", urlList.get(0).getUrl()));
            }
        }

        NeihanEntity.DataEntityX.DataEntity.GroupEntity.LargeCoverEntity largeCover = group.getLarge_cover();
        String thumbnail = null;
        if (largeCover != null) {
            List<NeihanEntity.DataEntityX.DataEntity.GroupEntity.LargeCoverEntity.UrlListEntityXXX> urlList = largeCover.getUrl_list();
            if (urlList != null && urlList.size() > 0) {
                thumbnail = urlList.get(0).getUrl();
            }
        }

        return new NeihanItemEntity(id, avatar, name, realTitle, thumbnail, duration, clarityList);
    }

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
