package com.example.smartkeycabinet.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.smartkeycabinet.R
import com.example.smartkeycabinet.databinding.FragmentMainBinding
import com.example.smartkeycabinet.keyBoxUtil.FailedType
import com.example.smartkeycabinet.keyBoxUtil.KeyBoxCallBack
import com.example.smartkeycabinet.keyBoxUtil.SerialPortUtil
import com.example.smartkeycabinet.util.ToastUtil
import com.tti.coffeeslaver.base.BaseFragment
import org.json.JSONObject


class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.rlGetCar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_getCarFragment)
        }
        viewBinding.rlReturnCar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_returnCarFragment)
        }
        viewBinding.rlAdmin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_adminLoginFragment)
        }
    }
}