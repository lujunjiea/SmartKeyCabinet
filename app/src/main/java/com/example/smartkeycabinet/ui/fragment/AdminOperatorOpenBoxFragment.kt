package com.example.smartkeycabinet.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.adapter.GridViewAdapter
import com.example.smartkeycabinet.databinding.FragmentOperatorOpenBoxBinding
import com.example.smartkeycabinet.keyBoxUtil.OpenBoxCallBack
import com.example.smartkeycabinet.keyBoxUtil.OperateBoxUtil
import com.example.smartkeycabinet.keyBoxUtil.UpdateBoxStatusUtil
import com.example.smartkeycabinet.model.BoxsModel
import com.example.smartkeycabinet.model.SaveRecordModel
import com.example.smartkeycabinet.net.BaseObserver
import com.example.smartkeycabinet.net.BaseResponse
import com.example.smartkeycabinet.net.HttpRequest
import com.example.smartkeycabinet.util.ProgressDialogUtils
import com.example.smartkeycabinet.util.ToastUtil
import com.tti.coffeeslaver.base.BaseFragment
import org.json.JSONObject


class AdminOperatorOpenBoxFragment : BaseFragment<FragmentOperatorOpenBoxBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentOperatorOpenBoxBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //返回
        viewBinding.tvBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
        initData()
    }

    private fun initView(list: MutableList<BoxsModel>) {
        viewBinding.gvList.numColumns = 5
        viewBinding.gvList.verticalSpacing = 10 //单个网格上下间距
        viewBinding.gvList.horizontalSpacing = 10 //单个网格左右间距
        val adapter: GridViewAdapter
        adapter = GridViewAdapter(list, activity!!)
        viewBinding.gvList.adapter = adapter

        viewBinding.gvList.setOnItemClickListener { adapterView, view, i, l ->
            openBox(list[i].id)
        }
    }

    //打开箱子
    fun openBox(boxNo: Int) {
        Log.e("", "打开箱门：$boxNo")
        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_opening_box))
        OperateBoxUtil.openBox(boxNo, object : OpenBoxCallBack{
            override fun onSuccess() {
                ProgressDialogUtils.hideProgressDialog()
                uploadOperatorRecord(boxNo)
                tip(true)
            }

            override fun onFailed() {
                ProgressDialogUtils.hideProgressDialog()
                tip(false)
            }

        })
    }
    fun tip(openIsSuccess: Boolean) {
        activity!!.runOnUiThread {
            if (openIsSuccess) {
                ToastUtil.showToast("开门成功")
            } else {
                ToastUtil.showToast("开门失败")
            }
        }
    }

    //上传操作记录
    private fun uploadOperatorRecord(boxNo: Int) {
        UpdateBoxStatusUtil.uploadOperatorRecord(SaveRecordModel("管理员操作", boxNo, ""))
//        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_admin_upload_record))
//        HttpRequest.saveOperatorRecord(SaveRecordModel("管理员操作", boxNo, ""), object : BaseObserver<JSONObject>(){
//            override fun onSuccess(baseResponse: BaseResponse<JSONObject>?) {
//                ProgressDialogUtils.hideProgressDialog()
//                if (baseResponse != null && baseResponse.isSuccess) {
//                    ToastUtil.showToast("操作记录上传成功")
//                } else {
//                    ToastUtil.showToast("数据异常")
//                }
//            }
//
//            override fun onFailure(e: Throwable?) {
//                super.onFailure(e)
//                ProgressDialogUtils.hideProgressDialog()
//                ToastUtil.showToast(getString(R.string.str_http_error))
//            }
//        })
    }

    private fun initData() {
        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_admin_get_box_list))
        HttpRequest.getBoxList(object : BaseObserver<MutableList<BoxsModel>>() {
            override fun onSuccess(baseResponse: BaseResponse<MutableList<BoxsModel>>?) {
                ProgressDialogUtils.hideProgressDialog()
                if (baseResponse != null && baseResponse.data.isNotEmpty()) {
                    Log.e("", "柜子列表数量：" + baseResponse!!.data.size)
                    initView(baseResponse.data)
                } else {
                    ToastUtil.showToast("数据异常")
                }
            }

            override fun onFailure(e: Throwable?) {
                ProgressDialogUtils.hideProgressDialog()
                ToastUtil.showToast(getString(R.string.str_http_error))
            }
        })
    }
}