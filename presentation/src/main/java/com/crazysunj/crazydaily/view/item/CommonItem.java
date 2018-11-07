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
package com.crazysunj.crazydaily.view.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author: sunjian
 * created on: 2018/7/5 上午11:06
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CommonItem extends LinearLayout {

    private String text;
    private Drawable icon;
    private boolean isBottomLine;
    @BindView(R.id.common_item_icon)
    ImageView mIcon;
    @BindView(R.id.common_item_text)
    TextView mText;

    public CommonItem(Context context) {
        super(context, null);
    }

    public CommonItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        int dp_10 = getResources().getDimensionPixelSize(R.dimen.space_10);
        int dp_8 = getResources().getDimensionPixelSize(R.dimen.space_8);
        setPadding(dp_10, dp_8, dp_10, dp_8);
        initAttrs(context, attrs);
        setBackgroundResource(isBottomLine ? R.drawable.shape_bottom_line : R.color.color_white);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.layout_common_item, this);
        ButterKnife.bind(this);
        if (icon != null) {
            mIcon.setImageDrawable(icon);
        }
        if (text != null) {
            mText.setText(text);
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonItem);
        text = a.getString(R.styleable.CommonItem_common_item_text);
        icon = a.getDrawable(R.styleable.CommonItem_common_item_icon);
        isBottomLine = a.getBoolean(R.styleable.CommonItem_common_item_bottom_line, true);
        a.recycle();
    }

    public void setText(String text) {
        mText.setText(text);
    }

    public void setIcon(@DrawableRes int icon) {
        mIcon.setImageResource(icon);
    }
}
