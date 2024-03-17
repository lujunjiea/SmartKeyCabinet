package com.example.smartkeycabinet.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.callback.DialogDismissListener
import com.example.smartkeycabinet.databinding.FragmentGetCarCodeBinding
import com.example.smartkeycabinet.dialog.AlertDialog
import com.example.smartkeycabinet.keyBoxUtil.OpenBoxCallBack
import com.example.smartkeycabinet.keyBoxUtil.OperateBoxUtil
import com.example.smartkeycabinet.keyBoxUtil.UpdateBoxStatusUtil
import com.example.smartkeycabinet.model.SaveRecordModel
import com.example.smartkeycabinet.net.BaseObserver
import com.example.smartkeycabinet.net.BaseResponse
import com.example.smartkeycabinet.net.HttpRequest
import com.example.smartkeycabinet.util.KeyboardUtil
import com.example.smartkeycabinet.util.MonitorDoorStatusUtil
import com.example.smartkeycabinet.util.ProgressDialogUtils
import com.example.smartkeycabinet.util.ToastUtil
import com.tti.coffeeslaver.base.BaseFragment


class GetCarCodeFragment : BaseFragment<FragmentGetCarCodeBinding>() {
    var mView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentGetCarCodeBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        viewBinding.tvEnter.setOnClickListener {
            val getCarCode = viewBinding.etGetCarCode.text.toString()
            checkGetCardCode(getCarCode)
        }

        //返回
        viewBinding.tvBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }

    //校验取车码
    fun checkGetCardCode(getCarCode: String) {
        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_check_get_car_code))
        HttpRequest.checkCarCode(getCarCode,object :BaseObserver<String>() {
            override fun onSuccess(baseResponse: BaseResponse<String>?) {
                ProgressDialogUtils.hideProgressDialog()
                if (baseResponse != null) {
                    if (baseResponse.isSuccess) {
                        if (!TextUtils.isEmpty(baseResponse.data)) {
                            val boxNo: Int = baseResponse.data.toInt()
                            openBox(boxNo)
                        } else {
                            ToastUtil.showToast("数据异常")
                        }

                    } else {
                        ToastUtil.showToast(baseResponse.message+"")
                    }
                } else {
                    ToastUtil.showToast("数据异常，请联系运维人员")
                }

            }

            override fun onFailure(e: Throwable?) {
                super.onFailure(e)
                ProgressDialogUtils.hideProgressDialog()
                ToastUtil.showToast(getString(R.string.str_http_error))
            }
        })
    }

    //打开箱子
    fun openBox(boxNo: Int) {
        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_opening_box))
        OperateBoxUtil.openBox(boxNo, object : OpenBoxCallBack{
            override fun onSuccess() {
                //开门成功
                result(true, boxNo)
                UpdateBoxStatusUtil.uploadOperatorRecord(SaveRecordModel("取车码", boxNo, viewBinding.etGetCarCode.text.toString()))
            }

            override fun onFailed() {
                //开门失败
                result(false, boxNo)
            }
        })
    }

    fun result(isSuccess: Boolean, boxNo: Int) {
        activity!!.runOnUiThread {
            ProgressDialogUtils.hideProgressDialog()
            AlertDialog(activity!!).openBoxStatusBuild(isSuccess, object :DialogDismissListener{
                override fun onDismissListener() {
                    Log.e("","dialog消失-----返回首页")
                    Navigation.findNavController(mView!!).popBackStack(R.id.mainFragment, false)
                }
            },1).show()
        }
        var boxStatus = UpdateBoxStatusUtil.BOX_STATUS_IDLE
        if (!isSuccess) {
            boxStatus = UpdateBoxStatusUtil.BOX_STATUS_FAILED
        }
        MonitorDoorStatusUtil.addBoxNo(boxNo)//将箱格加入监听列表
        UpdateBoxStatusUtil.updateBoxStatus(boxStatus, boxNo,"")
    }

    override fun onPause() {
        super.onPause()
        KeyboardUtil(activity!!,null).hideSystemboard(viewBinding.etGetCarCode)
    }
}