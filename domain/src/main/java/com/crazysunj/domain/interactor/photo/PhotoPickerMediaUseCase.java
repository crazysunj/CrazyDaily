package com.crazysunj.domain.interactor.photo;

import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/9/17 下午3:38
 * description:
 */
public class PhotoPickerMediaUseCase extends UseCase<List<MediaEntity>, PhotoPickerMediaUseCase.Params> {
    private final PhotoPickerRepository mPhotoPickerRepository;

    @Inject
    public PhotoPickerMediaUseCase(PhotoPickerRepository photoPickerRepository) {
        mPhotoPickerRepository = photoPickerRepository;
    }

    /**
     * 根据修改时间排序，好像跟微信的没对上，微信应该想的更多
     */
    @Override
    protected Flowable<List<MediaEntity>> buildUseCaseObservable(Params params) {
        return mPhotoPickerRepository.getMediaList(params.toDate, params.bucketIds);
    }

    public static final class Params {

        private final Date toDate;
        private String[] bucketIds;

        private Params(String[] bucketIds) {
            this.toDate = new Date();
            this.bucketIds = bucketIds;
        }

        public static Params get(String... bucketIds) {
            return new Params(bucketIds);
        }
    }
}
