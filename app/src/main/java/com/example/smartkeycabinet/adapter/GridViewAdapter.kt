package com.example.smartkeycabinet.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.model.BoxsModel


class GridViewAdapter(var list: List<BoxsModel>, val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("NewApi")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var myHolder: MyHolder?
        if (convertView == null) {
            myHolder = MyHolder()
            view = LayoutInflater.from(context).inflate(R.layout.item_gridview_layout, null)
            myHolder.snTV = view!!.findViewById(R.id.tv_sn)
            myHolder.openBoxTV = view.findViewById(R.id.tv_open_box)
            myHolder.bgLL = view.findViewById(R.id.ll_bg)
            view.tag = myHolder
        } else {
            view = convertView
            myHolder = view.tag as MyHolder
        }
        myHolder.snTV.text = "${list[position].id}"
        when(list[position].keyStatus) {
            0 -> {//无钥匙
                myHolder.bgLL.background = context.getDrawable(R.drawable.shape_et)
            }
            1 -> {//有钥匙
                myHolder.bgLL.background = context.getDrawable(R.drawable.shape_box_key)
            }
            else -> {//损坏
                myHolder.bgLL.background = context.getDrawable(R.drawable.shape_failure)
            }
        }
        return view
    }

    class MyHolder() {
        lateinit var bgLL: LinearLayout
        lateinit var openBoxTV: TextView
        lateinit var snTV: TextView
    }
}