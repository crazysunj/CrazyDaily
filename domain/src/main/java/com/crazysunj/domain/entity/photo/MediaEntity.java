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
     * 播放时长，可用于判断是否为视频
     */
    private long duration;

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

    public boolean isImage() {
        return duration == 0;
    }

    @Override
    public String toString() {
        return "MediaEntity{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", length=" + length +
                ", duration=" + duration +
                '}';
    }
}
