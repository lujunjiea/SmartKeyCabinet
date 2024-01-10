package com.example.smartkeycabinet

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.smartkeycabinet.util.AppManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class App : Application(){


    companion object {
        var job: Job = Job()
        lateinit var instance: App
        val scope = CoroutineScope(job)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    override fun attachBaseContext(base: Context) {//launching on devices
        super.attachBaseContext(base)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.e("","AppApplication onLowMemory() 啊,app被系统杀死")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.e("","AppApplication onTrimMemory() level: $level")
        when (level) {
            TRIM_MEMORY_UI_HIDDEN -> {
                Log.e("","AppApplication onTrimMemory() 当前进程的界面已经不可见，这时是释放UI相关的资源的好时机。调用退出 app方法")
                AppManager.getInstance().AppExit(this)
            }
        }
    }
}