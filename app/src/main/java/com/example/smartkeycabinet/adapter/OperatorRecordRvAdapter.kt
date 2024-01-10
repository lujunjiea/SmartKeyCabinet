package com.example.smartkeycabinet.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.model.OperatorRecordModel


class OperatorRecordRvAdapter(layoutResId: Int = R.layout.item_operator_record_layout) :
    BaseQuickAdapter<OperatorRecordModel, BaseViewHolder>(layoutResId), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: OperatorRecordModel) {
        holder.setText(R.id.tv_sn, "${holder.layoutPosition}")
        holder.setText(R.id.tv_operation_mode, item.operationType)
        holder.setText(R.id.tv_identification_mode, item.identifyType)
        holder.setText(R.id.tv_operator_people, item.operatorName)
        holder.setText(R.id.tv_department, item.department)
        holder.setText(R.id.tv_operation_box_no, item.operatorId)
        holder.setText(R.id.tv_phone, item.phone)
        holder.setText(R.id.tv_operation_time, item.createTime)
    }
}