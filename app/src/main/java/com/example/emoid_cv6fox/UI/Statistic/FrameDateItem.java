package com.example.emoid_cv6fox.UI.Statistic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.Config.ConfigView;
import com.example.emoid_v2.R;

/**
 * @author: lenovo
 * @date: 2020/11/14
 */
public class FrameDateItem extends LinearLayout {
    private Paint mPaint,bgPaint;
    enum STATE{NOTHING,SHOW}
    private STATE state=STATE.NOTHING;
    //private STATE state=STATE.SHOW;
    private int []X=null;

    private boolean isSellected=false;
    //private final Integer[] faceID={R.color.colorFace1,R.color.colorFace2,R.color.colorFace3,R.color.colorFace4};

    public FrameDateItem(Context context) {
        super(context);setWillNotDraw(false);
    }
    public FrameDateItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);setWillNotDraw(false);//!!!!!!让layout触发onDraw
    }

    //初始化
    private boolean initFlag=true;
    private void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.colorLevel1));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15f);

        bgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(getResources().getColor(R.color.styleLevel_000));
        bgPaint.setStyle(Paint.Style.FILL);

    }



    @Override
    protected void onDraw(Canvas canvas) {
        if(initFlag){
            init();
            initFlag=false;
        }
        switch (state){
            case NOTHING:
                break;
            case SHOW:
                /*
                if(isSellected)
                    drawBg(canvas);
                if(X!=null&&X[0]+X[1]+X[2]+X[3]!=0) {

                }*/
                showDetail(canvas);

                break;
        }
        super.onDraw(canvas);
        //Log.i("IN102",String.valueOf(getWidth()));
        //canvas.drawCircle(getWidth()/2f,getHeight()/2f,Math.min(getWidth(),getHeight())/2f,mPaint);
    }
    private void drawBg(Canvas canvas){
        canvas.drawRect(0,getHeight()/4f,getWidth(),getHeight()*3f/4,bgPaint);
    }
    private void showDetail(Canvas canvas){
        float padding=20;
        float sum=0;
        float anglePer;
        for(int i=0;i<4;i++)
            sum+=(float) X[i];
        anglePer=360/sum;
        float Xc=getWidth()/2f;
        float Yc=getHeight()/2f;
        float r=Math.min(Xc,Yc);
        RectF rectF=new RectF(Xc-r+padding,Yc-r+padding,Xc+r-padding,Yc+r-padding);
        sum=0;
        for(int i=0;i<4;i++){
            mPaint.setColor(getResources().getColor(ConfigView.Face.colorId[i]));
            canvas.drawArc(rectF,sum,X[i]*anglePer,false,mPaint);
            sum+=X[i]*anglePer;
        }
    }

    //开始绘制
    public void startShow(int []X){
        this.X=new int[X.length];
        for(int i=0;i<X.length;i++)
            this.X[i]=X[i];
        if(X[0]+X[1]+X[2]+X[3]==0) {
            state=STATE.NOTHING;
            return;
        }
        state=STATE.SHOW;
        invalidate();
    }
    public void setSelected(boolean isSellected){
        this.isSellected=isSellected;




    }
}
