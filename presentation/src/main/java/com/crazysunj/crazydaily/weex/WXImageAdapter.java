package com.crazysunj.crazydaily.weex;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * author: sunjian
 * created on: 2018/3/13 上午10:39
 * description:https://github.com/crazysunj/CrazyDaily
 */

public class WXImageAdapter implements IWXImgLoaderAdapter {
    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        Glide.with(view.getContext())
                .load(url)
                .crossFade()
                .into(view);
    }
}
