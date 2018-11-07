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
package com.crazysunj.crazydaily.ui.note.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.util.ScreenUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: sunjian
 * created on: 2018/9/25 上午10:30
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteEditDialog extends DialogFragment {

    private static final String TAG = "NoteEditDialog";
    @BindView(R.id.dialog_note_wrap)
    View mWrap;
    @BindView(R.id.dialog_note_edit)
    AppCompatTextView mEdit;
    @BindView(R.id.dialog_note_delete)
    AppCompatTextView mDelete;
    @BindView(R.id.dialog_note_cancel)
    AppCompatTextView mCancel;


    private Unbinder mUnbinder;
    private FragmentActivity mActivity;
    private OnItemClickListener mOnItemClickListener;

    public static NoteEditDialog get() {
        return new NoteEditDialog();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachActivity((FragmentActivity) activity);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachActivity((FragmentActivity) context);
    }

    private void onAttachActivity(FragmentActivity activity) {
        mActivity = activity;
    }

    //设置样式
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.PickerDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_note_edit, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.WHITE);
        float radius = ScreenUtil.dp2px(mActivity, 15);
        float[] radii = new float[]{radius, radius, radius, radius, 0, 0, 0, 0};
        drawable.setCornerRadii(radii);
        GradientDrawable itemDrawable = new GradientDrawable();
        itemDrawable.setColor(Color.parseColor("#F2F2F2"));
        itemDrawable.setCornerRadius(ScreenUtil.dp2px(mActivity, 35));
        mEdit.setBackground(itemDrawable);
        mDelete.setBackground(itemDrawable);
        mWrap.setBackground(drawable);
        mEdit.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onEdit();
            }
        });
        mDelete.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onDelete();
            }
        });
        mCancel.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onCancel();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = ScreenUtil.dp2px(mActivity, 240);
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        mActivity = null;
        super.onDestroy();
    }

    public void show(FragmentActivity activity) {
        super.show(activity.getSupportFragmentManager(), TAG);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onEdit();

        void onDelete();

        void onCancel();
    }
}
