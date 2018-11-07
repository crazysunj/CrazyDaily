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
package com.crazysunj.crazydaily.ui.photo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.ui.adapter.PhotoPickerBucketAdapter;
import com.crazysunj.crazydaily.util.ScreenUtil;
import com.crazysunj.domain.entity.photo.BucketEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: sunjian
 * created on: 2018/9/25 上午10:30
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class BucketDialog extends DialogFragment {

    private static final String DATA = "data";
    private static final String TAG = "BucketDialog";

    @BindView(R.id.dialog_bucket_list)
    RecyclerView mBucketList;
    private Unbinder mUnbinder;
    private FragmentActivity mActivity;
    private OnBucketItemClickListener mOnBucketItemClickListener;

    public static BucketDialog get(ArrayList<BucketEntity> data) {
        BucketDialog dialog = new BucketDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DATA, data);
        dialog.setArguments(bundle);
        return dialog;
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
        View view = inflater.inflate(R.layout.dialog_photo_picker, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mBucketList.setLayoutManager(new LinearLayoutManager(mActivity));
        mBucketList.getItemAnimator().setChangeDuration(0);
        Bundle bundle = getArguments();
        List<BucketEntity> bucketList;
        if (bundle == null) {
            bucketList = new ArrayList<>();
        } else {
            bucketList = bundle.getParcelableArrayList(DATA);
        }
        PhotoPickerBucketAdapter adapter = new PhotoPickerBucketAdapter(bucketList);
        adapter.setOnBucketItemClickListener(item -> {
            if (mOnBucketItemClickListener != null) {
                mOnBucketItemClickListener.onItemClick(item);
            }
        });
        mBucketList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = ScreenUtil.dp2px(mActivity, 600);
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

    public void setOnBucketItemClickListener(OnBucketItemClickListener listener) {
        mOnBucketItemClickListener = listener;
    }

    public interface OnBucketItemClickListener {
        void onItemClick(BucketEntity item);
    }
}
