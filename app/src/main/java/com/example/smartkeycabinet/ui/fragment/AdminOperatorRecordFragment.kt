package com.example.smartkeycabinet.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.adapter.OperatorRecordRvAdapter
import com.example.smartkeycabinet.databinding.FragmentOperatorRecordBinding
import com.example.smartkeycabinet.model.GetOperatorRecordBodyModel
import com.example.smartkeycabinet.model.OperatorRecordBean
import com.example.smartkeycabinet.model.OperatorRecordModel
import com.example.smartkeycabinet.net.BaseObserver
import com.example.smartkeycabinet.net.BaseResponse
import com.example.smartkeycabinet.net.HttpRequest
import com.example.smartkeycabinet.util.ProgressDialogUtils
import com.example.smartkeycabinet.util.ToastUtil
import com.tti.coffeeslaver.base.BaseFragment
import java.util.Calendar


class AdminOperatorRecordFragment : BaseFragment<FragmentOperatorRecordBinding>(), SwipeRefreshLayout.OnRefreshListener{
    private val mAdapter by lazy {
        OperatorRecordRvAdapter().apply {
        }
    }
    var mView: View? = null
    var startTime = ""
    var endTime = ""
    var page = 1
    var pageSize = 10
    var mLinearLayoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentOperatorRecordBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        mLinearLayoutManager = LinearLayoutManager(activity)
        viewBinding.recyclerview.layoutManager = mLinearLayoutManager
        viewBinding.recyclerview.adapter = mAdapter
        setLoadMoreAdapter(mAdapter)

        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                resetData()
                viewBinding.swipeRefreshLayout.isRefreshing = false
                initData()
            }, 1000)
        }
    }

    private fun initListener() {
        viewBinding.btStartTime.setOnClickListener {
            showDateDialog(1)
        }
        viewBinding.btEndTime.setOnClickListener {
            showDateDialog(2)
        }
        viewBinding.tvSearch.setOnClickListener {
            resetData()
            initData()
        }
        //返回
        viewBinding.tvBack.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

    private fun initData() {
        var searchName = viewBinding.etOperator.text.toString()
        //开始结束时间，必须拼上时分秒，不然接口会报错
        if (startTime.isNotEmpty()){
            startTime = "$startTime 00:00:00"
        }
        if (endTime.isNotEmpty()) {
            endTime = "$endTime 23:59:59"
        }
        val operatorRecordBodyModel = GetOperatorRecordBodyModel(page, pageSize, searchName, startTime, endTime)
        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_admin_get_operator_record))
        HttpRequest.getOperatorRecordList(operatorRecordBodyModel, object : BaseObserver<OperatorRecordBean>() {
            override fun onSuccess(baseResponse: BaseResponse<OperatorRecordBean>?) {
                ProgressDialogUtils.hideProgressDialog()
                Log.e("------","接口返回列表数量："+ baseResponse!!.data.list.size)
                if (baseResponse != null && baseResponse.data != null && baseResponse.data.list.isNotEmpty()) {
                    loadMoreResult(baseResponse.data.list, mAdapter)
                }
            }

            override fun onFailure(e: Throwable?) {
                super.onFailure(e)
                ProgressDialogUtils.hideProgressDialog()
                ToastUtil.showToast(getString(R.string.str_http_error))
            }
        })
    }

    private fun resetData() {
        page = 1
        pageSize = 10
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
    }

    //加载更多适配
    private fun <T> setLoadMoreAdapter(mAdapter: BaseQuickAdapter<T, BaseViewHolder>) {
//        //设置加载更多
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            Handler().postDelayed({
                page += 1
                initData()
            }, 500)
        }

        //设置是否自动加载更多
        mAdapter.loadMoreModule.isAutoLoadMore = false
//        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
    }


    private fun <T> loadMoreResult(data: List<T>, mAdapter: BaseQuickAdapter<T, BaseViewHolder>) {
        if (data.isEmpty() && page == 0) {

        } else {
            if (page == 0) {
                //下拉刷新使用
                mAdapter.setList(data)
            } else {
                //上拉加载更多使用
                mAdapter.addData(data)
            }
            if (data.size < pageSize) {
                //如果s少于20,显示没有更多数据布局
                val isLoadEndMoreGone = (page == 0)
                mAdapter.loadMoreModule.loadMoreEnd(isLoadEndMoreGone)
            } else {
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        }
    }
    //下拉刷新
    override fun onRefresh() {
        resetData()
        viewBinding.swipeRefreshLayout.isRefreshing = false
        initData()
    }

    /**
     * type: 1开始时间 2结束时间
     */
    @SuppressLint("SetTextI18n")
    fun showDateDialog(type: Int) {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            activity!!,
            object : OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                    Log.e("-----","年$year 月$monthOfYear 日$dayOfMonth")
                    // 处理选择的日期（例如：更新UI显示）
                    if (type == 1) {
                        startTime = "$year-${monthOfYear+1}-$dayOfMonth"
                        viewBinding.btStartTime.text = "开始时间: $startTime"
                    } else {
                        endTime = "$year-${monthOfYear+1}-$dayOfMonth"
                        viewBinding.btEndTime.text = "结束时间: $endTime"
                    }
                }
            },
            year,
            month,
            day
        )

        //点击取消按钮，置空时间
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "取消", object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (type == 1) {
                    startTime = ""
                    viewBinding.btStartTime.text = "开始时间: --"
                } else {
                    endTime = ""
                    viewBinding.btEndTime.text = "结束时间: --"
                }
            }
        })
        datePickerDialog.show()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}
