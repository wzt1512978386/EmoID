package com.example.emoid_cv6fox.UI.Connect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_v2.R;


import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * @author: wzt
 * @date: 2020/11/15
 */
public class FrameConnect extends FrameLayout {
    private Context context;
    private LinearLayout listLayout;
    private ViewRefresh refresh;
    private GifImageView gif;

    public FrameConnect(@NonNull Context context) {
        super(context);
        setWillNotDraw(false);

    }
    public FrameConnect(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }


    private float H,W;
    private float pullH,dragH;
    private float startHeight,startHeightG;
    private float refreshHeight;
    private float gifHeight;

    private boolean isBack=false;

    private void init(){
        MyApp.frameConnect=this;

        listLayout = (LinearLayout) findViewById(R.id.linearlayout_connect_list_device);
        refresh=(ViewRefresh)findViewById(R.id.refreshview_connect);
        gif=(GifImageView)findViewById(R.id.gifimageview_connect);
        GifDrawable gifDrawable=(GifDrawable)gif.getDrawable();
        gifDrawable.stop();
        refreshHeight=refresh.getHeight();
//            gifHeight=gif.getHeight();
        H = getHeight();
        W = getWidth();
        pullH = H / 4;
        dragH=pullH/4;
        startHeight = getY();
        startHeightG=getY();
    }
    private boolean isInit=true;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isInit) {
            init();
            isInit=false;

        }
    }

    /*
    private void dodo(ViewGroup viewGroup){
        for(int i=0;i<viewGroup.getChildCount();i++){
            ViewGroup viewGroup1=getChildAt(i);
            .setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }
    }*

     */

    private float Ystart,Ycurrent,Yd;

    private boolean isRefresh=false;
    public boolean isScan=false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isRefresh)
            return true;
        if(isBack)
            return true;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Ystart = ev.getY();
                Ycurrent = Ystart;
                break;
            case MotionEvent.ACTION_MOVE:
                Ycurrent = ev.getY();
                Yd = Ycurrent - Ystart;
                //Log.i("IN101","move");
                if(Yd<0||Yd>pullH+dragH)
                    break;
                //float Yoffset = Math.min(pullHeight, Yd);
                if(Yd<=pullH) {
                    listLayout.setTranslationY(startHeight + Yd);
                    gif.setTranslationY(startHeightG + Yd / pullH * (refreshHeight-gifHeight/2));
                    gif.setScaleY((1+Yd/pullH));
                    gif.setScaleX((1+Yd/pullH));
                    refresh.setDraw(Yd / pullH);
                }
                else if(Yd<=pullH+dragH){

                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //isBack=true;
                Ycurrent = ev.getY();
                Yd = Ycurrent - Ystart;
                if(Yd<0||Yd>pullH+dragH)
                    break;
                if(Yd<=pullH) {
                    ValueAnimator valueAnimator=ValueAnimator.ofFloat(Yd,0);
                    valueAnimator.setDuration((int)(1000*Yd/pullH));
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float val=(float)animation.getAnimatedValue();
                            listLayout.setTranslationY(startHeight + val);
                            gif.setTranslationY(startHeightG + val / pullH * (refreshHeight-gifHeight/2));
                            gif.setScaleY((1+val/pullH));
                            gif.setScaleX((1+val/pullH));
                            refresh.setDraw(val / pullH);
                        }
                    });
                    valueAnimator.start();
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isRefresh=false;
                        }
                    });
                }
                else if(Yd<=pullH+dragH){
                    if(!isScan){
                        MyApp.pageConnect.toScan();
                    }
                    isRefresh=true;

                }
                /*
                if(Yd<=0)
                    break;
                if(Yd<=pullHeight){
                    ValueAnimator tempAnimator=ValueAnimator.ofFloat(Yd,0);
                    tempAnimator.setDuration((int)(backTime*Yd/pullHeight));
                    tempAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float var=(float)animation.getAnimatedValue();
                            headView.setTranslationY(startHeight+var);
                            headView.setSpringAngle(180*var/pullHeight);
                        }
                    });
                    tempAnimator.start();
                }
                else if(Yd<=backHeight) {
                    ValueAnimator tempAnimator=ValueAnimator.ofFloat(pullHeight,0);
                    tempAnimator.setDuration(backTime);
                    tempAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float var=(float)animation.getAnimatedValue();
                            headView.setTranslationY(startHeight+var);
                            headView.setSpringAngle(180*var*Yd/pullHeight/pullHeight);
                        }
                    });
                    tempAnimator.start();
                }
                else{
                    isRefresh=true;
                    headView.setScanAnimate(true);
                    refreshListener.onRefresh();
                }

                 */

        }
        //return super.onTouchEvent(ev);
        return true;
    }
}
