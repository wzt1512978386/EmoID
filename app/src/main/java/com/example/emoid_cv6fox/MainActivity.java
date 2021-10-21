package com.example.emoid_cv6fox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Cheater.Cheater;
import com.example.emoid_cv6fox.page.PaperAdapter;
import com.example.emoid_cv6fox.Config.ConfigSys;
import com.example.emoid_cv6fox.Broadcast.MyBroadcastReceiver;
import com.example.emoid_cv6fox.Util.UtilSys;
import com.example.emoid_cv6fox.proccess.DataManager;
import com.example.emoid_cv6fox.proccess.PythonManager;
import com.example.emoid_v2.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    //外部调用
    public Context context;
    public ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;

    //控件
    View []tabButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定
        context=this;
        MyApp.mainActivity=this;
        MyApp.dataManager=new DataManager();
        MyApp.cheater=new Cheater(this);
        MyApp.pythonManager=new PythonManager(this);



        setContentView(R.layout.activity_main);
        requestPermission();
        initPager();
        initTab();
        initBroadcast();


    }
    //权限申请
    private void requestPermission() {
        UtilSys.requestPermisson(this, Manifest.permission.BLUETOOTH_ADMIN,0);
        UtilSys.requestPermisson(this, Manifest.permission.BLUETOOTH,0);
        UtilSys.requestPermisson(this,Manifest.permission.WRITE_EXTERNAL_STORAGE,0);
        UtilSys.requestPermisson(this,Manifest.permission.READ_EXTERNAL_STORAGE,0);
        //Android10的蓝牙扫描要添加定位权限
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i("IN101", "sdk < 28 Q");
            UtilSys.requestPermisson(this,Manifest.permission.ACCESS_FINE_LOCATION, 1);
            UtilSys.requestPermisson(this,Manifest.permission.ACCESS_COARSE_LOCATION, 1);
        }
        else {
            UtilSys.requestPermisson(this,Manifest.permission.ACCESS_FINE_LOCATION, 2);
            UtilSys.requestPermisson(this,Manifest.permission.ACCESS_COARSE_LOCATION, 2);
            UtilSys.requestPermisson(this, "android.permission.ACCESS_BACKGROUND_LOCATION", 2);
        }
    }
    //页面初始化
    private void initPager(){
        //获取
        viewPager=findViewById(R.id.viewpage_main);
        pagerAdapter=new PaperAdapter(this,getSupportFragmentManager());
        //
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(ConfigSys.PAGE_NUM);//设置缓存页面数！！！
        viewPager.setCurrentItem(ConfigSys.INIT_PAGE_ID);//当前页面

    }
    //导航栏初始化
    private void initTab(){
        final String []title={"蓝牙","统计","","推荐","更多"};
        final Integer []Rid={R.drawable.tab_1,R.drawable.tab_2,0,R.drawable.tab_3,R.drawable.tab_4};
        tabLayout=(TabLayout)findViewById(R.id.tablayout_main);
        tabLayout.setupWithViewPager(viewPager);
        tabButton=new View[ConfigSys.PAGE_NUM];
        for(int i = 0; i< ConfigSys.PAGE_NUM; i++) {
            if(i==ConfigSys.INIT_PAGE_ID)
                continue;
            tabButton[i]= LayoutInflater.from(this).inflate(R.layout.item_tab,null);
            tabButton[i].setAlpha(0.5F);
            //获取图标和图标名
            ImageView icon=(ImageView)tabButton[i].findViewById(R.id.imageview_main_tab_item);
            TextView iconName=(TextView)tabButton[i].findViewById(R.id.textview_main_tab_item);
            //设置属性
            icon.setImageResource(Rid[i]);
            iconName.setText(title[i]);
            tabLayout.getTabAt(i).setCustomView(tabButton[i]);
        }
        //选择中间
        tabLayout.getTabAt(ConfigSys.INIT_PAGE_ID).select();

        final ImageButton bt_tabm=(ImageButton) findViewById(R.id.imagebutton_main_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//标签栏选中的不同情况
                if(tab.getPosition()==3)
                    MyApp.frameRecommend.framePie.flash();
                if(tab.getPosition()!=ConfigSys.INIT_PAGE_ID)
                    tabButton[tab.getPosition()].setAlpha(1.0f);
                else {
                    bt_tabm.setY(bt_tabm.getY() + 6);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition()!=ConfigSys.INIT_PAGE_ID)
                    tabButton[tab.getPosition()].setAlpha(0.5f);
                else {
                    bt_tabm.setY(bt_tabm.getY() - 6);
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        bt_tabm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(ConfigSys.INIT_PAGE_ID);
            }
        });
    }
    private void initBroadcast(){
        new MyBroadcastReceiver().register(this);
    }

}