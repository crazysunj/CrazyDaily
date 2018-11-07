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
package com.crazysunj.domain.interactor.photo;

import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: sunjian
 * created on: 2018/9/17 下午3:38
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class PhotoPickerBucketUseCase extends UseCase<List<BucketEntity>, Void> {
    private final PhotoPickerRepository mPhotoPickerRepository;

    @Inject
    public PhotoPickerBucketUseCase(PhotoPickerRepository photoPickerRepository) {
        mPhotoPickerRepository = photoPickerRepository;
    }

    /**
     * 根据修改时间排序，好像跟微信的没对上，微信应该想的更多
     */
    @Override
    protected Flowable<List<BucketEntity>> buildUseCaseObservable(Void aVoid) {
        return mPhotoPickerRepository.getBucketList()
                .observeOn(Schedulers.io())
                .flatMap(Flowable::fromIterable)
                .toSortedList(BucketEntity::compareTo)
                .toFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
