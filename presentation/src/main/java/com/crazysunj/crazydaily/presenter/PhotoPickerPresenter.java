/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.PhotoPickerContract;
import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.domain.interactor.photo.PhotoPickerBucketUseCase;
import com.crazysunj.domain.interactor.photo.PhotoPickerMediaUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * @author: sunjian
 * created on: 2018/9/17 下午2:32
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ActivityScope
public class PhotoPickerPresenter extends BasePresenter<PhotoPickerContract.View> implements PhotoPickerContract.Presenter {
    private PhotoPickerBucketUseCase mPhotoPickerBucketUseCase;
    private PhotoPickerMediaUseCase mPhotoPickerMediaUseCase;

    @Inject
    PhotoPickerPresenter(PhotoPickerBucketUseCase photoPickerBucketUseCase, PhotoPickerMediaUseCase photoPickerMediaUseCase) {
        mPhotoPickerBucketUseCase = photoPickerBucketUseCase;
        mPhotoPickerMediaUseCase = photoPickerMediaUseCase;
    }

    @Override
    public void getMediaList(int imageOffset, int videoOffset, String bucketId) {
        mPhotoPickerMediaUseCase.execute(PhotoPickerMediaUseCase.Params.get(imageOffset, videoOffset, bucketId), new BaseSubscriber<MediaEntity.MediaResponseData>() {
            @Override
            public void onNext(MediaEntity.MediaResponseData data) {
                if (mView != null) {
                    mView.showMediaList(data.imageOffset, data.videoOffset, data.mediaList);
                }
            }
        });
    }

    @Override
    public void getBucketList() {
        mPhotoPickerBucketUseCase.execute(new BaseSubscriber<List<BucketEntity>>() {
            @Override
            public void onNext(List<BucketEntity> bucketEntityList) {
                if (mView != null) {
                    mView.showBucketList(bucketEntityList);
                }
            }
        });
    }
}
