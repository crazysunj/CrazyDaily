package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXUtils;
import com.zzhoujay.richtext.RichText;

import java.util.regex.Pattern;

/**
 * author: sunjian
 * created on: 2018/5/22 下午2:52
 * description:
 */
public class WXRichTextView extends WXComponent<TextView> {

    private static final Pattern TEXT_SIZE_REGEX = Pattern.compile("\\d+px");

    private TextView mRichTextView;

    private String mText;

    /**
     * 若文本超过设置高度使之不可滑动
     * 若需要自适应则不需要拦截事件，并用类似ScrollView包裹TextView
     */
    private static class RichTextView extends AppCompatTextView {

        public RichTextView(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            return true;
        }
    }

    public WXRichTextView(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
        //通过这可以获取标签之间的内容
        if (node instanceof WXCustomTextDomObject) {
            WXCustomTextDomObject object = (WXCustomTextDomObject) node;
            object.setCallback(text -> {
                if (TextUtils.isEmpty(mText) || !mText.equals(text)) {
                    mText = text;
                    if (mRichTextView != null) {
                        RichText.from(mText).bind(getContext()).into(mRichTextView);
                    }
                }
            });
        }
    }

    @Override
    protected TextView initComponentHostView(@NonNull Context context) {
        RichText.initCacheDir(context);
        mRichTextView = new RichTextView(context);
        //可以获取真实高度进行自适应
//        mRichTextView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//        });
        //通过这可以获取标签之间的内容
        if (!TextUtils.isEmpty(mText)) {
            RichText.from(mText).bind(getContext()).into(mRichTextView);
        }
        return mRichTextView;
    }

    //通过属性绑定文本
//    @WXComponentProp(name = "richText")
//    public void setRichText(String richText) {
//        if (TextUtils.isEmpty(richText)) {
//            mRichTextView.setText("");
//            return;
//        }
//        RichText.from(richText).bind(getContext()).into(mRichTextView);
//    }

    @WXComponentProp(name = "richColor")
    public void setRichTextColor(String richColor) {
        final int color;
        try {
            color = Color.parseColor(richColor);
            mRichTextView.setTextColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @WXComponentProp(name = "richSize")
    public void setRichTextSize(String size) {
        if (TextUtils.isEmpty(size)) {
            mRichTextView.setTextSize(0);
            return;
        }
        boolean matches = TEXT_SIZE_REGEX.matcher(size).matches();
        if (matches) {
            int realSize = WXUtils.getInt(size);
            mRichTextView.setTextSize(realSize);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        RichText.clear(getContext());
    }
}
