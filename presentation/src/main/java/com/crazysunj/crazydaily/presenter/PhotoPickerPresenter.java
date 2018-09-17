package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.PhotoPickerContract;
import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.interactor.photo.PhotoPickerUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * author: sunjian
 * created on: 2018/9/17 下午2:32
 * description:
 */
@ActivityScope
public class PhotoPickerPresenter extends BasePresenter<PhotoPickerContract.View> implements PhotoPickerContract.Presenter {
    private PhotoPickerUseCase mPhotoPickerUseCase;

    @Inject
    public PhotoPickerPresenter(PhotoPickerUseCase photoPickerUseCase) {
        mPhotoPickerUseCase = photoPickerUseCase;
    }

    @Override
    public void getMediaList(String bucketId, int page, int limit) {

    }

    @Override
    public void getBucketList() {
        mPhotoPickerUseCase.execute(new BaseSubscriber<List<BucketEntity>>() {
            @Override
            public void onNext(List<BucketEntity> bucketEntityList) {
                mView.showBucketList(bucketEntityList);
            }
        });
    }
}
