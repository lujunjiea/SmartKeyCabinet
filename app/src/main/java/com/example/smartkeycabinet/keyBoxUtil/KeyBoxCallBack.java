package com.example.smartkeycabinet.keyBoxUtil;


public interface KeyBoxCallBack<T> {
    default void onData(T data) {
    }

    default void onFailed(FailedType failedType) {
    }

    default void onSuccess() {
    }
}
