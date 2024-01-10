package com.example.smartkeycabinet.net.util;

import com.example.smartkeycabinet.model.MorenModel;
import com.example.smartkeycabinet.net.adapter.MorenSensorTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyUtils {

    public static <T> RequestBody getBody(T t) {
        Gson gson = new Gson();
        //传入参数排序
//        if (type == 1) {
//            gson = new GsonBuilder().registerTypeAdapter(MorenModel.class, new MorenSensorTypeAdapter()).create();
//        }
        MediaType mediaType = MediaType.Companion.parse("application/json; charset=utf-8");
        return RequestBody.Companion.create(gson.toJson(t, t.getClass()), mediaType);
    }
}
