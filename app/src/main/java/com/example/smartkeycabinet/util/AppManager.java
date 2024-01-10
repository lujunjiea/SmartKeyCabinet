package com.example.smartkeycabinet.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.smartkeycabinet.ui.MainActivity;
import com.example.smartkeycabinet.ui.StartActivity;

public class AppManager {
    private static AppManager mInstance;

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (mInstance == null) {
            synchronized (AppManager.class) {
                mInstance = new AppManager();
            }
        }
        return mInstance;
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            //杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    /**
     * 重启应用程序
     */
    public static void reApp(Context context) {
        try {
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());

        } catch (Exception e) {
            Log.e("","reApp :" + e);
        } finally {
            //重启程序
            startHomeActivity(context);
            //杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 启动入口Activity
     */
    public static void startHomeActivity(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
