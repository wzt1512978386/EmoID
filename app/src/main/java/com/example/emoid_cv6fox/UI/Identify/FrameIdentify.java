package com.example.emoid_cv6fox.UI.Identify;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.UI.Identify.Heart.FrameHeart;
import com.example.emoid_cv6fox.UI.Identify.Heart.ViewRipple;

/**
 * @author: wzt
 * @date: 2020/11/21
 */
public class FrameIdentify extends FrameLayout {
    //外部调用
    public Context context;
    public FrameHeart frameHeart;
    public ViewRipple viewRipple;
    public ViewWave viewWave;
    //空间变量
    private float W,H;//框架宽高
    private float Xc,Yc;//框架中心坐标


    public FrameIdentify(@NonNull Context context) {
        super(context);
        this.context=context;
        setWillNotDraw(false);
    }
    public FrameIdentify(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setWillNotDraw(false);//让frame可以绘制
    }

    //初始化
    private boolean flagInit=true;
    private void init(){
        MyApp.frameIdentify=this;
        initMeasure();
    }
    private void initMeasure(){
        W=getWidth();
        H=getHeight();
        Xc=W/2;
        Yc=H/2;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(flagInit){
            init();
            flagInit=false;
        }
        super.onDraw(canvas);
    }
}
