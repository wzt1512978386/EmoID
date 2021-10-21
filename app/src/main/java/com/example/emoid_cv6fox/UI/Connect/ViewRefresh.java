package com.example.emoid_cv6fox.UI.Connect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.emoid_v2.R;


/**
 * @author: wzt
 * @date: 2020/11/15
 */
public class ViewRefresh extends View {
    private Paint mPaint;
    private Path mPath;
    enum STATE{NOTHING,DRAW}
    STATE state=STATE.NOTHING;

    private boolean isInit=false;
    private float H,W;

    public ViewRefresh(Context context) {
        super(context);
        init(context);
    }
    public ViewRefresh(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        mPath=new Path();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorLevel2));
        mPaint.setPathEffect(new CornerPathEffect(20));


    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInit){
            W=getWidth();
            H=getHeight();
        }
        super.onDraw(canvas);
        switch (state){
            case NOTHING:
                break;
            case DRAW:
                drawPull(canvas);
                break;
        }
    }
    private float rate;
    private void drawPull(Canvas canvas){
        mPath.reset();
        mPath.moveTo(0,0);
        float num=50;
        float Yscale=H;
        float Xper=W/num;

        for(int i=1;i<=num;i++){
            float fx=((float) Math.cos(Math.PI*2*i/num)-1)/2f;
            mPath.lineTo(i*Xper,fx*Yscale*rate*-1);
        }
        canvas.drawPath(mPath,mPaint);
    }
    public void setDraw(float rate){
        state=STATE.DRAW;
        this.rate=rate;
        invalidate();
    }
}
