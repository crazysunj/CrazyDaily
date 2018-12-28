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

import android.view.View;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseHelperAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.ui.adapter.helper.PhotoPickerAdapterHelper;
import com.crazysunj.crazydaily.util.StringUtil;
import com.crazysunj.domain.entity.photo.MediaEntity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author: sunjian
 * created on: 2018/9/21 下午1:51
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class PhotoPickerAdapter extends BaseHelperAdapter<MediaEntity, BaseViewHolder, PhotoPickerAdapterHelper> {

    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectClickListener mOnItemSelectClickListener;

    @Inject
    public PhotoPickerAdapter() {
        super(new PhotoPickerAdapterHelper());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, MediaEntity item) {
        AppCompatImageView data = holder.getView(R.id.item_photo_picker_data, AppCompatImageView.class);
        ImageLoader.load(mContext, item.getData(), R.mipmap.ic_launcher, data);
        // 选中
        AppCompatTextView select = holder.getView(R.id.item_photo_picker_select, AppCompatTextView.class);
        final int index = item.getIndex();
        if (index > 0) {
            // 选中
            select.setSelected(true);
            select.setText(String.valueOf(index));
        } else {
            select.setSelected(false);
            select.setText(R.string.ic_tick);
        }
        View video = holder.getView(R.id.item_photo_picker_video);
        // 视频信息
        final long duration = item.getDuration();
        if (duration > 0) {
            // 视频
            video.setVisibility(View.VISIBLE);
            AppCompatTextView durationView = holder.getView(R.id.item_photo_picker_duration, AppCompatTextView.class);
            durationView.setText(StringUtil.handleTimeStringByMilli(duration));
        } else {
            video.setVisibility(View.GONE);
        }
        // 监听
        holder.getView(R.id.item_photo_picker_select_wrap).setOnClickListener(v -> {
            if (mOnItemSelectClickListener != null) {
                mOnItemSelectClickListener.onItemSelectClick(item);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(item);
            }
        });
    }

    public void resetIndex(int unselectIndex) {
        List<MediaEntity> data = mHelper.getData();
        for (MediaEntity item : data) {
            final int index = item.getIndex();
            if (index > unselectIndex) {
                item.setIndex(index - 1);
                mHelper.notifyDataChanged(item);
            }
        }
    }

    public void notifyData(List<MediaEntity> mediaList) {
        mHelper.notifyModuleDataChanged(mediaList, PhotoPickerAdapterHelper.LEVEL_PHOTO_PICKER);
    }

    public void appendData(List<MediaEntity> mediaList) {
        mHelper.addData(mediaList);
    }

    public void notifyItem(MediaEntity item) {
        mHelper.notifyDataChanged(item);
    }

    public String[] getSelectImage(int selectCount) {
        String[] images = new String[selectCount];
        for (MediaEntity entity : mData) {
            if (entity.getIndex() > 0) {
                images[selectCount - entity.getIndex()] = entity.getData();
            }
        }
        return images;
    }

    public void setOnItemSelectClickListener(OnItemSelectClickListener listener) {
        mOnItemSelectClickListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemSelectClickListener {
        void onItemSelectClick(MediaEntity item);
    }

    public interface OnItemClickListener {
        void onItemClick(MediaEntity item);
    }
}
