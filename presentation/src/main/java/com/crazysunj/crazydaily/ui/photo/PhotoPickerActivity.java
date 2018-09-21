package com.crazysunj.crazydaily.ui.photo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.presenter.PhotoPickerPresenter;
import com.crazysunj.crazydaily.presenter.contract.PhotoPickerContract;
import com.crazysunj.crazydaily.ui.adapter.PhotoPickerAdapter;
import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/9/17 下午2:26
 * description: 选择相册视频
 */
public class PhotoPickerActivity extends BaseActivity<PhotoPickerPresenter> implements PhotoPickerContract.View {

    private static final int MAX_SELECT_NUMBER = 9;
    @BindView(R.id.photo_picker_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.photo_picker_complete)
    AppCompatTextView mComplete;
    @BindView(R.id.photo_picker_list)
    RecyclerView mPickerList;
    @BindView(R.id.photo_picker_select)
    AppCompatTextView mSelect;
    private PhotoPickerAdapter mAdapter;
    private int mImageOffset = 0;
    private int mVideoOffset = 0;
    private int selectCount = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, PhotoPickerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        showBack(mToolbar);
        mPickerList.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new PhotoPickerAdapter();
        mPickerList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mComplete.setText("完成");
        mPresenter.getBucketList();
        mPresenter.getMediaList(mImageOffset, mVideoOffset, String.valueOf(Integer.MAX_VALUE));
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemSelectClickListener(this::handleItemSelect);
    }

    private void handleItemSelect(MediaEntity item) {
        final int currentIndex = item.getIndex();
        if (currentIndex > 0) {
            selectCount--;
            item.setIndex(0);
            mAdapter.notifyItem(item);
            mAdapter.resetIndex(currentIndex);
            if (selectCount == 0) {
                mComplete.setText("完成");
                mComplete.setEnabled(false);
            } else {
                mComplete.setText(String.format(Locale.getDefault(), "完成(%d/%d)", selectCount, MAX_SELECT_NUMBER));
            }
        } else {
            if (selectCount < MAX_SELECT_NUMBER) {
                selectCount++;
                item.setIndex(selectCount);
                mAdapter.notifyItem(item);
                if (!mComplete.isEnabled()) {
                    mComplete.setEnabled(true);
                }
                mComplete.setText(String.format(Locale.getDefault(), "完成(%d/%d)", selectCount, MAX_SELECT_NUMBER));
            } else {
                Toast.makeText(this, "最多能选中" + MAX_SELECT_NUMBER + "张哦", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showMediaList(int imageOffset, int videoOffset, List<MediaEntity> mediaList) {
        mImageOffset = imageOffset;
        mVideoOffset = videoOffset;
        mAdapter.notifyData(mediaList);
        Log.d("PhotoPickerActivity", "mImageOffset:" + imageOffset);
        Log.d("PhotoPickerActivity", "videoOffset:" + videoOffset);
        for (MediaEntity mediaEntity : mediaList) {
            Log.d("PhotoPickerActivity", "modifiedDate:" + mediaEntity.getModifiedDate());
            Log.d("PhotoPickerActivity", "duration:" + mediaEntity.getDuration());
        }
        Log.d("PhotoPickerActivity", "size:" + mediaList.size());
    }

    @Override
    public void showBucketList(List<BucketEntity> bucketList) {
        for (BucketEntity bucketEntity : bucketList) {
//            Log.d("PhotoPickerActivity", "bucketName:" + bucketEntity.getBucketName());
//            Log.d("PhotoPickerActivity", "count:" + bucketEntity.getCount());
            if (bucketEntity.getBucketName().equals("图片和视频")) {
                mPresenter.getMediaList(0, 0, bucketEntity.getBucketId());
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

    @Override
    protected int getContentResId() {
        return R.layout.activity_photo_picker;
    }
}
