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
package com.crazysunj.crazydaily.flutter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.crazysunj.crazydaily.view.banner.VpSwipeRefreshLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: sunjian
 * created on: 2019/1/15 上午10:06
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class FlutterGankioRefreshLayout extends VpSwipeRefreshLayout {

    private boolean isIntercept = true;

    public FlutterGankioRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public FlutterGankioRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isIntercept) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }
}
