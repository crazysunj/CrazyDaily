package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.ImmutableDomObject;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.zzhoujay.richtext.RichText;

/**
 * author: sunjian
 * created on: 2018/5/22 下午2:52
 * description:
 */
public class RichTextView extends WXVContainer<FrameLayout> {

    private TextView textView;

    public RichTextView(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
    }

    @Override
    protected FrameLayout initComponentHostView(@NonNull Context context) {
        RichText.initCacheDir(context);
        FrameLayout root = (FrameLayout) View.inflate(context, R.layout.layout_rich_text, null);
        textView = (TextView) root.findViewById(R.id.wx_text);
        return root;
    }

    @Override
    public ImmutableDomObject getDomObject() {
        return super.getDomObject();
    }

    @WXComponentProp(name = "richtext")
    public void setRichText(String richText) {
        float height = getLayoutHeight();
        RichText.from(richText).bind(getContext()).into(textView);
//        setProperty("height", "400px");
//        FrameLayout hostView = getHostView();
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) hostView.getLayoutParams();
//        setHostLayoutParams(hostView, (int) getLayoutWidth(), 400, params.leftMargin, params.rightMargin, params.topMargin, params.bottomMargin);
//        WXDomObject domObject = (WXDomObject) getDomObject();
//        domObject.setLayoutHeight(400);
//        Map<String, Object> map = new HashMap<>();
//        map.put("height", 400);
//        updateProperties(map);
//        notifyNativeSizeChanged((int) getLayoutWidth(),400);
        textView.measure(0, 0);
        float viewHeight = getLayoutHeight();
        Log.d("RichTextView", "height:" + height + " viewHeight:" + viewHeight+" ---"+textView.getMeasuredHeight());
    }


    @Override
    protected MeasureOutput measure(int width, int height) {
        Log.d("RichTextView", "width:" + width + " height:" + height);
        return super.measure(width, height);
    }

    @Override
    public void destroy() {
        super.destroy();
        RichText.clear(getContext());
    }
}
