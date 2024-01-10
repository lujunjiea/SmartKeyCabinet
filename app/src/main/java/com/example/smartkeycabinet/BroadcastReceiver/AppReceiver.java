package com.example.smartkeycabinet.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.smartkeycabinet.util.AppManager;

public class AppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("","[action=" + action + "]");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) { //开机启动广播
            Log.i("","AppReceiver onReceive() [ Received boot start broadcast ]");
            AppManager.getInstance().startHomeActivity(context);
        }
    }
}
