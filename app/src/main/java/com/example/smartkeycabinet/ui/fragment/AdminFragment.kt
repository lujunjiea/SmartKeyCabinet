package com.example.smartkeycabinet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.smartkeycabinet.BuildConfig
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.databinding.FragmentAdminBinding
import com.example.smartkeycabinet.net.BaseObserver
import com.example.smartkeycabinet.net.BaseResponse
import com.example.smartkeycabinet.net.HttpRequest
import com.example.smartkeycabinet.util.KeyboardUtil
import com.example.smartkeycabinet.util.ProgressDialogUtils
import com.example.smartkeycabinet.util.ToastUtil
import com.example.smartkeycabinet.util.Utils
import com.tti.coffeeslaver.base.BaseFragment


class AdminFragment : BaseFragment<FragmentAdminBinding>() {
    var mView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAdminBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        viewBinding.tvEnter.setOnClickListener {
            //点击确定后隐藏系统软键盘
            KeyboardUtil(activity!!,null).hideSystemboard(viewBinding.etAdminPassword)
            val pw = viewBinding.etAdminPassword.text.toString()
            checkPassword(pw)
        }

        //返回
        viewBinding.tvBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        viewBinding.tvVersion.text = BuildConfig.VERSION_NAME
    }

    //校验密码
    fun checkPassword(pw: String) {
        ProgressDialogUtils.showProgressDialog(activity, getString(R.string.str_admin_check_password))
        HttpRequest.checkAdminPw(pw,object : BaseObserver<String>() {
            override fun onSuccess(baseResponse: BaseResponse<String>?) {
                ProgressDialogUtils.hideProgressDialog()
                if (baseResponse!!.isSuccess) {
                    Navigation.findNavController(mView!!).navigate(R.id.action_adminLoginFragment_to_adminHandlerFragment)
                } else {
                    ToastUtil.showToast("密码错误！")
                }
            }

            override fun onFailure(e: Throwable?) {
                super.onFailure(e)
                ProgressDialogUtils.hideProgressDialog()
                ToastUtil.showToast(getString(R.string.str_http_error))
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewBinding.etAdminPassword.setText("")
    }

    override fun onPause() {
        super.onPause()
        KeyboardUtil(activity!!,null).hideSystemboard(viewBinding.etAdminPassword)
    }

}