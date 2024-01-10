package com.example.smartkeycabinet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.databinding.FragmentGetCarBinding
import com.example.smartkeycabinet.util.ToastUtil
import com.tti.coffeeslaver.base.BaseFragment


class GetCarTypeFragment : BaseFragment<FragmentGetCarBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentGetCarBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //人脸识别，二维码，功能未开启
        viewBinding.tvCheckFace.setOnClickListener {
            ToastUtil.showToast(getString(R.string.str_feature_not_available))
        }
        viewBinding.tvQrcode.setOnClickListener {
            ToastUtil.showToast(getString(R.string.str_feature_not_available))
        }

        //取车码取车
        viewBinding.tvGetCarCode.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_getCarFragment_to_getCarCodeFragment)
        }

        //返回
        viewBinding.tvBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
    }
}