package com.example.emoid_cv6fox.Broadcast;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.example.emoid_cv6fox.App.MyApp;

public  class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String info,str;
        switch (intent.getAction()){
            case "com.INFO":
                info=intent.getStringExtra("info");
                Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
                break;
            case "com.LOGI":
                info=intent.getStringExtra("info");
                MyApp.terminal.append("\n I : "+info);
                break;
            case "com.LOGW":
                info=intent.getStringExtra("info");
                MyApp.terminal.append("\n W : "+info);
                break;
            case BluetoothDevice.ACTION_FOUND:      //蓝牙设备查找到
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //Log.i("IN101","找到了,有|name："+device.getName()+"|type:" +device.getType()
                //+"|calss"+device.getBluetoothClass()+"|Alia"+device.getAlias());
                //UUID deviceUuid=device.;
                if(device.getName()==null)//不显示没有名字的设备
                    break;
                if(!MyApp.btService.devList.contains(device)) {//判断设备是否已经存在
                    MyApp.btService.devList.add(device);
                    MyApp.pageConnect.foundDev(device);
                }
                break;
            default:break;
        }
    }
    public void register(Activity activity){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.INFO");
        intentFilter.addAction("com.LOGI");
        intentFilter.addAction("com.LOGW");
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(this,intentFilter);
    }
}