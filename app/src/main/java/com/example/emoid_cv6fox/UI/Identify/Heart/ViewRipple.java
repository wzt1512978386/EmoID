package com.example.emoid_cv6fox.UI.Identify.Heart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_v2.R;
import com.example.emoid_cv6fox.Util.MyList;

/**
 * @author: wzt
 * @date: 2020/11/29
 */
public class ViewRipple extends View {
    //数据
    private MyList data;
    private int drawNum=20;
    //空间长度
    private float Hf,Wf;
    private float Xcf,Ycf;
    //绘制工具
    private Paint rPaint;
    //状态
    enum STATE{NOTHING,DRAW};
    private STATE state=STATE.NOTHING;
    public ViewRipple(Context context) {
        super(context);
    }
    public ViewRipple(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(){
        MyApp.frameIdentify.viewRipple=this;
        initMeasure();

        rPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setColor(getResources().getColor(R.color.colorWhite));
        rPaint.setStrokeWidth(5);

        data=new MyList(drawNum);
    }
    private void initMeasure(){
        Hf=getHeight();
        Wf=getWidth();
        Xcf=Wf/2;
        Ycf=Hf/2;
    }

    private boolean initFlag=true;
    @Override
    protected void onDraw(Canvas canvas) {
        if(initFlag){
            initFlag=false;
            init();
        }
        if(MyApp.mainActivity.viewPager.getCurrentItem()!=2)///////////非当页面不会绘制
            return;
        switch (state){
            case NOTHING:
                break;
            case DRAW:
                drawRipple(canvas);
                break;
        }

        super.onDraw(canvas);
    }
    private void drawRipple(Canvas canvas){
        float r=Wf/4;
        float rPer=r/drawNum;
        for(int i=0;i<data.getLen();i++){
            float i_=data.get(i);
            rPaint.setAlpha((int)(Math.max(230-255f/drawNum*i_,0)));
            canvas.drawCircle(Xcf,Ycf,r+rPer*(i_/drawNum)*i_,rPaint);
            data.set(i,i_+1);
        }
        while(data.get(0)>=drawNum){
            data.pop();
        }
        if(data.getLen()>0){
            invalidate();
        }
    }
    public void startDraw(){
        //if(data.get(0)==0&&data.getLen()>0)
//            return;
        data.push(0);
        state=STATE.DRAW;
        invalidate();
    }
    public void stopDraw(){
        state=STATE.NOTHING;
    }
}
