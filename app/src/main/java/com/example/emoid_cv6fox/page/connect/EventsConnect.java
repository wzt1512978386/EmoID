package com.example.emoid_cv6fox.page.connect;

import android.bluetooth.BluetoothDevice;

import com.example.emoid_cv6fox.Cheater.Cheater2;
import com.example.emoid_cv6fox.Util.UtilSys;
import com.example.emoid_cv6fox.page.connect.Device.DeviceListener;

/**
 * @author: wzt
 * @date: 2020/12/2
 */
public class EventsConnect {
    private PageConnect PC;
    public EventsConnect(PageConnect pageConnect){this.PC=pageConnect;}
    public void setEvents(){ OnClick(); }
    private void OnClick(){
        PC.deviceAdpter.setDeviceListener(new DeviceListener() {
            @Override
            public void onDeviceClick(BluetoothDevice dev) {
                //PC.btService.connect(dev);//--------------------------狐狸
                //PC.btService2.connect(dev);
                //老狐狸
                Cheater2.doCheater();
                UtilSys.sendInfo("开始连接设备："+dev.getName());
            }
        });
    }

}
