package cn.ljt.serialport

import java.io.File
import java.io.Serializable

class Device(
    var name: String,
    var root: String?,
    var file: File?
) : Serializable