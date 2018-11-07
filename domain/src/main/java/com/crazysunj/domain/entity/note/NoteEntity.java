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
package com.crazysunj.domain.entity.note;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author: sunjian
 * created on: 2018/9/27 下午4:00
 * description: https://github.com/crazysunj/CrazyDaily
 */
@Entity(nameInDb = "Note")
public class NoteEntity implements Parcelable {
    public static class StringConverter implements PropertyConverter<List<String>, String> {

        @Override
        public List<String> convertToEntityProperty(String databaseValue) {
            if (TextUtils.isEmpty(databaseValue)) {
                return null;
            }
            return Arrays.asList(databaseValue.split(","));
        }

        @Override
        public String convertToDatabaseValue(List<String> entityProperty) {
            if (entityProperty == null || entityProperty.isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (String data : entityProperty) {
                sb.append(data);
                sb.append(",");
            }
            return sb.toString();
        }
    }

    /**
     * id
     */
    @Id
    private Long id;
    /**
     * 图片
     */
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> images;
    /**
     * 文本内容
     */
    @NotNull
    private String text;
    /**
     * 是否能下载
     */
    private boolean isCanDownload;
    /**
     * 标志位 0 为草稿 1为已发布
     */
    private Integer flag;

    public static final int FLAG_SUBMIT = 1;
    public static final int FLAG_DRAFT = 0;

    @Generated(hash = 2062519629)
    public NoteEntity(Long id, List<String> images, @NotNull String text,
                      boolean isCanDownload, Integer flag) {
        this.id = id;
        this.images = images;
        this.text = text;
        this.isCanDownload = isCanDownload;
        this.flag = flag;
    }

    @Generated(hash = 734234824)
    public NoteEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsCanDownload() {
        return this.isCanDownload;
    }

    public void setIsCanDownload(boolean isCanDownload) {
        this.isCanDownload = isCanDownload;
    }

    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", images=" + images +
                ", text='" + text + '\'' +
                ", isCanDownload=" + isCanDownload +
                ", flag=" + flag +
                '}';
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NoteEntity)) {
            return false;
        }
        return this == obj || id.equals(((NoteEntity) obj).getId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeStringList(this.images);
        dest.writeString(this.text);
        dest.writeByte(this.isCanDownload ? (byte) 1 : (byte) 0);
        dest.writeValue(this.flag);
    }

    protected NoteEntity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.images = in.createStringArrayList();
        this.text = in.readString();
        this.isCanDownload = in.readByte() != 0;
        this.flag = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<NoteEntity> CREATOR = new Parcelable.Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel source) {
            return new NoteEntity(source);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };
}
