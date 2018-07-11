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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.crazysunj.data.api.HttpHelper;
import com.crazysunj.data.service.DownloadService;
import com.crazysunj.domain.repository.download.DownloadRepository;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import retrofit2.Response;

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
    public Flowable<File> download(String url, File saveFileDir) {
        return mDownloadService.download(url)
                .observeOn(Schedulers.io())
                .map(response -> convertFile(saveFileDir, response))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Nullable
    private File convertFile(File saveFileDir, Response<ResponseBody> response) {
        final ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        BufferedSink bufferedSink = null;
        Source source = null;
        try {
            File saveFile = new File(saveFileDir, getFileName(response));
            bufferedSink = Okio.buffer(Okio.sink(saveFile));
            source = Okio.source(responseBody.byteStream());
            bufferedSink.writeAll(source);
            bufferedSink.flush();
            return saveFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedSink != null) {
                    bufferedSink.close();
                }
                if (source != null) {
                    source.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @NonNull
    private String getFileName(Response<ResponseBody> response) {
        final okhttp3.Response raw = response.raw();
        String contentDisposition = raw.header("Content-Disposition");
        if (TextUtils.isEmpty(contentDisposition)) {
            String file = raw.request().url().url().getFile();
            return file.substring(file.lastIndexOf("/") + 1, file.contains("?") ? file.indexOf("?") : file.length());
        } else {
            String fileName;
            try {
                fileName = URLDecoder.decode(contentDisposition.substring(contentDisposition.indexOf("filename=") + 9), "UTF-8");
                fileName = fileName.replaceAll("\"", "");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                fileName = contentDisposition.substring(contentDisposition.indexOf("filename=") + 9);
                fileName = fileName.replaceAll("\"", "");
            }
            return fileName;
        }
    }
}
