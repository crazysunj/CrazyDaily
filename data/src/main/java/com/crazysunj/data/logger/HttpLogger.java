package com.crazysunj.data.logger;

import com.crazysunj.data.util.JsonUtil;
import com.crazysunj.data.util.LoggerUtil;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author: sunjian
 * created on: 2017/9/22 下午4:00
 * description:
 */

public class HttpLogger implements HttpLoggingInterceptor.Logger {

    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LoggerUtil.d(mMessage.toString());
        }
    }
}
