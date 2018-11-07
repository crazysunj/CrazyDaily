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
package com.crazysunj.crazydaily.view.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: sunjian
 * created on: 2018/5/5 下午3:47
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WrapBannerView extends FrameLayout {

    public WrapBannerView(@NonNull Context context) {
        super(context);
    }

    public WrapBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapBannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            if (mOnBannerSlideListener != null) {
                mOnBannerSlideListener.onSlide(true);
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
            if (mOnBannerSlideListener != null) {
                mOnBannerSlideListener.onSlide(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private OnBannerSlideListener mOnBannerSlideListener;

    public void setOnBannerSlideListener(OnBannerSlideListener listener) {
        mOnBannerSlideListener = listener;
    }

    public interface OnBannerSlideListener {
        void onSlide(boolean isCanSlide);
    }
}
