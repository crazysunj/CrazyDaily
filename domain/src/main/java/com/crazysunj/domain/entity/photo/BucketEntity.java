package com.crazysunj.domain.entity.photo;

import java.util.List;

/**
 * author: sunjian
 * created on: 2018/9/17 下午3:03
 * description:
 */
public class BucketEntity {
    private String bucketId;
    private String bucketName;
    private String data;
    private int count;
    private List<String> bucketIds;

    public BucketEntity(String bucketId, String bucketName, String data, int count) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.data = data;
        this.count = count;
    }

    public BucketEntity(String bucketId, String bucketName, String data) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.data = data;
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
                ", count=" + count +
                '}';
    }
}
