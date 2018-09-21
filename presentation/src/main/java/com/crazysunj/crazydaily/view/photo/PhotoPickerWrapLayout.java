package com.crazysunj.crazydaily.view.photo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * author: sunjian
 * created on: 2018/9/21 下午3:54
 * description: 自身高度与自身宽度保持一致
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
