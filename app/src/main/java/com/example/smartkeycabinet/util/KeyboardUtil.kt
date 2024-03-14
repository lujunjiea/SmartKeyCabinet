package com.example.smartkeycabinet.util

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.example.smartkeycabinet.R

class KeyboardUtil(private var activity: FragmentActivity, val mKeyboardView: KeyboardView?) {

//    private val provinceKeyboard: Keyboard
    private var abcKeyboard: Keyboard
    private var editText: EditText? = null

    init {
        // 省份键盘
//        provinceKeyboard = Keyboard(activity, R.xml.province)
        // abc键盘
        abcKeyboard = Keyboard(activity, R.xml.abc)

        mKeyboardView?.apply {
//            keyboard = provinceKeyboard
            keyboard = abcKeyboard
            isEnabled = true
            // 设置按键没有点击放大镜显示的效果
            isPreviewEnabled = false
            setOnKeyboardActionListener(object : KeyboardView.OnKeyboardActionListener {
                override fun onPress(primaryCode: Int) {
                }

                override fun onRelease(primaryCode: Int) {
                }

                override fun onText(text: CharSequence?) {
                }

                override fun swipeLeft() {
                }

                override fun swipeRight() {
                }

                override fun swipeDown() {
                }

                override fun swipeUp() {
                }

                override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
                    val editable = editText?.text
                    val startIndex: Int = editText?.selectionStart ?: 0
                    when (primaryCode) {
                        -1 -> changeKeyboard(true)
                        -2 -> changeKeyboard(false)
                        -3 -> {
                            if (startIndex > 0) {
                                editable?.delete(startIndex-1, startIndex)
                            }
                        }
                        else -> {
                            // 清空之前数据
//                            editText?.text?.clear()
                            editable?.append(primaryCode.toChar().toString())
                        }
                    }
                }

            })
        }
    }

    fun setEditText(editText: EditText) {
        this.editText = editText
    }

    /**
     * 指定切换软键盘
     * isNumber false 省份软键盘， true 数字字母软键盘
     */
    fun changeKeyboard(isNumber: Boolean) {
        if (isNumber) {
            mKeyboardView?.keyboard = abcKeyboard
        } else {
//            mKeyboardView?.keyboard = provinceKeyboard
        }
    }

    /**
     * 软键盘展示状态
     */
    fun isShow() = mKeyboardView?.visibility == View.VISIBLE

    /**
     * 显示软键盘
     */
    fun showKeyboard() {
        val visibility = mKeyboardView?.visibility
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView?.visibility = View.VISIBLE
        }
    }

    /**
     * 隐藏软键盘
     */
    fun hideKeyboard() {
        val visibility = mKeyboardView?.visibility
        if (visibility == View.VISIBLE) {
            mKeyboardView?.visibility = View.INVISIBLE
        }
    }

    /**
     * 禁掉系统软键盘
     */
    fun hideSoftInputMethod(editText: EditText) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        editText.inputType = InputType.TYPE_NULL
    }

    @SuppressLint("NewApi")
    fun hideSystemboard(view: View) {
        if (view.requestFocus()) {
//            val imm = activity!!.getSystemService(InputMethodManager::class.java)
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @SuppressLint("NewApi")
    fun showSystemKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}