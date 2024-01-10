package com.example.smartkeycabinet.net;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smartkeycabinet.util.ToastUtil;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.HttpException;


public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        onSuccess(baseResponse);
    }


    /**
     * 成功回调
     *
     * @param baseResponse 返回数据
     */
    public abstract void onSuccess(BaseResponse<T> baseResponse);

    public void onFailure(Throwable e) {
        try {
            if (e instanceof ConnectException || e instanceof TimeoutException
                    || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
                ToastUtil.showToast("请检查网络");
                Log.e("","网络连接失败" + e);
            } else if (e instanceof HttpException) {
                Log.e("","网络连接失败" + e);
            } else if (e instanceof JsonSyntaxException || e instanceof NullPointerException) {
                Log.e("","Json解析出错,看日志" + e);
            } else {
                Log.e("","网络异常信息" + e);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
