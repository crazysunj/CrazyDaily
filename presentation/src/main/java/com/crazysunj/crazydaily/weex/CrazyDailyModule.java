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

import android.app.Activity;
import android.content.Context;

import com.crazysunj.crazydaily.ui.browser.BrowserActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * @author: sunjian
 * created on: 2018/3/15 上午11:37
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailyModule extends WXModule {

    @JSMethod(uiThread = true)
    public void router(String url) {
        BrowserActivity.start(mWXSDKInstance.getContext(), url);
    }

    @JSMethod(uiThread = true)
    public void setTitle(String titile) {
        final Context context = mWXSDKInstance.getContext();
        if (context instanceof Activity) {
            ((Activity) context).setTitle(titile);
        }
    }
}
