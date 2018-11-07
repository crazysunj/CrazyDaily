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
package com.crazysunj.domain.entity.photo;

import com.crazysunj.multitypeadapter.entity.MultiTypeEntity;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author: sunjian
 * created on: 2018/9/17 下午2:53
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class MediaEntity implements Comparable<MediaEntity>, MultiTypeEntity {
    public static class MediaResponseData {
        public int imageOffset;
        public int videoOffset;
        public List<MediaEntity> mediaList;

        public MediaResponseData(int imageOffset, int videoOffset, List<MediaEntity> mediaList) {
            this.imageOffset = imageOffset;
            this.videoOffset = videoOffset;
            this.mediaList = mediaList;
        }
    }

    public static final int TYPE_PHOTO_PICKER = 0;

    /**
     * 默认加载数量临界值
     */
    public static final int DEFAULT_LIMIT = 40;
    /**
     * id
     */
    private long id;
    /**
     * 源地址
     */
    private String data;
    /**
     * 创建时间
     */
    private long createDate;
    /**
     * 最后修改时间
     */
    private long modifiedDate;
    /**
     * 文件大小
     */
    private long length;
    /**
     * 播放时长，可用于判断是否为视频
     */
    private long duration;
    /**
     * 用于记录选中下标
     */
    private int index;

    public MediaEntity() {
    }

    public MediaEntity(long id, String data, long createDate, long modifiedDate, long length) {
        this.id = id;
        this.data = data;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.length = length;
    }

    public MediaEntity(long id, String data, long createDate, long modifiedDate, long length, long duration) {
        this.id = id;
        this.data = data;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.length = length;
        this.duration = duration;
    }

    @Override
    public int getItemType() {
        return TYPE_PHOTO_PICKER;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public long getLength() {
        return length;
    }

    public long getDuration() {
        return duration;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isImage() {
        return duration == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof MediaEntity) {
            if (this == obj) {
                return true;
            } else {
                return this.id == ((MediaEntity) obj).id;
            }
        }
        return false;
    }

//    @Override
//    public String toString() {
//        return "MediaEntity{" +
//                "id=" + id +
//                ", data='" + data + '\'' +
//                ", createDate=" + createDate +
//                ", modifiedDate=" + modifiedDate +
//                ", length=" + length +
//                ", duration=" + duration +
//                '}';
//    }

    @Override
    public int compareTo(@NonNull MediaEntity mediaEntity) {
        return Long.compare(modifiedDate, mediaEntity.modifiedDate);
    }
}
