package com.crazysunj.crazydaily.weex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crazysunj.data.util.LoggerUtil;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

/**
 * author: sunjian
 * created on: 2018/3/13 上午10:39
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class WeexActivity extends AppCompatActivity implements IWXRenderListener {

    private WXSDKInstance mWXSDKInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        mWXSDKInstance.render("Gankio", WXFileUtils.loadAsset("tabPager.js", this), null, null, WXRenderStrategy.APPEND_ASYNC);
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

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, WeexActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        setContentView(view);
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
