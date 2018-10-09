package com.crazysunj.crazydaily.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.cardslideview.CardHandler;
import com.crazysunj.cardslideview.CardViewPager;
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

/**
 * author: sunjian
 * created on: 2018/10/8 下午4:37
 * description:
 */
public class NoteAdapter extends BaseAdapter<NoteEntity, BaseViewHolder> {

    private OnMenuClickListener mOnMenuClickListener;

    @Inject
    public NoteAdapter() {
        super(R.layout.item_note);
    }

    @Override
    protected void convert(BaseViewHolder holder, NoteEntity item) {
        TextView date = holder.getTextView(R.id.item_note_date);
        ImageView menu = holder.getImageView(R.id.item_note_menu);
        CardViewPager images = holder.getView(R.id.item_note_images, CardViewPager.class);
        TextView indicator = holder.getTextView(R.id.item_note_images_indicator);
        TextView content = holder.getTextView(R.id.item_note_content);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(ScreenUtil.dp2px(mContext, 15));
        drawable.setColor(Color.parseColor("#7FB2B2B2"));
        indicator.setBackground(drawable);
        date.setText(DateUtil.formatDate(item.getId(), DateUtil.PATTERN_ONE));
        menu.setColorFilter(Color.parseColor("#333333"));
        images.bind(((FragmentActivity) mContext).getSupportFragmentManager(), new NoteHandler(), item.getImages());
        content.setText(item.getText());
        images.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                holder.getTextView(R.id.item_note_images_indicator)
                        .setText(String.format(Locale.getDefault(), "%d/%d", i + 1, mData.get(holder.getLayoutPosition()).getImages().size()));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        menu.setOnClickListener(v -> {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.onClick(mData.get(holder.getLayoutPosition()));
            }
        });
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

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mOnMenuClickListener = listener;
    }

    public interface OnMenuClickListener {
        void onClick(NoteEntity item);
    }

    private static class NoteHandler implements CardHandler<String> {

        @Override
        public View onBind(Context context, String data, int position, int mode) {
            ImageView imageView = new ImageView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            ImageLoader.load(context, data, imageView);
            return imageView;
        }
    }
}
