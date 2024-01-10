package com.example.smartkeycabinet.net.util;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxBindUtils {
    public static <T> void setIOSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                //子线程访问网络
                .subscribeOn(Schedulers.newThread())
                //回调到子线程
                .observeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static <T> void setMainSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                //子线程访问网络
                .subscribeOn(Schedulers.newThread())
                //回调到主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
