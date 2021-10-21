package com.example.emoid_cv6fox.UI.Statistic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;


import com.example.emoid_cv6fox.UI.ViewFace;
import com.example.emoid_cv6fox.room.Record;
import com.example.emoid_cv6fox.room.RecordDataBase;
import com.example.emoid_cv6fox.Util.UtilDate;
import com.example.emoid_v2.R;

import java.util.List;

/**
 * @author: wzt
 * @date: 2020/11/14
 */
public class ViewLineChart extends View {
    private Context context;
    private Paint bPaint,sPaint,cPaint;
    private TextPaint tPaint;
    private Path sPath;

    //private final int[]ColorId={R.color.colorFace1,R.color.colorFace2,R.color.colorFace3,R.color.colorFace4};
    private final int []colorId={R.color.face1,R.color.face2,R.color.face3,R.color.face4};

    enum STATE{NOTHING,DRAW,COR}
    private STATE state=STATE.COR;

    private int numShow;//要展示的天数
    private int curY,curM,curD;

    private ViewFace viewFace;
    private Bitmap mBmp;
    private Canvas mCanvas;

    //长度
    private float Wtext;
    private float Htext;

    public ViewLineChart(Context context) {
        super(context);
        init(context);
    }

    public ViewLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        this.context=context;
        //画笔的定义
        bPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        sPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//阴影
        cPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//坐标轴
        tPaint=new TextPaint(TextPaint.ANTI_ALIAS_FLAG);

        bPaint.setStyle(Paint.Style.STROKE);
        sPaint.setStyle(Paint.Style.FILL);
        cPaint.setStyle(Paint.Style.STROKE);

        cPaint.setStrokeWidth(2);
        cPaint.setColor(0xff000000);
        cPaint.setAlpha((int)(0.1*255));

        bPaint.setColor(0xff000000);
        bPaint.setStrokeWidth(4);
        viewFace =new ViewFace(context);

        sPaint.setColor(getResources().getColor(R.color.styleLevel_001));
        sPaint.setAlpha(50);
        sPath=new Path();

        tPaint.setColor(getResources().getColor(R.color.styleLevel_005));
        tPaint.setTextSize(20f);
        Htext=tPaint.getFontMetrics().bottom-tPaint.getFontMetrics().top;
        Wtext=tPaint.getTextWidths("00",new float[]{20f,20f});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(state==STATE.COR) {
            mBmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            //mCanvas.setBitmap(mBmp);
            mCanvas = new Canvas(mBmp);
        }

        super.onDraw(canvas);
        switch (state){
            case COR:
                drawCoor(mCanvas);
                state=STATE.NOTHING;
                canvas.drawBitmap(mBmp,0,0,bPaint);
                //setBackground(new BitmapDrawable(mBmp));
                break;
            case DRAW:
                canvas.drawBitmap(mBmp,0,0,bPaint);
                drawBrokenLine(canvas);
                break;
            case NOTHING:
                canvas.drawBitmap(mBmp,0,0,bPaint);
                //drawCoor(canvas);
                break;

        }
    }
    private void drawCoor(Canvas canvas){
        float Yper=getHeight()/4f;
        float Xpad=Yper;
        for(int i=0;i<5;i++)
            canvas.drawLine(0,Yper*i,getWidth(),Yper*i,cPaint);
        for(int i=0;i<4;i++){
            viewFace.setPos(Xpad/2,Yper/2+Yper*i,Yper*1/3);
            viewFace.setTF(i,0);
            viewFace.draw(canvas);
        }
    }
    private void drawBrokenLine(Canvas canvas){
        float Yper=getHeight()/4f;
        float Xpad=Yper;
        float Xper=(getWidth()-20-Xpad)/(numShow-1);
        int Y=curY;
        int M=curM;
        int D=curD;
        float []data=new float[numShow];
        for(int i=0;i<numShow;i++){
            canvas.drawText(String.valueOf(D),Xpad+Xper*(numShow-1-i)-10,getHeight()-Yper/6+Htext/2,tPaint);
            data[numShow-i-1]=5-getData(Y,M,D);
            D--;
            if(D<1){
                M--;
                if(M<1){
                    Y--;
                    M=12;
                }
                D= UtilDate.getDaysOfMonth(Y,M);
            }
            //绘制坐标

        }
        float startX=0,startY=1;
        sPath.reset();
        sPath.moveTo(Xpad+startX*Xper,getHeight()-(-Yper/2+startY*Yper));
        float endX,endY;
        boolean flagStart=false;
        for(int i=0;i<numShow;i++){
            if(data[i]!=5) {
                if(!flagStart) {
                    flagStart=true;
                    startY = data[i];
                    sPath.moveTo(Xpad+startX*Xper,getHeight()-(-Yper/2+startY*Yper));
                }

                bPaint.setColor(getResources().getColor(colorId[4-(int)(data[i]+0.5)]));
                //bPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(Xpad+i*Xper,getHeight()-(-Yper/2+data[i]*Yper),Yper/12,bPaint);
                //bPaint.setStyle(Paint.Style.STROKE);
                endX=i;
                endY=data[i];
                sPath.lineTo(Xpad+endX*Xper,getHeight()-(-Yper/2+endY*Yper));
                float startX_=startX;
                float startY_=startY;
                int j;
                if(endY>startY) {
                    for (j = (int) (startY+0.5) +1; j <= (int) (endY+0.5f); j++) {
                        bPaint.setColor(getResources().getColor(colorId[5 - j]));
                        canvas.drawLine(startX_ * Xper + Xpad, getHeight() - ( -Yper/2+startY_ * Yper), Xpad + Xper * ((endX - startX) / (endY - startY) * (j - startY - 0.5f) + startX), getHeight() - (-Yper/2+ (j - 0.5f) * Yper), bPaint);
                        startX_ = ((endX - startX) / (endY - startY) * (j - 0.5f - startY) + startX);
                        startY_ = j - 0.5f;
                    }
                    bPaint.setColor(getResources().getColor(colorId[5-j]));
                    canvas.drawLine(startX_*Xper+Xpad,getHeight()-(-Yper/2+startY_*Yper),endX*Xper+Xpad,getHeight()-(-Yper/2+endY*Yper),bPaint);
                    //canvas.drawLine(startX,startY,Xpad+i*Xper,getHeight()-(Yper/2+Yper*data[i]),bPaint);
                    startX=i;
                    startY=data[i];

                }
                else {
                    for (j = (int)(startY+0.5) ; j >= (int) (endY+0.5)+1; j--) {
                        bPaint.setColor(getResources().getColor(colorId[4-j]));
                        canvas.drawLine(startX_ * Xper + Xpad, getHeight() - (-Yper/2+startY_ * Yper), Xpad + Xper * ((endX - startX) / (endY - startY) * (j - startY - 0.5f) + startX), getHeight() - (-Yper/2+ (j - 0.5f) * Yper), bPaint);
                        startX_ = ((endX - startX) / (endY - startY) * (j - 0.5f - startY) + startX);
                        startY_ = j - 0.5f;
                    }
                    bPaint.setColor(getResources().getColor(colorId[4-j]));
                    canvas.drawLine(startX_*Xper+Xpad,getHeight()-(-Yper/2+startY_*Yper),endX*Xper+Xpad,getHeight()-(-Yper/2+endY*Yper),bPaint);
                    //canvas.drawLine(startX,startY,Xpad+i*Xper,getHeight()-(Yper/2+Yper*data[i]),bPaint);
                    startX=i;
                    startY=data[i];
                }

            }
        }/////////////////////
        if(data[numShow-1]==5){
            canvas.drawLine(startX*Xper+Xpad,getHeight()-(-Yper/2+startY*Yper),startX*Xper+Xpad,getHeight()-(-Yper/2+startY*Yper),bPaint);
        }
        sPath.lineTo(getWidth()-20,getHeight()-Yper/2+Yper/4);
        sPath.lineTo(Xpad,getHeight()-Yper/2+Yper/4);
        canvas.drawPath(sPath,sPaint);
    }
    public void setShowDetail(int numShow,int curY,int curM,int curD){
        this.numShow=numShow;
        this.curY=curY;
        this.curM=curM;
        this.curD=curD;
        state=STATE.DRAW;
        invalidate();
    }





    private float getData(int Y, int M, int D){
        List<Record> tempList=RecordDataBase.get().getRecordDao().findByDate(Y,M,D);
        float sum=0;
        for(int i=0;i<tempList.size();i++)
            sum+=tempList.get(i).type;
        if(tempList.size()!=0)
            sum/=tempList.size();
        tempList=null;
        Log.i("IN102",Y+" "+M+" "+D+" sum:"+sum);
        return sum;
    }

}
