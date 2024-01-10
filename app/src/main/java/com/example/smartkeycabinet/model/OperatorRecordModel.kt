package com.example.smartkeycabinet.model


data class OperatorRecordModel(val id: String, val operationType: String,
                               val identifyType: String, val operatorId: String,
                               val operatorName: String, val department: String,
                               val phone: String, val createTime: String) {}
