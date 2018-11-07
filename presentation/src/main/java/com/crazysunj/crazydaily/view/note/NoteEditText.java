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
package com.crazysunj.crazydaily.view.note;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.util.ScreenUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: sunjian
 * created on: 2018/9/29 下午2:24
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteEditText extends LinearLayout {
    @BindView(R.id.edit_text)
    AppCompatEditText mEditText;
    @BindView(R.id.icon_delete)
    AppCompatImageView mDelete;
    private int preTextHeight = 0;
    private int deleteIconTop = 0;
    private OnEditTextListener monEditTextListener;

    public NoteEditText(Context context) {
        this(context, null);
    }

    public NoteEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_edit_text_note, this);
        ButterKnife.bind(this);
        setOrientation(HORIZONTAL);
        initAttrs(context, attrs);
        mEditText.setPadding(0, 0, 0, getPaddingBottom());
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final Layout layout = mEditText.getLayout();
                if (layout == null) {
                    return;
                }
                final int height = layout.getHeight();
                if (preTextHeight != height) {
                    layoutDelete(height);
                }
                String input = s.toString();
                mDelete.setVisibility(input.length() > 0 ? VISIBLE : GONE);
                if (monEditTextListener != null) {
                    monEditTextListener.onShowInputText(input);
                }
            }
        });
        //noinspection ConstantConditions
        mDelete.setOnClickListener(v -> mEditText.getText().clear());
        //当键盘弹出隐藏的时候会 调用此方法。
        mEditText.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (monEditTextListener != null) {
                monEditTextListener.onShowSoftKeyBoard();
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NoteEditText);
        Drawable deleteIcon = a.getDrawable(R.styleable.NoteEditText_note_delete_icon);
        mDelete.setImageDrawable(deleteIcon == null ? ContextCompat.getDrawable(context, R.mipmap.ic_delete) : deleteIcon);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.NoteEditText_note_text_size, ScreenUtil.sp2px(context, 16)));
        mEditText.setMinHeight(a.getDimensionPixelSize(R.styleable.NoteEditText_note_minHeight, ScreenUtil.dp2px(context, 180)));
        mEditText.setTextColor(a.getColor(R.styleable.NoteEditText_note_text_color, ContextCompat.getColor(context, R.color.color_333333)));
        mEditText.setHintTextColor(a.getColor(R.styleable.NoteEditText_note_hint_text_color, ContextCompat.getColor(context, R.color.color_b2b2b2)));
        String hint = a.getString(R.styleable.NoteEditText_note_hint_text);
        mEditText.setHint(TextUtils.isEmpty(hint) ? "写点什么呢？" : hint);
        deleteIconTop = a.getDimensionPixelSize(R.styleable.NoteEditText_note_delete_icon_top, deleteIconTop);
        a.recycle();
    }

    public void setText(String text) {
        mEditText.setText(text);
    }

    public String getText() {
        Editable editable = mEditText.getText();
        if (editable == null) {
            return "";
        }
        return editable.toString();
    }

    private void layoutDelete(int height) {
        preTextHeight = height;
        mDelete.setPadding(0, deleteIconTop + preTextHeight - (int) mEditText.getTextSize(), 0, 0);
    }

    public void setOnEditTextListener(OnEditTextListener listener) {
        monEditTextListener = listener;
    }

    public interface OnEditTextListener {
        void onShowSoftKeyBoard();

        void onShowInputText(String text);
    }
}
