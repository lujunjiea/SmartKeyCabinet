package com.example.smartkeycabinet.model


data class GetCarListBodyModel(val parkingArea: String, val parkingNo: String,val parkingType: String,val parkingStatus: String,
                               val plateNumber: String,val page: Int,val pageSize: Int)
