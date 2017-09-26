package com.crazysunj.data.exception;

/**
 * author: sunjian
 * created on: 2017/9/19 下午6:55
 * description:
 */

public class ApiException extends RuntimeException {
    
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }
}
