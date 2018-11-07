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
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author: sunjian
 * created on: 2018/4/20 上午10:50
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class BezierTransition extends Transition {

    private static final String PROPNAME_SCREEN_LOCATION = "crazysunj:bezier:screenLocation";

    public BezierTransition() {
        setPathMotion(new PathMotion() {
            @Override
            public Path getPath(float startX, float startY, float endX, float endY) {
                Path path = new Path();
                path.moveTo(startX, startY);

                float controlPointX = (startX + endX) / 4;
                float controlPointY = (startY + endY) * 1.0f / 2;

                path.quadTo(controlPointX, controlPointY, endX, endY);
                return path;
            }
        });
    }

    private void captureValues(TransitionValues transitionValues) {
        Rect rect = new Rect();
        transitionValues.view.getGlobalVisibleRect(rect);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, rect);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }

        final int id = startValues.view.getId();
        if (id <= 0) {
            return null;
        }

        Rect startRect = (Rect) startValues.values.get(PROPNAME_SCREEN_LOCATION);
        Rect endRect = (Rect) endValues.values.get(PROPNAME_SCREEN_LOCATION);

        final View view = endValues.view;
        Path path = getPathMotion().getPath(startRect.centerX(), startRect.centerY(), endRect.centerX(), endRect.centerY());
        return ObjectAnimator.ofObject(view, new PropPosition(PointF.class, "position", new PointF(endRect.centerX(), endRect.centerY())), null, path);
    }

    static class PropPosition extends Property<View, PointF> {

        PointF endPoint;

        PropPosition(Class<PointF> type, String name, PointF endPoint) {
            super(type, name);
            this.endPoint = endPoint;
        }

        @Override
        public void set(View view, PointF value) {
            int x = Math.round(value.x);
            int y = Math.round(value.y);

            int startX = Math.round(endPoint.x);
            int startY = Math.round(endPoint.y);

            int transY = y - startY;
            int transX = x - startX;

            view.setTranslationX(transX);
            view.setTranslationY(transY);
        }

        @Override
        public PointF get(View object) {
            return null;
        }
    }
}
