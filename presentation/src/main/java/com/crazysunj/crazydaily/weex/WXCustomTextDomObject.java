package com.crazysunj.crazydaily.weex;

import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXTextDomObject;

/**
 * author: sunjian
 * created on: 2018/5/23 下午7:07
 * description:
 */
public class WXCustomTextDomObject extends WXTextDomObject {

    private OnTextCallback mCallback;

    @Override
    public void layoutAfter() {
        super.layoutAfter();
        final int childCount = childCount();
        if (childCount > 0) {
            WXAttr attrs = getChild(0).getAttrs();
            String text = WXAttr.getValue(attrs);
            if (mCallback != null) {
                mCallback.onCall(text);
            }
        }
    }

    public void setCallback(OnTextCallback callback) {
        mCallback = callback;
    }

    public interface OnTextCallback {
        void onCall(String text);
    }
}
