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
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.crazysunj.crazydaily.app.GlideApp;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
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

    @SuppressWarnings("all")
    public static void loadWithVignette(Context context, String url, @DrawableRes int placeholderId, ImageView imageView) {
        Single.just(placeholderId)
                .observeOn(Schedulers.io())
                .map(id -> {
                    GPUImageVignetteFilter filter = new GPUImageVignetteFilter();
                    filter.setVignetteCenter(new PointF(0.5f, 0.5f));
                    filter.setVignetteColor(new float[]{0.0f, 0.0f, 0.0f});
                    filter.setVignetteStart(0.0f);
                    filter.setVignetteEnd(0.75f);
                    GPUImage gpuImage = new GPUImage(context);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(context, id);
                    gpuImage.setImage(bitmapDrawable.getBitmap());
                    gpuImage.setFilter(filter);
                    return new BitmapDrawable(context.getResources(), gpuImage.getBitmapWithFilterApplied());

                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Drawable>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        this.disposable = disposable;
                    }

                    @Override
                    public void onSuccess(Drawable drawable) {
                        GlideApp.with(context)
                                .load(url)
                                .placeholder(drawable)
                                .transform(VIGNETTE_TRANSFORMATION)
                                .dontAnimate()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .into(imageView);
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        GlideApp.with(context)
                                .load(url)
                                .placeholder(placeholderId)
                                .transform(VIGNETTE_TRANSFORMATION)
                                .dontAnimate()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .into(imageView);
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

}
