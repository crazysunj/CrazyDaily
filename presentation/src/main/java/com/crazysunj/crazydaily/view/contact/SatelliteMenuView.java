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
package com.crazysunj.crazydaily.view.contact;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.TypefaceCompat;

/**
 * @author: sunjian
 * created on: 2018/4/18 下午3:27
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class SatelliteMenuView extends FrameLayout {

    public static final int RIGHT_BOTTOM = 0;
    public static final int LEFT_BOTTOM = 1;
    public static final int RIGHT_TOP = 2;
    public static final int LEFT_TOP = 3;
    private static final int ANIMATION_DURATION = 400;
    private static final float BUTTON_RATIO = 0.8f;
    private static final float TEXT_RATIO = 0.16f;
    private static final float SIZE_EXPANSION_RATIO = 1.2f;

    private final int DIRECTION;
    private final int SIZE;
    private final int RADIUS;
    private final LayoutParams PARAMS;

    @IntDef({RIGHT_BOTTOM, LEFT_BOTTOM, RIGHT_TOP, LEFT_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    private View mButtonView;
    private boolean isOpen;
    private OnItemClickListener mOnItemClickListener;

    public SatelliteMenuView(@NonNull Context context) {
        this(context, null);
    }

    public SatelliteMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SatelliteMenuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SatelliteMenuView);
        RADIUS = (int) a.getDimension(R.styleable.SatelliteMenuView_satellite_menu_radius, dp2px(context, 120));
        DIRECTION = a.getInteger(R.styleable.SatelliteMenuView_satellite_menu_direction, LEFT_TOP);
        SIZE = (int) a.getDimension(R.styleable.SatelliteMenuView_satellite_menu_size, dp2px(context, 40));
        a.recycle();
        PARAMS = new LayoutParams(SIZE, SIZE);
        PARAMS.gravity = Gravity.END | Gravity.BOTTOM;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            final int widthSize = (int) (MeasureSpec.getSize(widthMeasureSpec) * SIZE_EXPANSION_RATIO);
            final int heightSize = (int) (MeasureSpec.getSize(heightMeasureSpec) * SIZE_EXPANSION_RATIO);
            super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY));
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            final int heightSize = (int) (MeasureSpec.getSize(heightMeasureSpec) * SIZE_EXPANSION_RATIO);
            int measureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
        } else if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.AT_MOST) {
            final int widthSize = (int) (MeasureSpec.getSize(widthMeasureSpec) * SIZE_EXPANSION_RATIO);
            int measureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
        } else {
            int measureSpec = MeasureSpec.makeMeasureSpec((int) ((SIZE + RADIUS) * SIZE_EXPANSION_RATIO), MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
        }
    }

    public void setImgRes(int imgRes) {
        final Context context = getContext();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imgRes);
        final int size = (int) (SIZE * BUTTON_RATIO);
        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.END | Gravity.BOTTOM;
        addView(imageView, params);
        mButtonView = imageView;
        mButtonView.setOnClickListener(v -> {
            if (isOpen) {
                close();
            } else {
                show();
            }
        });
    }

    public void setImgUrl(String url) {
        final Context context = getContext();
        ImageView imageView = new ImageView(context);
        ImageLoader.load(context, url, imageView);
        final int size = (int) (SIZE * BUTTON_RATIO);
        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.END | Gravity.BOTTOM;
        addView(imageView, params);
        mButtonView = imageView;
        mButtonView.setOnClickListener(v -> {
            if (isOpen) {
                close();
            } else {
                show();
            }
        });
    }

    public void setImgText(ImgTextEntity entity) {
        final Context context = getContext();
        TextView textView = new TextView(context);
        @SuppressLint("RestrictedApi")
        Typeface typeface = TypefaceCompat.createFromResourcesFontFile(context, getResources(), R.font.crazydailyicon, "", 0);
        textView.setTypeface(typeface);
        textView.setText(entity.text);
        textView.setTextSize(SIZE * BUTTON_RATIO * TEXT_RATIO);
        OvalShape shape = new OvalShape();
        PaintDrawable shapeDrawable = new PaintDrawable(entity.color);
        shapeDrawable.setShape(shape);
        textView.setTextColor(Color.WHITE);
        textView.setBackground(shapeDrawable);
        textView.setGravity(Gravity.CENTER);
        final int size = (int) (SIZE * BUTTON_RATIO);
        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.END | Gravity.BOTTOM;
        addView(textView, params);
        mButtonView = textView;
        mButtonView.setOnClickListener(v -> {
            if (isOpen) {
                close();
            } else {
                show();
            }
        });
    }


    public void setImgRes(ImgResEntity entity) {
        final Context context = getContext();
        final int dp_6 = dp2px(context, 6);
        final int dp_3 = dp2px(context, 3);
        FloatingActionButton fab = new FloatingActionButton(context);
        fab.setPadding(dp_6, dp_6, dp_6, dp_6);
        fab.setElevation(dp_3);
        fab.setSize(FloatingActionButton.SIZE_MINI);
        fab.setBackgroundTintList(ColorStateList.valueOf(entity.color));
        fab.setImageResource(entity.res);
        fab.setUseCompatPadding(true);
        final int size = (int) (SIZE * BUTTON_RATIO);
        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.END | Gravity.BOTTOM;
        addView(fab, params);
        mButtonView = fab;
        mButtonView.setOnClickListener(v -> {
            if (isOpen) {
                close();
            } else {
                show();
            }
        });
    }

    public void setChildImgRes(@DrawableRes int[] imgRes) {
        if (mButtonView != null) {
            throw new RuntimeException("请在设置子菜单后添加点击按钮");
        }
        if (imgRes == null || imgRes.length == 0) {
            return;
        }
        removeAllViews();
        final int size = imgRes.length;
        final Context context = getContext();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imgRes[i]);
            addView(imageView, PARAMS);
            final int position = i;
            imageView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public void setChildImgUrl(List<String> urls) {
        if (mButtonView != null) {
            throw new RuntimeException("请在设置子菜单后添加点击按钮");
        }
        if (urls == null || urls.size() == 0) {
            return;
        }
        removeAllViews();
        final int size = urls.size();
        final Context context = getContext();
        for (int i = 0; i < size; i++) {
            final String url = urls.get(i);
            ImageView imageView = new ImageView(context);
            ImageLoader.load(context, url, imageView);
            addView(imageView, PARAMS);
            final int position = i;
            imageView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public void setChildImgRes(List<ImgResEntity> data) {
        if (mButtonView != null) {
            throw new RuntimeException("请在设置子菜单后添加点击按钮");
        }
        if (data == null || data.size() == 0) {
            return;
        }
        removeAllViews();
        final int size = data.size();
        final Context context = getContext();
        final int dp_6 = dp2px(context, 6);
        final int dp_3 = dp2px(context, 3);
        for (int i = 0; i < size; i++) {
            ImgResEntity entity = data.get(i);
            FloatingActionButton fab = new FloatingActionButton(context);
            fab.setPadding(dp_6, dp_6, dp_6, dp_6);
            fab.setElevation(dp_3);
            fab.setSize(FloatingActionButton.SIZE_MINI);
            fab.setBackgroundTintList(ColorStateList.valueOf(entity.color));
            fab.setImageResource(entity.res);
            fab.setScaleX(0);
            fab.setScaleY(0);
            fab.setUseCompatPadding(true);
            addView(fab, PARAMS);
            final int position = i;
            fab.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public void setChildImgText(List<ImgTextEntity> data) {
        if (mButtonView != null) {
            throw new RuntimeException("请在设置子菜单后添加点击按钮");
        }
        if (data == null || data.size() == 0) {
            return;
        }
        removeAllViews();
        final int size = data.size();
        final Context context = getContext();
        for (int i = 0; i < size; i++) {
            ImgTextEntity entity = data.get(i);
            TextView textView = new TextView(context);
            @SuppressLint("RestrictedApi")
            Typeface typeface = TypefaceCompat.createFromResourcesFontFile(context, getResources(), R.font.crazydailyicon, "", 0);
            textView.setTypeface(typeface);
            textView.setText(entity.text);
            textView.setTextSize(SIZE * TEXT_RATIO);
            OvalShape shape = new OvalShape();
            PaintDrawable shapeDrawable = new PaintDrawable(entity.color);
            shapeDrawable.setShape(shape);
            textView.setTextColor(Color.WHITE);
            textView.setBackground(shapeDrawable);
            textView.setGravity(Gravity.CENTER);
            textView.setAlpha(0);
            textView.setScaleX(0);
            textView.setScaleY(0);
            addView(textView, PARAMS);
            final int position = i;
            textView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public void show() {
        if (mButtonView == null) {
            throw new RuntimeException("请在设置点击按钮");
        }
        final int size = getChildCount() - 1;
        if (size == 0) {
            throw new RuntimeException("请在设置子菜单");
        }

        isOpen = true;
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mButtonView, "rotation", 0, 45);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        AnimatorSet.Builder builder = animatorSet.play(rotation);

        for (int i = 0; i < size; i++) {
            PointF point = new PointF();
            int avgAngle = (90 / (size - 1));
            int angle = avgAngle * i;
            switch (DIRECTION) {
                case RIGHT_BOTTOM:
                    point.x = (float) Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                case LEFT_BOTTOM:
                    point.x = (float) -Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                case RIGHT_TOP:
                    point.x = (float) Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) -Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                case LEFT_TOP:
                    point.x = (float) -Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) -Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                default:
                    point.x = (float) -Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) -Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
            }
            View child = getChildAt(i);
            ObjectAnimator translationX = ObjectAnimator.ofFloat(child, "translationX", 0, point.x);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(child, "alpha", 0, 1.0f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(child, "scaleX", 0, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(child, "scaleY", 0, 1.0f);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(child, "translationY", 0, point.y);
            builder.with(translationX).with(translationY).with(alpha).with(scaleX).with(scaleY);
        }
        animatorSet.start();
    }

    public boolean isMenuOpen() {
        return isOpen;
    }

    public void close() {
        if (mButtonView == null) {
            throw new RuntimeException("请在设置点击按钮");
        }
        final int size = getChildCount() - 1;
        if (size == 0) {
            throw new RuntimeException("请在设置子菜单");
        }

        isOpen = false;
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mButtonView, "rotation", 45, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        AnimatorSet.Builder builder = animatorSet.play(rotation);

        for (int i = 0; i < size; i++) {
            PointF point = new PointF();
            int avgAngle = (90 / (size - 1));
            int angle = avgAngle * i;
            switch (DIRECTION) {
                case RIGHT_BOTTOM:
                    point.x = (float) Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                case LEFT_BOTTOM:
                    point.x = (float) -Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                case RIGHT_TOP:
                    point.x = (float) Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) -Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                case LEFT_TOP:
                    point.x = (float) -Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) -Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
                default:
                    point.x = (float) -Math.cos(angle * Math.PI / 180) * RADIUS;
                    point.y = (float) -Math.sin(angle * Math.PI / 180) * RADIUS;
                    break;
            }
            View child = getChildAt(i);
            ObjectAnimator translationX = ObjectAnimator.ofFloat(child, "translationX", point.x, 0);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(child, "alpha", 1.0f, 0);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(child, "scaleX", 1.0f, 0);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(child, "scaleY", 1.0f, 0);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(child, "translationY", point.y, 0);
            builder.with(translationX).with(translationY).with(alpha).with(scaleX).with(scaleY);
        }
        animatorSet.start();
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
