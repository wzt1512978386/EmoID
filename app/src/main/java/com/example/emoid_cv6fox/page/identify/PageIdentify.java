package com.example.emoid_cv6fox.page.identify;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Cheater.Cheater;
import com.example.emoid_cv6fox.Config.ConfigCollect;
import com.example.emoid_v2.R;


import java.util.Arrays;

import pl.droidsonroids.gif.GifImageView;

public class PageIdentify extends Fragment {

    //内部调用
    private View root;


    //控件
    private TextView collectRate;//数据收集率
    private ImageView[]connState;//设备连接情况
    protected GifImageView bt_man;//中间的人

    //按钮
    protected ImageButton bt_switch;//显示心电图的设备交换
    protected ImageButton bt_heart_up;
    protected ImageButton bt_heart_down;

    //狐狸按钮
    protected Button bt_cheater1;//neut
    protected Button bt_cheater2;//happy
    protected Button bt_cheater3;//anger
    protected Button bt_cheater4;//nothing

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.page_identify, container, false);//设置对应布局
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyApp.pageIdentify=this;

        initCollectRate();
        initConnState();

        //爱心按钮初始化
        bt_switch=(ImageButton)root.findViewById(R.id.imagebutton_identify_heart_switch);
        bt_heart_up=(ImageButton)root.findViewById(R.id.imageview_identify_smallheart_2);
        bt_heart_down=(ImageButton)root.findViewById(R.id.imageview_identify_smallheart_1);

        //狐狸按钮初始化
        bt_cheater1=(Button)root.findViewById(R.id.button_identify_cheater1);
        bt_cheater2=(Button)root.findViewById(R.id.button_identify_cheater2);
        bt_cheater3=(Button)root.findViewById(R.id.button_identify_cheater3);
        bt_cheater4=(Button)root.findViewById(R.id.button_identify_cheater4);
        //控件获取
        //bt_man=(GifImageView) root.findViewById(R.id.gif_man);
        //GifDrawable gifDrawable=(GifDrawable) bt_man.getDrawable();
        //gifDrawable.stop();
        //设置响应事件
        new EventsIdentify(this).setEvents();

    }
    private void initCollectRate(){
        collectRate=(TextView)root.findViewById(R.id.textview_identify_section_righttop_rate);
        collectRate.setText(String.format(getResources().getString(R.string.collectRate),0));
    }
    private void initConnState(){
        connState=new ImageView[ConfigCollect.DEVICE_NUM];
        connState[0]=(ImageView)root.findViewById(R.id.imageview_identify_connstate1);
        connState[1]=(ImageView)root.findViewById(R.id.imageview_identify_connstate2);
        connState[2]=(ImageView)root.findViewById(R.id.imageview_identify_connstate3);
        setConnState(false);
    }

    private boolean connflag=false;
    public void setConnState(boolean state){
        connflag=state;
        for(int i = 0; i< ConfigCollect.DEVICE_NUM; i++){
            if(state)
                connState[i].setImageResource(R.drawable.p_image_correct);
            else
                connState[i].setImageResource(R.drawable.p_image_wrong);
        }
    }

    private int times=0;//简单的计算识别结果的间隔  狐狸
    //收集数据有更新的
    public void dataUpdate(){
        times++;
        /*不能在子线程里直接更改UI  “setText”
        collectRate.setText(String.format(getResources().getString(R.string.collectRate),
                (int)(MyApp.data.dataCollect[0].getLen()*100f/ConfigCollect.COLLECT_NUM)));

        //假的数据更新
        //collectRate.setText(String.format(getResources().getString(R.string.collectRate),
        //        (int)(MyApp.cheater.dataCollectLen*100f/ConfigCollect.COLLECT_NUM)));
        if(!connflag) {
            setConnState(true);
        }
        */



        //若收集完则不用再更新
        if(MyApp.cheater.isCollectFinshed) {
            Log.i("IN111", ""+times);
            if(times%200==0) {
                float rates[] = new float[4];

                for (int i = 0; i < 4; i++)
                    rates[i] = (float) Math.random();
                Arrays.sort(rates);
                int type = MyApp.cheater.curType;
                if (type >= 0 && type < 4) {
                    rates[type] = 1f;//(float)(rates[type]*(2 + Math.random() * 2)+0.3f);
                } else if (type == 4) {
                    for (int i = 0; i < 4; i++) {
                        rates[i] = 1;
                    }
                }

                MyApp.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyApp.frameRecommend.framePie.setResult(rates);
                    }
                });

            }
            return;
        }

        MyApp.handler.post(new Runnable() {
            @Override
            public void run() {
                collectRate.setText(String.format(getResources().getString(R.string.collectRate),
                        //(int)(MyApp.data.dataCollect[0].getLen()*100f/ConfigCollect.COLLECT_NUM_REAL)));
                        (int)(MyApp.cheater.dataCollectLen*100f/ConfigCollect.COLLECT_NUM_REAL)));
                if(!connflag) {
                    setConnState(true);
                }
            }
        });



    }


}
