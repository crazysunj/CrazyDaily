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
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.util.DateUtil;
import com.crazysunj.crazydaily.util.ScreenUtil;
import com.crazysunj.domain.entity.note.NoteEntity;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: sunjian
 * created on: 2018/10/8 下午4:37
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteAdapter extends BaseAdapter<NoteEntity, BaseViewHolder> {

    private OnMenuClickListener mOnMenuClickListener;

    @Inject
    public NoteAdapter() {
        super(R.layout.item_note);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, NoteEntity item) {
        TextView date = holder.getTextView(R.id.item_note_date);
        ImageView menu = holder.getImageView(R.id.item_note_menu);
        RecyclerView images = holder.getView(R.id.item_note_images, RecyclerView.class);
        TextView indicator = holder.getTextView(R.id.item_note_images_indicator);
        TextView content = holder.getTextView(R.id.item_note_content);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(ScreenUtil.dp2px(mContext, 15));
        drawable.setColor(Color.parseColor("#7FB2B2B2"));
        indicator.setBackground(drawable);
        date.setText(DateUtil.formatDate(item.getId(), DateUtil.PATTERN_ONE));
        menu.setColorFilter(Color.parseColor("#333333"));
        content.setText(item.getText());
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        images.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        images.setAdapter(new ImageAdapter(item.getImages()));
        // 重置
        images.setOnFlingListener(null);
        pagerSnapHelper.attachToRecyclerView(images);
        images.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //noinspection ConstantConditions
                final int i = layoutManager.getPosition(pagerSnapHelper.findSnapView(layoutManager));
                scrollImage(i, holder);
            }
        });
        scrollImage(0, holder);
        menu.setOnClickListener(v -> {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.onClick(mData.get(holder.getLayoutPosition()));
            }
        });
    }

    private void scrollImage(int i, BaseViewHolder holder) {
        final int position = holder.getAdapterPosition();
        holder.getTextView(R.id.item_note_images_indicator)
                .setText(String.format(Locale.getDefault(), "%d/%d", i + 1, mData.get(position).getImages().size()));
    }

    private static class ImageAdapter extends BaseAdapter<String, BaseViewHolder> {

        private ImageAdapter(List<String> data) {
            super(data, R.layout.layout_image);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, String item) {
            ImageView imageView = holder.getImageView(R.id.view_image);
            ImageLoader.load(mContext, item, imageView);
        }
    }

    public void appendNote(@NotNull List<NoteEntity> notes) {
        final int size = mData.size();
        mData.addAll(notes);
        notifyItemRangeInserted(size, notes.size());
    }

    public void appendNote(@NotNull NoteEntity note) {
        mData.add(0, note);
        notifyItemInserted(0);
    }

    public void changeNote(@NotNull NoteEntity note) {
        final int postion = mData.indexOf(note);
        mData.set(postion, note);
        notifyItemChanged(postion);
    }

    public void removeNote(@NotNull Long id) {
        final int postion = getRemovePosition(id);
        mData.remove(postion);
        notifyItemRemoved(postion);
        notifyItemRangeChanged(postion, mData.size() - postion);
    }

    private int getRemovePosition(Long id) {
        final int size = mData.size();
        for (int i = 0; i < size; i++) {
            if (mData.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mOnMenuClickListener = listener;
    }

    public interface OnMenuClickListener {
        void onClick(NoteEntity item);
    }
}
