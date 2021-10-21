package com.example.emoid_cv6fox.proccess;

import com.example.emoid_cv6fox.Config.ConfigCollect;
import com.example.emoid_cv6fox.Config.ConfigView;
import com.example.emoid_cv6fox.Util.MyList;

/**
 * @author: wzt
 * @date: 2020/11/28
 */
public class Data {
    //全局数据
    public MyList dataDraw;//绘制的数据
    public MyList []dataCollect;//从蓝牙收集的数据
    //
    public Data(){
        int drawNum= ConfigView.Heart.DRAW_DATA_NUM;
        int collectNum= ConfigCollect.COLLECT_NUM;
        dataDraw=new MyList(drawNum);
        dataCollect=new MyList[ConfigCollect.DEVICE_NUM];
        for(int i = 0; i< ConfigCollect.DEVICE_NUM; i++){
            dataCollect[i]=new MyList(collectNum);
        }
    }
    public void updateDrawLen(){
        int drawNum= ConfigView.Heart.DRAW_DATA_NUM;
        dataDraw=new MyList(drawNum);
    }

}
