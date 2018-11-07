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
package com.crazysunj.crazydaily.view.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.util.ScreenUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: sunjian
 * created on: 2018/10/4 上午11:11
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailyAlertDialog extends DialogFragment {
    private static final String TAG = "CrazyDailyAlertDialog";

    private static final String MESSAGE = "message";
    private static final String MESSAGE_COLOR = "messageColor";
    private static final String MESSAGE_SIZE = "messageSize";
    private static final String NEGATIVE = "negative";
    private static final String NEGATIVE_COLOR = "negativeColor";
    private static final String NEGATIVE_SIZE = "negativeSize";
    private static final String POSITIVE = "positive";
    private static final String POSITIVE_COLOR = "positiveColor";
    private static final String POSITIVE_SIZE = "positiveSize";

    @BindView(R.id.dialog_alert_message)
    TextView mMessage;
    @BindView(R.id.dialog_alert_button_negative)
    TextView mNegative;
    @BindView(R.id.dialog_alert_button_positive)
    TextView mPositive;
    @BindView(R.id.dialog_alert_wrapper)
    LinearLayout mWrapper;
    private Unbinder mUnbinder;
    private OnClickListener mOnNegativeClickListener;
    private OnClickListener mOnPositiveClickListener;

    //设置样式
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.NormalDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_alert, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        FragmentActivity activity = getActivity();
        if (window != null && activity != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.8f);
            window.setAttributes(params);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void initView() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.WHITE);
        drawable.setCornerRadius(ScreenUtil.dp2px(mWrapper.getContext(), 10));
        mWrapper.setBackground(drawable);
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        final String message = arguments.getString(MESSAGE);
        mMessage.setText(message);
        final int messageColor = arguments.getInt(MESSAGE_COLOR);
        mMessage.setTextColor(messageColor);
        final float messageSize = arguments.getFloat(MESSAGE_SIZE);
        mMessage.setTextSize(messageSize);
        final String negative = arguments.getString(NEGATIVE);
        mNegative.setText(negative);
        final int negativeColor = arguments.getInt(NEGATIVE_COLOR);
        mNegative.setTextColor(negativeColor);
        final float negativeSize = arguments.getFloat(NEGATIVE_SIZE);
        mNegative.setTextSize(negativeSize);
        final String positive = arguments.getString(POSITIVE);
        mPositive.setText(positive);
        final int positiveColor = arguments.getInt(POSITIVE_COLOR);
        mPositive.setTextColor(positiveColor);
        final float positiveSize = arguments.getFloat(POSITIVE_SIZE);
        mPositive.setTextSize(positiveSize);
        mNegative.setOnClickListener(v -> {
            if (mOnNegativeClickListener != null) {
                mOnNegativeClickListener.onClick(v);
            }
        });
        mPositive.setOnClickListener(v -> {
            if (mOnPositiveClickListener != null) {
                mOnPositiveClickListener.onClick(v);
            }
        });
    }

    public void show(FragmentActivity activity) {
        super.show(activity.getSupportFragmentManager(), TAG);
    }

    public void setOnNegativeClickListener(OnClickListener listener) {
        mOnNegativeClickListener = listener;
    }

    public void setOnPositiveClickListener(OnClickListener listener) {
        mOnPositiveClickListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public static class Builder {
        private String messgae;
        private int messageColor;
        private float messageSize;
        private String negative;
        private int negativeColor;
        private float negativeSize;
        private String positive;
        private int positiveColor;
        private float positiveSize;
        private OnClickListener onNegativeClickListener;
        private OnClickListener onPositiveClickListener;

        public Builder setMessgae(String messgae) {
            this.messgae = messgae;
            return this;
        }

        public Builder setMessageColor(int messageColor) {
            this.messageColor = messageColor;
            return this;
        }

        public Builder setMessageSize(float messageSize) {
            this.messageSize = messageSize;
            return this;
        }

        public Builder setNegative(String negative) {
            this.negative = negative;
            return this;
        }

        public Builder setNegativeColor(int negativeColor) {
            this.negativeColor = negativeColor;
            return this;
        }

        public Builder setNegativeSize(float negativeSize) {
            this.negativeSize = negativeSize;
            return this;
        }

        public Builder setOnNegativeClickListener(OnClickListener listener) {
            this.onNegativeClickListener = listener;
            return this;
        }

        public Builder setPositive(String positive) {
            this.positive = positive;
            return this;
        }

        public Builder setPositiveColor(int positiveColor) {
            this.positiveColor = positiveColor;
            return this;
        }

        public Builder setPositiveSize(float positiveSize) {
            this.positiveSize = positiveSize;
            return this;
        }

        public Builder setOnPositiveClickListener(OnClickListener listener) {
            this.onPositiveClickListener = listener;
            return this;
        }

        public CrazyDailyAlertDialog build() {
            CrazyDailyAlertDialog dialog = new CrazyDailyAlertDialog();
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE, messgae == null ? "" : messgae);
            bundle.putInt(MESSAGE_COLOR, messageColor == 0 ? Color.parseColor("#666666") : messageColor);
            bundle.putFloat(MESSAGE_SIZE, messageSize == 0 ? 18 : messageSize);
            bundle.putString(NEGATIVE, negative == null ? "" : negative);
            bundle.putInt(NEGATIVE_COLOR, negativeColor == 0 ? Color.parseColor("#333333") : negativeColor);
            bundle.putFloat(NEGATIVE_SIZE, negativeSize == 0 ? 20 : negativeSize);
            bundle.putString(POSITIVE, positive == null ? "" : positive);
            bundle.putInt(POSITIVE_COLOR, positiveColor == 0 ? Color.parseColor("#333333") : positiveColor);
            bundle.putFloat(POSITIVE_SIZE, positiveSize == 0 ? 20 : positiveSize);
            dialog.setArguments(bundle);
            dialog.setOnNegativeClickListener(onNegativeClickListener);
            dialog.setOnPositiveClickListener(onPositiveClickListener);
            return dialog;
        }
    }
}
