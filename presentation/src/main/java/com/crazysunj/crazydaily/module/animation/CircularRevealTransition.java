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
package com.crazysunj.crazydaily.module.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.crazysunj.crazydaily.R;

import java.util.ArrayList;

/**
 * @author: sunjian
 * created on: 2018/4/19 下午4:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CircularRevealTransition extends Visibility {

    private static final String PROPNAME_ALPHA = "crazysunj:circularReveal:alpha";
    private static final String PROPNAME_RADIUS = "crazysunj:circularReveal:radius";
    private static final String PROPNAME_TRANSLATION_Y = "crazysunj:circularReveal:translationY";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put(PROPNAME_ALPHA, 0.2f);
        final View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_RADIUS, 0);
        transitionValues.values.put(PROPNAME_TRANSLATION_Y, -view.getBottom());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        transitionValues.values.put(PROPNAME_ALPHA, 1.0f);
        final View view = transitionValues.view;
        int radius = (int) Math.hypot(view.getWidth(), view.getHeight());
        transitionValues.values.put(PROPNAME_RADIUS, radius);
        transitionValues.values.put(PROPNAME_TRANSLATION_Y, 0);
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }
        final int id = view.getId();
        switch (id) {
            case R.id.satellite_menu:
                int startTranslationY = (int) startValues.values.get(PROPNAME_TRANSLATION_Y);
                float startAlpha = (float) startValues.values.get(PROPNAME_ALPHA);
                int endTranslationY = (int) endValues.values.get(PROPNAME_TRANSLATION_Y);
                float endAlpha = (float) endValues.values.get(PROPNAME_ALPHA);
                PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", startTranslationY, endTranslationY);
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", startAlpha, endAlpha);
                ObjectAnimator menuAnim = ObjectAnimator.ofPropertyValuesHolder(view, translationY, alpha);
                menuAnim.setInterpolator(new BounceInterpolator());
                menuAnim.setDuration(1000);
                return menuAnim;
            case R.id.cool_bg_view:
                int startRadius = (int) startValues.values.get(PROPNAME_RADIUS);
                int endRadius = (int) endValues.values.get(PROPNAME_RADIUS);
                Animator coolAnim = new NoPauseAnimator(ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, startRadius, endRadius));
                coolAnim.setDuration(1000);
                coolAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                return coolAnim;
            default:
                return null;
        }
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }
        final int id = view.getId();
        switch (id) {
            case R.id.satellite_menu:
                int startTranslationY = (int) endValues.values.get(PROPNAME_TRANSLATION_Y);
                float startAlpha = (float) endValues.values.get(PROPNAME_ALPHA);
                int endTranslationY = (int) startValues.values.get(PROPNAME_TRANSLATION_Y);
                float endAlpha = (float) startValues.values.get(PROPNAME_ALPHA);
                PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", startTranslationY, endTranslationY);
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", startAlpha, endAlpha);
                ObjectAnimator menuAnim = ObjectAnimator.ofPropertyValuesHolder(view, translationY, alpha);
                menuAnim.setInterpolator(new DecelerateInterpolator());
                menuAnim.setDuration(500);
                return menuAnim;
            case R.id.cool_bg_view:
                int startRadius = (int) endValues.values.get(PROPNAME_RADIUS);
                int endRadius = (int) startValues.values.get(PROPNAME_RADIUS);
                Animator coolAnim = new NoPauseAnimator(ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, startRadius, endRadius));
                coolAnim.setInterpolator(new DecelerateInterpolator());
                coolAnim.setDuration(600);
                return coolAnim;
            default:
                return null;
        }
    }


    public static class NoPauseAnimator extends Animator {
        private final Animator mAnimator;
        private final ArrayMap<AnimatorListener, AnimatorListener> mListeners = new ArrayMap<>();

        public NoPauseAnimator(Animator animator) {
            mAnimator = animator;
        }

        @Override
        public void addListener(AnimatorListener listener) {
            AnimatorListener wrapper = new AnimatorListenerWrapper(this, listener);
            if (!mListeners.containsKey(listener)) {
                mListeners.put(listener, wrapper);
                mAnimator.addListener(wrapper);
            }
        }

        @Override
        public void cancel() {
            mAnimator.cancel();
        }

        @Override
        public void end() {
            mAnimator.end();
        }

        @Override
        public long getDuration() {
            return mAnimator.getDuration();
        }

        @Override
        public TimeInterpolator getInterpolator() {
            return mAnimator.getInterpolator();
        }

        @Override
        public ArrayList<AnimatorListener> getListeners() {
            return new ArrayList<>(mListeners.keySet());
        }

        @Override
        public long getStartDelay() {
            return mAnimator.getStartDelay();
        }

        @Override
        public boolean isPaused() {
            return mAnimator.isPaused();
        }

        @Override
        public boolean isRunning() {
            return mAnimator.isRunning();
        }

        @Override
        public boolean isStarted() {
            return mAnimator.isStarted();
        }

        @Override
        public void removeAllListeners() {
            mListeners.clear();
            mAnimator.removeAllListeners();
        }

        @Override
        public void removeListener(AnimatorListener listener) {
            AnimatorListener wrapper = mListeners.get(listener);
            if (wrapper != null) {
                mListeners.remove(listener);
                mAnimator.removeListener(wrapper);
            }
        }

        @Override
        public Animator setDuration(long durationMS) {
            mAnimator.setDuration(durationMS);
            return this;
        }

        @Override
        public void setInterpolator(TimeInterpolator timeInterpolator) {
            mAnimator.setInterpolator(timeInterpolator);
        }

        @Override
        public void setStartDelay(long delayMS) {
            mAnimator.setStartDelay(delayMS);
        }

        @Override
        public void setTarget(Object target) {
            mAnimator.setTarget(target);
        }

        @Override
        public void setupEndValues() {
            mAnimator.setupEndValues();
        }

        @Override
        public void setupStartValues() {
            mAnimator.setupStartValues();
        }

        @Override
        public void start() {
            mAnimator.start();
        }
    }

    public static class AnimatorListenerWrapper implements Animator.AnimatorListener {
        private final Animator mAnimator;
        private final Animator.AnimatorListener mListener;

        public AnimatorListenerWrapper(Animator animator, Animator.AnimatorListener listener) {
            mAnimator = animator;
            mListener = listener;
        }

        @Override
        public void onAnimationStart(Animator animator) {
            mListener.onAnimationStart(mAnimator);
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mListener.onAnimationEnd(mAnimator);
        }

        @Override
        public void onAnimationCancel(Animator animator) {
            mListener.onAnimationCancel(mAnimator);
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
            mListener.onAnimationRepeat(mAnimator);
        }
    }
}
