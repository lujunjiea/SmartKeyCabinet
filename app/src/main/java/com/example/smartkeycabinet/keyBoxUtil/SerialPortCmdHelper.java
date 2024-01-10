package com.example.smartkeycabinet.keyBoxUtil;

import android.util.Log;

public enum SerialPortCmdHelper {
    /**
     * 实例
     */
    INSTANCE;
    /**
     * 钥匙位卡号
     */
    public String _CardNumber = "";

    private SerialPortDataListener mListener;


    public void setListener(SerialPortDataListener listener) {
        mListener = listener;
    }

    /**
     * 获得钥匙位卡号
     *
     * @return String类型字符串
     */
    public String getCard() {
        return _CardNumber;
    }

    public void receiverData(byte[] bytes) {
        //先校验命令
        boolean isSuccess = new KeyBoxCommunication().dataCheck(bytes);
        if (!isSuccess) {
            Log.e("","数据校验失败，不进行处理");
        } else {
            mListener.onData(bytes);
        }
    }
}
