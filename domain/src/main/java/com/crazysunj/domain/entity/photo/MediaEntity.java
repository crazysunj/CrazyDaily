package com.crazysunj.domain.entity.photo;

/**
 * author: sunjian
 * created on: 2018/9/17 下午2:53
 * description:
 */
public class MediaEntity {
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
     * 是否是图片
     */
    private boolean isImage;

    public MediaEntity(long id, String data, long createDate, long modifiedDate, long length, boolean isImage) {
        this.id = id;
        this.data = data;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.length = length;
        this.isImage = isImage;
    }

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

    public boolean isImage() {
        return isImage;
    }

    @Override
    public String toString() {
        return "MediaEntity{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", length=" + length +
                ", isImage=" + isImage +
                '}';
    }
}
