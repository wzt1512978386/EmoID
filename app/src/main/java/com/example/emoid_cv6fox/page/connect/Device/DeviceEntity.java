package com.example.emoid_cv6fox.page.connect.Device;

import android.bluetooth.BluetoothDevice;

/**
 * @author: wzt
 * @date: 2020/12/2
 */
public class DeviceEntity {
    public BluetoothDevice dev;//设备
    public String name;//蓝牙名
    protected String addr;//蓝牙地址
    protected int type;//类型
    protected int signal;//信号
    protected boolean isMatched;//是否匹配过
    public DeviceEntity(BluetoothDevice dev,String name, String addr, int type, int signal,boolean isMatched){
        this.dev=dev;
        this.addr=addr;
        this.name=name;
        this.type=type;
        this.signal=signal;
        this.isMatched=isMatched;
    }
}