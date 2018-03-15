package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * author: sunjian
 * created on: 2018/3/14 下午3:16
 * description:
 */

public class MyText extends WXComponent<TextView> {

    public MyText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected TextView initComponentHostView(@NonNull Context context) {
        TextView textView = new TextView(context);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.RED);
//        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        textView.setLayoutParams(params);
        return textView;
    }

    @WXComponentProp(name = "tel")
    public void setTel(String telNumber) {
        getHostView().setText("tel: " + telNumber);
    }
}
