package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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

        if (TextUtils.isEmpty(url)) {
            return;
        }
        final Context context = view.getContext();

        if (url.startsWith("mipmap://")) {
            String resIdStr = getResIdStr(url);
            if (TextUtils.isEmpty(resIdStr)) {
                return;
            }
            Log.d("WXImageAdapter", resIdStr);
            int imgId = context.getResources().getIdentifier(resIdStr, "mipmap", context.getPackageName());
            view.setImageResource(imgId);
            return;
        }

        Glide.with(context)
                .load(url)
                .crossFade()
                .into(view);
    }

    private String getResIdStr(String url) {
        int start = url.lastIndexOf("/") + 1;
        int end = url.indexOf(".");
        if (start > end) {
            return null;
        }
        return url.substring(start, end);
    }
}
