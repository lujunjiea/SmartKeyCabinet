package com.example.smartkeycabinet.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.huidu.toolkit.HuiduTech;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartkeycabinet.R;
import com.example.smartkeycabinet.event.ShowKeyEvent;
import com.example.smartkeycabinet.keyBoxUtil.SerialPortUtil;
import com.example.smartkeycabinet.net.BaseObserver;
import com.example.smartkeycabinet.net.BaseResponse;
import com.example.smartkeycabinet.net.HttpRequest;
import com.example.smartkeycabinet.util.EventBusUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /**
     * 迈冲 7.0 rk3288 型号 隐藏状态栏和导航栏方法
     */
    Intent systemBarIntent = new Intent("com.tchip.changeBarHideStatus");
    Intent statusBarIntent = new Intent("com.tchip.changeStatusBarHideStatus");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusUtil.register(this);
        initPort();
        hindVirtualKey(true, true);
    }

    private void initPort() {
        SerialPortUtil.INSTANCE.openPort("/dev/ttyS4",19200);
    }

    //显示虚拟按键广播
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShowKeyEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hindVirtualKey(false,false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    /**
     * 是否隐藏虚拟按键
     *
     * @param enable
     */
    public void hindVirtualKey(boolean enable, boolean isHideStatusBar) {
        String phoneModel = android.os.Build.MODEL;
        if (phoneModel.equals("rk3288") || phoneModel.equals("rk3566_r")){
            HuiduTech helper = new HuiduTech(this);
            helper.showNavigationBar(true);
            helper.showStatusBar(true);
            Log.e("20221031","DeviceModel"+ helper.getDeviceModel());
            return;
        }
        Intent intent = new Intent();
        if (enable) { //隐藏虚拟按键
            execRootCmdSilent("settings put system systembar_hide 1");//1为隐藏导航栏，0显示导航栏
            execRootCmdSilent("settings put system systemstatusbar_hide 1");//1隐藏状态栏，0显示状态栏
            sendBroadcast(systemBarIntent);
            sendBroadcast(statusBarIntent);
            hideNavigation();
            //设置广播发送隐藏虚拟按键命令
            intent.setAction("com.android.intent.action.NAVBAR_SHOW");
            intent.putExtra("cmd", "hide");
            this.sendOrderedBroadcast(intent, null);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else { //显示虚拟按键
            execRootCmdSilent("settings put system systembar_hide 0");//1为隐藏导航栏，0显示导航栏
            execRootCmdSilent("settings put system systemstatusbar_hide 0");//1隐藏状态栏，0显示状态栏
            sendBroadcast(systemBarIntent);
            sendBroadcast(statusBarIntent);
            showNavigation();
            //设置广播发送显示虚拟按键命令
            intent.setAction("com.android.intent.action.NAVBAR_SHOW");
            intent.putExtra("cmd", "show");
            this.sendOrderedBroadcast(intent, null);
        }
        hindStatusBar(isHideStatusBar);
    }

    /**
     * 执行 cmd
     *
     * @param cmd
     * @return
     */
    private static int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 迈冲隐藏虚拟按键
     *
     * @return
     */
    public boolean hideNavigation() {
        boolean ishide;
        try {
            Log.i("Navigation", "迈冲隐藏虚拟键");
            String command;
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib service call activity 42 s16 com.android.systemui";
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
            proc.waitFor();
            ishide = true;
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            ishide = false;
        }
        return ishide;
    }

    /**
     * 迈冲显示虚拟按键
     *
     * @return
     */
    public boolean showNavigation() {
        boolean isshow;
        try {
            Log.i("Navigation", "迈冲隐藏虚拟键");
            String command;
            command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib am startservice -n com.android.systemui/.SystemUIService";
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
            proc.waitFor();
            isshow = true;
        } catch (Exception e) {
            isshow = false;
            e.printStackTrace();
        }
        return isshow;
    }

    /**
     * 是否隐藏状态栏
     *
     * @param enable
     */
    public void hindStatusBar(boolean enable) {
        if (enable) { //隐藏状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
        } else { //显示状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}