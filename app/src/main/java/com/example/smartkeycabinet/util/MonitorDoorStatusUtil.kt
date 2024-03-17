package com.example.smartkeycabinet.util

import android.util.Log
import com.example.smartkeycabinet.keyBoxUtil.OpenBoxCallBack
import com.example.smartkeycabinet.keyBoxUtil.OperateBoxUtil
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.Executors

/**
 * 监听打开过的箱格是否关闭， 如未关闭需提示用户
 */
object MonitorDoorStatusUtil {
    var checkBoxNos = mutableListOf<Int>()  //打开的箱格号集合
    var isChecking = false //是否正在检查箱格状态

    //线程池， 检测门状态， 因检测多个箱格状态时需要有间隔Sleep线程， 防止阻塞主线程
    private val threadPool = Executors.newFixedThreadPool(2)
    fun executeCheckBoxOperation(operation: CheckBoxOperation) {
        threadPool.submit(operation::performOperation)
    }

    /**
     * 将需要检测的箱格号加入到检测列表
     */
    fun addBoxNo(boxNo: Int) {
        checkBoxNos.add(boxNo)
    }

    /**
     * 检测完成的箱格号删除
     */
    private fun removeBoxNo(boxNo: Int) {
        if (checkBoxNos.contains(boxNo)) {
            checkBoxNos.remove(boxNo)
        }
    }

    fun checkDoorStatus() {
        if (isChecking) {
            Log.e("", "正在检测中---return")
            return
        }
        isChecking = true
        val timer = Timer(true)
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                if (checkBoxNos.size > 0) {
                    executeCheckBoxOperation(object : CheckBoxOperation {
                        override fun performOperation() {
                            checkBoxNos.forEach {
                                OperateBoxUtil.checkBoxStatus(it, object : OpenBoxCallBack {
                                    override fun onSuccess() {
                                        boxOpen(it) //箱格为开的处理
                                    }

                                    override fun onFailed() {
                                        boxClose(it) //箱格为关
                                    }
                                })
                                Thread.sleep(2000)
                            }
                        }
                    })
                } else {
                    isChecking = false
                    cancel()
                }
            }
        }
        timer.purge()
        timer.schedule(timerTask, 0, 10_000)
    }

    fun boxClose(boxNo: Int) {
        Log.e("","箱格关闭状态，删除该箱格轮询")
        removeBoxNo(boxNo)
    }

    fun boxOpen(boxNo: Int) {
        Log.e("","箱格打开状态，提示用户")
    }

}

@FunctionalInterface
interface CheckBoxOperation {
    fun performOperation()
}