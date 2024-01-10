package com.example.smartkeycabinet.ui.fragment


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.adapter.SpinnerAdapter
import com.example.smartkeycabinet.callback.DialogDismissListener
import com.example.smartkeycabinet.callback.DialogItemClickListener
import com.example.smartkeycabinet.databinding.FragmentReturnCarBinding
import com.example.smartkeycabinet.dialog.AlertDialog
import com.example.smartkeycabinet.keyBoxUtil.OpenBoxCallBack
import com.example.smartkeycabinet.keyBoxUtil.OperateBoxUtil
import com.example.smartkeycabinet.keyBoxUtil.UpdateBoxStatusUtil
import com.example.smartkeycabinet.model.CarListBean
import com.example.smartkeycabinet.model.CarListBean.ListBean
import com.example.smartkeycabinet.model.GetCarListBodyModel
import com.example.smartkeycabinet.model.ReturnCarBody
import com.example.smartkeycabinet.model.ReturnCarModel
import com.example.smartkeycabinet.net.BaseObserver
import com.example.smartkeycabinet.net.BaseResponse
import com.example.smartkeycabinet.net.HttpRequest
import com.example.smartkeycabinet.util.KeyboardUtil
import com.example.smartkeycabinet.util.ProgressDialogUtils
import com.example.smartkeycabinet.util.ToastUtil
import com.tti.coffeeslaver.base.BaseFragment


class ReturnCarFragment : BaseFragment<FragmentReturnCarBinding>() {
    var carList = mutableListOf<ListBean>()
    var keyboardUtil : KeyboardUtil? = null
    var body: ReturnCarBody = ReturnCarBody("","","","", "0")
    var mView: View? = null
    var retryCount = 0 //重新获取空箱格次数
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentReturnCarBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        getCarPosition() //获取车位列表
        initEditTextFocusListener()
        initListener()
        initKeyView()
    }

    private fun initKeyView() {
        keyboardUtil = KeyboardUtil(activity!!, viewBinding.keyboardView)
        keyboardUtil!!.hideSoftInputMethod(viewBinding.etCarNo)
        keyboardUtil!!.hideKeyboard()
        keyboardUtil!!.setEditText(viewBinding.etCarNo)
        keyboardUtil!!.changeKeyboard(false)
//        viewBinding.etCarNo.requestFocus()
    }

    private fun initListener() {
        viewBinding.tvSelectCarPosition.setOnClickListener {
            //隐藏车牌号键盘
            keyboardUtil!!.hideKeyboard()
            if (carList.isEmpty()) {
                ToastUtil.showToast("数据异常，车位列表为空，请联系管理员")
                return@setOnClickListener
            }
            //弹出dialog
            AlertDialog(activity!!).carPositionListDialog(carList,activity!!, object :DialogItemClickListener{
                @SuppressLint("SetTextI18n")
                override fun onItemListener(position: Int) {
                    body.parkingArea = carList[position].parkingArea
                    body.parkingNo = carList[position].parkingNo
                    viewBinding.tvSelectCarPosition.text = body.parkingArea+body.parkingNo
                }
            }).show()
        }

        viewBinding.tvEnter.setOnClickListener {
            Log.e("","点击还车")
            returnCar("0")
        }

        viewBinding.tvBack.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

    fun returnCar(type: String) {
        body.plateNumber = viewBinding.etCarNo.text.toString()
        body.kilometers = viewBinding.etReturnCarKil.text.toString()
        body.type = type //0更新server信息  1不更新，只是查找空箱格
        if (body.plateNumber.isEmpty() || body.kilometers.isEmpty() || body.parkingNo.isEmpty()) {
            ToastUtil.showToast("请完善信息!")
            return
        }
        if (type.equals("0")) {
            ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_returning_car))
        } else {
            ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_returning_car_retry_get_box))
        }

        HttpRequest.returnCar(body, object : BaseObserver<ReturnCarModel>() {
            override fun onSuccess(baseResponse: BaseResponse<ReturnCarModel>?) {
                ProgressDialogUtils.hideProgressDialog()
                if (baseResponse != null) {
                    if (baseResponse.isSuccess && baseResponse.data != null) {
                        openBox(baseResponse.data.id)
                    } else {
                        ToastUtil.showToast("还车失败：${baseResponse.message}")
                    }
                } else {
                    ToastUtil.showToast("数据异常, 请联系运维人员!")
                }
            }

            override fun onFailure(e: Throwable?) {
                super.onFailure(e)
                ProgressDialogUtils.hideProgressDialog()
                ToastUtil.showToast(getString(R.string.str_http_error))
            }
        })
    }

    /**
     * 还车开箱
     * 开箱如果失败，重新获取一个箱格， 最多获取3次， 失败时报错
     */
    fun openBox(boxNo: Int) {
        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_opening_box))
        OperateBoxUtil.openBox(boxNo, object : OpenBoxCallBack{
            override fun onSuccess() {
                result(true, boxNo)
            }

            override fun onFailed() {
                if (retryCount >= 3) {
                    //已重新获取3次，报错
                    result(false, boxNo)
                } else {
                    //重新获取箱格
                    ProgressDialogUtils.hideProgressDialog()
                    returnCar("1")
                }
            }
        })
    }

    fun result(isSuccess: Boolean, boxNo: Int) {
        activity!!.runOnUiThread {
            ProgressDialogUtils.hideProgressDialog()
            AlertDialog(activity!!).openBoxStatusBuild(isSuccess, object : DialogDismissListener {
                override fun onDismissListener() {
                    if (isSuccess) {
                        Log.e("","dialog消失-----返回首页")
                        Navigation.findNavController(mView!!).popBackStack(R.id.mainFragment, false)
                    }
                }
            },2).show()
        }
        var boxStatus = UpdateBoxStatusUtil.BOX_STATUS_BUSY
        if (!isSuccess) {
            boxStatus = UpdateBoxStatusUtil.BOX_STATUS_FAILED
        }
        UpdateBoxStatusUtil.updateBoxStatus(boxStatus, boxNo, body.plateNumber)
    }

    /**
     * 各种焦点监听，以及软键盘的显示隐藏
     * 该方法主要处理车牌自定义键盘与公里数系统软键盘之间的显示隐藏方面的冲突
     */
    private fun initEditTextFocusListener() {
        //当点击车牌号输入框时，弹出自定义车牌键盘
        viewBinding.etCarNo.setOnClickListener {
            keyboardUtil!!.showKeyboard()
        }

        //当车牌号输入框获得焦点时，弹出车牌自定义键盘， 失去焦点时，隐藏车牌自定义键盘
        viewBinding.etCarNo.setOnFocusChangeListener { view, b ->
            if (b) {
                Log.e("","获得焦点")
                keyboardUtil!!.showKeyboard()
            } else {
                Log.e("","失去焦点")
                keyboardUtil!!.hideKeyboard()
            }
        }

        //当点击整体背景时， 隐藏车牌自定义键盘
        viewBinding.rlBg.setOnClickListener { keyboardUtil!!.hideKeyboard() }

        //当公里数输入框获得焦点时，弹出系统软键盘， 失去焦点时，隐藏系统软键盘
        viewBinding.etReturnCarKil.setOnFocusChangeListener { view, b ->
            if (!b) {
                Log.e("","公里数输入框失去焦点")
                keyboardUtil!!.hideSystemboard(viewBinding.etCarNo)
            } else {
                keyboardUtil!!.showSystemKeyboard(viewBinding.etReturnCarKil)
            }
        }
    }

    /**
     * 获取停车位列表
     */
    private fun getCarPosition() {
        var body = GetCarListBodyModel("","","","","",1, 200)
        HttpRequest.getCarPositionList(body, object: BaseObserver<CarListBean>() {
            override fun onSuccess(baseResponse: BaseResponse<CarListBean>?) {
                if (baseResponse != null && baseResponse.isSuccess && baseResponse.data.list.isNotEmpty()) {
                    carList = baseResponse.data.list
                } else {
                    ToastUtil.showToast("数据异常!")
                }
            }

            override fun onFailure(e: Throwable?) {
                super.onFailure(e)
                ToastUtil.showToast("车位列表获取失败!")
            }
        })
    }

}