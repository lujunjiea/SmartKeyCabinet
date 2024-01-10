package cn.ljt.serialport

import android.util.Log
import java.io.File
import java.io.FileReader
import java.io.LineNumberReader
import java.util.*

class SerialPortFinder private constructor() {
    /**
     * 获取 Drivers
     *
     * @return Drivers
     * @throws Exception Exception
     */
    @get:Throws(Exception::class)
    private val drivers: ArrayList<Driver>
        get() {
            val drivers = ArrayList<Driver>()
            LineNumberReader(FileReader(DRIVERS_PATH)).useLines { lines ->
                lines.forEach { str ->
                    val fields = str.split(Regex(" +")).toTypedArray()
                    if (fields.size >= 5 && fields[fields.size - 1] == SERIAL_FIELD) {
                        val driverName = str.substring(0, 0x15).trim { it <= ' ' }
                        drivers.add(Driver(driverName, fields[fields.size - 4]))
                    }
                }
            }
            return drivers
        }

    /**
     * 获取串口
     *
     * @return 串口
     */
    val devices: ArrayList<Device>
        get() {
            val devices = ArrayList<Device>()
            try {
                for (driver in drivers) {
                    val driverName = driver.name
                    val driverDevices = driver.devices
                    for (file in driverDevices) {
                        val devicesName = file.name
                        devices.add(Device(devicesName, driverName, file))
                    }
                }
            } catch (e: Exception) {
                Log.i(TAG, "------Exception: ${e.toString()}")
                e.printStackTrace()
            }
            return devices
        }

    private var device: Device? = null

    fun getDevice(path: String): Device? {
        for (device in devices) {
            if (device.file != null && path == device.file!!.absolutePath) {
                return device
            }
        }
        return null
    }

    companion object {
        private val TAG = SerialPortFinder::class.java.simpleName
        private const val DRIVERS_PATH = "/proc/tty/drivers"
        private const val SERIAL_FIELD = "serial"

        @Volatile
        private var instance: SerialPortFinder? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: SerialPortFinder().also { instance = it }
        }
    }

    init {
        val file = File(DRIVERS_PATH)
        val b = file.canRead()
        Log.i(TAG, "SerialPortFinder: file.canRead() = $b")
    }
}