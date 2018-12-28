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

import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.component.WXComponent;

/**
 * @author: sunjian
 * created on: 2018/6/11 下午4:56
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WXWebViewModule extends WXModule {

    private enum Action {
        reload,
        goBack,
        goForward,
        postMessage
    }

    @JSMethod(uiThread = true)
    public void goBack(String ref) {
        action(Action.goBack, ref);
    }

    @JSMethod(uiThread = true)
    public void goForward(String ref) {
        action(Action.goForward, ref);
    }

    @JSMethod(uiThread = true)
    public void reload(String ref) {
        action(Action.reload, ref);
    }

    @JSMethod(uiThread = true)
    public void postMessage(String ref, Object msg) {
        action(Action.postMessage, ref, msg);
    }

    private void action(Action action, String ref, Object data) {
        WXComponent webComponent =
                WXSDKManager.getInstance()
                        .getWXRenderManager()
                        .getWXComponent(mWXSDKInstance.getInstanceId(), ref);
        if (webComponent instanceof WXWebComponent) {
            ((WXWebComponent) webComponent).setAction(action.name(), data);
        }
    }

    private void action(Action action, String ref) {
        action(action, ref, null);
    }
}
