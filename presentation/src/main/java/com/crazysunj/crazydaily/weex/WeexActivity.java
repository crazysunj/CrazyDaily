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
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.constant.WeexConstant;
import com.crazysunj.data.util.LoggerUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.dom.WXEvent;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.utils.WXFileUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author: sunjian
 * created on: 2018/3/13 上午10:39
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WeexActivity extends AppCompatActivity implements IWXRenderListener {
    private static final String TAG = WeexActivity.class.getSimpleName();

    private WXSDKInstance mWXSDKInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        final Intent intent = getIntent();
        mWXSDKInstance.render(intent.getStringExtra(ActivityConstant.PAGE), WXFileUtils.loadAsset(intent.getStringExtra(ActivityConstant.PATH), this), null, null, WXRenderStrategy.APPEND_ASYNC);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    /**
     * 处理weex返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        BasicComponentData basicComponentData = mWXSDKInstance.getRootComponent().getBasicComponentData();
        final WXAttr attrs = basicComponentData.getAttrs();
        boolean isHasInterceptBack = attrs.containsKey(WeexConstant.ATTR_INTERCEPT_BACK);
        if ((keyCode == KeyEvent.KEYCODE_BACK) && isHasInterceptBack) {
            Object interceptBackObj = attrs.get(WeexConstant.ATTR_INTERCEPT_BACK);
            try {
                boolean interceptBack = false;
                if (interceptBackObj != null) {
                    interceptBack = Boolean.parseBoolean(interceptBackObj.toString());
                }
                if (interceptBack) {
                    WXEvent events = basicComponentData.getEvents();
                    boolean hasBack = events.contains(WeexConstant.EVENT_BACK);
                    if (hasBack) {
                        mWXSDKInstance.fireEvent(basicComponentData.mRef, WeexConstant.EVENT_BACK);
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void start(Activity activity, String page, String path) {
        Intent intent = new Intent(activity, WeexActivity.class);
        intent.putExtra(ActivityConstant.PAGE, page);
        intent.putExtra(ActivityConstant.PATH, path);
        activity.startActivity(intent);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        LoggerUtil.d(TAG, "onRenderSuccess ------ width:" + width + " height:" + height);
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        LoggerUtil.d(TAG, "onRefreshSuccess ------ width:" + width + " height:" + height);
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        LoggerUtil.e(TAG, msg);
    }
}
