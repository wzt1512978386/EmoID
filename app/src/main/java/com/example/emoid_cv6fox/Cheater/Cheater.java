package com.example.emoid_cv6fox.Cheater;

import android.app.Activity;
import android.util.Log;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigBT;
import com.example.emoid_cv6fox.Config.ConfigCollect;
import com.example.emoid_cv6fox.Config.ConfigView;
import com.example.emoid_cv6fox.Enum.STATE;
import com.example.emoid_cv6fox.proccess.AssetsManager;

/**
 * @author: wzt
 * @date: 2020/12/4
 */
public class Cheater {
    private String fileName;
    private int fs = 1000;
    public int drawTimes=0;
    public int curType;
    public int dataCollectLen=0;
    public boolean isCollectFinshed=false;

    public Cheater(Activity activity) {
        MyApp.assetsManager = new AssetsManager(activity);
    }

    public void setFileType(int type) {
        if(MyApp.pageConnect.connectST!= STATE.done)
            return;

        this.curType=type;
        MyApp.assetsManager.initReader(type);
        drawHeartLine();
    }

    private void drawHeartLine() {
        drawTimes++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    int curDrawTime=drawTimes;
                    Log.i("IN101","开始接受数据");
                    for (int i = 0; i < ConfigBT.CHANNEL_NUM; i++) {

                        for (int j = 0; j < ConfigCollect.COLLECT_NUM; j++) {
                            if(drawTimes!=curDrawTime) {
                                Log.i("IN101","更换数据，该绘制线程死掉");
                                return;
                            }
                            float temp;
                            if(curType==4)
                                temp=(float)( (Math.random()-0.5)/50);
                            else
                                temp = MyApp.assetsManager.readFloatData();
                            
                            if(i!= ConfigView.Heart.DEVICE_ID)
                                continue;


                            dataCollectLen=Math.min(dataCollectLen+1,ConfigCollect.COLLECT_NUM_REAL);//判断是否收集满

                            MyApp.pageIdentify.dataUpdate();//更新百分比

                            //判断是否收集完
                            if(dataCollectLen==ConfigCollect.COLLECT_NUM_REAL&&!isCollectFinshed) {
                                isCollectFinshed = true;
                                //若收集完一组，则会在0.5s内跳到结果界面
                                MyApp.handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyApp.mainActivity.viewPager.setCurrentItem(3, true);
                                    }
                                }, 500);

                            }

                            MyApp.data.dataDraw.forcePush(temp);
                            if(j%(int)(fs/10)==0) {
                                try {
                                    if(j%(int)(fs)==0) {
                                        MyApp.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                MyApp.frameIdentify.viewRipple.startDraw();
                                                if(!isCollectFinshed) {
                                                    MyApp.frameIdentify.viewWave.setRate(MyApp.cheater.dataCollectLen * 1f / ConfigCollect.COLLECT_NUM_REAL+0.05f);
                                                }
                                                else{
                                                    MyApp.frameIdentify.viewWave.leave();
                                                }
                                            }
                                        });
                                    }
                                    wait((int)(fs/10));
                                } catch (InterruptedException e) {
                                    Log.i("IN101", "wait不了");
                                }
                                MyApp.frameIdentify.frameHeart.startDraw();
                            }
                            //Log.i("IN101","temp:"+temp);
                        }
                    }
                    //重新再绘制一次，有循环效果
                    setFileType(curType);
                    Log.i("IN101","该数据源已绘制完毕，重新选择同种类型数据源进行绘制");
                }
            }
        }).start();
    }
    private final int RATE=1;//情绪比例
    private final int HEART=2;//心电图

    public void getCmdMsg(String msg) {
        int cmd = Integer.parseInt(msg.split("--")[0]);
        switch (cmd) {
            case RATE:
                //Log.i("IN101","接受到更改结果指令");
                String[] rateStr = msg.split("--");
                float[] rates = new float[4];
                for (int i = 0; i < 4; i++) {
                    rates[i] = Float.parseFloat(rateStr[i + 1]);
                    rates[i]+=(Math.random()-0.5)*5;
                }
                MyApp.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyApp.frameRecommend.framePie.setResult(rates);
                    }
                });

                break;
            case HEART:
                String typeStr=msg.split("--")[1];
                int type=Integer.parseInt(typeStr);
                //Log.i("IN101","接受到更改绘制数据指令，为"+type);
                MyApp.cheater.setFileType(type);
                break;
        }
    }
}
