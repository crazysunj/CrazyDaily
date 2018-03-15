package com.crazysunj.crazydaily.weex;

import com.crazysunj.crazydaily.ui.BrowserActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

/**
 * author: sunjian
 * created on: 2018/3/15 上午11:37
 * description: https://github.com/crazysunj/CrazyDaily
 */

public class RouterModule extends WXModule {

    @JSMethod(uiThread = true)
    public void router(String url) {
        BrowserActivity.start(mWXSDKInstance.getContext(), url);
    }
}
