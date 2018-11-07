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
package com.crazysunj.crazydaily.view.threed;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: sunjian
 * created on: 2018/4/24 下午1:38
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CubeReversalView extends FrameLayout {

    private static final int DEGREE = 90;
    private static final int DURATION = 800;
    private TopInAnimation mTopInAnimation;
    private TopOutAnimation mTopOutAnimation;
    private BottomInAnimation mBottomInAnimation;
    private BottomOutAnimation mBottomOutAnimation;
    private FrameLayout mBackgroundView;
    private FrameLayout mForegroundView;
    private final float mTranslationYDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
    private GradientDrawable mBackgroundDrawable;
    private GradientDrawable mForegroundDrawable;
    private ArgbEvaluator mArgbEvaluator;

    public CubeReversalView(@NonNull Context context) {
        this(context, null);
    }

    public CubeReversalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CubeReversalView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBottomOutAnimation = new BottomOutAnimation();
        mBottomOutAnimation.setDuration(DURATION);
        mBottomOutAnimation.setFillAfter(true);
        mBottomInAnimation = new BottomInAnimation();
        mBottomInAnimation.setDuration(DURATION);
        mBottomInAnimation.setFillAfter(true);
        mTopOutAnimation = new TopOutAnimation();
        mTopOutAnimation.setDuration(DURATION);
        mTopOutAnimation.setFillAfter(true);
        mTopInAnimation = new TopInAnimation();
        mTopInAnimation.setDuration(DURATION);
        mTopInAnimation.setFillAfter(true);
        mArgbEvaluator = new ArgbEvaluator();
        mBackgroundDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0x00000000, 0x00000000, 0x00000000});
        mForegroundDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0x00000000, 0x00000000, 0x00000000});
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("必须两个哦");
        }

        Context context = getContext();
        FrameLayout backgroundLayout = new FrameLayout(context);
        View backgroundView = getChildAt(0);
        View foregroundView = getChildAt(1);

        FrameLayout foregroundLayout = new FrameLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        removeAllViewsInLayout();
        ImageView backgroundImg = new ImageView(context);
        ImageView foregroundImg = new ImageView(context);
        backgroundImg.setImageDrawable(mBackgroundDrawable);
        foregroundImg.setImageDrawable(mForegroundDrawable);
        backgroundLayout.addView(backgroundView);
        backgroundLayout.addView(backgroundImg);
        foregroundLayout.addView(foregroundView);
        foregroundLayout.addView(foregroundImg);

        addViewInLayout(foregroundLayout, 0, params);
        addViewInLayout(backgroundLayout, 0, params);
        mBackgroundView = (FrameLayout) getChildAt(0);
        mForegroundView = (FrameLayout) getChildAt(1);
    }

    public void start(boolean isTop) {
        start(isTop, 0);
    }

    public void start(boolean isTop, int index) {
        final float distance = -mTranslationYDistance * index;
        if (isTop) {
            mForegroundView.startAnimation(mTopOutAnimation);
            mBackgroundView.startAnimation(mTopInAnimation);
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f)
                    .setDuration(DURATION);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(animation -> {
                final float value = (float) animation.getAnimatedValue();
                final float oppositeValue = 1 - value;

                int foreStartColor = (int) mArgbEvaluator.evaluate(oppositeValue * 0.8f, 0x00000000, 0xff000000);
                int foreCenterColor = (int) mArgbEvaluator.evaluate(oppositeValue * 0.4f, 0x00000000, 0xff000000);
                int[] foreColors = {foreStartColor, foreCenterColor, 0x00000000};

                int backStartColor = (int) mArgbEvaluator.evaluate(value * 0.8f, 0x00000000, 0xff000000);
                int backCenterColor = (int) mArgbEvaluator.evaluate(value * 0.4f, 0x00000000, 0xff000000);
                int[] backColors = {backStartColor, backCenterColor, 0x00000000};

                mForegroundDrawable.setColors(foreColors);
                mBackgroundDrawable.setColors(backColors);
                setTranslationY(distance * oppositeValue);
            });
            animator.start();
        } else {
            mForegroundView.startAnimation(mBottomInAnimation);
            mBackgroundView.startAnimation(mBottomOutAnimation);
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f)
                    .setDuration(DURATION);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addUpdateListener(animation -> {
                final float value = (float) animation.getAnimatedValue();
                final float oppositeValue = 1 - value;

                int foreStartColor = (int) mArgbEvaluator.evaluate(value * 0.8f, 0x00000000, 0xff000000);
                int foreCenterColor = (int) mArgbEvaluator.evaluate(value * 0.4f, 0x00000000, 0xff000000);
                int[] foreColors = {foreStartColor, foreCenterColor, 0x00000000};

                int backStartColor = (int) mArgbEvaluator.evaluate(oppositeValue * 0.8f, 0x00000000, 0xff000000);
                int backCenterColor = (int) mArgbEvaluator.evaluate(oppositeValue * 0.4f, 0x00000000, 0xff000000);
                int[] backColors = {backStartColor, backCenterColor, 0x00000000};

                mForegroundDrawable.setColors(foreColors);
                mBackgroundDrawable.setColors(backColors);
                setTranslationY(distance * value);
            });
            animator.start();
        }
    }

    public ImageView getForegroundView() {
        if (mForegroundView == null) {
            return null;
        }
        View foregroundView = mForegroundView.getChildAt(0);
        if (foregroundView instanceof ImageView) {
            return (ImageView) foregroundView;
        }
        return null;
    }

    public ImageView getBackgroundView() {
        if (mBackgroundView == null) {
            return null;
        }
        View backgroundView = mBackgroundView.getChildAt(0);
        if (backgroundView instanceof ImageView) {
            return (ImageView) backgroundView;
        }
        return null;
    }

    static class BottomInAnimation extends Animation {
        private Camera mCamera;
        private Matrix mMatrix;
        private int mWidth;
        private int mHeight;

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mMatrix = new Matrix();
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mCamera.save();
            final float rotate = -DEGREE * interpolatedTime;
            mCamera.rotateX(rotate);
            mCamera.getMatrix(mMatrix);
            mCamera.restore();
            mMatrix.preTranslate(-mWidth / 2, 0);
            mMatrix.postTranslate(mWidth / 2, interpolatedTime * mHeight);
            t.getMatrix().postConcat(mMatrix);
        }
    }

    static class BottomOutAnimation extends Animation {
        private Camera mCamera;
        private Matrix mMatrix;
        private int mWidth;
        private int mHeight;

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mMatrix = new Matrix();
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mCamera.save();
            final float rotate = DEGREE - DEGREE * interpolatedTime;
            mCamera.translate(0, -mHeight * interpolatedTime, 0);
            mCamera.rotateX(rotate);
            mCamera.getMatrix(mMatrix);
            mCamera.restore();
            mMatrix.preTranslate(-mWidth / 2, -mHeight);
            mMatrix.postTranslate(mWidth / 2, 0);
            t.getMatrix().postConcat(mMatrix);
        }
    }

    static class TopInAnimation extends Animation {
        private Camera mCamera;
        private Matrix mMatrix;
        private int mWidth;
        private int mHeight;

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mMatrix = new Matrix();
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mCamera.save();
            final float rotate = DEGREE * interpolatedTime;
            mCamera.translate(0, -mHeight + interpolatedTime * mHeight, 0);
            mCamera.rotateX(rotate);
            mCamera.getMatrix(mMatrix);
            mCamera.restore();
            mMatrix.preTranslate(-mWidth / 2, -mHeight);
            mMatrix.postTranslate(mWidth / 2, 0);
            t.getMatrix().postConcat(mMatrix);
        }
    }

    static class TopOutAnimation extends Animation {
        private Camera mCamera;
        private Matrix mMatrix;
        private int mWidth;
        private int mHeight;

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mMatrix = new Matrix();
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mCamera.save();
            final float rotate = -DEGREE + DEGREE * interpolatedTime;
            mCamera.rotateX(rotate);
            mCamera.getMatrix(mMatrix);
            mCamera.restore();
            mMatrix.preTranslate(-mWidth / 2, 0);
            mMatrix.postTranslate(mWidth / 2, mHeight - mHeight * interpolatedTime);
            t.getMatrix().postConcat(mMatrix);
        }
    }
}
