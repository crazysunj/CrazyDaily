package com.crazysunj.crazydaily.ui.photo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.presenter.PhotoPickerPresenter;
import com.crazysunj.crazydaily.presenter.contract.PhotoPickerContract;
import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;

import java.util.Date;
import java.util.List;

/**
 * author: sunjian
 * created on: 2018/9/17 下午2:26
 * description: 选择相册视频
 */
public class PhotoPickerActivity extends BaseActivity<PhotoPickerPresenter> implements PhotoPickerContract.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, PhotoPickerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_photo_picker;
    }

    @Override
    protected void initData() {
        mPresenter.getBucketList();
//        mPresenter.getMediaList(String.valueOf(Integer.MIN_VALUE));
    }

    @Override
    public void showMediaList(Date toDate, int page, int offset, List<MediaEntity> mediaList) {
        Log.d("PhotoPickerActivity", "date:" + toDate);
        Log.d("PhotoPickerActivity", "page:" + page);
        Log.d("PhotoPickerActivity", "offset:" + offset);
//        for (MediaEntity mediaEntity : mediaList) {
//            Log.d("PhotoPickerActivity", "data:" + mediaEntity.getData());
//            Log.d("PhotoPickerActivity", "modifiedDate:" + mediaEntity.getModifiedDate());
//        }
        Log.d("PhotoPickerActivity", "size:" + mediaList.size());
    }

    @Override
    public void showBucketList(List<BucketEntity> bucketList) {
        for (BucketEntity bucketEntity : bucketList) {
//            Log.d("PhotoPickerActivity", "bucketName:" + bucketEntity.getBucketName());
//            Log.d("PhotoPickerActivity", "count:" + bucketEntity.getCount());
            if (!bucketEntity.getBucketName().equals("所有视频")) {
                mPresenter.getMediaList(new Date(), 1, 0, bucketEntity.getCount(), bucketEntity.getBucketId());
            }
//            List<String> bucketIds = bucketEntity.getBucketIds();
//            if (bucketIds != null) {
//                bucketIds.add(0, String.valueOf(Integer.MIN_VALUE));
//                mPresenter.getMediaList(bucketEntity.getCount(), bucketIds.toArray(new String[bucketIds.size()]));
////                Log.d("PhotoPickerActivity", bucketIds.toString());
//            }
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent()
                .inject(this);
    }
}
