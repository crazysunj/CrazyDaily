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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: sunjian
 * created on: 2018/4/18 上午10:37
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CoolBGView extends View {

    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private static final int ZERO = 0;
    private static final int BLUR_RADIUS = 20;//模糊度（radius最大只能设置25f）
    private float mHalfHeight;
    private float mFifthWidth;
    private float mElevenTwentiethsHeight;
    private float mThreeTenthsHeight;
    private int mWidth;
    private int mHeight;

    public CoolBGView(Context context) {
        this(context, null);
    }

    public CoolBGView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoolBGView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mHalfHeight = mHeight * 1.0f / 2;
        mFifthWidth = mWidth * 1.0f / 5;
        mElevenTwentiethsHeight = mHeight * 11.0f / 20;
        mThreeTenthsHeight = mHeight * 3.0f / 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Context context = getContext();

        canvas.save();
        mPath.reset();
        mPath.moveTo(ZERO, mHalfHeight);
        mPath.quadTo(mFifthWidth, mElevenTwentiethsHeight, mWidth, mThreeTenthsHeight);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(ZERO, mHeight);
        mPath.close();
        canvas.clipPath(mPath);
        canvas.drawColor(Color.WHITE);
        canvas.restore();

        if (mBitmap == null) {
            return;
        }
        canvas.save();
        mPath.reset();
        mPath.moveTo(ZERO, ZERO);
        mPath.lineTo(ZERO, mHalfHeight);
        mPath.quadTo(mFifthWidth, mElevenTwentiethsHeight, mWidth, mThreeTenthsHeight);
        mPath.lineTo(mWidth, ZERO);
        mPath.close();
        canvas.clipPath(mPath);
        canvas.drawBitmap(blurBitmap(context, mBitmap), ZERO, ZERO, mPaint);
        canvas.restore();
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBitmap.recycle();
        mBitmap = null;
    }

    /**
     * 获取模糊的图片
     *
     * @param context 上下文对象
     * @param bitmap  传入的bitmap图片
     */
    private Bitmap blurBitmap(Context context, Bitmap bitmap) {
        //用需要创建高斯模糊bitmap创建一个空的bitmap
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //设定模糊度(注：radius最大只能设置25f)
        blurScript.setRadius(BLUR_RADIUS);
        // 设置blurScript对象的输入内存
        blurScript.setInput(allIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(allOut);
        // 将数据填充到Allocation中
        allOut.copyTo(outBitmap);
        rs.destroy();
        return outBitmap;
    }
}
