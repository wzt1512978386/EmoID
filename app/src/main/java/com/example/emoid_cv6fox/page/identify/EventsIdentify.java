package com.example.emoid_cv6fox.page.identify;

import android.view.View;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Cheater.Cheater;
import com.example.emoid_cv6fox.Config.ConfigBT;
import com.example.emoid_cv6fox.Config.ConfigView;
import com.example.emoid_cv6fox.Util.UtilSys;

/**
 * @author: wzt
 * @date: 2020/12/2
 */
public class EventsIdentify {
    //内部调用
    private PageIdentify PI;
    public EventsIdentify(PageIdentify pageIdentify){
        this.PI=pageIdentify;
    }
    public void setEvents(){
        onClick();
    }
    private void onClick(){
        //显示心电图的设备交换
        PI.bt_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MyApp.mainActivity,"kais",Toast.LENGTH_SHORT).show();
                UtilSys.LOG_I("开始数据处理前期工作");
                ConfigView.Heart.DEVICE_ID=(ConfigView.Heart.DEVICE_ID+1)%ConfigBT.CHANNEL_NUM;
                PI.bt_switch.setImageResource(ConfigView.Heart.deviceId[ConfigView.Heart.DEVICE_ID]);
            }
        });
        //心电图幅度增加
        PI.bt_heart_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.frameIdentify.frameHeart.YZoomOUT();
            }
        });
        //心电图府邸减少
        PI.bt_heart_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.frameIdentify.frameHeart.YZoomIN();
            }
        });
        //狐狸按钮事件
        //neut
        PI.bt_cheater1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("1");
                MyApp.cheater.setFileType(1);
            }
        });
        //happy
        PI.bt_cheater2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("2");
                MyApp.cheater.setFileType(0);
            }
        });
        //anger
        PI.bt_cheater3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("3");
                MyApp.cheater.setFileType(3);
            }
        });
        //nothing
        PI.bt_cheater4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("4");
                MyApp.cheater.setFileType(4);
            }
        });
    }
}
