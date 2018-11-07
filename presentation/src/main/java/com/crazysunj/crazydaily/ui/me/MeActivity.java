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
package com.crazysunj.crazydaily.ui.me;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.module.permission.PermissionHelper;
import com.crazysunj.crazydaily.module.permission.PermissionStorage;
import com.crazysunj.crazydaily.ui.browser.BrowserActivity;
import com.crazysunj.crazydaily.ui.photo.PhotoActivity;
import com.crazysunj.crazydaily.util.CacheUtil;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.crazydaily.view.item.CommonItem;
import com.pgyersdk.feedback.PgyerFeedbackManager;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
@RuntimePermissions
public class MeActivity extends BaseActivity implements PermissionStorage {

    private static final int REQUEST_SELECT_PICTURE = 0x01;

    @BindView(R.id.me_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.me_handle_img)
    CommonItem mHandleImg;
    @BindView(R.id.me_clear_cache)
    CommonItem mClearCache;

    @BindView(R.id.me_feedback)
    CommonItem mFeedback;
    @BindView(R.id.me_update)
    CommonItem mUpdate;

    @BindView(R.id.me_about_app)
    CommonItem mAboutApp;
    @BindView(R.id.me_about_me)
    CommonItem mAboutMe;

    public static void start(Context context) {
        Intent intent = new Intent(context, MeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unRegister();
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initListener() {
        mAboutApp.setOnClickListener(v -> BrowserActivity.start(this, getString(R.string.url_about_app)));
        mAboutMe.setOnClickListener(v -> BrowserActivity.start(this, getString(R.string.url_about_me)));
        mUpdate.setOnClickListener(v -> MeActivityPermissionsDispatcher.showUpdateWithPermissionCheck(this));
        mFeedback.setOnClickListener(v -> MeActivityPermissionsDispatcher.showFeedbackWithPermissionCheck(this));
        mClearCache.setOnClickListener(v -> MeActivityPermissionsDispatcher.clearCacheWithPermissionCheck(this));
        mHandleImg.setOnClickListener(v -> MeActivityPermissionsDispatcher.handleImgWithPermissionCheck(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                PhotoActivity.start(this, selectedUri.toString());
            } else {
                SnackbarUtil.show(this, "选择图片失败");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showUpdate() {
        new PgyUpdateManager.Builder()
                //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setUserCanRetry(false)
                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setForced(false)
                // 检查更新前是否删除本地历史
                .setDeleteHistroyApk(true)
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        // 无更新
                        SnackbarUtil.show(MeActivity.this, "已经是最新的哦");
                    }

                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        SnackbarUtil.show(MeActivity.this, "正在下载中");
                        PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        SnackbarUtil.show(MeActivity.this, "更新失败哦");
                    }
                })
                //注意 ：下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                // 使用蒲公英提供的下载方法，这个接口才有效。
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        SnackbarUtil.show(MeActivity.this, "更新失败哦");
                    }

                    @Override
                    public void downloadSuccessful(Uri uri) {
                        // 使用蒲公英提供的安装方法提示用户 安装apk
                        PgyUpdateManager.installApk(uri);
                    }

                    @Override
                    public void onProgressUpdate(Integer... integers) {
                    }
                })
                .register();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showFeedback() {
        new PgyerFeedbackManager.PgyerFeedbackBuilder()
                //设置是否摇一摇的方式激活反馈，默认为 true
                .setShakeInvoke(false)
                //设置以Dialog的方式打开
                .setDisplayType(PgyerFeedbackManager.TYPE.DIALOG_TYPE)
                .builder().invoke();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void handleImg() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE);
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void clearCache() {
        final boolean isSuccess = CacheUtil.cleanExternalCache(this);
        SnackbarUtil.show(this, "清除缓存" + (isSuccess ? "成功" : "失败"));
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showRationaleForStorage(PermissionRequest request) {
        PermissionHelper.storageShowRationale(this, request);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showDeniedForStorage() {
        PermissionHelper.storagePermissionDenied(this);
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void showNeverAskForStorage() {
        PermissionHelper.storageNeverAskAgain(this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_me;
    }
}
