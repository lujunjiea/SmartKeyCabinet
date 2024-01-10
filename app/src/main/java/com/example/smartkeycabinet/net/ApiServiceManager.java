package com.example.smartkeycabinet.net;

import android.text.TextUtils;
import android.util.Log;

import com.example.smartkeycabinet.net.converter.LenientGsonConverterFactory;
import com.example.smartkeycabinet.net.converter.NullOnEmptyConverterFactory;
import com.example.smartkeycabinet.net.interceptor.HeaderInterceptor;
import com.example.smartkeycabinet.net.interceptor.LogInterceptor;
import com.example.smartkeycabinet.net.interceptor.ParameterInterceptor;
import com.example.smartkeycabinet.net.interceptor.RetryInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceManager {
    /**
     * 连接超时时间
     */
    private static final int DEFAULT_CONNECT_TIME = 10;
    /**
     * 设置写操作超时时间
     */
    private static final int DEFAULT_WRITE_TIME = 30;
    /**
     * 设置读操作超时时间
     */
    private static final int DEFAULT_READ_TIME = 30;
    private static final ApiServiceManager INSTANCE = new ApiServiceManager();
    private final Retrofit retrofit;

    private ApiServiceManager() {
        String baseUrl = NetConstant.RELEASE_SERVER_URL;
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalArgumentException("baseUrl is empty");
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Log.d("ApiServiceManager","网络数据-->  " + message))
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new RetryInterceptor(3))
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new ParameterInterceptor())
                .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                //设置使用okhttp网络请求
                .client(okHttpClient)
                //设置服务器路径
                .baseUrl(baseUrl)
                //必须第一个
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(LenientGsonConverterFactory.create())
                //添加转化库，默认是Gson
                .addConverterFactory(GsonConverterFactory.create())
                //添加回调库，采用RxJava
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public static ApiServiceManager getInstance() {
        return INSTANCE;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
