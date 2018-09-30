package com.crazysunj.crazydaily.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.module.image.ImageLoader;

import java.util.List;

/**
 * author: sunjian
 * created on: 2018/9/30 下午2:17
 * description:
 */
public class NoteEditAdapter extends BaseAdapter<String, BaseViewHolder> {

    private OnItemDeleteListener mOnItemDeleteListener;
    private OnItemSelectListener mOnItemSelectListener;

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
                    mOnItemDeleteListener.onDelete(position);
                }
            });
            holder.itemView.setOnClickListener(null);
        }
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
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        mOnItemDeleteListener = listener;
    }

    public interface OnItemSelectListener {
        void onSelect(int selectCount);
    }

    public interface OnItemDeleteListener {
        void onDelete(int position);
    }
}
