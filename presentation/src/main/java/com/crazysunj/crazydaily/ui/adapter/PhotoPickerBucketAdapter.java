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
package com.crazysunj.crazydaily.ui.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.domain.entity.photo.BucketEntity;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author: sunjian
 * created on: 2018/9/25 上午11:06
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class PhotoPickerBucketAdapter extends BaseAdapter<BucketEntity, BaseViewHolder> {

    private OnBucketItemClickListener mOnBucketItemClickListener;

    public PhotoPickerBucketAdapter(List<BucketEntity> data) {
        super(data, R.layout.item_photo_picker_bucket);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, BucketEntity item) {
        ImageView icon = holder.getImageView(R.id.item_bucket_icon);
        TextView info = holder.getTextView(R.id.item_bucket_info);
        View selectIcon = holder.getView(R.id.item_bucket_select_icon);
        View videoIcon = holder.getView(R.id.item_bucket_video_icon);
        ImageLoader.load(mContext, item.getData(), icon);
        final String bucketName = item.getBucketName();
        final String bucketId = item.getBucketId();
        info.setText(getInfo(bucketId, bucketName, item.getCount()));
        selectIcon.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);
        videoIcon.setVisibility(String.valueOf(Integer.MIN_VALUE).equals(bucketId) ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> {
            handleSelect(item);
            if (mOnBucketItemClickListener != null) {
                mOnBucketItemClickListener.onItemClick(item);
            }
        });
    }

    private void handleSelect(BucketEntity item) {
        final int size = mData.size();
        for (int i = 0; i < size; i++) {
            BucketEntity entity = mData.get(i);
            if (entity.isSelected()) {
                entity.setSelected(false);
                notifyItemChanged(i);
                break;
            }
        }
        item.setSelected(true);
        notifyItemChanged(mData.indexOf(item));
    }

    private CharSequence getInfo(String bucketId, String bucketName, int count) {
        SpannableStringBuilder builder = new SpannableStringBuilder(bucketName);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, bucketName.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(16, true), 0, bucketName.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        if (!String.valueOf(Integer.MAX_VALUE).equals(bucketId)) {
            builder.append("\n").append(String.valueOf(count)).append("张");
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), bucketName.length(), builder.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            builder.setSpan(new AbsoluteSizeSpan(12, true), bucketName.length(), builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    public void setOnBucketItemClickListener(OnBucketItemClickListener listener) {
        mOnBucketItemClickListener = listener;
    }

    public interface OnBucketItemClickListener {
        void onItemClick(BucketEntity item);
    }
}
