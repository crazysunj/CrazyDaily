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
package com.crazysunj.crazydaily.view.photo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author: sunjian
 * created on: 2018/9/21 下午3:54
 * description: 自身高度与自身宽度保持一致 https://github.com/crazysunj/CrazyDaily
 */
public class PhotoPickerWrapLayout extends RelativeLayout {

    public PhotoPickerWrapLayout(Context context) {
        this(context, null);
    }

    public PhotoPickerWrapLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoPickerWrapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
