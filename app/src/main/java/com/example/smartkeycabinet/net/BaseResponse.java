package com.example.smartkeycabinet.net;

public class BaseResponse<T> {
    //成功的code
    private static int SUCCESS_CODE = 200;
    //响应码
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回的具体数据
     */
    private T data;

    public static int getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static void setSuccessCode(int successCode) {
        SUCCESS_CODE = successCode;
    }

    public boolean isSuccess() {
        return getStatus_code() == SUCCESS_CODE;
    }

    public int getStatus_code() {
        return code;
    }

    public void setStatus_code(int status_code) {
        this.code = status_code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status_code=" + code +
                ", message='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
