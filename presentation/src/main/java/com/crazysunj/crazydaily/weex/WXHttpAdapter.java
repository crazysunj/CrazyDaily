package com.crazysunj.crazydaily.weex;

import android.annotation.TargetApi;

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
 * author: sunjian
 * created on: 2018/3/13 上午10:39
 * description:https://github.com/crazysunj/CrazyDaily
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
            okHttpRequest = (new okhttp3.Request.Builder()).url(request.url).build();
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

    @TargetApi(24)
    private Headers getHeaders(WXRequest request) {
        okhttp3.Headers.Builder builder = new okhttp3.Headers.Builder();
        if (request.paramMap != null) {
            Set<String> keySet = request.paramMap.keySet();
            keySet.forEach(key -> builder.add(key, request.paramMap.get(key)));
        }
        return builder.build();
    }
}
