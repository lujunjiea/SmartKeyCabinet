package cn.ljt.serialport.thread

import cn.ljt.serialport.Constant
import java.io.IOException
import java.io.InputStream

internal abstract class SerialPortReadThread(
    tName: String,
    private var mInputStream: InputStream?
) : Thread(tName) {
    abstract fun onDataReceived(bytes: ByteArray?)
    override fun run() {
        super.run()
        try {
            while (!isInterrupted) {
                val available = mInputStream?.available() ?: 0
                if (available > 0) {
                    val mReadBuffer = ByteArray(1024)
                    val size = mInputStream?.read(mReadBuffer) ?: 0
                    if (0 >= size) {
                        return
                    }
                    val readBytes = ByteArray(size)
                    System.arraycopy(mReadBuffer, 0, readBytes, 0, size)
                    onDataReceived(readBytes)
                } else {
                    sleep(Constant.readThreadSleepTime)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is InterruptedException) {
                interrupt()
            }
        }
    }

    @Synchronized
    override fun start() {
        super.start()
    }

    /**
     * 关闭线程 释放资源
     */
    fun release() {
        interrupt()
        mInputStream?.close()
        mInputStream = null
    }

}