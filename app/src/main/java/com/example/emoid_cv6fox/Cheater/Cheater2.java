package com.example.emoid_cv6fox.Cheater;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Enum.STATE;
import com.example.emoid_cv6fox.Util.UtilSys;

/**
 * @author: wzt
 * @date: 2021/4/21
 */
public class Cheater2 {
    public static void doCheater(){
        MyApp.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MyApp.pageConnect.connectST=STATE.done;
                UtilSys.sendInfo("设备连接成功!");
                //设置默认读取的数据来源为neut
                MyApp.cheater.setFileType(1);
                MyApp.mainActivity.viewPager.setCurrentItem(2, true);
                MyApp.pageConnect.connectST= STATE.done;
            }
        }, 2000);

    }
}
