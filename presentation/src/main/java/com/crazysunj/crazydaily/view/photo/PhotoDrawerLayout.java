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

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * @author: sunjian
 * created on: 2018/5/28 下午2:10
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class PhotoDrawerLayout extends DrawerLayout {

    private static final int MIN_DRAWER_MARGIN = 52;

    public PhotoDrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public PhotoDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final int minDrawerMargin = (int) (MIN_DRAWER_MARGIN * getResources().getDisplayMetrics().density + 0.5f);
        final int width = getResources().getDisplayMetrics().widthPixels - minDrawerMargin;
        setEdgeSize(width);
        setMinDrawerMargin(minDrawerMargin);
    }

    /**
     * 设置右边至少空出宽度
     *
     * @param px 单位px
     */
    private void setMinDrawerMargin(int px) {
        try {
            Field minDrawerMargin = getClass().getSuperclass().getDeclaredField(
                    "mMinDrawerMargin");
            minDrawerMargin.setAccessible(true);
            minDrawerMargin.setInt(this, px);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置拖动范围
     *
     * @param px 单位像素
     */
    private void setEdgeSize(int px) {
        try {
            Field leftDragger = getClass().getSuperclass().getDeclaredField(
                    "mLeftDragger");
            leftDragger.setAccessible(true);
            ViewDragHelper viewDragHelper = (ViewDragHelper) leftDragger
                    .get(this);
            Field edgeSize = viewDragHelper.getClass().getDeclaredField(
                    "mEdgeSize");
            edgeSize.setAccessible(true);
            edgeSize.setInt(viewDragHelper, px);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
