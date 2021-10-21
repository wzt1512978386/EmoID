package com.example.emoid_cv6fox.UI.Identify.Heart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.Config.ConfigView;
import com.example.emoid_v2.R;
import com.example.emoid_cv6fox.Util.MyList;

import java.util.ArrayList;

public class Viewheart extends View {
    private Paint mPaint;
    private Paint cPaint;
    private Paint rPaint;

    private Path mPath;
    private Path cPath;
    private Path rPath;

    private TextPaint tPaint;

    enum STATE{CORD,BROKEN,REALTIME,NOTHING}
    private STATE state=STATE.NOTHING;
    //空间数值
    private float height,width;

    public Viewheart(Context context) {
        super(context);
        init(context);
    }
    public Viewheart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public Viewheart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public Viewheart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context){
        //折线
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xffff00ff);
        mPaint.setStrokeWidth(5f);
        mPath=new Path();

        //坐标轴
        cPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        cPaint.setAntiAlias(true);
        cPaint.setStyle(Paint.Style.STROKE);
        cPaint.setColor(getResources().getColor(R.color.colorBlack));
        cPaint.setStrokeWidth(5f);
        cPath=new Path();

        //文字
        tPaint=new TextPaint();
        tPaint.setTextSize(30);
        //tPaint.setAntiAlias(true);
        //tPaint.setStyle(Paint.Style.STROKE);
        tPaint.setColor(getResources().getColor(R.color.colorBlack));
        tPaint.setStrokeWidth(1);

        //实时心电图
        rPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        rPaint.setAntiAlias(true);
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setColor(0xffff00ff);
        rPaint.setStrokeWidth(5f);
        rPath=new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.height=getHeight();
        this.width=getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (state){
            case NOTHING:
                break;
            case BROKEN:
                drawBrokenData(canvas);
                break;
            case CORD:
                drawCord(canvas);
                break;
            case REALTIME:
                drawRealtimeLine(canvas);
                break;
            default:
                break;
        }
    }
    private float padding=30;
    private float scaleLen=8;
    private float scaleDisX,scaleDisY;
    private int scaleNum=10;
    private float textPaddingY=20;
    private float textPaddingX=30;
    private void drawCord(Canvas canvas){
        scaleDisX=(width-2*padding)/(scaleNum-1);
        scaleDisY=(height-2*padding)/(scaleNum-1);
        cPath.moveTo(padding,padding);
        cPath.lineTo(padding,height-padding);
        cPath.lineTo(width-padding,height-padding);
        //x轴刻度
        for(int i=0;i<10;i++){
            cPath.moveTo(padding+i*scaleDisX,height-padding);
            cPath.lineTo(padding+i*scaleDisX,height-padding+scaleLen);
            canvas.drawText(String.valueOf((int)(dataNum/10*i)),padding+i*scaleDisX,height-padding+scaleLen+textPaddingY,tPaint);
        }
        //y轴刻度
        for(int i=0;i<10;i++){
            cPath.moveTo(padding,height-padding-i*scaleDisY);
            cPath.lineTo(padding-scaleLen,height-padding-i*scaleDisY);
            canvas.drawText(String.valueOf((int)((max-min)/10*i+min)),padding-scaleLen-textPaddingX,height-padding-i*scaleDisY,tPaint);
        }
        canvas.drawPath(cPath,cPaint);

    }
    public void drawTest(){
        state=STATE.CORD;
        invalidate();
    }
    private int dataNum= ConfigView.Heart.DRAW_DATA_NUM;

    private ArrayList<Short> data;
    private int index;
    public void toDrawBrokenLine(ArrayList<Short> data,int index){
        this.data=data;
        this.index=index;
        state=STATE.BROKEN;
        invalidate();
    }
    private float min;
    private float max;
    private void drawBrokenData(Canvas canvas){
        mPath.reset();
        mPath.moveTo(padding,height-padding);
        float Xper=(width-2*padding)/dataNum;
        min=4096;
        max=0;
        for(int i=0;i<dataNum;i++){
            min=Math.min(min,data.get(i+index*dataNum));
            max=Math.max(max,data.get(i+index*dataNum));
        }
        for(int i=0;i<dataNum;i++){
            float Ytemp=((height-2*padding)/(max-min)*(data.get(i+index*dataNum)-min));
            mPath.lineTo(padding+(i+1)*Xper,height-padding-Ytemp);
        }
        canvas.drawPath(mPath,mPaint);
    }

    private float beginRX,endRX,RY,YD;
    private void drawRealtimeLine(Canvas canvas){
        beginRX=getWidth()/6f;
        endRX=getWidth()*5/6f;
        RY=getHeight()/2f;
        YD=getHeight()/3f;
        float Yscale=YD*2/4096f;
        rPath.reset();
        rPath.moveTo(beginRX,RY);
        int curLen=Rdata.getLen();
        float Xper=(endRX-beginRX)/drawLen;
        if(curLen<drawLen){
            int i;
            rPath.lineTo(beginRX+(drawLen-curLen)*Xper,RY);
            for(i=0;i<curLen;i++){
                rPath.lineTo(beginRX+(drawLen-curLen+i)*Xper,RY-(Rdata.get(i)-2048)*Yscale);
            }
            rPath.lineTo(endRX,RY);
        }
        else {
            curLen=drawLen;
            for (int i = 0; i < drawLen; i++) {
                rPath.lineTo(beginRX+i*Xper,RY-(Rdata.get(i)-2048)*Yscale);
            }
        }
        canvas.drawPath(rPath,rPaint);
    }


    private int dataIndex;
    private int drawLen=100;
    private MyList Rdata;
    public void updateData(MyList data){
        state= STATE.REALTIME;
        this.Rdata=data;
        //this.dataIndex=dataIndex;
        invalidate();
    }
}
