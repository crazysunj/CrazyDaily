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
package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * @author: sunjian
 * created on: 2018/3/13 上午10:39
 * description: https://github.com/crazysunj/CrazyDaily
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
            int imgId = context.getResources().getIdentifier(resIdStr, "mipmap", context.getPackageName());
            view.setImageResource(imgId);
            return;
        }
        ImageLoader.load(context, url, view);
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
