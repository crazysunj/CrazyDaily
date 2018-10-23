package com.crazysunj.crazydaily.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.util.DisplayMetrics;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author: sunjian
 * created on: 2018/10/23 下午5:09
 * description:
 */
public class ImagePreViewAdapter extends BaseAdapter<String, BaseViewHolder> {

    private List<String> mFilePaths;

    public ImagePreViewAdapter(List<String> data) {
        super(data, R.layout.layout_item_image_preview);
        mFilePaths = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder holder, String data) {
        SubsamplingScaleImageView imageView = holder.getView(R.id.item_image_preview, SubsamplingScaleImageView.class);
        ImageLoader.download(mContext, data, file -> {
            final String path = file.getAbsolutePath();
            if (!mFilePaths.contains(path)) {
                mFilePaths.add(path);
            }
            float initImageScale = getImageScale(path);
            imageView.setMaxScale(initImageScale + 3.0f);
            imageView.setImage(ImageSource.uri(Uri.fromFile(file)), new ImageViewState(initImageScale, new PointF(0, 0), 0));
        });
    }

    public List<String> getData() {
        return mData;
    }

    public void removeImage(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size() - position);
    }
    
    public void deleteTemFile() {
        if (mFilePaths != null && !mFilePaths.isEmpty()) {
            for (String path : mFilePaths) {
                File file = new File(path);
                if (file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                }
            }
        }
    }

    private float getImageScale(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
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
}
