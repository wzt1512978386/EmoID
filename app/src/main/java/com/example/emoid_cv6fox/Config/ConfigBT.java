package com.example.emoid_cv6fox.Config;

import java.util.UUID;

/**
 * @author: wzt
 * @date: 2020/12/1
 */
public class ConfigBT {
    //BT设备
    public static final int SEND_BYTE_NUM=12;//蓝牙设备一次发送的字节数
    //public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//UID
      public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final int CHANNEL_NUM=3;//蓝牙设备通道数



    public static int SCAN_TIME=5000;//蓝牙扫描时间ms
}
