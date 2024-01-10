package com.example.smartkeycabinet.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.smartkeycabinet.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomTimeView extends LinearLayout {
    private TextView dateTV,weekTV, timeTV;

    private String currentDate;//年月日
    private String currentTime;//时分秒
    private String currentDay;//星期几
    public CustomTimeView(Context context) {
        super(context);
    }

    public void updateTime(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormat.format(now);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        currentTime = timeFormat.format(now);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        currentDay = dayFormat.format(now);

        dateTV.setText(currentDate);
        weekTV.setText(currentDay);
        timeTV.setText(currentTime);
    }

    public void startClockUpdater() {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                updateTime();
                sendEmptyMessageDelayed(0, 10_000); // 每隔10秒更新一次
            }
        };
        handler.sendEmptyMessage(0);
    }

    public CustomTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_clock_view_layout, this, true);
        dateTV = findViewById(R.id.tv_date);
        weekTV = findViewById(R.id.tv_week);
        timeTV = findViewById(R.id.tv_time);
        startClockUpdater();
    }

}
