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
import java.util.Calendar;
import java.util.Date;
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

//    @Override
//    public Flowable<List<MediaEntity>> getMediaList(String bucketId, int page, int limit) {
//        if (TextUtils.equals(bucketId, String.valueOf(Integer.MAX_VALUE))) {
//            // 图片和视频
//            return null;
//        } else if (TextUtils.equals(bucketId, String.valueOf(Integer.MIN_VALUE))) {
//            // 所有视频
//            return null;
//        } else {
//            final String selection = MediaStore.Images.Media.BUCKET_ID + "=?";
//            final String[] selectionArgs = new String[]{bucketId};
//            return Flowable.create((FlowableOnSubscribe<List<MediaEntity>>) e -> {
//                e.onNext(handleImageMediaList(selection, selectionArgs, page, limit));
//                e.onComplete();
//            }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
//        }
//    }

    @Override
    public Flowable<List<MediaEntity>> getMediaList(Date toDate, String... bucketIds) {
        if (bucketIds == null || bucketIds.length == 0) {
            return Flowable.empty();
        }
        final String bucketId = bucketIds[0];
        if (TextUtils.equals(bucketId, String.valueOf(Integer.MAX_VALUE))) {
            // 图片和视频
            return null;
        } else if (TextUtils.equals(bucketId, String.valueOf(Integer.MIN_VALUE))) {
            // 所有视频
            return Flowable.create((FlowableOnSubscribe<List<MediaEntity>>) e -> {
                String[] videoProjection = new String[]{
                        MediaStore.Video.Media._ID,
                        MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.DATE_ADDED,
                        MediaStore.Video.Media.DATE_MODIFIED,
                        MediaStore.Video.Media.SIZE,
                };
                Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                e.onNext(handleVideoMediaList(videoUri, videoProjection, toDate, bucketIds));
                e.onComplete();
            }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
        } else {
            return Flowable.create((FlowableOnSubscribe<List<MediaEntity>>) e -> {
                String[] imageProjection = new String[]{
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media.DATE_MODIFIED,
                        MediaStore.Images.Media.SIZE,
                };
                Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                e.onNext(handleImageMediaList(imageUri,
                        imageProjection, toDate, bucketId));
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
    private List<MediaEntity> handleVideoMediaList(Uri videoUri, String[] videoProjection, Date toDate, String[] bucketIds) {


        int preCount = 1;
        int length = 0;
        List<MediaEntity> videoMediaList = new ArrayList<>();
        do {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDate);
            calendar.add(Calendar.MONTH, -preCount++);
            Date fromDate = calendar.getTime();
            String selection = String.format("%s=? and %s>=? and %s<=?", MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.DATE_ADDED);
            for (String bucketId : bucketIds) {
                String[] selectionArgs = {bucketId, String.valueOf(fromDate.getTime() / 1000), String.valueOf(toDate.getTime() / 1000)};
                List<MediaEntity> imageMediaListByDB = handleImageMediaListByDB(selection, selectionArgs, videoUri, videoProjection);

            }
            imageMediaList.addAll(imageMediaListByDB);
            toDate = fromDate;
            final int size = imageMediaListByDB.size();
            length += size;
        } while (length < MediaEntity.DEFAULT_LIMIT);
        return handleVideoMediaListByDB(selection, selectionArgs, videoUri, videoProjection);
    }

    @NonNull
    private List<MediaEntity> handleVideoMediaListByDB(String selection, String[] selectionArgs, Uri videoUri, String[] videoProjection) {
        List<MediaEntity> mediaEntityList = new ArrayList<>();
        Cursor cursor = mContentResolver.query(
                videoUri, videoProjection, selection,
                selectionArgs, String.format("%s DESC", MediaStore.Video.Media.DATE_ADDED));
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
                mediaEntityList.add(new MediaEntity(id, data, createDate, modifiedDate, length, false));
            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return mediaEntityList;
    }

    @NonNull
    private List<MediaEntity> handleImageMediaList(Uri imageUri, String[] imageProjection, Date toDate, String bucketId) {
        int preCount = 1;
        List<MediaEntity> imageMediaList = new ArrayList<>();
        do {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDate);
            calendar.add(Calendar.MONTH, -preCount++);
            Date fromDate = calendar.getTime();
            String[] selectionArgs = {bucketId, String.valueOf(fromDate.getTime() / 1000), String.valueOf(toDate.getTime() / 1000)};
            String selection = String.format("%s=? and %s>=? and %s<=?", MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.DATE_ADDED);
            List<MediaEntity> imageMediaListByDB = handleImageMediaListByDB(selection, selectionArgs, imageUri, imageProjection);
            imageMediaList.addAll(imageMediaListByDB);
            toDate = fromDate;
        } while (imageMediaList.size() < MediaEntity.DEFAULT_LIMIT);
        return imageMediaList;
    }

    @NonNull
    private List<MediaEntity> handleImageMediaListByDB(String selection, String[] selectionArgs, Uri imageUri, String[] imageProjection) {
        List<MediaEntity> mediaEntityList = new ArrayList<>();
        Cursor cursor = mContentResolver.query(
                imageUri, imageProjection, selection,
                selectionArgs, String.format("%s DESC", MediaStore.Images.Media.DATE_ADDED));
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
