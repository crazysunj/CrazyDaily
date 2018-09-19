package com.crazysunj.domain.entity.photo;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * author: sunjian
 * created on: 2018/9/17 下午3:03
 * description:
 */
public class BucketEntity implements Comparable<BucketEntity> {
    public static final long DEFAULT_DATE_MODIFIED = -1;
    public static final int DEFAULT_COUNT = -1;
    private String bucketId;
    private String bucketName;
    private String data;
    private long dateModified;
    private int count;
    private List<String> bucketIds;

    public BucketEntity(String bucketId, String bucketName, String data, long dateModified, int count) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.data = data;
        this.dateModified = dateModified;
        this.count = count;
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

    public List<String> getBucketIds() {
        return bucketIds;
    }

    public void setBucketIds(List<String> bucketIds) {
        this.bucketIds = bucketIds;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BucketEntity{" +
                "bucketId='" + bucketId + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", data='" + data + '\'' +
                ", dateModified=" + dateModified +
                ", count=" + count +
                ", bucketIds=" + bucketIds +
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
}
