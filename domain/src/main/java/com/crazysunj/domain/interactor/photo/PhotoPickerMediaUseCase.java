package com.crazysunj.domain.interactor.photo;

import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: sunjian
 * created on: 2018/9/17 下午3:38
 * description:
 */
public class PhotoPickerMediaUseCase extends UseCase<MediaEntity.MediaResponseData, PhotoPickerMediaUseCase.Params> {
    private final PhotoPickerRepository mPhotoPickerRepository;

    @Inject
    public PhotoPickerMediaUseCase(PhotoPickerRepository photoPickerRepository) {
        mPhotoPickerRepository = photoPickerRepository;
    }

    /**
     * 根据修改时间排序，好像跟微信的没对上，微信应该想的更多
     */
    @Override
    protected Flowable<MediaEntity.MediaResponseData> buildUseCaseObservable(Params params) {
        return mPhotoPickerRepository.getMediaList(params.toDate, params.page, params.offset, params.count, params.bucketId)
                .observeOn(Schedulers.io())
                .map(this::sortList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private MediaEntity.MediaResponseData sortList(MediaEntity.MediaResponseData data) {
        List<MediaEntity> mediaList = data.mediaList;
        if (mediaList == null || mediaList.isEmpty()) {
            return data;
        }
        Collections.sort(mediaList);
        return data;
    }

    public static final class Params {

        private Date toDate;
        private int page;
        private int offset;
        private int count;
        private String bucketId;

        private Params(Date toDate, int page, int offset, int count, String bucketId) {
            this.toDate = toDate;
            this.page = page;
            this.offset = offset;
            this.count = count;
            this.bucketId = bucketId;
        }

        public static Params get(Date toDate, int page, int offset, int count, String bucketId) {
            return new Params(toDate, page, offset, count, bucketId);
        }
    }
}
