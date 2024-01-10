package com.example.smartkeycabinet.model


data class GetOperatorRecordBodyModel(val page: Int,
                                      val pageSize: Int,
                                      val operatorName: String, val startTime: String, val endTime: String)
