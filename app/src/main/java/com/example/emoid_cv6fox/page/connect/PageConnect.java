package com.example.emoid_cv6fox.page.connect;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.BlueTooth.BtService;
import com.example.emoid_cv6fox.BlueTooth.BtService2;
import com.example.emoid_cv6fox.Enum.STATE;

import com.example.emoid_cv6fox.page.connect.Device.DeviceAdpter;
import com.example.emoid_cv6fox.page.connect.Device.DeviceEntity;
import com.example.emoid_cv6fox.Util.UtilSys;
import com.example.emoid_v2.R;

import java.util.ArrayList;
import java.util.List;

public class PageConnect extends Fragment {
    //本身定义
    private View root;
    private Activity activity;
    private Context context;
    //蓝牙服务
    protected BtService btService;//假的蓝牙设备
    protected BtService2 btService2;//真正的蓝牙设备

    //动画定义


    //下滑布局
    public List<DeviceEntity> deviceList;
    protected DeviceAdpter deviceAdpter;
    private RecyclerView deviceListView;

    //状态
    public STATE scanST=STATE.notdo;//判断是否正在扫描
    public STATE connectST=STATE.notdo;//判断是否正在连接
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.page_connect, container, false);//设置对应布局
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        context=getContext();
        MyApp.pageConnect=this;


        initBT();//蓝牙初始化
        initRecycler();//适配器初始化

        new EventsConnect(this).setEvents();
    }


    public void initBT(){
        btService =new BtService();//假的蓝牙设备
        btService2 =new BtService2();//真的蓝牙设备
    }

    public void initRecycler(){
        //获取
        deviceListView=root.findViewById(R.id.recylerview_connect);
        deviceList=new ArrayList<>();
        deviceAdpter=new DeviceAdpter(activity,deviceList);

        deviceListView.setAdapter(deviceAdpter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(activity);//线性布局
        deviceListView.setLayoutManager(layoutManager);
    }


    public void toScan(){
        deviceList.clear();
        deviceAdpter.notifyDataSetChanged();
        btService.startScan();
        UtilSys.sendInfo("开始扫描蓝牙设备");
    }



    public void foundDev(BluetoothDevice dev) {
        if(dev.getName()==null)
            deviceList.add(new DeviceEntity(dev,"N/A",dev.getAddress(),dev.getType(),20,false));
        else
            deviceList.add(new DeviceEntity(dev,dev.getName(),dev.getAddress(),dev.getType(),20,false));
        deviceAdpter.notifyDataSetChanged();
    }
}
