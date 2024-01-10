package com.example.smartkeycabinet.net.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder().build();

        long startTime = System.nanoTime();
       // Log.i("LogInterceptor", String.format("Request: %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long endTime = System.nanoTime();
       // Log.i("LogInterceptor", String.format("Response: %s in %.1fms%n%s", response.request().url(), (endTime - startTime) / 1e6d, response.headers()));

        return response;
    }

}
