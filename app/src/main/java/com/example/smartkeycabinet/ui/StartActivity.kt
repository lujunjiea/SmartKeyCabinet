package com.example.smartkeycabinet.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.smartkeycabinet.App
import com.example.smartkeycabinet.databinding.ActivityStartBinding
import com.tti.coffeeslaver.base.BaseVbActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseVbActivity<ActivityStartBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        Log.e("","onCreate")
        App.scope.launch {
            delay(3000)
            Log.e("","跳转界面")
            startActivity(Intent(this@StartActivity,MainActivity::class.java))
            finish()
        }
    }
}