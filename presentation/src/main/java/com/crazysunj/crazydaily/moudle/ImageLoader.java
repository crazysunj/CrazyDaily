/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.crazydaily.moudle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.crazysunj.crazydaily.app.GlideApp;
import com.crazysunj.data.util.LoggerUtil;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;


/**
 * author: sunjian
 * created on: 2018/4/28 下午1:41
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class ImageLoader {

    private static VignetteFilterTransformation VIGNETTE_TRANSFORMATION = new VignetteFilterTransformation();


    public static void load(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    public static void load(Context context, String url, @DrawableRes int placeholderId, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .into(imageView);
    }

    public static void loadWithVignette(Context context, String url, @DrawableRes int placeholderId, ImageView imageView) {
        BitmapPool bitmapPool = GlideApp.get(context).getBitmapPool();
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .transform(VIGNETTE_TRANSFORMATION)
                .into(new DrawableImageViewTarget(imageView) {
                    @Override
                    public void setDrawable(Drawable drawable) {
                        try {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                            BitmapResource bitmapResource = BitmapResource.obtain(bitmapDrawable.getBitmap(), bitmapPool);
                            Resource<Bitmap> transform = VIGNETTE_TRANSFORMATION.transform(context, bitmapResource, view.getWidth(), view.getHeight());
                            view.setImageBitmap(transform.get());
                        } catch (Exception e) {
                            LoggerUtil.e(e.getMessage(), e);
                        }
                    }
                });
    }

}
