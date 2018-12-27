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
package com.crazysunj.crazydaily.ui.note;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.ui.adapter.ImagePreViewAdapter;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author: sunjian
 * created on: 2018/10/3 上午11:29
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NotePreviewActivity extends BaseActivity {

    public static final int REQUEST_CODE = 2;
    public static final int RESULT_CODE = 2;

    @BindView(R.id.note_preview_image_list)
    RecyclerView mImageList;
    @BindView(R.id.note_preview_bar)
    View mBar;
    @BindView(R.id.note_preview_back)
    AppCompatImageView mBack;
    @BindView(R.id.note_preview_title)
    AppCompatTextView mTitle;
    @BindView(R.id.note_preview_delete)
    AppCompatImageView mDelete;

    private ImagePreViewAdapter mAdapter;
    private PagerSnapHelper mPagerSnapHelper;

    public static void start(Activity activity, int position, ArrayList<String> data, Pair<View, String>... pairs) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                (activity, pairs);
        Intent intent = new Intent(activity, NotePreviewActivity.class);
        intent.putExtra(ActivityConstant.POSITION, position);
        intent.putExtra(ActivityConstant.DATA, data);
        activity.startActivityForResult(intent, REQUEST_CODE, optionsCompat.toBundle());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 0, mBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.deleteTemFile();
    }

    @Override
    protected void initView() {
        mDelete.setColorFilter(Color.WHITE);
        mPagerSnapHelper = new PagerSnapHelper();
        mImageList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 重置
        mImageList.setOnFlingListener(null);
        mPagerSnapHelper.attachToRecyclerView(mImageList);
        mImageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                scrollImage(getCurrentPosition(recyclerView));
            }
        });
    }

    private int getCurrentPosition(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //noinspection ConstantConditions
        return layoutManager.getPosition(mPagerSnapHelper.findSnapView(layoutManager));
    }

    private float getImageScale(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // 拿到图片的宽和高
        int dw = bitmap.getWidth();
        int dh = bitmap.getHeight();
        float scale = 1.0f;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw;
        }
        return scale;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        ArrayList<String> data = intent.getStringArrayListExtra(ActivityConstant.DATA);
        int position = intent.getIntExtra(ActivityConstant.POSITION, 0);
        mTitle.setText(String.format("%s/%s", String.valueOf(position + 1), data.size()));
        handlePreViewImageData(data, imageData -> {
            if (imageData == null) {
                return;
            }
            mAdapter = new ImagePreViewAdapter(imageData);
            mImageList.setAdapter(mAdapter);
            //noinspection ConstantConditions
            mImageList.getLayoutManager().scrollToPosition(position);
        });
    }

    private void handlePreViewImageData(List<String> data, OnCompleteListener listener) {
        List<ImagePreViewAdapter.ImagePreViewEntity> imageData = new ArrayList<>();
        handlePreViewImageItem(0, data, imageData, listener);
    }

    private void handlePreViewImageItem(int index, List<String> data, List<ImagePreViewAdapter.ImagePreViewEntity> imageData, OnCompleteListener listener) {
        String original = data.get(index);
        ImageLoader.download(this, original, file -> {
            if (file == null) {
                listener.onComplete(null);
                return;
            }
            String absolutePath = file.getAbsolutePath();
            float imageScale = getImageScale(absolutePath);
            imageData.add(new ImagePreViewAdapter.ImagePreViewEntity(original, absolutePath, imageScale + 3.0f,
                    ImageSource.uri(Uri.fromFile(file)), new ImageViewState(imageScale, new PointF(0, 0), 0)));
            final int nextIndex = index + 1;
            if (nextIndex == data.size()) {
                listener.onComplete(imageData);
            } else {
                handlePreViewImageItem(nextIndex, data, imageData, listener);
            }
        });
    }

    public interface OnCompleteListener {
        /**
         * 监听地址地址结束
         */
        void onComplete(@Nullable List<ImagePreViewAdapter.ImagePreViewEntity> imageData);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(v -> back());
        mDelete.setOnClickListener(v -> {
            int currentPosition = getCurrentPosition(mImageList);
            int currentSize = mAdapter.getData().size();
            mAdapter.removeImage(currentPosition);
            if (currentSize <= 1) {
                // 删完
                mTitle.setText("-/-");
            } else if (currentPosition == currentSize - 1) {
                // 删除的是最后一个
                mTitle.setText(String.format("%s/%s", String.valueOf(currentPosition), String.valueOf(currentSize - 1)));
            } else {
                mTitle.setText(String.format("%s/%s", String.valueOf(currentPosition + 1), String.valueOf(currentSize - 1)));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        Intent intent = getIntent();
        intent.putExtra(ActivityConstant.DATA, mAdapter.getData());
        setResult(RESULT_CODE, intent);
        finish();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_note_preview;
    }

    private void scrollImage(int i) {
        if (mTitle != null) {
            mTitle.setText(String.format("%s/%s", String.valueOf(i + 1), String.valueOf(mAdapter.getData().size())));
        }
    }
}
