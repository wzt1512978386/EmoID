package com.example.emoid_cv6fox.UI.Identify.Heart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigView;

import com.example.emoid_cv6fox.Util.MyList;
import com.example.emoid_v2.R;

/**
 * @author: wzt
 * @date: 2020/11/22
 */
public class FrameHeart extends FrameLayout {
    public FrameHeart(@NonNull Context context) {
        super(context);setWillNotDraw(false);
    }
    public FrameHeart(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);setWillNotDraw(false);
    }

    //外部调用
    public Context context;

    //空间变量
    private float Wf,Hf;//控件尺寸
    private float Xcf,Ycf;//框架frame中心坐标
    private float Wl,Hl;
    private float XlL,YlL;
    private float XlR,YlR;

    //绘制工具
    private Paint mPaint,bPaint,wPaint;
    private Path mPath,bPath;

    //状态
    enum STATE{NOTHONG,DRAW};
    private STATE state=STATE.NOTHONG;

    //数据
    private MyList dataDraw;


    //实时图 心电图的幅度
    private float YZoom=1f;

    private void init(){
        MyApp.frameIdentify.frameHeart=this;
        initMeasure();

        //绘制工具初始化
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.heart));

        mPath=new Path();

        bPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bPaint.setStyle(Paint.Style.STROKE);
        bPaint.setColor(getResources().getColor(R.color.colorRed));
        bPaint.setPathEffect(new CornerPathEffect(20));
        bPaint.setStrokeWidth(3f);

        bPath=new Path();

        wPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        wPaint.setStyle(Paint.Style.STROKE);
        wPaint.setColor(getResources().getColor(R.color.colorWhite));
        wPaint.setStrokeWidth(3f);

        dataDraw=MyApp.data.dataDraw;
    }

    private void initMeasure(){
        Wf=getWidth();
        Hf=getHeight();
        Xcf=Wf/2;
        Ycf=Hf/2;

        //左右两个小爱心
        ImageView heart1=(ImageView) findViewById(R.id.imageview_identify_smallheart_1);
        ImageView heart2=(ImageView) findViewById(R.id.imageview_identify_smallheart_2);
        Wl=heart1.getWidth();
        Hl=heart1.getHeight();

        XlL=heart1.getX()+Wl/2;
        YlL=heart1.getY()+Hl/2;
        XlR=heart2.getX()+Wl/2;
        YlR=heart2.getY()+Hl/2;
    }

    private boolean initFlag=true;
    private boolean drawBackgroundFlag=true;
    @Override
    protected void onDraw(Canvas canvas) {
        if(initFlag){
            init();
            initFlag=false;
        }
        if(MyApp.mainActivity.viewPager.getCurrentItem()!=2)///////////非当页面不会绘制
            return;
        switch (state){
            case NOTHONG:
                if(drawBackgroundFlag) {

                    drawBackgroundFlag=false;
                    Bitmap mbitmap = Bitmap.createBitmap((int)Wf, (int)Hf, Bitmap.Config.ARGB_8888);
                    Canvas mCanvas = new Canvas(mbitmap);

                    drawBackground(mCanvas);
                    setBackground(new BitmapDrawable(mbitmap));
                    //c.setBitmap(bitmap);
                    //canvas.drawBitmap(bitmap, 0, 0,paint);
                }
                break;
            case DRAW:
                //drawBackground(canvas);
                drawRealTime(canvas);
                break;
        }
        super.onDraw(canvas);
    }
    //绘制实时图 心电图
    private void drawRealTime(Canvas canvas){
        //canvas.drawCircle(XlL,YlL,20,bPaint);
        float Xper=(XlR-XlL)/ ConfigView.Heart.DRAW_DATA_NUM;
        float Yscale=Hf/3/3.3f*YZoom;
        bPath.reset();
        bPath.moveTo(XlL,YlL);

        //计算数据的平均值
        float aver=calAverage();

        for(int i = 0; i< ConfigView.Heart.DRAW_DATA_NUM; i++){
            bPath.lineTo(XlL+Xper/2+Xper*i,YlL-(dataDraw.get(i)-aver)*Yscale);
        }
        canvas.drawPath(bPath,bPaint);
    }
    //计算心电图的平均
    private float calAverage(){
        float aver=0;
        int T=(int)(ConfigView.Heart.DRAW_DATA_NUM/100f);
        for(int i=0;i<100;i++){
            aver+=dataDraw.get(i*T);
        }
        return aver/100f;
    }


    //绘制背景
    private void drawBackground(Canvas canvas){
        float Rh=Hf*5/6/2;//斜正方形半径
        float Rhs=Rh*(float) Math.sqrt(2)/2;//R heart section 两个圆半径
        float Xch=Xcf;//X center of heart
        float Ych=Hf/6+Rh;

        float Xscale=Wf*8/9/(Rh+Rhs*2);
        mPath.moveTo(Xch,Ych+Rh);
        mPath.lineTo(Xch-Rh*Xscale,Ych);
        mPath.lineTo(Xch,Ych-Rh);
        mPath.lineTo(Xch+Rh*Xscale,Ych);
        canvas.drawPath(mPath,mPaint);
        RectF rectF=new RectF(Xch+(-Rh/2-Rhs)*Xscale,Ych-Rh/2-Rhs,Xch+(-Rh/2+Rhs)*Xscale,Ych-Rh/2+Rhs);
        canvas.drawOval(rectF,mPaint);
        rectF=new RectF(Xch+(Rh/2-Rhs)*Xscale,Ych-Rh/2-Rhs,Xch+(Rh/2+Rhs)*Xscale,Ych-Rh/2+Rhs);
        canvas.drawOval(rectF,mPaint);
        //canvas.drawCircle(Xch-Rh/2,Ych-Rh/2,Rhs,mPaint);
        //canvas.drawCircle(Xch+Rh/2,Ych-Rh/2,Rhs,mPaint);
    }
    //开始绘制心电图调用
    public void startDraw(){
        state=STATE.DRAW;
        invalidate();
    }
    //设置心电图幅度减少
    public void YZoomIN(){
        YZoom*=0.8;
    }
    //设置心电图幅度增加
    public void YZoomOUT(){
        YZoom*=1.2;
    }



}
