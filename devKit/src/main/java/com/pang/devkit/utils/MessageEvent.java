package com.pang.devkit.utils;

/**
 * 作者: Created by 张展硕  2021/3/8.
 * 时间: 2021/3/8
 * 描述：
 */

public class MessageEvent {
    public String message;
    public String MethodName; //用去区分数据的功能名称


    public MessageEvent(String message, String methodName) {
        this.message = message;
        MethodName = methodName;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
