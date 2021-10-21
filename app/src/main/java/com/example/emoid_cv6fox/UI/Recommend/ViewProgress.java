package com.example.emoid_cv6fox.UI.Recommend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.emoid_v2.R;

/**
 * @author: wzt
 * @date: 2020/11/15
 */
//进度条
public class ViewProgress extends View {

    enum STATE{COR,RUN}
    private STATE state= STATE.COR;
    private Paint mPaint,cPaint;
    private TextPaint tPaint;

    public ViewProgress(Context context) {
        super(context);
        init(context);
    }

    public ViewProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(420);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorTabm));

        cPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        cPaint.setStrokeWidth(20);
        cPaint.setStyle(Paint.Style.STROKE);
        cPaint.setColor(getResources().getColor(R.color.colorWhite));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (state){
            case RUN:
                drawRun(canvas);
            case COR:
                drawCor(canvas);
                break;
        }
    }
    float runTime;
    private void drawRun(Canvas canvas){
        float Xpad=40;
        float Yc=getHeight()/2;
        runTime=getWidth()/2;
        canvas.drawLine(Xpad,Yc,runTime,Yc,mPaint);
        canvas.drawCircle(runTime,Yc,10,mPaint);
    }
    private void drawCor(Canvas canvas){
        float Xpad=40;
        float Yc=getHeight()/2;
        canvas.drawLine(Xpad,Yc,getWidth()-Xpad,Yc,cPaint);
    }
}
