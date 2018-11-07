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
package com.crazysunj.crazydaily.view.scan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.journeyapps.barcodescanner.ViewfinderView;

/**
 * @author: sunjian
 * created on: 2018/7/2 下午2:27
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailyViewfinderView extends ViewfinderView {

    protected static final long LASER_ANIM_DELAY = 12;
    protected static final float CORNER_LINE_RATE = 0.1F;

    protected final float CORNER_LINE_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
    protected final float LASER_WIDTH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
    protected final float LASER_OFFSET = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
    protected static final float[] LASER_POSITIONS = new float[]{0f, 0.4f, 0.6f, 1.0f};
    protected final int[] LASER_COLORS;
    /**
     * 相对framingRect
     */
    protected int mLaserPosition = 0;
    protected LinearGradient mLinearGradient;


    public CrazyDailyViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 关于透明度自己调
        LASER_COLORS = new int[]{0x06ffffff & laserColor, laserColor, laserColor, 0x06ffffff & laserColor};
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        final Rect frame = framingRect;
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();

        // 画角，也可以用Path
        paint.setColor(laserColor);
        canvas.drawRect(frame.left, frame.top, frame.left + CORNER_LINE_WIDTH, frame.top + frame.height() * CORNER_LINE_RATE, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + frame.width() * CORNER_LINE_RATE, frame.top + CORNER_LINE_WIDTH, paint);
        canvas.drawRect(frame.right - frame.width() * CORNER_LINE_RATE, frame.top, frame.right, frame.top + CORNER_LINE_WIDTH, paint);
        canvas.drawRect(frame.right - CORNER_LINE_WIDTH, frame.top, frame.right, frame.top + frame.height() * CORNER_LINE_RATE, paint);
        canvas.drawRect(frame.left, frame.bottom - CORNER_LINE_WIDTH, frame.left + frame.width() * CORNER_LINE_RATE, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - frame.height() * CORNER_LINE_RATE, frame.left + CORNER_LINE_WIDTH, frame.bottom, paint);
        canvas.drawRect(frame.right - frame.width() * CORNER_LINE_RATE, frame.bottom - CORNER_LINE_WIDTH, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - CORNER_LINE_WIDTH, frame.bottom - frame.height() * CORNER_LINE_RATE, frame.right, frame.bottom, paint);

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
        canvas.drawRect(frame.right, frame.top, width, frame.bottom, paint);
        canvas.drawRect(0, frame.bottom, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            // 画激光线
            mLaserPosition += LASER_OFFSET;
            if (mLaserPosition > frame.height()) {
                mLaserPosition = 0;
            }
            mLinearGradient = new LinearGradient(frame.left, frame.top + mLaserPosition, frame.right, frame.top + mLaserPosition, LASER_COLORS, LASER_POSITIONS, Shader.TileMode.CLAMP);
            paint.setShader(mLinearGradient);
            canvas.drawRect(frame.left + frame.width() * CORNER_LINE_RATE / 2, frame.top + mLaserPosition, frame.right - frame.width() * CORNER_LINE_RATE / 2, frame.top + mLaserPosition + LASER_WIDTH, paint);
            paint.setShader(null);
        }

        // Request another update at the animation interval, but only repaint the laser line,
        // not the entire viewfinder mask.
        postInvalidateDelayed(LASER_ANIM_DELAY,
                frame.left,
                frame.top,
                frame.right,
                frame.bottom);
    }
}
