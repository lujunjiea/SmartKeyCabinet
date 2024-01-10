package com.example.smartkeycabinet.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartkeycabinet.App;

public class ToastUtil {
    public static void showToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast toast = Toast.makeText(App.instance, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 100); //居中显示
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setGravity(Gravity.CENTER);
        messageTextView.setTextSize(25);//设置toast字体大小
        toast.show();
    }

}
