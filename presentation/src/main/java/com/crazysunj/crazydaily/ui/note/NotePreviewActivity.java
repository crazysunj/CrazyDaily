package com.crazysunj.crazydaily.ui.note;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jaeger.library.StatusBarUtil;

import java.io.File;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/10/3 上午11:29
 * description:
 */
public class NotePreviewActivity extends BaseActivity {

    public static final int REQUEST_CODE = 2;
    public static final int RESULT_CODE = 2;
    @BindView(R.id.note_preview_image)
    SubsamplingScaleImageView mImage;
    @BindView(R.id.note_preview_bar)
    View mBar;
    @BindView(R.id.note_preview_back)
    AppCompatImageView mBack;
    @BindView(R.id.note_preview_delete)
    AppCompatImageView mDelete;

    private File mSaveFile;

    public static void start(Activity activity, int position, String data, Pair<View, String>... pairs) {
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
        if (mSaveFile != null && mSaveFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            mSaveFile.delete();
        }
    }

    @Override
    protected void initView() {
        mDelete.setColorFilter(Color.WHITE);
    }

    @Override
    protected void initData() {
        String data = getIntent().getStringExtra(ActivityConstant.DATA);
        ImageLoader.download(this, data, file -> {
            mSaveFile = file;
            float initImageScale = getInitImageScale(file.getAbsolutePath());
            mImage.setMaxScale(initImageScale + 3.0f);
            mImage.setImage(ImageSource.uri(Uri.fromFile(file)), new ImageViewState(initImageScale, new PointF(0, 0), 0));
        });
    }

    public float getInitImageScale(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
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
    protected void initListener() {
        mBack.setOnClickListener(v -> finish());
        mDelete.setOnClickListener(v -> {
            Intent intent = getIntent();
            setResult(RESULT_CODE, intent);
            finish();
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_note_preview;
    }
}
