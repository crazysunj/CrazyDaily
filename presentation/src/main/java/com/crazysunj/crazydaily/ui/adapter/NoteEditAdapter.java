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
import android.widget.ImageView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.module.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author: sunjian
 * created on: 2018/9/30 下午2:17
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteEditAdapter extends BaseAdapter<String, BaseViewHolder> {

    private OnItemDeleteListener mOnItemDeleteListener;
    private OnItemSelectListener mOnItemSelectListener;
    private OnItemClickListener mOnItemClickListener;

    public NoteEditAdapter(List<String> data) {
        super(data, R.layout.item_note_edit);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ImageView image = holder.getImageView(R.id.item_note_edit_image);
        final String url = mData.get(position);
        ImageView delete = holder.getImageView(R.id.item_note_edit_delete);
        if (url == null) {
            image.setImageResource(R.mipmap.ic_photo_picker_add);
            delete.setVisibility(View.GONE);
            delete.setOnClickListener(null);
            holder.itemView.setOnClickListener(v -> {
                if (mOnItemSelectListener != null) {
                    mOnItemSelectListener.onSelect(mData.size() - 1);
                }
            });
        } else {
            ImageLoader.load(mContext, url, image);
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(v -> {
                if (mOnItemDeleteListener != null) {
                    mOnItemDeleteListener.onDelete(holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    final int layoutPosition = holder.getLayoutPosition();
                    ArrayList<String> data = new ArrayList<>();
                    final int size = mData.size() - 1;
                    for (int i = 0; i < size; i++) {
                        data.add(mData.get(i));
                    }
                    mOnItemClickListener.onItemClick(layoutPosition, data, holder.getImageView(R.id.item_note_edit_image));
                }
            });
        }
    }

    public List<String> getImages() {
        List<String> images = new ArrayList<>();
        for (String s : mData) {
            if (s == null) {
                continue;
            }
            images.add(s);
        }
        return images;
    }

    public void removePhotoAddItem() {
        removeImage(mData.size() - 1);
    }

    public void addPhotoAddItem() {
        mData.add(null);
        final int position = mData.size() - 1;
        notifyItemInserted(position);
        notifyItemChanged(position);
    }

    public boolean isMinImageSize() {
        final int size = mData.size();
        return size == 1 && mData.get(0) == null;
    }

    public boolean isMaxAddImageSize(int maxLength) {
        final int size = mData.size();
        return maxLength == size;
    }

    public boolean isMaxRemoveImageSize(int maxLength) {
        final int size = mData.size();
        return maxLength == size && mData.get(size - 1) != null;
    }

    public void resetData(@NonNull List<String> images, int maxLength) {
        int resetSize = images.size();
        mData.clear();
        mData.addAll(images);
        if (resetSize < maxLength) {
            mData.add(null);
        }
        notifyDataSetChanged();
    }

    public void appendImage(List<String> images) {
        mData.addAll(0, images);
        final int size = images.size();
        notifyItemRangeInserted(0, size);
        notifyItemRangeChanged(0, size);
    }

    public void removeImage(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size() - position);
    }

    public boolean isPhotoAddItem(int position) {
        return mData.get(position) == null;
    }

    public void moveImage(int dragPosition, int targetPosition) {
        String dragItem = mData.remove(dragPosition);
        mData.add(targetPosition, dragItem);
        notifyItemMoved(dragPosition, targetPosition);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        mOnItemDeleteListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ArrayList<String> data, View view);
    }

    public interface OnItemSelectListener {
        void onSelect(int selectCount);
    }

    public interface OnItemDeleteListener {
        void onDelete(int position);
    }
}
