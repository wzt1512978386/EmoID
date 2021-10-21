package com.example.emoid_cv6fox.UI.Identify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_v2.R;


public class ViewWave extends View {
    //全局
    private Context context;
    //画笔
    private Paint mPaint;
    private Path mPath;
    //状态
    enum StateAni{START,STOP,LEAVE};
    private StateAni stateAni= StateAni.STOP;
    //位置数值定义
    private float waveHeight;

    public ViewWave(Context context) {
        super(context);

    }
    public ViewWave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    private void init(){
        this.context=context;
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.styleLevel_001));

        MyApp.frameIdentify.viewWave=this;



        time=0;
        mPath=new Path();
    }

    public void start(){
        stateAni= StateAni.START;
        invalidate();
    }
    public void stop(){
        stateAni= StateAni.STOP;
    }

    private boolean initFlag=true;
    @Override
    protected void onDraw(Canvas canvas) {
        if(initFlag){
            initFlag=false;
            init();
        }
        switch (stateAni){
            case START:
                //startDraw(canvas);
                drawWave(canvas);
                //invalidate();
                break;
            case STOP:
                break;
            case LEAVE:
                drawLeave(canvas);
                break;
            default:
        }
        super.onDraw(canvas);
    }

    private float rate=0;
    private void drawWave(Canvas canvas){
        float startAngle=90;
        float Rc=getWidth()/2f;
        float angleZoom=(float) Math.PI/180f;
        float angleMove=(float)Math.acos((Rc-Rc*rate*2)/Rc)/angleZoom;
        canvas.drawArc(new RectF(0,0,getWidth(),getHeight()),
                startAngle-angleMove,2*angleMove,false,mPaint);
    }

    private float time;
    private float rand=(float) Math.random();
    private void startDraw(Canvas canvas){
        mPath.reset();
        waveHeight=getHeight()/2f;
        float startX=0;
        float startY=waveHeight/2f;
        mPath.moveTo(startX,startY);
        if((float) Math.sin((time%100)/100*Math.PI*2)==0){
         //   rand=(float) Math.random();
            rand=0.7f;
        }
        for(int i=0;i<1000;i++){
            float x=(i/1000f)*getWidth();
            float y=waveFunc(4f,waveHeight/2f*rand,(float) Math.sin((time%100)/100*Math.PI*2),x);
            mPath.lineTo(x,startY-y);
        }
        mPath.lineTo(getWidth(),startY);
        mPath.lineTo(getWidth(),getWidth());
        mPath.lineTo(0,getHeight());
        canvas.drawPath(mPath,mPaint);
        time+=2;


    }
    //n :波浪数    h :波浪高度   t :当前时刻   x : 当前x

    private float waveFunc(float n,float h,float t,float x){
        float ans;
        ans=(float)Math.cos(2*Math.PI*n*((x)/1000))*h*t/2;
        return ans;
    }

    public void setRate(float rate){
        stateAni=StateAni.START;
        curAlpha=1f;
        mPaint.setAlpha(255);
        this.rate=Math.min(rate,1f);
        invalidate();
    }
    public void leave(){
        if(!isLeaving) {
            isLeaving=true;
            stateAni=StateAni.LEAVE;
            invalidate();
        }
    }
    private float curAlpha=1f;
    private boolean isLeaving=false;
    private void drawLeave(Canvas canvas){
        curAlpha=Math.max(curAlpha-0.1f,0f);
        mPaint.setAlpha((int)(curAlpha*255));
        drawWave(canvas);
        invalidate();
    }



}
