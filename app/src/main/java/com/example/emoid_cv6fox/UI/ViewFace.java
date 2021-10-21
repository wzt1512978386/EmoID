package com.example.emoid_cv6fox.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.Config.ConfigView;
import com.example.emoid_v2.R;

/**
 * @author: wzt
 * @date: 2020/11/14
 */
public class ViewFace extends View {
    private Context context;
    private int type;
    private int form;


    private Paint mPaint;
    private Path mPath;

    private float H,W,Xc,Yc,r;
    private float paintW;
    private boolean getable=false;

    public ViewFace(Context context) {
        super(context);
        init(context);
    }

    public ViewFace(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.burce);
        type=array.getInt(R.styleable.burce_type,0);
        form=array.getInt(R.styleable.burce_form,0);
        array.recycle();
        init(context);
    }



    private void init(Context context){
        this.context=context;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setPathEffect(new CornerPathEffect(10));
        mPath=new Path();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(!getable) {
            H = getHeight();
            W = getWidth();
            Xc = W / 2;
            Yc = H / 2;
            r = Math.min(Xc, Yc);
        }
        paintW=r/10;
        r-=paintW/2;
        mPaint.setStrokeWidth(paintW);

        mPaint.setColor(getResources().getColor(ConfigView.Face.colorId[type]));
        switch (type){
            case 0:
                drawFace0(canvas);
                break;
            case 1:
                drawFace1(canvas);
                break;
            case 2:
                drawFace2(canvas);
                break;
            case 3:
                drawFace3(canvas);
                break;
        }
        super.onDraw(canvas);
    }
    private void drawFace0(Canvas canvas){
        if(form==1)
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(Xc,Yc,r,mPaint);
        if(form==1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0xffffffff);
        }
        canvas.drawLine(Xc-r/5,Yc-r/4,Xc-r*3/5,Yc,mPaint);
        canvas.drawLine(Xc+r/5,Yc-r/4,Xc+r*3/5,Yc,mPaint);
        canvas.drawLine(Xc-r/5,Yc-r/4,Xc-r*3/5,Yc-r/2,mPaint);
        canvas.drawLine(Xc+r/5,Yc-r/4,Xc+r*3/5,Yc-r/2,mPaint);
        RectF rectF=new RectF(Xc-r*3/4,Yc-r*3/4,Xc+r*3/4,Yc+r*3/4);
        canvas.drawArc(rectF,20,140,false,mPaint);
        canvas.drawLine(Xc- (float) Math.cos(Math.PI*20/180)*r*3/4,
                Yc+(float) Math.sin(Math.PI*20/180)*r*3/4,
                Xc+ (float) Math.cos(Math.PI*20/180)*r*3/4,
                Yc+(float) Math.sin(Math.PI*20/180)*r*3/4,mPaint);
    }

    private void drawFace1(Canvas canvas){
        if(form==1)
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(Xc,Yc,r,mPaint);
        if(form==1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0xffffffff);
        }
        float Xper=r*2/5;
        canvas.drawLine(Xc-Xper*3/2,Yc,Xc-Xper/2,Yc,mPaint);
        canvas.drawLine(Xc+Xper*3/2,Yc,Xc+Xper/2,Yc,mPaint);
        RectF rectF=new RectF(Xc-r*2/5,Yc-r/2,Xc+r*2/5,Yc+r/2);
        canvas.drawArc(rectF,50,80,false,mPaint);
        rectF=new RectF(Xc-r*4/5,Yc-r*3/5,Xc+r*4/5,Yc+r*3/5);
        canvas.drawArc(rectF,-145,30,false,mPaint);
        canvas.drawArc(rectF,-65,30,false,mPaint);
    }
    private void drawFace2(Canvas canvas){
        if(form==1)
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(Xc,Yc,r,mPaint);
        if(form==1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0xffffffff);
        }
        canvas.drawCircle(Xc-r/2,Yc,r/7,mPaint);
        canvas.drawCircle(Xc+r/2,Yc,r/7,mPaint);
        canvas.drawLine(Xc-r*3/5,Yc+r/2,Xc+r*3/5,Yc+r/2,mPaint);
        canvas.drawLine(Xc-r/2-r/7,Yc-r/7*2,Xc-r/2+r/7,Yc-r/7*2,mPaint);
        canvas.drawLine(Xc+r/2-r/7,Yc-r/7*2,Xc+r/2+r/7,Yc-r/7*2,mPaint);
    }
    private void drawFace3(Canvas canvas){
        if(form==1)
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(Xc,Yc,r,mPaint);
        if(form==1) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0xffffffff);
        }
        RectF rectF=new RectF(Xc-r/3*2,Yc+r/4,Xc+r/3*2,Yc+r*2*8/9);
        canvas.drawArc(rectF,-30,-120,false,mPaint);
        canvas.drawCircle(Xc-r/3,Yc,r/7,mPaint);
        canvas.drawCircle(Xc+r/3,Yc,r/7,mPaint);
        canvas.drawLine(Xc-r/2-r/7,Yc-r/7*3,Xc-r/2+r/7,Yc-r/7*2,mPaint);
        canvas.drawLine(Xc+r/2-r/7,Yc-r/7*2,Xc+r/2+r/7,Yc-r/7*3,mPaint);
        //canvas.dra
    }
    public void setTF(int type,int form){
        this.type=type;
        this.form=form;
        if(!getable)
            invalidate();
    }
    public void setPos(float Xc,float Yc,float r){
        this.Xc=Xc;
        this.Yc=Yc;
        this.r=r;
        getable=true;
    }
    public void setType(int type){
        this.type=type;
        invalidate();
    }
}
