package com.example.smartkeycabinet.keyBoxUtil

import android.util.Log
import com.example.smartkeycabinet.App
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.model.SaveRecordModel
import com.example.smartkeycabinet.model.UpdateBoxStatusBody
import com.example.smartkeycabinet.net.BaseObserver
import com.example.smartkeycabinet.net.BaseResponse
import com.example.smartkeycabinet.net.HttpRequest
import com.example.smartkeycabinet.util.ToastUtil
import org.json.JSONObject


object UpdateBoxStatusUtil {
    const val BOX_STATUS_IDLE = 0 //无钥匙
    const val BOX_STATUS_BUSY = 1 //有钥匙
    const val BOX_STATUS_FAILED = 2 //损坏

    //更新箱格状态
    fun updateBoxStatus(boxStatus: Int, boxId: Int, plateNumber: String) {
        HttpRequest.updateBoxStatus(UpdateBoxStatusBody(boxId, boxStatus, plateNumber), object : BaseObserver<String>() {
            override fun onSuccess(baseResponse: BaseResponse<String>?) {
                if (baseResponse != null) {
                    if (baseResponse.isSuccess) {
                        Log.e("","箱格状态更新成功")
                    } else {
                        Log.e("","箱格状态更新失败")
                    }
                }
            }

            override fun onFailure(e: Throwable?) {
                super.onFailure(e)
                Log.e("","箱格状态更新失败")
            }
        })
    }

    //上传操作记录
    fun uploadOperatorRecord(body: SaveRecordModel) {
        HttpRequest.saveOperatorRecord(body, object : BaseObserver<JSONObject>(){
            override fun onSuccess(baseResponse: BaseResponse<JSONObject>?) {
                if (baseResponse != null && baseResponse.isSuccess) {
                    Log.e("","操作记录上传成功")
                } else {
                    Log.e("","操作记录上传失败：${baseResponse?.message}")
                }
            }

            override fun onFailure(e: Throwable?) {
                super.onFailure(e)
                Log.e("","网络数据异常")
            }
        })
    }

}