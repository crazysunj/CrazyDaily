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
package com.crazysunj.crazydaily.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.di.component.DaggerServiceComponent;
import com.crazysunj.crazydaily.di.module.ServiceModule;
import com.crazysunj.crazydaily.presenter.DownloadPresenter;
import com.crazysunj.crazydaily.presenter.contract.DownloadContract;
import com.crazysunj.crazydaily.util.FileUtil;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

/**
 * author: sunjian
 * created on: 2018/6/13 下午2:51
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class DownloadService extends Service implements DownloadContract.View {

    private static final int NOTIFICATION_ID = UUID.randomUUID().hashCode();
    private static final String CHANNEL_ID_DOWNLOAD = "'download'";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotificationBuilder;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(ActivityConstant.URL, url);
        context.startService(intent);
    }

    @Inject
    protected DownloadPresenter mPresenter;

    public DownloadService() {
        onPrepare();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();
    }

    private void initNotification() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 适配通知栏8.0
            assert mNotificationManager != null;
            NotificationChannel channel = mNotificationManager.getNotificationChannel(CHANNEL_ID_DOWNLOAD);
            if (channel == null) {
                channel = new NotificationChannel(CHANNEL_ID_DOWNLOAD, "下载通知", NotificationManager.IMPORTANCE_MIN);
                mNotificationManager.createNotificationChannel(channel);
            }
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                // 这里可以设计成与Activity通信，然后Activity去回调
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "设置好通知栏权限，请重新下载", Toast.LENGTH_SHORT).show();
                stopSelf();
            }
        }
        mNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID_DOWNLOAD)
                .setContentText("正在下载")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setWhen(System.currentTimeMillis());
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
        Toast.makeText(this, "正在下载，可在通知栏查看进度哦", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(null, flags, startId);
        }
        final String url = intent.getStringExtra(ActivityConstant.URL);
        if (TextUtils.isEmpty(url)) {
            return super.onStartCommand(intent, flags, startId);
        }
        mPresenter.download(url, FileUtil.getDownloadFile(this));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onProgress(int progress) {
        mNotificationBuilder.setContentText(String.format(Locale.getDefault(), "正在下载:%d%%", progress))
                .setProgress(100, progress, false);
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    @Override
    public void onSuccess(File saveFile) {
        Toast.makeText(this, "下载完成，保存路径：" + saveFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider_authorities), saveFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(saveFile);
        }
        intent.setData(uri);
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        mNotificationBuilder.setContentIntent(pendingintent);
    }

    @Override
    public void onFailed(Throwable e) {
        mNotificationBuilder.setContentText("下载失败");
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
    }

    @Override
    public void onComplete() {
        mNotificationBuilder.setContentText("下载完成，请点击使用");
        mNotificationManager.notify(NOTIFICATION_ID, mNotificationBuilder.build());
        stopSelf();
    }

    private void onPrepare() {
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void initInject() {
        DaggerServiceComponent.builder()
                .appComponent(App.getInstance().getAppComponent())
                .serviceModule(getServiceModule())
                .build()
                .inject(this);
    }

    private ServiceModule getServiceModule() {
        return new ServiceModule(this);
    }
}
