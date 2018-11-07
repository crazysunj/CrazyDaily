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
package com.crazysunj.crazydaily.ui.photo;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.module.permission.PermissionHelper;
import com.crazysunj.crazydaily.module.permission.PermissionStorage;
import com.crazysunj.crazydaily.presenter.PhotoPickerPresenter;
import com.crazysunj.crazydaily.presenter.contract.PhotoPickerContract;
import com.crazysunj.crazydaily.ui.adapter.PhotoPickerAdapter;
import com.crazysunj.crazydaily.ui.photo.dialog.BucketDialog;
import com.crazysunj.domain.entity.photo.BucketEntity;
import com.crazysunj.domain.entity.photo.MediaEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author: sunjian
 * created on: 2018/9/17 下午2:26
 * description: 选择相册视频 https://github.com/crazysunj/CrazyDaily
 */
@RuntimePermissions
public class PhotoPickerActivity extends BaseActivity<PhotoPickerPresenter> implements PhotoPickerContract.View, PermissionStorage {

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 1;

    public static final int MAX_SELECT_NUMBER = 9;
    /**
     * 最后两排的时候加载
     */
    private static final int MAX_LOAD_NUMBER = 8;
    @BindView(R.id.photo_picker_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.photo_picker_complete)
    AppCompatTextView mComplete;
    @BindView(R.id.photo_picker_time_info)
    AppCompatTextView mTimeInfo;
    @BindView(R.id.photo_picker_list)
    RecyclerView mPickerList;
    @BindView(R.id.photo_picker_select)
    AppCompatTextView mSelect;
    @Inject
    PhotoPickerAdapter mAdapter;
    private int mImageOffset = 0;
    private int mVideoOffset = 0;
    private int selectCount = 0;
    private List<BucketEntity> mBucketList;
    private BucketDialog mBucketDialog;
    private boolean mIsComplete = false;
    private boolean mIsFirst = false;
    private boolean mIsLoading = false;
    private String mBucketId = String.valueOf(Integer.MAX_VALUE);
    private ObjectAnimator mTimeInfoShowAnim;
    private ObjectAnimator mTimeInfoHideAnim;
    private int diffCount;

    public static void start(Context context, int selectCount) {
        Intent intent = new Intent(context, PhotoPickerActivity.class);
        intent.putExtra(ActivityConstant.SELECT_COUNT, selectCount);
        context.startActivity(intent);
    }

    public static void start(Activity activity, int selectCount) {
        Intent intent = new Intent(activity, PhotoPickerActivity.class);
        intent.putExtra(ActivityConstant.SELECT_COUNT, selectCount);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void initView() {
        showBack(mToolbar);
        mComplete.setEnabled(false);
        mPickerList.setLayoutManager(new GridLayoutManager(this, 4));
        //noinspection ConstantConditions
        mPickerList.getItemAnimator().setChangeDuration(0);
        mPickerList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mComplete.setText("完成");
        diffCount = getIntent().getIntExtra(ActivityConstant.SELECT_COUNT, 0);
        PhotoPickerActivityPermissionsDispatcher.pickerMediaWithPermissionCheck(this);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemSelectClickListener(this::handleItemSelect);
        mSelect.setOnClickListener(v -> showDialog());
        mPickerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    hideTimeInfo();
                } else {
                    showTimeInfo();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //noinspection ConstantConditions
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                handleTimeInfo(firstVisibleItemPosition);
                final int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                final int count = layoutManager.getItemCount();
                if (!mIsComplete && !mIsLoading && dy > 0 && count - lastVisibleItemPosition <= MAX_LOAD_NUMBER) {
                    // 可以设计成等待队列
                    requestData();
                }
            }
        });
        hideTimeInfo();
        mComplete.setOnClickListener(v -> {
            String[] images = mAdapter.getSelectImage(selectCount);
            Intent intent = new Intent();
            intent.putExtra(ActivityConstant.IMAGES, images);
            setResult(RESULT_CODE, intent);
            finish();
        });
    }

    private void showTimeInfo() {
        if (mTimeInfoHideAnim != null && mTimeInfoHideAnim.isRunning()) {
            mTimeInfoHideAnim.cancel();
        }
        if (mTimeInfoShowAnim == null) {
            mTimeInfoShowAnim = ObjectAnimator.ofFloat(mTimeInfo, "alpha", 0f, 1.0f).setDuration(500);
        }
        if (!mTimeInfoShowAnim.isRunning()) {
            mTimeInfoShowAnim.setFloatValues(mTimeInfo.getAlpha(), 1.0f);
            mTimeInfoShowAnim.start();
        }
    }

    private void hideTimeInfo() {
        if (mTimeInfoHideAnim == null) {
            mTimeInfoHideAnim = ObjectAnimator.ofFloat(mTimeInfo, "alpha", 1.0f, 0f).setDuration(500);
            mTimeInfoHideAnim.setStartDelay(1500);
        }
        mTimeInfoHideAnim.start();
    }

    private void handleTimeInfo(int firstVisibleItemPosition) {
        final long modifiedDate = mAdapter.getHelper().getData().get(firstVisibleItemPosition).getModifiedDate();
        Calendar currentCalendar = Calendar.getInstance();
        final int currentYear = currentCalendar.get(Calendar.YEAR);
        final int currentYearWeek = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        Calendar modifiedCalendar = Calendar.getInstance();
        modifiedCalendar.setTimeInMillis(modifiedDate * 1000);
        final int modifiedYear = modifiedCalendar.get(Calendar.YEAR);
        final int modifiedYearWeek = modifiedCalendar.get(Calendar.WEEK_OF_YEAR);
        if (currentYear == modifiedYear && currentYearWeek == modifiedYearWeek) {
            mTimeInfo.setText("本周");
            return;
        }
        final int currentMonth = currentCalendar.get(Calendar.MONTH);
        final int modifiedMonth = modifiedCalendar.get(Calendar.MONTH);
        if (currentYear == modifiedYear && currentMonth == modifiedMonth) {
            mTimeInfo.setText("这个月");
            return;
        }
        mTimeInfo.setText(String.format(Locale.getDefault(), "%d/%d", modifiedYear, modifiedMonth + 1));
    }

    /**
     * 后期可以把图片展示切换到fragment里面
     */
    private void showDialog() {
        if (mBucketList == null) {
            Toast.makeText(this, "图片还在加载中", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBucketDialog == null) {
            mBucketDialog = BucketDialog.get((ArrayList<BucketEntity>) mBucketList);
            mBucketDialog.setOnBucketItemClickListener(item -> {
                resetData();
                mBucketId = item.getBucketId();
                requestData();
                mBucketDialog.dismiss();
            });
        }
        mBucketDialog.show(this);
    }

    private void resetData() {
        mImageOffset = 0;
        mVideoOffset = 0;
        selectCount = 0;
        mIsComplete = false;
        mIsFirst = true;
        mComplete.setText("完成");
        mComplete.setEnabled(false);
    }

    /**
     * 处理图片选择
     */
    private void handleItemSelect(MediaEntity item) {
        final int currentIndex = item.getIndex();
        final int maxNum = MAX_SELECT_NUMBER - diffCount;
        if (currentIndex > 0) {
            selectCount--;
            item.setIndex(0);
            mAdapter.notifyItem(item);
            mAdapter.resetIndex(currentIndex);
            if (selectCount == 0) {
                mComplete.setText("完成");
                mComplete.setEnabled(false);
            } else {
                mComplete.setText(String.format(Locale.getDefault(), "完成(%d/%d)", selectCount, maxNum));
            }
        } else {
            if (selectCount < maxNum) {
                selectCount++;
                item.setIndex(selectCount);
                mAdapter.notifyItem(item);
                if (!mComplete.isEnabled()) {
                    mComplete.setEnabled(true);
                }
                mComplete.setText(String.format(Locale.getDefault(), "完成(%d/%d)", selectCount, maxNum));
            } else {
                Toast.makeText(this, "最多能选中" + maxNum + "张哦", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showMediaList(int imageOffset, int videoOffset, List<MediaEntity> mediaList) {
        mImageOffset = imageOffset;
        mVideoOffset = videoOffset;
        if (mIsFirst) {
            mAdapter.notifyData(mediaList);
            mIsFirst = false;
        } else {
            mAdapter.appendData(mediaList);
        }
        mIsLoading = false;
        if (mediaList.size() < MediaEntity.DEFAULT_LIMIT) {
            mIsComplete = true;
        }
    }

    @Override
    public void showBucketList(List<BucketEntity> bucketList) {
        mBucketList = bucketList;
        for (BucketEntity entity : mBucketList) {
            if (entity.isSelected()) {
                mBucketId = entity.getBucketId();
                requestData();
                break;
            }
        }
    }

    private void requestData() {
        mIsLoading = true;
        mPresenter.getMediaList(mImageOffset, mVideoOffset, mBucketId);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhotoPickerActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void pickerMedia() {
        mPresenter.getBucketList();
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showRationaleForStorage(PermissionRequest request) {
        PermissionHelper.storageShowRationale(this, request);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showDeniedForStorage() {
        PermissionHelper.storagePermissionDenied(this, PermissionHelper.TYPE_TOAST);
        finish();
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showNeverAskForStorage() {
        PermissionHelper.storageNeverAskAgain(this, PermissionHelper.TYPE_TOAST);
        finish();
    }
}
