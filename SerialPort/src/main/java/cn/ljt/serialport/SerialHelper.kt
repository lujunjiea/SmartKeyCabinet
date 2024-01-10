package cn.ljt.serialport

import android.util.Log
import cn.ljt.serialport.listener.OnOpenSerialPortListener
import cn.ljt.serialport.listener.OnSerialPortDataListener
import java.io.File

class SerialHelper private constructor() {
    private val serialManagerMap = mutableMapOf<String, SerialPortManager?>()

    fun open(path: String, baudrate: Int, listener: Listener): SerialPortManager? {
        if (serialManagerMap.containsKey(path)) {
            Log.d(TAG,"串口 $path 已经打开")
            return null
        }
//        val path = "/dev/ttyS1"
//        val baudrate = 115200
        val mDevice = SerialPortFinder.getInstance().getDevice(path)
        val serialPortManager = SerialPortManager()
        if (mDevice?.file != null) {
            serialPortManager.setOnOpenSerialPortListener(object : OnOpenSerialPortListener {
                override fun onFail(device: File?, status: OnOpenSerialPortListener.Status?) {
                    listener.onFail(device, status)
                }

                override fun onSuccess(device: File?) {
                    listener.onSuccess(device)
                }
            }).setOnSerialPortDataListener(object : OnSerialPortDataListener {
                /**
                 * 接收数据回调方法
                 *
                 * @param bytes
                 */
                override fun onDataReceived(bytes: ByteArray?) {
                    listener.onDataReceived(bytes)
                }

                /**
                 * 发送数据方法回调
                 *
                 * @param bytes
                 */
                override fun onDataSent(bytes: ByteArray?) {
                    listener.onDataSent(bytes)
                }
            }).openSerialPort(mDevice.file!!, baudrate)
        }
        serialManagerMap[path] = serialPortManager
        return serialPortManager
    }

    fun close(path: String) {
        serialManagerMap[path]?.closeSerialPort()
        serialManagerMap.remove(path)
    }

    fun getSerialManager(path: String): SerialPortManager? {
        return serialManagerMap[path]
    }

    fun getDevices(): ArrayList<Device> {
        return SerialPortFinder.getInstance().devices
    }

    interface Listener {
        fun onSuccess(device: File?) {}
        fun onFail(device: File?, status: OnOpenSerialPortListener.Status?) {}

        fun onDataReceived(bytes: ByteArray?)
        fun onDataSent(bytes: ByteArray?)
    }

    companion object {
        private val TAG = SerialHelper::class.java.name
        val INSTANCE: SerialHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SerialHelper()
        }
    }
}