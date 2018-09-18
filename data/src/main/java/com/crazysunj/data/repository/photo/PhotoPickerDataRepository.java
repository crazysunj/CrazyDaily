package com.crazysunj.data.repository.photo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;
import com.crazysunj.domain.repository.photo.PhotoPickerRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * author: sunjian
 * created on: 2018/9/17 下午3:06
 * description:
 */
public class PhotoPickerDataRepository implements PhotoPickerRepository {

    private ContentResolver mContentResolver;

    @Inject
    public PhotoPickerDataRepository(Context context) {
        mContentResolver = context.getContentResolver();
    }

    @Override
    public Flowable<List<MediaEntity>> getMediaList(String bucketId, int page, int limit) {
        if (TextUtils.equals(bucketId, String.valueOf(Integer.MAX_VALUE))) {
            // 图片和视频
            return null;
        } else if (TextUtils.equals(bucketId, String.valueOf(Integer.MIN_VALUE))) {
            // 所有视频
            return null;
        } else {
            final String selection = MediaStore.Images.Media.BUCKET_ID + "=?";
            final String[] selectionArgs = new String[]{bucketId};
            return Flowable.create((FlowableOnSubscribe<List<MediaEntity>>) e -> {
                e.onNext(handleImageMediaList(selection, selectionArgs, page, limit));
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
    private List<MediaEntity> handleImageMediaList(String selection, String[] selectionArgs, int page, int limit) {
        final int offset = (page - 1) * limit;
        String[] imageProjection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE,
        };
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return handleImageMediaListByDB(selection, selectionArgs, limit, offset, imageUri, imageProjection);
    }

    @NonNull
    private List<MediaEntity> handleImageMediaListByDB(String selection, String[] selectionArgs, int limit, int offset, Uri imageUri, String[] imageProjection) {
        List<MediaEntity> mediaEntityList = new ArrayList<>();
        Cursor cursor = mContentResolver.query(
                imageUri, imageProjection, selection,
                selectionArgs, MediaStore.Images.Media.DATE_ADDED + " DESC LIMIT " + limit + " OFFSET " + offset);
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
                mediaEntityList.add(new MediaEntity(id, data, createDate, modifiedDate, length, true));
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
        };
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] videoProjection = new String[]{
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.DATA,
        };
        Uri videoeUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        List<BucketEntity> imageBucketList = handleImageBucketList(imageUri, imageProjection);
        BucketEntity videoBucketEntity = handleVideoBucketEntity(videoeUri, videoProjection);
        if (imageBucketList.isEmpty()) {
            imageBucketList.add(videoBucketEntity);
        } else {
            imageBucketList.add(1, videoBucketEntity);
        }
        return imageBucketList;
    }

    @Nullable
    private BucketEntity handleVideoBucketEntity(Uri uri, String[] projection) {
        Cursor cursor = null;
        try {
            cursor = mContentResolver.query(uri, projection, null, null, MediaStore.Video.Media.DATE_ADDED + " DESC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BucketEntity videoBucketEntity = null;
        int allCount = 0;
        if (cursor != null && cursor.getCount() > 0) {
            List<String> bucketIds = new ArrayList<>();
            cursor.moveToFirst();
            do {
                String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                Cursor c = mContentResolver.query(uri, projection, MediaStore.Video.Media.BUCKET_ID + "=?", new String[]{bucketId}, null);
                int count = 0;
                if (c != null && c.getCount() > 0) {
                    count = c.getCount();
                }
                if (c != null && !c.isClosed()) {
                    c.close();
                }
                if (videoBucketEntity == null) {
                    videoBucketEntity = new BucketEntity(String.valueOf(Integer.MIN_VALUE), "所有视频", data);
                }
                allCount += count;
                bucketIds.add(bucketId);
            } while (cursor.moveToNext());
            videoBucketEntity.setBucketIds(bucketIds);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (videoBucketEntity != null) {
            videoBucketEntity.setCount(allCount);
        }
        return videoBucketEntity;
    }

    @NonNull
    private List<BucketEntity> handleImageBucketList(Uri uri, String[] projection) {
        List<BucketEntity> bucketEntityList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContentResolver.query(uri, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
        } catch (Exception e) {
            e.printStackTrace();
        }

        BucketEntity imageAndVideoBucketEntity = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                String bucketName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Cursor c = mContentResolver.query(uri, projection, MediaStore.Images.Media.BUCKET_ID + "=?", new String[]{bucketId}, null);
                int count = 0;
                if (c != null && c.getCount() > 0) {
                    count = c.getCount();
                }
                if (c != null && !c.isClosed()) {
                    c.close();
                }
                if (imageAndVideoBucketEntity == null) {
                    imageAndVideoBucketEntity = new BucketEntity(String.valueOf(Integer.MAX_VALUE), "图片和视频", data, -1);
                    bucketEntityList.add(imageAndVideoBucketEntity);
                }
                bucketEntityList.add(new BucketEntity(bucketId, bucketName, data, count));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return bucketEntityList;
    }
}
