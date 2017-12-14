package com.example.administrator.bing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @autour: wanbin
 * date: 2017/12/7 0007 16:42
 * version: ${version}
 * des: 双折线图
 */
public class BothLineChart extends View {
    private int mWidth;
    private int mHeight;
    private int YMaxValue =250;    //y轴最大值

    private int myInterval;//Y轴  每个刻度的间距间距

    private int dividerCount =5; //纵轴分割数量
    private int xCount =7;
    private int spacing =30;//左右偏移值

    private int mTopInterval =62;//X轴距离view顶部长度
    private int mBottomInterval =47;//X轴距离view顶部长度
    private Paint mPaintText;
    private Paint mPaintChart;

    private int leftMargin =64;
    private int rightMargin = 32;

    private int  rectangleWidth = 20; //矩形的宽度
    private int rectanleMargin = 5 ;//矩形之间的间距

    private int xMargin = 30;//第一天和y轴的距离
    private Rect mBound;
    private int  radius =10;//圆的半径
    private Paint linePaint;
    private Path mPath;
    private Paint mTopPaint;

    public BothLineChart(Context context) {
        super(context);
        init();
    }

    public BothLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BothLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mPaintText = new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextSize(10);
        mPaintText.setAntiAlias(true);
        mPaintChart = new Paint();
        mPaintChart.setAntiAlias(true);
        mPaintChart.setColor(Color.RED);
        mBound = new Rect();

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#0F9EFF"));
        linePaint.setAntiAlias(true);

        mTopPaint = new Paint();
        mTopPaint.setAntiAlias(true);
        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize ;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize ;
        }


        Log.e("=====>","widthSize: " +width +" noPaddingWidth :"+" heightSize :"+height+" ");
        Log.i("=====>"," getWidth();: " + getWidth() +"  getHeight() :"+"  :"+ getHeight()+" ");
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getWidth();
        mHeight = getHeight();

        myInterval = (getHeight()-mTopInterval-mBottomInterval)/dividerCount;
        Log.e("=====>>>>","widthSize: " +mWidth +" noPaddingWidth :"+" heightSize :"+mHeight+" ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e("wangbin","onDraw");
//画x轴线
        canvas.drawLine(leftMargin-20,mHeight-mBottomInterval,mWidth-rightMargin,mHeight-mBottomInterval,mPaintText);
        //画y轴线
        canvas.drawLine(leftMargin,mTopInterval,leftMargin,mHeight-mBottomInterval+20,mPaintText);
        int y = myInterval * (dividerCount ); // 只拿纵轴的dividerCount-1/dividerCount画图
        mPaintText.setTextSize(18);
        //画y轴文字 和线
        for (int i =1;i<dividerCount+1;i++){
            //底部的真正距离
            int bottom = mHeight - mBottomInterval;
            // y = bottom / (dividerCount);
            int value = YMaxValue / dividerCount;

            canvas.drawText(String.valueOf(value*i), leftMargin-35, (bottom-y * (value*i)/YMaxValue)+8, mPaintText);
            canvas.drawLine(leftMargin,bottom-y * (value*i)/YMaxValue,mWidth-rightMargin,bottom-y * (value*i)/YMaxValue,mPaintText);
        }


        int w = (mWidth-leftMargin-rightMargin)/xCount;

        for (int j =0;j<xCount;j++){
            mPaintText.getTextBounds(String.valueOf(j + 1)+" " , 0, String.valueOf(j).length(), mBound);
            canvas.drawText((j + 1) + "月13", w * j+leftMargin+xMargin +rectangleWidth, mHeight-mBottomInterval+mBound.height()+5, mPaintText);
        }

        drawTopText(canvas);
        drawLineChart(canvas);

    }

    public void drawTopText(Canvas canvas){
        mTopPaint.setTextSize(18);
        int textHight = mTopInterval/2;
        mTopPaint.setColor(Color.parseColor("#0F9EFF"));
        canvas.drawText("近7次血压检测记录",leftMargin,textHight,mTopPaint);

        canvas.drawText("舒张压",mWidth-rightMargin*2,textHight,mPaintText);
        canvas.drawCircle(mWidth-rightMargin*2-radius,textHight-radius,radius,mTopPaint);
        mTopPaint.setColor(Color.parseColor("#00BFA5"));
        canvas.drawText("收缩压",mWidth-rightMargin*6,textHight,mPaintText);
        canvas.drawCircle(mWidth-rightMargin*6-radius,textHight-radius,radius,mTopPaint);

    }

    /**
     * 画折线
     * @param canvas
     */
    private void drawLineChart(Canvas canvas) {
        int xArr [] = new int[xCount];
        int yArr [] = new int[xCount];
        int w = (mWidth-leftMargin-rightMargin)/xCount;
        int y = myInterval * (dividerCount ); // 只拿纵轴的dividerCount-1/dividerCount画图

        mPath.reset();
        linePaint.setAntiAlias(true);
        for (int i=0;i<xCount;i++){
            int  value = (int) (Math.random()*YMaxValue);
            xArr[i] =w * i + leftMargin+xMargin+rectangleWidth+radius*2;
            yArr[i] = ((mHeight - mBottomInterval) - (y * value / YMaxValue));
            linePaint.setColor(Color.parseColor("#0F9EFF"));
            linePaint.setStyle(Paint.Style.FILL);//设置空
            linePaint.setShader(null);
            linePaint.setPathEffect(null);
            canvas.drawCircle(xArr[i],yArr[i] ,radius,linePaint);

            /**
             * 绘制点上的值，
             */
            canvas.drawText(value+"",xArr[i],yArr[i]-20,mPaintText);


            if (i ==0){
                mPath.moveTo(xArr[i],yArr[i]);
            }else {
                mPath.lineTo(xArr[i],yArr[i]);
            }
            linePaint.setColor(Color.parseColor("#0F9EFF"));
            linePaint.setStrokeWidth(5);
            linePaint.setStyle(Paint.Style.STROKE);//设置空
            canvas.drawPath(mPath,linePaint);
        }

        mPath.reset();

        for (int j=0;j<xCount;j++){
            int  value = (int) (Math.random()*YMaxValue);
            xArr[j] =w * j + leftMargin+xMargin+rectangleWidth+radius*2;
            yArr[j] = ((mHeight - mBottomInterval) - (y * value / YMaxValue));
            mPaintChart.setColor(Color.parseColor("#00BFA5"));
            mPaintChart.setStyle(Paint.Style.FILL);//设置空
            mPaintChart.setShader(null);
            mPaintChart.setPathEffect(null);
            canvas.drawCircle(xArr[j],yArr[j] ,radius,mPaintChart);

            /**
             * 绘制点上的值，
             */
            canvas.drawText(value+"",xArr[j],yArr[j]-20,mPaintText);


            if (j ==0){
                mPath.moveTo(xArr[j],yArr[j]);
            }else {
                mPath.lineTo(xArr[j],yArr[j]);
            }
            mPaintChart.setColor(Color.parseColor("#00BFA5"));
            mPaintChart.setStrokeWidth(5);
            mPaintChart.setStyle(Paint.Style.STROKE);//设置空
            canvas.drawPath(mPath,mPaintChart);
        }
    }
}
