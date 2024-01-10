package com.example.smartkeycabinet.net.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjo4LCJsb2dpbl90eXBlIjoiTUFOQUdFUiIsInVzZXJfa2V5IjoiYzBmNTkxZGYtMzFiMy00MGRhLTllNmMtZDMyNDc3NzhlYmFlIiwidXNlcm5hbWUiOiJ6YXRfMDA4In0.c3HffKeH9YJWTKwSTso9z7b8DcfH-vlhAtimHJ4I0gjyqTjJ7hq0jKrE9rvoak4bhM8-3bOQViyA0ca8igsWlg";


    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
//                .addHeader("app-name", "Solo")
//                .addHeader("api-code", String.valueOf(1002))
//                .addHeader("version", BuildConfig.VERSION_NAME)
//                .addHeader("timezone", TimeZone.getDefault().getID())
//                .addHeader("timestamp", String.valueOf(System.currentTimeMillis()))
//                .addHeader("Authorization", token)
                .build();
        return chain.proceed(request);
    }

}
