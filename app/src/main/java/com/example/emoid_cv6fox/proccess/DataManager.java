package com.example.emoid_cv6fox.proccess;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigBT;
import com.example.emoid_cv6fox.Config.ConfigView;

/**
 * @author: wzt
 * @date: 2020/11/28
 */
public class DataManager {
    private  short[]temp;
    private  boolean colectFlag;
    private long time1,time2;//测试时间
    private boolean doFlag=false;

    //采集数，表示到目前为止采集了多少组数据，每组数据大小不一定一样，用于设置绘制间隔的参考
    private int collectNum=0;

    public DataManager(){
        temp=new short[3];
        colectFlag=true;
    }
    public  void pushData(byte []buff,int buffNum) {
        //调用一回采集数加一
        collectNum++;

        //bufNum是SEND_BYTE_NUM的倍数
        int m=buffNum/ ConfigBT.SEND_BYTE_NUM;//buff中的数据量
        for(int i=0;i<m;i++){
            for(int j = 0; j< ConfigBT.CHANNEL_NUM; j++){//
                short v1,v2,v3,v4;
                v1=(short)( buff[i* ConfigBT.SEND_BYTE_NUM+j*4+0]-48);
                v2=(short)( buff[i* ConfigBT.SEND_BYTE_NUM+j*4+1]-48);
                v3=(short)( buff[i* ConfigBT.SEND_BYTE_NUM+j*4+2]-48);
                v4=(short)( buff[i* ConfigBT.SEND_BYTE_NUM+j*4+3]-48);
                temp[j]=(short)(v1*1000+v2*100+v3*10+v4);

                //保存解码的数据
                setPerData((float) temp[j] / 4096 * 3.3f-1.65f,j);//缩放化，变成-1.65 到1.65
            }

            //  显示每条数据收集信息
            // Log.i("IN103", "P:" + temp[0] + "--" + temp[1] + "--" + temp[2] + "   len:" + MyApp.data.dataCollect[0].getLen());

            //测算数据处理和运行模型的时间
            /*
            for (int j = 0; j < ConfigCollect.DEVICE_NUM; j++) {
                //setPerData((float) temp[j] / 4096 * 3.3f,j);
                //测收集1万5个数据的时间
                if(j==0) {
                    if (MyApp.data.dataCollect[0].getFLen() == 0)
                        time1 = System.currentTimeMillis();
                    //else if(MyApp.data.dataCollect[0].getFLen() % 3000 == 0&&doFlag)
                      //  MyApp.pageSetting.bt_pythonTest.callOnClick();

                    else if (MyApp.data.dataCollect[0].getFLen() % 15000 == 0) {
                        doFlag=true;
                        time2 = System.currentTimeMillis();
                        Log.i("IN107", "收集时间:" + (time2 - time1) + "ms");
                        time1 = time2;
                        //进行识别
                        MyApp.pageSetting.bt_pythonTest.callOnClick();
                    }
                }


            }
             */

            if (MyApp.data.dataCollect[0].getLen() % 100 == 0) {
                //更新收集数据的百分比
                MyApp.pageIdentify.dataUpdate();
            }
        }

        if(collectNum%100==0) {
            MyApp.frameIdentify.frameHeart.startDraw();//绘制心电图
        }
    }
    public void setPerData(float data,int channelID) {
        if (colectFlag) {
            MyApp.data.dataCollect[channelID].forcePush(data);
            if (channelID == ConfigView.Heart.DEVICE_ID) {
                MyApp.data.dataDraw.forcePush(data);
                //Log.i("IN101","data: "+data);
            }

        }
    }

}
