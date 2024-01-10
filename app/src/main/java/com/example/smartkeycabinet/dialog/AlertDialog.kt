package com.example.smartkeycabinet.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.callback.DialogDismissListener
import com.example.smartkeycabinet.callback.DialogItemClickListener
import com.example.smartkeycabinet.model.CarListBean.ListBean


class AlertDialog(private val mContext: Context) {
    private var dialog: Dialog? = null
    private val display: Display
    var countDownTimer: CountDownTimer? = null

    init {
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
    }

    /**
     * @param:type  1取车， 2还车
     * @param:isOpenSuccess  开箱成功true  失败false
     */
    @SuppressLint("NewApi", "UseCompatLoadingForDrawables")
    fun openBoxStatusBuild(
        isOpenSuccess: Boolean,
        listener: DialogDismissListener,
        type: Int
    ): AlertDialog {
        if (dialog == null) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_open_box_layout, null)
            val lLayout_rt_bg = view.findViewById<RelativeLayout>(R.id.lLayout_rt_bg)
            val openStatusIV = view.findViewById<ImageView>(R.id.iv_open_box_status)
            val msgTV = view.findViewById<TextView>(R.id.tv_msg)
            if (type == 1) { //取车提示
                if (isOpenSuccess) {
                    openStatusIV.background = mContext.getDrawable(R.drawable.img_open_box_success)
                    msgTV.setText(mContext.getString(R.string.str_open_success))
                } else {
                    openStatusIV.background = mContext.getDrawable(R.drawable.img_open_box_failed)
                    msgTV.setText(mContext.getString(R.string.str_open_failed))
                }
            } else { //还车提示
                if (isOpenSuccess) {
                    openStatusIV.background = mContext.getDrawable(R.drawable.img_open_box_success)
                    msgTV.setText(mContext.getString(R.string.str_return_car_open_success))
                } else {
                    openStatusIV.background = mContext.getDrawable(R.drawable.img_open_box_failed)
                    msgTV.setText(mContext.getString(R.string.str_return_car_open_failed))
                }
            }

            Handler().postDelayed(Runnable { dismiss() },5000)
            dialog = Dialog(mContext, R.style.AlertDialogStyle)
            dialog!!.setContentView(view)
            dialog!!.window!!.setGravity(Gravity.CENTER)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setOnDismissListener {
                listener.onDismissListener()
            }
            lLayout_rt_bg.layoutParams = FrameLayout.LayoutParams(
                (display.width * 0.65).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        return this
    }

    /**
     * 车位列表dialog
     */
    @SuppressLint("NewApi", "MissingInflatedId")
    fun carPositionListDialog(
        list: MutableList<ListBean>,
        context: Context,
        listener: DialogItemClickListener
    ): AlertDialog {
        if (dialog == null) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_car_list_layout, null)
            val lLayout_rt_bg = view.findViewById<RelativeLayout>(R.id.lLayout_rt_bg)
            val listView = view.findViewById<ListView>(R.id.lv_car_list)
            val adapter = com.example.smartkeycabinet.adapter.SpinnerAdapter(list, context)
            listView.adapter = adapter
            listView.setOnItemClickListener { adapterView, view, i, l ->
                listener.onItemListener(i)
                dismiss()
            }
            dialog = Dialog(mContext, R.style.AlertDialogStyle)
            dialog!!.setContentView(view)
            dialog!!.window!!.setGravity(Gravity.CENTER)
            dialog!!.setCanceledOnTouchOutside(true)
            lLayout_rt_bg.layoutParams = FrameLayout.LayoutParams(
                (display.width * 0.65).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        return this
    }


    @SuppressLint("MissingInflatedId")
    fun tipDialog(
        message: String?,
        noListener: View.OnClickListener?,
        yesListener: View.OnClickListener?,
    ): AlertDialog {
        if (dialog == null) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_tip_layout, null)
            val lLayout_rt_bg = view.findViewById<RelativeLayout>(R.id.lLayout_rt_bg)
            val tv_title = view.findViewById<TextView>(R.id.tv_msg)
            val tv_no = view.findViewById<TextView>(R.id.tv_no)
            val tv_yes = view.findViewById<TextView>(R.id.tv_yes)
            tv_title.text = message
            tv_no.setOnClickListener(noListener)
            tv_yes.setOnClickListener(yesListener)
            dialog = Dialog(mContext, R.style.AlertDialogStyle)
            dialog!!.setContentView(view)
            dialog!!.window!!.setGravity(Gravity.CENTER)
            dialog!!.setCanceledOnTouchOutside(false)
            lLayout_rt_bg.layoutParams = FrameLayout.LayoutParams(
                (display.width * 0.65).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        return this
    }

    @SuppressLint("MissingInflatedId")
    fun tipDialogYesOrNo(
        message: String?,
        noListener: View.OnClickListener?,
        yesListener: View.OnClickListener?,
    ): AlertDialog {
        if (dialog == null) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_tip_layout, null)
            val lLayout_rt_bg = view.findViewById<RelativeLayout>(R.id.lLayout_rt_bg)
            val tv_title = view.findViewById<TextView>(R.id.tv_msg)
            val tv_no = view.findViewById<TextView>(R.id.tv_no)
            val tv_yes = view.findViewById<TextView>(R.id.tv_yes)
            tv_title.text = message
            tv_no.setOnClickListener(noListener)
            tv_yes.setOnClickListener(yesListener)
            dialog = Dialog(mContext, R.style.AlertDialogStyle)
            dialog!!.setContentView(view)
            dialog!!.window!!.setGravity(Gravity.CENTER)
            dialog!!.setCanceledOnTouchOutside(false)
            lLayout_rt_bg.layoutParams = FrameLayout.LayoutParams(
                (display.width * 0.65).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        return this
    }

    fun show() {
        if (dialog != null && !dialog!!.isShowing && !(mContext as Activity).isFinishing) {
            dialog!!.show()
        }
    }

    val isShow: Boolean
        get() = if (dialog == null) {
            false
        } else dialog!!.isShowing

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            if (countDownTimer != null) {
                countDownTimer!!.cancel()
            }
            dialog!!.dismiss()
            dialog = null
        }
    }
}