package com.example.emoid_cv6fox.UI.Recommend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigView;

import com.example.emoid_cv6fox.UI.ViewFace;
import com.example.emoid_v2.R;

/**
 * @author: wzt
 * @date: 2020/11/29
 */
public class FramePie extends FrameLayout {
    //绘制工具
    private Paint mPaint,sPaint;//饼状内容、边框
    private TextPaint tPaint,dPaint;//type、data

    //空间长度
    private float Wf,Hf;
    private float Xcf,Ycf;
    private float Rf;
    private float paddig;
    //文本长度
    private float WtPer,Ht;
    private float WdPer,Hd;
    //private String []type={"Happy","Neut","Sadn","Angle"};
    private String []type={"Happy","Neutral","Sadness","Mixed"};
    //数据
    private float result[];
    private float resultSum=0;
    //颜色
    private final int []colorId={R.color.face1,R.color.face2,R.color.face3,R.color.face4};
    //当前识别的情绪
    private int emoResultId=0;
    private ViewFace faceResult;
    private TextView textResult;

    enum STATE{NOTHING,DRAW};
    private STATE state=STATE.DRAW;
    public FramePie(@NonNull Context context) {
        super(context);
        setWillNotDraw(false);
    }
    public FramePie(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    private void initMeasure(){
        paddig=15;

        Wf=getWidth()-2*paddig;
        Hf=getHeight()-2*paddig;
        Xcf=getWidth()/2f;
        Ycf=getHeight()/2f;
        Rf=Math.min(Wf,Hf)/2;



        //文本长度
        Ht=tPaint.getFontMetrics().bottom-tPaint.getFontMetrics().top;
        Hd=dPaint.getFontMetrics().bottom-dPaint.getFontMetrics().top;
        WtPer=tPaint.measureText("h");
        WdPer=dPaint.measureText("h");
    }
    private void init(){
        MyApp.frameRecommend.framePie=this;

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        sPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setColor(getResources().getColor(R.color.styleLevel_003));
        sPaint.setStrokeWidth(4);

        tPaint=new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        tPaint.setTextSize(35);
        tPaint.setTypeface(Typeface.DEFAULT_BOLD);
        tPaint.setColor(getResources().getColor(R.color.colorWhite));

        dPaint=new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        dPaint.setTextSize(20);
        dPaint.setColor(getResources().getColor(R.color.styleLevel_003));

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        initMeasure();

        result=new float[4];
        faceResult=(ViewFace)findViewById(R.id.viewface_recommend_result);
        textResult=(TextView)MyApp.frameRecommend.findViewById(R.id.textview_recommend_result);
    }

    private boolean initFlag=true;
    @Override
    protected void onDraw(Canvas canvas) {
        if(initFlag){
            initFlag=false;
            init();
        }
        if(MyApp.mainActivity.viewPager.getCurrentItem()!=3)///////////非当页面不会绘制
            return;
        switch (state){
            case NOTHING:
                return;
            case DRAW:
                drawPie(canvas);
        }
        super.onDraw(canvas);
    }
    private void drawPie(Canvas canvas) {
        float anglePer;
        RectF rectF = new RectF(paddig, paddig, paddig + Wf, paddig + Hf);
        float startAngle = -90;
        if (resultSum == 0) {
            for(int i=0;i<4;i++)
                result[i]=1;
            anglePer=90;
        }
        else{
            anglePer = 360 / resultSum;
        }

        float angleScale=(float)(Math.PI/180f);
        for (int i = 0; i < 4; i++) {
            mPaint.setColor(getResources().getColor(colorId[i]));
            canvas.drawArc(rectF, startAngle, result[i] * anglePer, true, mPaint);


            //canvas.drawText(type[i],Xcf+Rf*cos/2,Ycf+Rf*sin/2,tPaint);-WtPer*type[i].length()/
            startAngle += result[i] * anglePer;
        }
        startAngle = -90;
        for (int i = 0; i < 4; i++) {
            float angleTemp=startAngle+result[i] * anglePer/2;
            float cos=(float) Math.cos(angleTemp*angleScale);
            float sin=(float) Math.sin(angleTemp*angleScale);
            canvas.drawText(type[i],Xcf+Rf*cos*2/3-type[i].length()*10,Ycf+Rf*sin*2/3+Ht/4,tPaint);
            //canvas.drawText(type[i],Xcf+Rf*cos/2,Ycf+Rf*sin/2,tPaint);-WtPer*type[i].length()/
            startAngle += result[i] * anglePer;
        }

        canvas.drawCircle(Xcf, Ycf, Rf, sPaint);
    }
    public void setResult(float []result){
        resultSum=0;
        int maxId=0;
        float maxResult=result[0];
        for(int i=0;i<4;i++){
            this.result[i]=result[i];
            resultSum+=result[i];
            if(maxResult<result[i]) {
                maxId=i;
                maxResult=result[i];
            }
        }
        if(emoResultId!=maxId) {
            emoResultId = maxId;
            faceResult.setType(maxId);
            textResult.setText(ConfigView.Face.name[maxId]);
            textResult.setTextColor(getResources().getColor(ConfigView.Face.colorId[maxId]));
            GradientDrawable drawable=(GradientDrawable) textResult.getBackground();
            drawable.setColor(getResources().getColor(ConfigView.Face.color_dId[maxId]));
        }
        invalidate();
    }
    public void flash(){
        invalidate();
    }
}
