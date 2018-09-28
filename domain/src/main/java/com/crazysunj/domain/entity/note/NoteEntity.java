package com.crazysunj.domain.entity.note;

import java.util.List;

/**
 * author: sunjian
 * created on: 2018/9/27 下午4:00
 * description:
 */
public class NoteEntity {
    /**
     * id
     */
    private long id;
    /**
     * 图片
     */
    private List<String> images;
    /**
     * 文本内容
     */
    private String text;
    /**
     * 是否能下载
     */
    private boolean isCanDownload;

    public NoteEntity(long id, List<String> images, String text, boolean isCanDownload) {
        this.id = id;
        this.images = images;
        this.text = text;
        this.isCanDownload = isCanDownload;
    }

    public long getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }

    public String getText() {
        return text;
    }

    public boolean isCanDownload() {
        return isCanDownload;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", images=" + images +
                ", text='" + text + '\'' +
                ", isCanDownload=" + isCanDownload +
                '}';
    }
}
