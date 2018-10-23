package com.crazysunj.crazydaily.ui.note;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.ui.adapter.ImagePreViewAdapter;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/10/3 上午11:29
 * description:
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

    @Override
    protected void initData() {
        Intent intent = getIntent();
        ArrayList<String> data = intent.getStringArrayListExtra(ActivityConstant.DATA);
        int position = intent.getIntExtra(ActivityConstant.POSITION, 0);
        mTitle.setText(String.format("%s/%s", String.valueOf(position + 1), data.size()));
        mAdapter = new ImagePreViewAdapter(data);
        mImageList.setAdapter(mAdapter);
        //noinspection ConstantConditions
        mImageList.getLayoutManager().scrollToPosition(position);
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(v -> finish());
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
    protected int getContentResId() {
        return R.layout.activity_note_preview;
    }

    private void scrollImage(int i) {
        mTitle.setText(String.format("%s/%s", String.valueOf(i + 1), String.valueOf(mAdapter.getData().size())));
    }
}
