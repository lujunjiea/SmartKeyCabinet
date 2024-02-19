package com.example.smartkeycabinet.keyBoxUtil;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.smartkeycabinet.util.Utils;

import java.io.File;

import cn.ljt.serialport.Device;
import cn.ljt.serialport.SerialPortFinder;
import cn.ljt.serialport.SerialPortManager;
import cn.ljt.serialport.listener.OnOpenSerialPortListener;
import cn.ljt.serialport.listener.OnSerialPortDataListener;

public enum SerialPortUtil {

    INSTANCE;
    /**
     * 串口
     */
    private SerialPortManager mSerialPortManager;

    /**
     * 串口打开标记
     */
    private boolean isOpen = false;

    /**
     * 当前命令回调
     */
    private KeyBoxCallBack<Object> mCallBack;

    private CommandType currentType = null; //当前命令类型

    /**
     * 打开串口
     * @param path     "/dev/ttyS4"
     * @param baudRate 115200
     */
    public void openPort(String path, int baudRate) {
        if (isOpen) {
            Log.d("SerialPortUtil", "串口 已经打开");
            return;
        }
        Device mDevice = SerialPortFinder.Companion.getInstance().getDevice(path);
        mSerialPortManager = new SerialPortManager();
        if (mDevice != null && mDevice.getFile() != null && mSerialPortManager != null) {
            mSerialPortManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
                        @Override
                        public void onSuccess(@Nullable File file) {
                            isOpen = true;
                            if (file != null) {
                                Log.d("SerialPortUtil", String.format("串口 [%s] 打开成功", file.getPath()));
                                SerialPortCmdHelper.INSTANCE.setListener(mListener);
                            }
                        }

                        @Override
                        public void onFail(@Nullable File file, @Nullable Status status) {
                            if (file != null) {
                                Log.e("SerialPortUtil", String.format("串口 [%s] 打开失败", file.getPath()));
                            }
                        }
                    })
                    .setOnSerialPortDataListener(new OnSerialPortDataListener() {
                        @Override
                        public void onDataReceived(@Nullable byte[] bytes) {
                            Log.i("SerialPortUtil", "onDataReceived: " + Utils.toHexString(bytes));
                            try {
                                SerialPortCmdHelper.INSTANCE.receiverData(bytes);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onDataSent(@Nullable byte[] bytes) {
                            Log.i("SerialPortUtil", "onDataSent: " + Utils.toHexString(bytes));
                        }
                    })
                    .openSerialPort(mDevice.getFile(), baudRate);
        }
    }

    /**
     * 通过校验的数据回调
     */
    private final SerialPortDataListener mListener = new SerialPortDataListener() {
        @Override
        public void onData(byte[] data) {
            //根据当前命令，对数据进行解析
            switch (currentType) {
                case CHECK_DOOR_STATUS:
                    //查看门状态
                    String card = SerialPortCmdHelper.INSTANCE.getCard();
                    if (mCallBack != null) {
                        mCallBack.onData(card);
                        reSetCommand();
                    }
                    break;
                case OPEN_DOOR:
                    //打开箱门
                    if (mCallBack != null) {
                        mCallBack.onSuccess();
                        reSetCommand();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void reSetCommand() {
//        mCallBack = null;
//        currentType = null;
    }

    /**
     * 开箱门
     * @param boxNo
     */
    public void openDoor(int boxNo, KeyBoxCallBack<Object> callBack) {
        if (mSerialPortManager == null) {
            callBack.onFailed(FailedType.SERIAL_PORT_FAILED);
            return;
        }
        //设置回调
        mCallBack = callBack;
        //设置当前命令类型
        currentType = CommandType.OPEN_DOOR;
        //获取命令
        byte[] bs = new KeyBoxCommunication().KeyBoxOperate(KeyBoxOperateType.smallDoorOpen, 1, boxNo,"打开小箱门");
        //发送命令
        mSerialPortManager.sendBytes(bs);

        //超时处理， 如果3s后未收到回复（收到回复后会将currentType和mCallBack设为null），则走超时回调
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("","是否超时？");
//                if (currentType == CommandType.OPEN_DOOR && mCallBack != null) {
//                    Log.e("","是否超时？  超时");
//                    mCallBack.onFailed(FailedType.COMMAND_TIME_OUT);
//                    return;
//                }
//                Log.e("","是否超时？  未超时");
//            }
//        }, 3000);
    }

    /**
     * 检测箱格状态
     * @param boxNo
     */
    public void checkDoorStatus(int boxNo, KeyBoxCallBack<Object> callBack) {
        if (mSerialPortManager == null) {
            callBack.onFailed(FailedType.SERIAL_PORT_FAILED);
            return;
        }
        //设置回调
        mCallBack = callBack;
        //设置当前命令类型
        currentType = CommandType.CHECK_DOOR_STATUS;
        //获取命令
        byte[] bs = new KeyBoxCommunication().KeyBoxOperate(KeyBoxOperateType.testSmallDoorState, 1, boxNo,"小箱门检测");
        //发送命令
        mSerialPortManager.sendBytes(bs);

        //超时处理， 如果3s后未收到回复（收到回复后会将currentType和mCallBack设为null），则走超时回调
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (currentType == CommandType.CHECK_DOOR_STATUS && mCallBack != null) {
//                    mCallBack.onFailed(FailedType.COMMAND_TIME_OUT);
//                }
//            }
//        }, 3000);
    }

}
