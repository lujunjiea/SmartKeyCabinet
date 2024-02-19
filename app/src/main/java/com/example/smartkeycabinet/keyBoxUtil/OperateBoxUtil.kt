package com.example.smartkeycabinet.keyBoxUtil

import android.util.Log


object OperateBoxUtil {
    const val retryMaxCount = 3 //最大重试次数
    var currentRetryNum = 0  //当前重试次数
    var mCallBack: OpenBoxCallBack? = null

    fun openBox(boxNo: Int, callBack: OpenBoxCallBack) {
        currentRetryNum = 0
        mCallBack = callBack
        openBox(boxNo)
    }

    fun openBox(boxNo: Int) {
        if (currentRetryNum >= retryMaxCount) {
            Log.e("","已尝试3次， 报错")
            if (mCallBack != null) {
                mCallBack!!.onFailed()
            }
            return
        }
        currentRetryNum++
        Log.e("","第$currentRetryNum 次尝试开门")
        SerialPortUtil.INSTANCE.openDoor(boxNo, object : KeyBoxCallBack<Any> {
            override fun onSuccess() {
//                Log.e("","开门命令发送成功，检测箱门状态")
//                Thread.sleep(500)
//                checkBoxStatus(boxNo)
                Log.e("","开门命令发送成功，不检测箱门状态，默认成功")
                if (mCallBack != null) {
                    mCallBack!!.onSuccess()
                }
            }
            override fun onFailed(failedType: FailedType?) {
                Log.e("","开门失败: ${failedType?.name}")
                //重试开门
                openBox(boxNo)
            }
        })
    }

    fun checkBoxStatus(boxNo: Int) {
        SerialPortUtil.INSTANCE.checkDoorStatus(boxNo, object : KeyBoxCallBack<Any> {
            override fun onData(data: Any?) {
                val doorStatus = data.toString()
                if (doorStatus.equals(KeyBoxCommunicationUtil.STATE_SMALL_DOOR_OPEN)) {
                    Log.e("","门状态为 打开")
                    if (mCallBack != null) {
                        mCallBack!!.onSuccess()
                    }
                } else {
                    Log.e("","门状态为 关闭 重试")
                    openBox(boxNo)
                }
            }
        })
    }
}