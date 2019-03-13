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

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.util.DateUtil;
import com.crazysunj.crazydaily.util.ScreenUtil;
import com.crazysunj.crazydaily.view.video.NeihanVideoPlayerController;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

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
    private final NiceVideoPlayer mVideoPlayer;

    @Inject
    public NoteAdapter(Activity activity) {
        super(R.layout.item_note);
        mVideoPlayer = new NiceVideoPlayer(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mVideoPlayer.setLayoutParams(params);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, @NonNull NoteEntity item) {
        TextView date = holder.getTextView(R.id.item_note_date);
        ImageView menu = holder.getImageView(R.id.item_note_menu);

        TextView content = holder.getTextView(R.id.item_note_content);
        date.setText(DateUtil.formatDate(item.getId(), DateUtil.PATTERN_ONE));
        menu.setColorFilter(Color.parseColor("#333333"));
        content.setText(item.getText());
        String videoUrl = item.getVideoUrl();
        ViewGroup typeVideo = holder.getView(R.id.item_note_type_video);
        ViewGroup typeImage = holder.getView(R.id.item_note_type_image);
        if (videoUrl == null) {
            typeVideo.setVisibility(View.GONE);
            typeImage.setVisibility(View.VISIBLE);
            RecyclerView images = holder.getView(R.id.item_note_images, RecyclerView.class);
            TextView indicator = holder.getTextView(R.id.item_note_images_indicator);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(ScreenUtil.dp2px(mContext, 15));
            drawable.setColor(Color.parseColor("#7FB2B2B2"));
            indicator.setBackground(drawable);
            // 图片显示
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
        } else {
            typeVideo.setVisibility(View.VISIBLE);
            typeImage.setVisibility(View.GONE);
            ImageView thumbnail = holder.getView(R.id.item_note_video_thumbnail);
            ImageLoader.load(mContext, videoUrl, thumbnail);
            thumbnail.setOnClickListener(v -> {
                ViewGroup parent = (ViewGroup) mVideoPlayer.getParent();
                if (parent != null) {
                    parent.removeView(mVideoPlayer);
                    resetRecycler();
                }
                NeihanVideoPlayerController controller = new NeihanVideoPlayerController(mContext);
                controller.setTitle("");
                mVideoPlayer.setUp(videoUrl, null);
                mVideoPlayer.setController(controller);
                mVideoPlayer.start();
                typeVideo.addView(mVideoPlayer);
            });
        }
        menu.setOnClickListener(v -> {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.onClick(mData.get(holder.getLayoutPosition()));
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        int position = holder.getLayoutPosition();
        NoteEntity item = mData.get(position);
        String videoUrl = item.getVideoUrl();
        if (videoUrl != null) {
            ViewGroup typeVideo = holder.getView(R.id.item_note_type_video);
            if (typeVideo.getChildCount() > 2) {
                // 说明已经添加video播放或者说已进入窗口模式
                typeVideo.removeView(mVideoPlayer);
                item.setRecycler(true);
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        NoteEntity item = mData.get(position);
        String videoUrl = item.getVideoUrl();
        if (videoUrl != null) {
            ViewGroup typeVideo = holder.getView(R.id.item_note_type_video);
            if (item.isRecycler()) {
                // 说明该位置添加了video或者正在窗口播放
                if (mVideoPlayer.isTinyWindow()) {
                    mVideoPlayer.exitTinyWindow();
                }
                typeVideo.addView(mVideoPlayer);
                item.setRecycler(false);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int position = holder.getLayoutPosition();
        NoteEntity item = mData.get(position);
        String videoUrl = item.getVideoUrl();
        if (videoUrl != null) {
            ViewGroup typeVideo = holder.getView(R.id.item_note_type_video);
            if (typeVideo.getChildCount() > 2) {
                // 说明已经添加video播放
                typeVideo.removeView(mVideoPlayer);
                if (mVideoPlayer.isPlaying()) {
                    mVideoPlayer.enterTinyWindow();
                }
                item.setRecycler(true);
            }
        }
    }

    private void resetRecycler() {
        for (NoteEntity item : mData) {
            String videoUrl = item.getVideoUrl();
            if (videoUrl != null) {
                item.setRecycler(false);
            }
        }
    }

    public void onPause() {
        if (mVideoPlayer.isPlaying()) {
            mVideoPlayer.pause();
        }
    }

    private void scrollImage(int i, BaseViewHolder holder) {
        final int position = holder.getAdapterPosition();
        String videoUrl = mData.get(position).getVideoUrl();
        holder.getTextView(R.id.item_note_images_indicator)
                .setText(String.format(Locale.getDefault(), "%d/%d", i + 1, videoUrl == null ? mData.get(position).getImages().size() : 1));
    }

    private static class ImageAdapter extends BaseAdapter<String, BaseViewHolder> {

        private ImageAdapter(List<String> data) {
            super(data, R.layout.layout_image);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, @NonNull String item) {
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

    public boolean isEmpty() {
        return mData.isEmpty();
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
