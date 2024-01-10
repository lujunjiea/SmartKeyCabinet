package com.example.smartkeycabinet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.databinding.FragmentAdminHandlerBinding
import com.example.smartkeycabinet.event.ShowKeyEvent
import com.example.smartkeycabinet.ui.MainActivity
import com.example.smartkeycabinet.util.AppManager
import com.example.smartkeycabinet.util.EventBusUtil
import com.example.smartkeycabinet.util.ToastUtil
import com.tti.coffeeslaver.base.BaseFragment

class AdminHandlerFragment: BaseFragment<FragmentAdminHandlerBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAdminHandlerBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.tvAdminOperatorRecord.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_adminHandlerFragment_to_operatorRecordFragment)
        }
        viewBinding.tvAdminOpenBox.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_adminHandlerFragment_to_operatorOpenBoxFragment)
        }
        //返回
        viewBinding.tvBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        viewBinding.tvShow.setOnClickListener {
            EventBusUtil.post(ShowKeyEvent())
        }

        viewBinding.tvExit.setOnClickListener {
            AppManager.getInstance().AppExit(activity)
        }
    }
}