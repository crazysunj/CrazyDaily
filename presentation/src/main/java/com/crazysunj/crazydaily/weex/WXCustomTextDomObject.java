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
package com.crazysunj.crazydaily.weex;

import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXTextDomObject;

/**
 * author: sunjian
 * created on: 2018/5/23 下午7:07
 * description:https://github.com/crazysunj/CrazyDaily
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
