/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.crazydaily.ui;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.domain.constant.CacheConstant;

import java.io.File;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
@RuntimePermissions
public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.about_me_github)
    TextView mGithub;
    @BindView(R.id.about_me_blog)
    TextView mBlog;
    @BindView(R.id.about_me_clear_cache)
    TextView mClearCache;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutMeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        showBack();
    }

    @Override
    protected void initListener() {
        mGithub.setOnLongClickListener(v -> {
            String github = mGithub.getText().toString().trim();
            copyContent(github);
            return false;
        });

        mBlog.setOnLongClickListener(v -> {
            String blog = mBlog.getText().toString().trim();
            copyContent(blog);
            return false;
        });

        mClearCache.setOnClickListener(v -> AboutMeActivityPermissionsDispatcher.clearCacheWithPermissionCheck(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AboutMeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void clearCache() {
        File cacheDir = new File(getExternalCacheDir(), CacheConstant.CACHE_DIR_API);
        boolean isSuccess = false;
        if (cacheDir.exists() && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                isSuccess = file.delete();
            }
        }
        SnackbarUtil.show(this, "清除缓存" + (isSuccess ? "成功" : "失败"));
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForClearCache(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_storage_rationale)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForClearCache() {
        SnackbarUtil.show(this, R.string.permission_storage_denied);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForClearCache() {
        SnackbarUtil.show(this, R.string.permission_storage_neverask);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_about_me;
    }

    private void copyContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(content);
            SnackbarUtil.show(this, "已经复制到剪切板！");
        }
    }
}
