package com.example.emoid_cv6fox.BlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigBT;
import com.example.emoid_cv6fox.Enum.STATE;
import com.example.emoid_cv6fox.Util.UtilSys;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: wzt
 * @date: 2020/12/4
 */
public class BtService {
    //蓝牙工具
    private BluetoothAdapter btAdapter;
    public List<BluetoothDevice> devList;
    private BluetoothSocket btSocket;
    //读状态
    private boolean isRead=false;
    //日记标签
    private final String TAG="IN104";

    public BtService(){
        MyApp.btService=this;//绑定
        //初始化
        btAdapter=BluetoothAdapter.getDefaultAdapter();
        btSocket=null;
        devList=new ArrayList<>();
        if(!btAdapter.isEnabled())
            btAdapter.enable();
    }
    //开始蓝牙扫描
    public void startScan(){
        //先找之前配对过的
        devList.clear();
        MyApp.pageConnect.deviceList.clear();
        Set<BluetoothDevice> devSet=btAdapter.getBondedDevices();
        devList.addAll(devSet);
        for(BluetoothDevice dev:devList)
            MyApp.pageConnect.foundDev(dev);

        //寻找新的
        if(btAdapter.isDiscovering())//保证重新开始查找
           btAdapter.cancelDiscovery();
        btAdapter.startDiscovery();
        MyApp.pageConnect.scanST= STATE.doing;
        Log.i(TAG,"蓝牙开始扫描!");
        UtilSys.sendInfo("蓝牙开始扫描");

        //设置规定时间后结束
        MyApp.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              stopScan();
            }
        }, ConfigBT.SCAN_TIME);
    }
    //停止蓝牙扫描
    private void stopScan(){
        if(btAdapter.isDiscovering())//保证停止查找
            btAdapter.cancelDiscovery();
        Log.i(TAG,"蓝牙扫描结束!");
        UtilSys.sendInfo("蓝牙扫描结束");
    }
    //进行蓝牙连接
    public void connect(BluetoothDevice dev){
        //先关闭socket
        closeSocket();
        //尝试获取socket
        try {
            btSocket=dev.createInsecureRfcommSocketToServiceRecord(ConfigBT.SPP_UUID);
        } catch (IOException e) {
            Log.i(TAG,"Socket创建失败");
            UtilSys.sendInfo("Socket创建失败");
            return;
        }
        //连接和读数据都耗时，故要放在子线程中
        MyApp.EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                //尝试socket进行连接
                try {
                    MyApp.pageConnect.connectST=STATE.doing;
                    btSocket.connect();
                    Log.i(TAG,"设备连接成功！");
                    UtilSys.sendInfo("设备连接成功！");

                    //设置默认读取的数据来源为neut
                    MyApp.cheater.setFileType(1);

                    MyApp.pageConnect.connectST=STATE.done;
                    //0.5s后自动跳转到识别界面
                    MyApp.handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyApp.mainActivity.viewPager.setCurrentItem(2, true);
                        }
                    }, 500);

                } catch (IOException e) {
                    Log.i(TAG,"设备连接失败");
                    UtilSys.sendInfo("设备连接失败");
                    MyApp.pageConnect.connectST=STATE.notdo;
                    return;//连接失败就直接退出
                }
                //开始接受数据
                loopRead();
            }
        });

    }
    //不断接收数据
    private void loopRead(){
        DataInputStream in=null;
        try {
             in= new DataInputStream(btSocket.getInputStream ());
        } catch (IOException e) {
            Log.i(TAG,"读入流创建失败");
            UtilSys.sendInfo("读入流创建失败");
            return;
        }
        isRead=true;//让读
        while(isRead){
            try {
                String msg=in.readUTF();
                if(!msg.trim().isEmpty()) {
                    //UtilSys.LOG_I(msg);
                    MyApp.cheater.getCmdMsg(msg);
                }
            } catch (IOException e) {
                Log.i(TAG,"该段信息读入失败");
                UtilSys.LOG_W("该段信息读入失败");
            }
        }
    }
    //关闭Socket，为下次连接做准备
    private void closeSocket(){
        if(btSocket!=null) {
            try {
                isRead=false;//顺便不让读
                btSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
