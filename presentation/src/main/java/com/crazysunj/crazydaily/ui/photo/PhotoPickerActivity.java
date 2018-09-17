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
    }

    @Override
    public void showMediaList(List<MediaEntity> mediaList) {

    }

    @Override
    public void showBucketList(List<BucketEntity> bucketList) {
        Log.d("PhotoPickerActivity", bucketList.toString());
    }

    @Override
    protected void initInject() {
        getActivityComponent()
                .inject(this);
    }
}
