package com.example.emoid_cv6fox.App;

import android.app.Application;
import android.os.Handler;
import android.widget.TextView;

import com.example.emoid_cv6fox.BlueTooth.BtService2;
import com.example.emoid_cv6fox.BlueTooth.BtService;
import com.example.emoid_cv6fox.Cheater.Cheater;
import com.example.emoid_cv6fox.MainActivity;
import com.example.emoid_cv6fox.Model.Classifier;
import com.example.emoid_cv6fox.UI.Connect.FrameConnect;
import com.example.emoid_cv6fox.UI.Identify.FrameIdentify;
import com.example.emoid_cv6fox.UI.Recommend.FrameRecommend;
import com.example.emoid_cv6fox.page.Setting.PageSetting;
import com.example.emoid_cv6fox.page.connect.PageConnect;
import com.example.emoid_cv6fox.page.identify.PageIdentify;
import com.example.emoid_cv6fox.page.recommend.PageRecommend;
import com.example.emoid_cv6fox.page.statistic.PageStatistic;
import com.example.emoid_cv6fox.proccess.AssetsManager;
import com.example.emoid_cv6fox.proccess.Data;
import com.example.emoid_cv6fox.proccess.DataManager;
import com.example.emoid_cv6fox.proccess.PythonManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author: lenovo
 * @date: 2020/11/14
 */
public class MyApp extends Application {
    //全局调用
    public static Data data;//采集的数据
    public static TextView terminal;//终端输出

    //页面
    public static MainActivity mainActivity;
    public static PageConnect pageConnect;
    public static PageStatistic pageStatistic;
    public static PageIdentify pageIdentify;
    public static PageRecommend pageRecommend;
    public static PageSetting pageSetting;

    //python
    public static PythonManager pythonManager;


    //页面框架
    public static FrameIdentify frameIdentify;
    public static FrameRecommend frameRecommend;
    public static FrameConnect frameConnect;

    //线程
    public static final Handler handler = new Handler();
    public static final Executor EXECUTOR = Executors.newCachedThreadPool();

    //服务
    public static BtService2 btService2;
    public static BtService btService;

    //工具
    public static DataManager dataManager;
    public static AssetsManager assetsManager;
    public static Cheater cheater;

    private static  MyApp mInstance;

    //模型
    public static Classifier classifier;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        data=new Data();
    }
    public static MyApp get(){
        return mInstance;
    }

}
