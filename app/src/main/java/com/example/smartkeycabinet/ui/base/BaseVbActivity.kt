package com.tti.coffeeslaver.base

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseVbActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var viewBinding: VB

}