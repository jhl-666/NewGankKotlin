package com.ljhdemo.newgank.common.module;

/**
 * Created by hk_jacky on 2018/3/28.
 */

public class MessageEvent <T>{

    private String message;
    private T data;
    private int messageEventCode;

    public int getMessageEventCode() {
        return messageEventCode;
    }

    public void setMessageEventCode(int messageEventCode) {
        this.messageEventCode = messageEventCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageEvent(String message) {
        this.message = message;
    }
    public MessageEvent(String message,int messageEventCode) {
        this.message = message;
        this.messageEventCode = messageEventCode;
    }

    public MessageEvent(int messageEventCode) {
        this.messageEventCode = messageEventCode;
    }

    public MessageEvent(String message,T t){
        this.data = t;
        this.message = message;
    }

    public T getData(){
        return data;
    }
}
