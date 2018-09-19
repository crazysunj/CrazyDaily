package com.crazysunj.domain.repository.photo;

import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/9/17 下午2:49
 * description:
 */
public interface PhotoPickerRepository {
    Flowable<List<MediaEntity>> getMediaList(Date toDate, String[] bucketIds);

    Flowable<List<BucketEntity>> getBucketList();
}
