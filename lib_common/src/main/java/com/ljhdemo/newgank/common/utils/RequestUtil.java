package com.ljhdemo.newgank.common.utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestUtil {

    public static RequestUtil getInstance() {
        return RequestHolder.instance;
    }

    private RequestUtil() {
    }

    private static class RequestHolder {
        private static final RequestUtil instance = new RequestUtil();
    }

    /**
     * 构造RequestBody
     * @param body
     * @return
     */
    public RequestBody requestFactory(Object body) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                JsonUtil.toJson(body));
    }
}
