package com.ljhdemo.newgank.common.module;


public class MessageEvent <T>{

    private String message;
    private T data;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
        this.code = messageEventCode;
    }

    public MessageEvent(int messageEventCode) {
        this.code = messageEventCode;
    }

    public MessageEvent(String message,T t){
        this.data = t;
        this.message = message;
    }

    public T getData(){
        return data;
    }
}
