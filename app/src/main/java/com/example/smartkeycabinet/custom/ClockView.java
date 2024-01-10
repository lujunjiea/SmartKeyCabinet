package com.example.smartkeycabinet.custom;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.smartkeycabinet.R;

import java.util.Calendar;

public class ClockView extends View {
    //背景画笔
    private Paint mBackgroundPaint;
    //刻度线
    private Paint mScaleLinePaint;
    private Paint mHourPaint;
    private Paint mMinutePaint;
    private Paint mSecondPaint;
    //半径
    private int mRadius;
    //中心坐标
    private int mCenterX;
    private int mCenterY;
    private Handler mMainHandler;
    private int mClockBgColor;
    private int mBigScaleLineColor;
    private int mSmallScaleLineColor;
    private int mHourHandColor;
    private int mMinuteHandColor;
    private int mSecondHandColor;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {//构造方法
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mBackgroundPaint = new Paint();//实例化画笔
        mScaleLinePaint = new Paint();
        mHourPaint = new Paint();
        mMinutePaint = new Paint();
        mSecondPaint = new Paint();
        mMainHandler = new Handler();
        if (null == context || null == attrs) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        mClockBgColor = typedArray.getColor(R.styleable.ClockView_clock_background_color,
                context.getResources().getColor(R.color.white));
        mBigScaleLineColor = typedArray.getColor(R.styleable.ClockView_big_scale_line_color,
                context.getResources().getColor(R.color.black));
        mSmallScaleLineColor = typedArray.getColor(R.styleable.ClockView_small_scale_line_color,
                context.getResources().getColor(R.color.gray));
        mHourHandColor = typedArray.getColor(R.styleable.ClockView_hour_hand_color,
                context.getResources().getColor(R.color.black));
        mMinuteHandColor = typedArray.getColor(R.styleable.ClockView_minute_hand_color,
                context.getResources().getColor(R.color.black));
        mSecondHandColor = typedArray.getColor(R.styleable.ClockView_second_hand_color,
                context.getResources().getColor(R.color.red));
        typedArray.recycle();
    }

    /**
     * 计算view的宽高,在onDraw()之前
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int mViewWidth = Math.min(measuredWidth, measuredHeight);
        //30为时钟到边界的距离
        mRadius = mViewWidth / 2 - 20;
        //除2计算出中心坐标
        mCenterX = mCenterY = mViewWidth >> 1;
        //设置宽高，将控件设置成正方形
        setMeasuredDimension(mViewWidth, mViewWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {//画图canvas为画布
        super.onDraw(canvas);
        Calendar calendar = Calendar.getInstance();
        //小时
        int hour = calendar.get(Calendar.HOUR);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);
        //毫秒
        int milliSecond = calendar.get(Calendar.MILLISECOND);
        drawBackground(canvas);
        drawScaleLine(canvas);
        //画出时针
        drawHourHand(canvas, hour, minute);
        //画出分针
        drawMinuteHand(canvas, minute, second);
        //画出秒针
        drawSecondHand(canvas, second, milliSecond);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        mMainHandler.postDelayed(runnable, 50);
    }


    /**
     * 画背景
     */
    private void drawBackground(Canvas canvas) {
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mClockBgColor);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mBackgroundPaint);
    }

    /**
     * 画刻度线
     *
     * @param canvas
     */
    private void drawScaleLine(Canvas canvas) {
        //设置大刻度的颜色
        mScaleLinePaint.setColor(mBigScaleLineColor);
        //设置画笔的宽度，大刻度的刻度宽为5
        mScaleLinePaint.setStrokeWidth(5);
        //刻度线距离时钟边的距离为20
        int scaleLineMargin = 10;
        //大刻度线的长度
        int scaleLineLength = 30;
        //画12个大刻度
        for (int i = 0; i < 12; i++) {
            double radians = Math.toRadians(-90 + 30 * i);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            //使用三角函数计算末端的x值
            int stopX = (int) (cos * (mRadius - scaleLineMargin));
            //使用三角函数计算末端的y值
            int stopY = (int) (sin * (mRadius - scaleLineMargin));
            //使用三角函数计算初始端的x值
            int startX = (int) (cos * (mRadius - scaleLineMargin - scaleLineLength));
            //使用三角函数计算初始端的y值
            int startY = (int) (sin * (mRadius - scaleLineMargin - scaleLineLength));
            //画线
            canvas.drawLine(mCenterX + startX, mCenterY + startY,
                    mCenterX + stopX, mCenterY + stopY, mScaleLinePaint);
        }
        //设置小刻度颜色
        mScaleLinePaint.setColor(mSmallScaleLineColor);
        //设置画笔的宽度，小刻度的刻度宽为3
        mScaleLinePaint.setStrokeWidth(3);
        //小刻度线的长度为40
        scaleLineLength = 15;
        //画48个小刻度
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                continue;
            }
            double radians = Math.toRadians(-90 + 6 * i);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            //使用三角函数计算末端的x值
            int stopX = (int) (cos * (mRadius - scaleLineMargin));
            //使用三角函数计算末端的y值
            int stopY = (int) (sin * (mRadius - scaleLineMargin));
            //使用三角函数计算初始端的x值
            int startX = (int) (cos * (mRadius - scaleLineMargin - scaleLineLength));
            //使用三角函数计算初始端的y值
            int startY = (int) (sin * (mRadius - scaleLineMargin - scaleLineLength));
            //画线
            canvas.drawLine(mCenterX + startX, mCenterY + startY,
                    mCenterX + stopX, mCenterY + stopY, mScaleLinePaint);
        }
    }

    /**
     * 画出时针
     */
    private void drawHourHand(Canvas canvas, int hour, int minute) {
        int h = hour % 12;
        //时针颜色
        mHourPaint.setColor(mHourHandColor);
        // 时针长度，取半径的一般
        int hourLength = mRadius / 2;
        int hourWidth = 15;
        //时针宽度24
        mHourPaint.setStrokeWidth(hourWidth);
        double radians = Math.toRadians(-90 + 30 * h + minute * 0.5);
        //使用三角函数计算末端的x值
        int stopX = (int) (Math.cos(radians) * hourLength);
        //使用三角函数计算末端的y值
        int stopY = (int) (Math.sin(radians) * hourLength);

        //防锯齿
        mHourPaint.setAntiAlias(true);
        canvas.drawLine(mCenterX, mCenterY,
                mCenterX + stopX, mCenterY + stopY, mHourPaint);
        canvas.drawCircle(mCenterX, mCenterY, hourWidth >> 1, mHourPaint);
        canvas.drawCircle(stopX + mCenterX, mCenterY + stopY, hourWidth >> 1, mHourPaint);
        //时针空隙长度为70
        //使用三角函数计算初始端的x值
        int startX = (int) (Math.cos(radians) * (hourLength - 50));
        //使用三角函数计算初始端的y值
        int startY = (int) (Math.sin(radians) * (hourLength - 50));

        //时针中间的空隙
        mHourPaint.setStrokeWidth(hourWidth >> 1);
        mHourPaint.setColor(mClockBgColor);
        canvas.drawLine(mCenterX + startX, mCenterY + startY,
                mCenterX + stopX, mCenterY + stopY, mHourPaint);

        canvas.drawCircle(mCenterX + startX, mCenterY + startY, hourWidth >> 2, mHourPaint);
        canvas.drawCircle(mCenterX + stopX, mCenterY + stopY, hourWidth >> 2, mHourPaint);

    }

    /**
     * 画出分针
     */
    private void drawMinuteHand(Canvas canvas, int minute, int second) {
        //颜色问题
        mMinutePaint.setColor(mMinuteHandColor);
        //分针宽度
        int minuteHandWidth = 10;
        mMinutePaint.setStrokeWidth(minuteHandWidth);
        //分针长度为（半径 - 120）
        int minLenght = mRadius - 30;
        double radians = Math.toRadians(-90 + 6 * minute + second * 0.1);
        int stopX = (int) (Math.cos(radians) * minLenght);//使用三角函数计算末端的x值
        int stopY = (int) (Math.sin(radians) * minLenght);//使用三角函数计算末端的y值

        canvas.drawLine(mCenterX, mCenterY,
                mCenterX + stopX, mCenterY + stopY, mMinutePaint);//画线
        canvas.drawCircle(mCenterX, mCenterY, minuteHandWidth >> 1, mMinutePaint);
        canvas.drawCircle(stopX + mCenterX, mCenterY + stopY, minuteHandWidth >> 1, mMinutePaint);


        //分针中间的空隙，长度为 70
        int startX = (int) (Math.cos(radians) * (minLenght - 50));//使用三角函数计算初始端的x值
        int startY = (int) (Math.sin(radians) * (minLenght - 50));//使用三角函数计算初始端的y值

        mMinutePaint.setStrokeWidth(minuteHandWidth >> 1);
        mMinutePaint.setColor(mClockBgColor);
        canvas.drawLine(mCenterX + startX, mCenterY + startY,
                mCenterX + stopX, mCenterY + stopY, mMinutePaint);//画线
        canvas.drawCircle(mCenterX + startX, mCenterY + startY, minuteHandWidth >> 2, mMinutePaint);
        canvas.drawCircle(stopX + mCenterX, mCenterY + stopY, minuteHandWidth >> 2, mMinutePaint);

    }

    /**
     * 画出秒针
     */
    private void drawSecondHand(Canvas canvas, int second, int milliSecond) {
        mSecondPaint.setColor(mSecondHandColor);//设置画笔颜色
        mSecondPaint.setStrokeWidth(5);//设置画笔的宽度
        mSecondPaint.setAntiAlias(true);
        double ms = (6.0d * milliSecond) / 1000d;
        double argle = 6d * second + ms;
        //使用三角函数计算末端的x值，秒针长度为（半径-20）
        int stopX = (int) (Math.cos(Math.toRadians(-90 + argle)) * (mRadius - 20));
        //使用三角函数计算末端的y值
        int stopY = (int) (Math.sin(Math.toRadians(-90 + argle)) * (mRadius - 20));
        //使用三角函数计算初始端的x值
        int startX = (int) (Math.cos(Math.toRadians(-90 + argle - 180d)) * (50));
        //使用三角函数计算初始端的y值
        int startY = (int) (Math.sin(Math.toRadians(-90 + argle - 180d)) * (50));
        //画线
        canvas.drawLine(mCenterX + startX, mCenterY + startY,
                mCenterX + stopX, mCenterY + stopY, mSecondPaint);
        //画中间的圆
        canvas.drawCircle(mCenterX, mCenterY, 15, mSecondPaint);

        //画中间的白点
        mSecondPaint.setColor(mHourHandColor);//设置画笔颜色
        canvas.drawCircle(mCenterX, mCenterY, 5, mSecondPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (null == mMainHandler) {
            mMainHandler = new Handler();
        }
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mMainHandler) {
            mMainHandler.removeCallbacksAndMessages(null);
        }
    }
}
