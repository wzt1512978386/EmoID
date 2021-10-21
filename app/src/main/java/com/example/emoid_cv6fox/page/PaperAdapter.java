package com.example.emoid_cv6fox.page;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.emoid_cv6fox.page.connect.PageConnect;
import com.example.emoid_cv6fox.page.identify.PageIdentify;
import com.example.emoid_cv6fox.page.Setting.PageSetting;
import com.example.emoid_cv6fox.page.recommend.PageRecommend;
import com.example.emoid_cv6fox.page.statistic.PageStatistic;
import com.example.emoid_cv6fox.Config.ConfigSys;

import java.util.ArrayList;
import java.util.List;

//页面的适配器
public class PaperAdapter extends FragmentPagerAdapter {
    //private static final String[] T=
    private List<Fragment>list=new ArrayList();//页面为Fragment，故列表类型为Fragment
    private final Context context;

    public PaperAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context=context;
        //定义五个页面
        PageConnect pageConnect=new PageConnect();
        PageStatistic pageStatistic=new PageStatistic();
        PageIdentify pageIdentify=new PageIdentify();
        PageRecommend pageRecommend=new PageRecommend();
        PageSetting pageSetting =new PageSetting();

        //放进适配器列表
        list.add(pageConnect);
        list.add(pageStatistic);
        list.add(pageIdentify);
        list.add(pageRecommend);
        list.add(pageSetting);


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return ConfigSys.PAGE_NUM;
    }
}
