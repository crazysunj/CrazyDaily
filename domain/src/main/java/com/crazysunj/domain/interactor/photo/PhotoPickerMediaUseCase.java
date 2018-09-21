package com.crazysunj.domain.interactor.photo;

import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        return mPhotoPickerRepository.getMediaList(params.imageOffset, params.videoOffset, params.bucketId)
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
        private final int imageOffset;
        private final int videoOffset;
        private final String bucketId;

        private Params(int imageOffset, int videoOffset, String bucketId) {
            this.imageOffset = imageOffset;
            this.videoOffset = videoOffset;
            this.bucketId = bucketId;
        }

        public static Params get(int imageOffset, int videoOffset, String bucketId) {
            return new Params(imageOffset, videoOffset, bucketId);
        }
    }
}
