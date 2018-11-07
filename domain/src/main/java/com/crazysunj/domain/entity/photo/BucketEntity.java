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

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/**
 * @author: sunjian
 * created on: 2018/9/17 下午3:03
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class BucketEntity implements Comparable<BucketEntity>, Parcelable {
    public static final long DEFAULT_DATE_MODIFIED = -1;
    public static final int DEFAULT_COUNT = -1;
    private String bucketId;
    private String bucketName;
    private String data;
    private long dateModified;
    private int count;
    private boolean selected;

    public BucketEntity(String bucketId, String bucketName, String data, long dateModified, int count) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.data = data;
        this.dateModified = dateModified;
        this.count = count;
    }

    public BucketEntity(String bucketId, String bucketName, String data, int count) {
        this(bucketId, bucketName, data, DEFAULT_DATE_MODIFIED, count);
    }

    public BucketEntity(String bucketId, String bucketName, String data) {
        this(bucketId, bucketName, data, DEFAULT_DATE_MODIFIED, DEFAULT_COUNT);
    }

    public String getBucketId() {
        return bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "BucketEntity{" +
                "bucketId='" + bucketId + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", data='" + data + '\'' +
                ", dateModified=" + dateModified +
                ", count=" + count +
                ", selected=" + selected +
                '}';
    }

    @Override
    public int compareTo(@NonNull BucketEntity bucketEntity) {
        if (bucketEntity.dateModified == -1) {
            return 1;
        }
        if (dateModified == -1) {
            return -1;
        }
        return Long.compare(bucketEntity.dateModified, dateModified);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bucketId);
        dest.writeString(this.bucketName);
        dest.writeString(this.data);
        dest.writeLong(this.dateModified);
        dest.writeInt(this.count);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected BucketEntity(Parcel in) {
        this.bucketId = in.readString();
        this.bucketName = in.readString();
        this.data = in.readString();
        this.dateModified = in.readLong();
        this.count = in.readInt();
        this.selected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<BucketEntity> CREATOR = new Parcelable.Creator<BucketEntity>() {
        @Override
        public BucketEntity createFromParcel(Parcel source) {
            return new BucketEntity(source);
        }

        @Override
        public BucketEntity[] newArray(int size) {
            return new BucketEntity[size];
        }
    };
}
