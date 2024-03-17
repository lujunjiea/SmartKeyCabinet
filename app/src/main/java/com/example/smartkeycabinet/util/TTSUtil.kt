package com.example.smartkeycabinet.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.SpeechSynthesizer
import com.iflytek.cloud.SpeechUtility
import com.iflytek.cloud.SynthesizerListener
import com.iflytek.cloud.util.ResourceUtil


@SuppressLint("StaticFieldLeak")
object TTSUtil {
    //语音合成操作对象
    var mTts: SpeechSynthesizer? = null

    //默认发音人
    val voicerLocal = "xiaofeng"

    //引擎类型
    val mEngineType = SpeechConstant.TYPE_LOCAL
    private var mContext: Context? = null

    fun initTTS(context: Context) {
        this.mContext = context
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=f0ad3a13") //初始化讯飞语音
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener)
    }

    /**
     * 播放语音
     * @param text: 转为语音文字
     * @param callBack: 根据情况是否回调出去，方便后续拓展
     */
    fun startSpeak(text: String, callBack: TtsCallBack? = null) {
        setParam()
        val code = mTts?.startSpeaking(text, mTtsListener)
        if (code == ErrorCode.SUCCESS) {
            Log.d("", "播放成功")
        } else {
            Log.d("", "播放失败: $code")
        }
    }

    /**
     * 停止播放
     */
    fun stopSpeak() {
        if (mTts != null) {
            mTts?.stopSpeaking()
            mTts?.destroy()
        }
    }

    /**
     * 初始化监听
     */
    private val mTtsInitListener = InitListener { code ->
        Log.d("", "InitListener init() code = $code");
        if (code != ErrorCode.SUCCESS) {
            ToastUtil.showToast("语音服务初始化失败，错误码: $code")
        } else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
            ToastUtil.showToast("语音服务初始化成功")
        }
    }


    /**
     * 合成回调监听。
     */
    private val mTtsListener = object : SynthesizerListener {
        override fun onSpeakBegin() {
            Log.d("", "开始播放")
        }

        override fun onBufferProgress(p0: Int, p1: Int, p2: Int, p3: String?) {
//            Log.d("", "合成进度:$p0")
        }

        override fun onSpeakPaused() {
            Log.d("", "暂停播放")
        }

        override fun onSpeakResumed() {
            Log.d("", "继续播放")
        }

        override fun onSpeakProgress(p0: Int, p1: Int, p2: Int) {
//            Log.d("", "播放进度:$p0")
        }

        override fun onCompleted(p0: SpeechError?) {
            if (p0 == null) {
                Log.d("", "播放完成")
            } else {
                Log.d("", "播放失败 ${p0.getPlainDescription(true)}")
            }

        }

        override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {
            Log.e("","播放失败：$p0")
        }

    }

    fun setParam() {
        // 清空参数
        mTts?.setParameter(SpeechConstant.PARAMS, null);
        //设置使用本地引擎
        mTts?.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        //设置发音人资源路径
        mTts?.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
        //设置发音人
        mTts?.setParameter(SpeechConstant.VOICE_NAME, voicerLocal);

        //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY,"1");//支持实时音频流抛出，仅在synthesizeToUri条件下支持
        //设置合成语速
        mTts?.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts?.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts?.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts?.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //	mTts.setParameter(SpeechConstant.STREAM_TYPE, AudioManager.STREAM_MUSIC+"");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts?.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts?.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts?.setParameter(
            SpeechConstant.TTS_AUDIO_PATH,
            mContext?.getExternalFilesDir("msc")?.absolutePath + "/tts.pcm"
        );
    }

    //获取发音人资源路径
    private fun getResourcePath(): String {
        val tempBuffer = StringBuffer()
        val type = "tts"
        //合成通用资源
        tempBuffer.append(
            ResourceUtil.generateResourcePath(
                mContext, ResourceUtil.RESOURCE_TYPE.assets,
                "tts/common.jet"
            )
        )
        tempBuffer.append(";")
        //发音人资源
        tempBuffer.append(
            ResourceUtil.generateResourcePath(
                mContext, ResourceUtil.RESOURCE_TYPE.assets,
                "tts/xiaofeng.jet"
            )
        )
        return tempBuffer.toString()
    }
}

@FunctionalInterface
interface TtsCallBack {
    fun onSuccess(code: Int)
}

