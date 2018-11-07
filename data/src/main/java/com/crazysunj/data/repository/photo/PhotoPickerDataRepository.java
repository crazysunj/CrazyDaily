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
package com.crazysunj.data.repository.photo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: sunjian
 * created on: 2018/9/17 下午3:06
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class PhotoPickerDataRepository implements PhotoPickerRepository {

    private ContentResolver mContentResolver;

    @Inject
    public PhotoPickerDataRepository(Context context) {
        mContentResolver = context.getContentResolver();
    }

    @Override
    public Flowable<MediaEntity.MediaResponseData> getMediaList(int imageOffset, int videoOffset, String bucketId) {
        if (TextUtils.isEmpty(bucketId)) {
            return Flowable.empty();
        }
        if (TextUtils.equals(bucketId, String.valueOf(Integer.MAX_VALUE))) {
            // 图片和视频
            return Flowable.create((FlowableOnSubscribe<MediaEntity.MediaResponseData>) e -> {
                e.onNext(handleImageAndVideoMediaList(imageOffset, videoOffset));
                e.onComplete();
            }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
        } else if (TextUtils.equals(bucketId, String.valueOf(Integer.MIN_VALUE))) {
            // 所有视频
            return Flowable.create((FlowableOnSubscribe<MediaEntity.MediaResponseData>) e -> {
                e.onNext(handleVideoMediaList(imageOffset, videoOffset));
                e.onComplete();
            }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
        } else {
            return Flowable.create((FlowableOnSubscribe<MediaEntity.MediaResponseData>) e -> {
                e.onNext(handleImageMediaList(imageOffset, videoOffset, bucketId));
                e.onComplete();
            }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
        }
    }

    @Override
    public Flowable<List<BucketEntity>> getBucketList() {
        return Flowable.create((FlowableOnSubscribe<List<BucketEntity>>) e -> {
            e.onNext(handleBucketData());
            e.onComplete();
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
    }

    @NonNull
    private MediaEntity.MediaResponseData handleImageAndVideoMediaList(int imageOffset, int videoOffset) {
        String[] videoProjection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION,
        };
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] imageProjection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE,
        };
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return handleImageAndVideoMediaListByDB(imageUri, imageProjection, videoUri, videoProjection, imageOffset, videoOffset);
    }

    @NonNull
    private MediaEntity.MediaResponseData handleImageAndVideoMediaListByDB(Uri imageUri, String[] imageProjection, Uri videoUri, String[] videoProjection, int imageOffset, int videoOffset) {
        List<MediaEntity> mediaList = new ArrayList<>();
        MediaEntity.MediaResponseData mediaResponseData;
        List<MediaEntity> imageMediaList = handleImageMediaListByDB(null, null, imageUri, imageProjection, imageOffset);
        List<MediaEntity> videoMediaList = handleVideoMediaListByDB(videoUri, videoProjection, videoOffset);
        final int imageSize = imageMediaList.size();
        final int videoSize = videoMediaList.size();
        if (imageSize == 0 && videoSize == 0) {
            imageOffset = 0;
            videoOffset = 0;
            mediaResponseData = new MediaEntity.MediaResponseData(imageOffset, videoOffset, mediaList);
        } else if (imageSize == 0 && videoSize != 0) {
            if (videoSize >= MediaEntity.DEFAULT_LIMIT) {
                videoOffset += MediaEntity.DEFAULT_LIMIT;
            } else {
                imageOffset = 0;
                videoOffset = 0;
            }
            mediaList.addAll(videoMediaList);
            mediaResponseData = new MediaEntity.MediaResponseData(imageOffset, videoOffset, mediaList);
        } else if (imageSize != 0 && videoSize == 0) {
            if (imageSize >= MediaEntity.DEFAULT_LIMIT) {
                imageOffset += MediaEntity.DEFAULT_LIMIT;
            } else {
                imageOffset = 0;
                videoOffset = 0;
            }
            mediaList.addAll(imageMediaList);
            mediaResponseData = new MediaEntity.MediaResponseData(imageOffset, videoOffset, mediaList);
        } else {
            final int size = imageSize + videoSize >= MediaEntity.DEFAULT_LIMIT ? MediaEntity.DEFAULT_LIMIT : imageSize + videoSize;
            int imageIndex = 0;
            int videoIndex = 0;
            MediaEntity imageMediaEntity = imageMediaList.get(imageIndex++);
            MediaEntity videoMediaEntity = videoMediaList.get(videoIndex++);
            for (int i = 0; i < size; i++) {
                if (imageMediaEntity == null && videoMediaEntity != null) {
                    mediaList.add(videoMediaEntity);
                    if (i != size - 1) {
                        videoMediaEntity = videoIndex < videoSize ? videoMediaList.get(videoIndex++) : null;
                    }
                } else if (imageMediaEntity != null && videoMediaEntity == null) {
                    mediaList.add(imageMediaEntity);
                    if (i != size - 1) {
                        imageMediaEntity = imageIndex < imageSize ? imageMediaList.get(imageIndex++) : null;
                    }
                } else if (imageMediaEntity != null && videoMediaEntity != null) {
                    long imageModifiedDate = imageMediaEntity.getModifiedDate();
                    long videoModifiedDate = videoMediaEntity.getModifiedDate();
                    if (imageModifiedDate >= videoModifiedDate) {
                        mediaList.add(imageMediaEntity);
                        if (i == size - 1) {
                            videoIndex--;
                        } else {
                            imageMediaEntity = imageIndex < imageSize ? imageMediaList.get(imageIndex++) : null;
                        }
                    } else {
                        mediaList.add(videoMediaEntity);
                        if (i == size - 1) {
                            imageIndex--;
                        } else {
                            videoMediaEntity = videoIndex < videoSize ? videoMediaList.get(videoIndex++) : null;
                        }
                    }
                }
            }
            imageOffset += imageIndex;
            videoOffset += videoIndex;
            mediaResponseData = new MediaEntity.MediaResponseData(imageOffset, videoOffset, mediaList);
        }
        return mediaResponseData;
//        Cursor videoCursor = mContentResolver.query(
//                videoUri, videoProjection, null,
//                null, String.format("%s DESC LIMIT %s OFFSET %s", MediaStore.Video.Media.DATE_MODIFIED, MediaEntity.DEFAULT_LIMIT, videoOffset));
//        Cursor imageCursor = mContentResolver.query(
//                imageUri, imageProjection, null,
//                null, String.format("%s DESC LIMIT %s OFFSET %s", MediaStore.Images.Media.DATE_MODIFIED, MediaEntity.DEFAULT_LIMIT, imageOffset));
//
//        if (imageCursor != null && !imageCursor.isClosed()) {
//            imageCursor.close();
//        }
//        if (videoCursor != null && !videoCursor.isClosed()) {
//            videoCursor.close();
//        }
    }

    @NonNull
    private MediaEntity.MediaResponseData handleVideoMediaList(int imageOffset, int videoOffset) {
        String[] videoProjection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION,
        };
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        List<MediaEntity> videoMediaListByDB = handleVideoMediaListByDB(videoUri, videoProjection, videoOffset);
        final int videoMediaListSizeByDB = videoMediaListByDB.size();
        if (videoMediaListSizeByDB >= MediaEntity.DEFAULT_LIMIT) {
            videoOffset += MediaEntity.DEFAULT_LIMIT;
        } else {
            videoOffset = 0;
        }
        return new MediaEntity.MediaResponseData(imageOffset, videoOffset, videoMediaListByDB);
    }

//    @NonNull
//    private MediaEntity.MediaResponseData handleVideoMediaList(Uri videoUri, String[] videoProjection, Date toDate, int page, int offset, int count) {
//        List<MediaEntity> videoMediaList = new ArrayList<>();
//        do {
//            List<MediaEntity> testVideoMediaList = new ArrayList<>();
//            // 获取前一个月的最后一天
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(toDate);
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 0, 23, 59, 59);
//            Date fromDate = calendar.getTime();
//            String selection = String.format("%s>? and %s<=?", MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.DATE_ADDED);
//            String[] selectionArgs = {String.valueOf(fromDate.getTime() / 1000), String.valueOf(toDate.getTime() / 1000)};
//            int limit = MediaEntity.DEFAULT_LIMIT - videoMediaList.size();
//            List<MediaEntity> videoMediaListByDB = handleVideoMediaListByDB(selection, selectionArgs, videoUri, videoProjection, offset, limit);
//            int videoMediaListSizeByDB = videoMediaListByDB.size();
//
//            testVideoMediaList.addAll(videoMediaListByDB);
//
//            if (videoMediaListSizeByDB >= MediaEntity.DEFAULT_LIMIT) {
//                page++;
//                offset = (page - 1) * MediaEntity.DEFAULT_LIMIT;
//                videoMediaList.addAll(videoMediaListByDB);
//                break;
//            } else {
//                videoMediaList.addAll(videoMediaListByDB);
//                if (videoMediaList.size() >= count) {
//                    page = 1;
//                    offset = 0;
//                    break;
//                } else if (videoMediaList.size() >= MediaEntity.DEFAULT_LIMIT) {
//                    offset = videoMediaListSizeByDB;
//                    break;
//                } else {
//                    page = 1;
//                    offset = 0;
//                    toDate = fromDate;
//                }
//            }
//        } while (true);
//        return new MediaEntity().new MediaResponseData(toDate, page, offset, videoMediaList);
//    }

    @NonNull
    private List<MediaEntity> handleVideoMediaListByDB(Uri videoUri, String[] videoProjection, int videoOffset) {
        List<MediaEntity> mediaEntityList = new ArrayList<>();
        Cursor cursor = mContentResolver.query(
                videoUri, videoProjection, null,
                null, String.format("%s DESC LIMIT %s OFFSET %s", MediaStore.Video.Media.DATE_MODIFIED, MediaEntity.DEFAULT_LIMIT, videoOffset));
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                long length = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                if (TextUtils.isEmpty(data) || length <= 0) {
                    continue;
                }
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                long createDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
                long modifiedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                mediaEntityList.add(new MediaEntity(id, data, createDate, modifiedDate, length, duration));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return mediaEntityList;
    }

    @NonNull
    private MediaEntity.MediaResponseData handleImageMediaList(int imageOffset, int videoOffset, String bucketId) {
        String[] imageProjection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE,
        };
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] selectionArgs = {bucketId};
        String selection = String.format("%s=?", MediaStore.Images.Media.BUCKET_ID);
        List<MediaEntity> imageMediaListByDB = handleImageMediaListByDB(selection, selectionArgs, imageUri, imageProjection, imageOffset);
        final int imageMediaListSizeByDB = imageMediaListByDB.size();
        if (imageMediaListSizeByDB >= MediaEntity.DEFAULT_LIMIT) {
            imageOffset += MediaEntity.DEFAULT_LIMIT;
        } else {
            imageOffset = 0;
        }
        return new MediaEntity.MediaResponseData(imageOffset, videoOffset, imageMediaListByDB);
    }

//    @NonNull
//    private MediaEntity.MediaResponseData handleImageMediaList(Uri imageUri, String[] imageProjection, Date toDate, int page, int offset, int count, String bucketId) {
//        List<MediaEntity> imageMediaList = new ArrayList<>();
//        do {
//            // 获取前一个月的最后一天
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(toDate);
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 0, 23, 59, 59);
//            Date fromDate = calendar.getTime();
//            String[] selectionArgs = {bucketId, String.valueOf(fromDate.getTime() / 1000), String.valueOf(toDate.getTime() / 1000)};
//            String selection = String.format("%s=? and %s>? and %s<=?", MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.DATE_ADDED);
//            int limit = MediaEntity.DEFAULT_LIMIT - imageMediaList.size();
//            List<MediaEntity> imageMediaListByDB = handleImageMediaListByDB(selection, selectionArgs, imageUri, imageProjection, offset, limit);
//            int imageMediaListSizeByDB = imageMediaListByDB.size();
//
//            if (imageMediaListSizeByDB >= MediaEntity.DEFAULT_LIMIT) {
//                page++;
//                offset = (page - 1) * MediaEntity.DEFAULT_LIMIT;
//                imageMediaList.addAll(imageMediaListByDB);
//                break;
//            } else {
//                imageMediaList.addAll(imageMediaListByDB);
//                if (imageMediaList.size() >= count) {
//                    page = 1;
//                    offset = 0;
//                    break;
//                } else if (imageMediaList.size() >= MediaEntity.DEFAULT_LIMIT) {
//                    offset = imageMediaListSizeByDB;
//                    break;
//                } else {
//                    page = 1;
//                    offset = 0;
//                    toDate = fromDate;
//                }
//            }
//        } while (true);
//        return new MediaEntity().new MediaResponseData(toDate, page, offset, imageMediaList);
//    }

    @NonNull
    private List<MediaEntity> handleImageMediaListByDB(String selection, String[] selectionArgs, Uri imageUri, String[] imageProjection, int imageOffset) {
        List<MediaEntity> mediaEntityList = new ArrayList<>();
        Cursor cursor = mContentResolver.query(
                imageUri, imageProjection, selection,
                selectionArgs, String.format("%s DESC LIMIT %s OFFSET %s", MediaStore.Images.Media.DATE_MODIFIED, MediaEntity.DEFAULT_LIMIT, imageOffset));
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                long length = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                if (TextUtils.isEmpty(data) || length <= 0) {
                    continue;
                }
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                long createDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                long modifiedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                mediaEntityList.add(new MediaEntity(id, data, createDate, modifiedDate, length));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return mediaEntityList;
    }

    @NonNull
    private List<BucketEntity> handleBucketData() {
        String[] imageProjection = new String[]{
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_MODIFIED,
        };
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] videoProjection = new String[]{
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.DATA,
        };
        Uri videoeUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        List<BucketEntity> imageBucketList = handleImageBucketList(imageUri, imageProjection);
        BucketEntity videoBucketEntity = handleVideoBucketEntity(videoeUri, videoProjection);
        List<BucketEntity> imageAndVideoBucketList = new ArrayList<>();
        BucketEntity imageAndVideoBucketEntity = null;
        if (!imageBucketList.isEmpty()) {
            int count = 0;
            for (BucketEntity imageBucket : imageBucketList) {
                count += imageBucket.getCount();
            }
            if (videoBucketEntity != null) {
                count += videoBucketEntity.getCount();
            }
            imageAndVideoBucketEntity = new BucketEntity(String.valueOf(Integer.MAX_VALUE), "图片和视频", imageBucketList.get(0).getData(), count);
        }
        if (imageAndVideoBucketEntity == null && videoBucketEntity != null) {
            imageAndVideoBucketEntity = new BucketEntity(String.valueOf(Integer.MAX_VALUE), "图片和视频", videoBucketEntity.getData(), videoBucketEntity.getCount());
        }
        if (imageAndVideoBucketEntity != null) {
            imageAndVideoBucketEntity.setSelected(true);
            imageAndVideoBucketList.add(imageAndVideoBucketEntity);
        }
        if (videoBucketEntity != null) {
            imageAndVideoBucketList.add(videoBucketEntity);
        }
        imageAndVideoBucketList.addAll(imageBucketList);
        return imageAndVideoBucketList;
    }

    @Nullable
    private BucketEntity handleVideoBucketEntity(Uri uri, String[] projection) {
        Cursor cursor = mContentResolver.query(uri, projection, null, null, String.format("%s DESC", MediaStore.Video.Media.DATE_MODIFIED));
        BucketEntity videoBucketEntity = null;
//        int allCount = 0;
        if (cursor != null && cursor.getCount() > 0) {
//            List<String> bucketIds = new ArrayList<>();
            cursor.moveToFirst();
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            videoBucketEntity = new BucketEntity(String.valueOf(Integer.MIN_VALUE), "所有视频", data, cursor.getCount());
//            do {
//                String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
//                if (bucketIds.contains(bucketId)) {
//                    continue;
//                }
//                Cursor c = mContentResolver.query(uri, projection, MediaStore.Video.Media.BUCKET_ID + "=?", new String[]{bucketId}, null);
//                int count = 0;
//                if (c != null && c.getCount() > 0) {
//                    count = c.getCount();
//                }
//                if (c != null && !c.isClosed()) {
//                    c.close();
//                }
//                if (videoBucketEntity == null) {
//                    videoBucketEntity = new BucketEntity(String.valueOf(Integer.MIN_VALUE), "所有视频", data);
//                }
//                allCount += count;
//                bucketIds.add(bucketId);
//            } while (cursor.moveToNext());
//            if (videoBucketEntity != null) {
//                videoBucketEntity.setBucketIds(bucketIds);
//            }
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
//        if (videoBucketEntity != null) {
//            videoBucketEntity.setCount(allCount);
//        }
        return videoBucketEntity;
    }

    @NonNull
    private List<BucketEntity> handleImageBucketList(Uri uri, String[] projection) {
        List<BucketEntity> bucketEntityList = new ArrayList<>();
        Cursor cursor = mContentResolver.query(uri, projection, null, null, String.format("%s DESC", MediaStore.Images.Media.DATE_MODIFIED));
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                if (isFilterBucketEntity(bucketEntityList, bucketId)) {
                    continue;
                }
                String bucketName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                long dateModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                Cursor c = mContentResolver.query(uri, projection, MediaStore.Images.Media.BUCKET_ID + "=?", new String[]{bucketId}, null);
                int count = 0;
                if (c != null && c.getCount() > 0) {
                    count = c.getCount();
                }
                if (c != null && !c.isClosed()) {
                    c.close();
                }
                bucketEntityList.add(new BucketEntity(bucketId, bucketName, data, dateModified, count));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return bucketEntityList;
    }

    private boolean isFilterBucketEntity(List<BucketEntity> bucketEntityList, String bucketId) {
        if (TextUtils.isEmpty(bucketId)) {
            return true;
        }
        if (bucketEntityList == null || bucketEntityList.isEmpty()) {
            return false;
        }
        for (BucketEntity bucketEntity : bucketEntityList) {
            if (bucketId.equals(bucketEntity.getBucketId())) {
                return true;
            }
        }
        return false;
    }
}
