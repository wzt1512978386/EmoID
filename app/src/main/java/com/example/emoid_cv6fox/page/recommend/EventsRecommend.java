package com.example.emoid_cv6fox.page.recommend;

import android.view.View;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Util.UtilSys;
import com.example.emoid_cv6fox.page.identify.PageIdentify;

/**
 * @author: wzt
 * @date: 2021/4/21
 */
public class EventsRecommend {
    //内部调用
    private PageRecommend PR;
    public EventsRecommend(PageRecommend pageRecommend){
        this.PR=pageRecommend;
    }
    public void setEvents(){
        onClick();
    }
    private void onClick() {
        //狐狸事件初始化
        //neut
        PR.bt_cheater1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("1");
                MyApp.cheater.setFileType(1);
            }
        });
        //happy
        PR.bt_cheater2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("2");
                MyApp.cheater.setFileType(0);
            }
        });
        //anger
        PR.bt_cheater3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("3");
                MyApp.cheater.setFileType(3);
            }
        });
        //nothing
        PR.bt_cheater4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UtilSys.sendInfo("4");
                //MyApp.cheater.setFileType(4);
                MyApp.cheater.setFileType(2);/////////////////////////////
            }
        });
    }

}
