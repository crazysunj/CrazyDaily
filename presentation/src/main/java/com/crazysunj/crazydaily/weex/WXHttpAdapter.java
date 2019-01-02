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
package com.crazysunj.crazydaily.weex;

import android.text.TextUtils;

import com.crazysunj.crazydaily.constant.Constant;
import com.crazysunj.data.util.LoggerUtil;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;

import java.io.IOException;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author: sunjian
 * created on: 2018/3/13 上午10:39
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WXHttpAdapter implements IWXHttpAdapter {

    private OkHttpClient mOkHttpClient;

    public WXHttpAdapter(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    @Override
    public void sendRequest(WXRequest request, OnHttpListener listener) {

        if (listener != null) {
            listener.onHttpStart();
        }

        if (request == null) {
            if (listener != null) {
                WXResponse wxResponse = new WXResponse();
                wxResponse.errorMsg = "WXRequest为空";
                listener.onHttpFinish(wxResponse);
            }
            return;
        }

        Request okHttpRequest;
        if (Constant.POST.equalsIgnoreCase(request.method)) {
            okHttpRequest = (new Request.Builder())
                    .headers(getHeaders(request))
                    .url(request.url)
                    .post(RequestBody.create(MediaType.parse(request.body), request.body))
                    .build();
        } else {
            okHttpRequest = (new Request.Builder()).url(request.url).build();
        }

        mOkHttpClient.newCall(okHttpRequest)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (listener != null) {
                            WXResponse wxResponse = new WXResponse();
                            wxResponse.errorCode = String.valueOf(-100);
                            wxResponse.statusCode = String.valueOf(-100);
                            wxResponse.errorMsg = e.getMessage();
                            try {
                                listener.onHttpFinish(wxResponse);
                            } catch (Exception e1) {
                                LoggerUtil.d(e1.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (listener != null) {
                            WXResponse wxResponse = new WXResponse();
                            wxResponse.statusCode = String.valueOf(response.code());
                            ResponseBody body = response.body();
                            if (body == null) {
                                wxResponse.errorMsg = "body为空";
                            } else {
                                wxResponse.originalData = body.bytes();
                            }
                            try {
                                listener.onHttpFinish(wxResponse);
                            } catch (Exception e) {
                                LoggerUtil.d(e.getMessage());
                            }
                        }
                    }
                });
    }

    private Headers getHeaders(WXRequest request) {
        Headers.Builder builder = new Headers.Builder();
        if (request.paramMap != null) {
            Set<String> keySet = request.paramMap.keySet();
            for (String key : keySet) {
                String value = request.paramMap.get(key);
                if (!TextUtils.isEmpty(value)) {
                    builder.add(key, value);
                }
            }
        }
        return builder.build();
    }
}
