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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.crazysunj.crazydaily.R;
import com.crazysunj.data.util.LoggerUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author: sunjian
 * created on: 2018/3/14 下午2:14
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class TabPagerFragment extends Fragment implements IWXRenderListener {

    private FrameLayout mGankIoContent;
    private WXSDKInstance mWXSDKInstance;

    public static TabPagerFragment get(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        TabPagerFragment fragment = new TabPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String type = getArguments().getString("type");
        mGankIoContent = (FrameLayout) inflater.inflate(R.layout.layout_weex_gank_io, container, false);
        final Context context = getActivity();
        mWXSDKInstance = new WXSDKInstance(context);
        mWXSDKInstance.registerRenderListener(this);
        Map<String, Object> options = new HashMap<>();
        options.put("type", type);
        mWXSDKInstance.render("GankioList", WXFileUtils.loadAsset("weex/gankio/gankiolist.js", context), options, null, WXRenderStrategy.APPEND_ASYNC);
        return mGankIoContent;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (mGankIoContent.getChildCount() > 0) {
            mGankIoContent.removeAllViews();
        }
        mGankIoContent.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        LoggerUtil.d("onRenderSuccess ------ width:" + width + " height:" + height);
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        LoggerUtil.d("onRefreshSuccess ------ width:" + width + " height:" + height);
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        LoggerUtil.d(msg);
    }
}
