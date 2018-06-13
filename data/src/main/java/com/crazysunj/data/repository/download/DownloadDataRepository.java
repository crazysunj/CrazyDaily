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
package com.crazysunj.data.repository.download;

import android.support.annotation.Nullable;
import android.util.Log;

import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.DownloadService;
import com.crazysunj.domain.repository.download.DownloadRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:58
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class DownloadDataRepository implements DownloadRepository {

    private DownloadService mDownloadService;

    @Inject
    public DownloadDataRepository(HttpHelper httpHelper) {
        mDownloadService = httpHelper.getDownloadService();
    }

    @Override
    public Flowable<File> download(String url, File saveFile) {
        return mDownloadService.download(url)
                .observeOn(Schedulers.io())
                .map(responseBody -> convertFile(saveFile, responseBody))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Nullable
    private File convertFile(File saveFile, ResponseBody responseBody) {
        Log.d("DownloadService", saveFile.getAbsolutePath());
        InputStream is = null;
        byte[] buffer = new byte[1024];
        int len;
        FileOutputStream fos = null;
        try {
            is = responseBody.byteStream();
            fos = new FileOutputStream(saveFile);
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            return saveFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
