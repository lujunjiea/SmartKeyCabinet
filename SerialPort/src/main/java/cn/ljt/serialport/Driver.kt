package cn.ljt.serialport

import android.util.Log
import java.io.File
import java.util.*

internal class Driver(val name: String, private val mDeviceRoot: String) {
    val devices: ArrayList<File>
        get() {
            val devices = ArrayList<File>()
            val dev = File("/dev")
            if (!dev.exists()) {
                Log.i(TAG, dev.absolutePath + " not exist.")
                return devices
            }
            if (!dev.canRead()) {
                Log.i(TAG, dev.absolutePath + " no permissions")
                return devices
            }
            val files = dev.listFiles()
            files?.forEach { file ->
                if (file.absolutePath.startsWith(mDeviceRoot)) {
                    Log.d(TAG, "Found new device: $file")
                    devices.add(file)
                }
            }
            return devices
        }

    companion object {
        private val TAG = Driver::class.java.simpleName
    }
}