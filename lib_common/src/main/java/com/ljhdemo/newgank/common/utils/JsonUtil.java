package com.ljhdemo.newgank.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static class GsonHolder {
        private static Gson INSTNACE = new Gson();
    }

    public static Gson getGson() {
        return GsonHolder.INSTNACE;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return getGson().fromJson(json, type);
    }

    public static <T> T fromJson(JsonElement element, Class<T> type) {
        // 防止PHP服务端将空对象返回成数组
        if (element != null && element.isJsonArray()) {
            element = new JsonObject();
        }
        return getGson().fromJson(element, type);
    }

    public static <T> List<T> fromJson(String json, TypeToken<ArrayList<T>> token) {
        return getGson().fromJson(json, token.getType());
    }

    public static <T> List<T> fromJson(JsonElement element, TypeToken<ArrayList<T>> token) {
        if (element == null || element.isJsonObject()) {
            return null;
        }
        return getGson().fromJson(element, token.getType());
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        return getGson().toJson(obj);
    }

}