package com.example.emoid_cv6fox.BlueTooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigBT;
import com.example.emoid_cv6fox.Enum.STATE;
import com.example.emoid_cv6fox.page.connect.PageConnect;
import com.example.emoid_cv6fox.Util.UtilSys;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BTService_old {
    private Activity activity;
    private PageConnect fragment;
    private BluetoothAdapter btAdapter;
    private ArrayList<BluetoothDevice> devicesList;
    private Handler handler = new Handler();
    private LocalBroadcastManager localBroadcastManager;
    private Listener listener;

    private ConnectThread connectThread;
    private BluetoothDevice device;
    private int position;

    private boolean connFlag=false;
    //步数
    public static int steps=0;
    private final BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            switch (action){
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //Log.i("IN101","找到了,有|name："+device.getName()+"|type:" +device.getType()
                            //+"|calss"+device.getBluetoothClass()+"|Alia"+device.getAlias());
                    //UUID deviceUuid=device.;

                    if(device.getName()==null)
                        break;
                    //fragment.gifList.add(new GifImageView(R.layout.list_item));
                    devicesList.add(device);
                    listener.foundDev(device);
                    break;
            }

        }
    };
    public interface Listener{
        void foundDev(BluetoothDevice dev);
    }

    public BTService_old(PageConnect fragment, Listener listener){
        this.listener=listener;
        this.fragment=fragment;
        this.activity=fragment.getActivity();

        btAdapter=BluetoothAdapter.getDefaultAdapter();
        devicesList=new ArrayList<BluetoothDevice>();
        if(!btAdapter.isEnabled())
            btAdapter.enable();
        localBroadcastManager=LocalBroadcastManager.getInstance(activity);
        activity.registerReceiver(broadcastReceiver,getBTIntentFilter());

    }



    public static IntentFilter getBTIntentFilter(){
        final IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        return intentFilter;
    }

    public void startScan(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(btAdapter.isDiscovering())
                    btAdapter.cancelDiscovery();
                devicesList.clear();
                btAdapter.startDiscovery();
                Log.i("IN101","蓝牙开始扫描!");
            }
        }).start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(btAdapter.isDiscovering())
                    btAdapter.cancelDiscovery();
                Intent intent=new Intent(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                localBroadcastManager.sendBroadcast(intent);
                //StaticUtil.pageConnect.flashConnect.sectionTop.gifDrawable.stop();
                UtilSys.sendInfo("扫描完毕");
                MyApp.pageConnect.scanST= STATE.done;
            }
        }, ConfigBT.SCAN_TIME);

        btAdapter.startDiscovery();
    }
    public void connect(int pos){
        this.position=pos;
        device=devicesList.get(pos);
        Log.i("IN101","试图连接"+device.getName());
        connectThread=new ConnectThread(device);
        connectThread.start();
    }
    public void reConnect(){
        Log.i("IN101","reConnect!!");
        connectThread=new ConnectThread(device);
        connectThread.start();
    }
    private class ConnectThread extends Thread {
        private  BluetoothSocket mmSocket;
        private  BluetoothDevice mmDevice;
        private InputStream inputStream=null;
        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(ConfigBT.SPP_UUID);
            } catch (IOException e) {
                Log.i("IN101", "Socket's create() method failed");
            }
            mmSocket = tmp;
        }
        int II=0;//测试收集组数
        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            btAdapter.cancelDiscovery();
            boolean interuptFlag=false;
            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();


                if(!connFlag) {
                    UtilSys.sendInfo("连接成功！！");

                    connFlag=true;
                }
                inputStream=mmSocket.getInputStream();

                byte []buff=new byte[50000];
                int numByte;

                II=0;
                while(true){

                    /*
                    if(II>=3000) {
                        Log.i("IN101", "II 超过2000!");
                        interuptFlag = true;
                        throw(new IOException());

                        try {
                            inputStream.close();
                            mmSocket.close();
                            reConnect();
                        } catch (Exception e) {
                            Log.i("IN101", "scoket close defeat!");
                        }
                        return;



                    }
                    */


                    numByte=inputStream.read(buff);
                    //if(II%10==0)
                    Log.i("IN101","II  "+II+"   "+numByte);
                    II+=numByte/12;
                    steps+=numByte/12;;
                    if(numByte%12==0) {
                    //if(true){
                        //DataManager_old.pushData(buff,numByte);/////////////////////////
                        /*
                        int len=StaticUtil.proccess.data[3].size();
                        Log.i("IN103",String.valueOf(len));

                        if(len%Config.CHARTDATANUM==0&&len!=0){
                            StaticUtil.viewheart.toDrawBrokenLine(StaticUtil.proccess.data[3],len/Config.CHARTDATANUM-1);
                        }

                         */
                        /*
                        for(int i=0;i<6;i++){
                            Log.i("IN102","-----"+String.valueOf(buff[i]));
                        }

                        for(int i=0;i<2;i++){

                            int a1,b1,a2,b2,a3,b3;
                            a2=(int)(buff[i*3]&0xff);
                            b2=(int)(buff[i*3+1]&0xff);
                            a1=(int)((buff[i*3+2]&0xff)/16);
                            b1=(int)((buff[i*3+2]&0xff)%16);
                            a3=((int)(a1*256+a2));
                            b3=((int)(b1*256+b2));
                            Log.i("IN102","得到数据"+String.valueOf(a3)+"   "+String.valueOf(b3));
                        }

                         */
                    }
                    Log.i("IN102",new String(buff,0,numByte)+"   numByte= "+String.valueOf(numByte));
                }
                    /*
                catch (Exception ignored){
                    Log.i("IN101","Cound not read the info");
                    Log.i("IN101","try again!!");

                    /*
                    try {
                        mmSocket.close();
                    }
                    catch (Exception e){}
                    interuptFlag=true;
                    break;
                }
                     */
                    /*
                if(interuptFlag){
                    interuptFlag=false;

                    reConnect();
                }*/
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                Log.i("IN101", "Could not connect the client socket");
                UtilSys.sendInfo("设备不符连接失败");


                //gifDrawable.stop();
                try {
                    mmSocket.close();
                    reConnect();
                } catch (IOException closeException) {
                    Log.i("IN101", "Could not close the client socket");
                }

                /*
                try {
                    Log.i("IN101", "Trying fallback...");
                    //mmSocket = (BluetoothSocket) mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(mmDevice, 1);
                    //mmSocket.connect();
                    this.start();
                    Log.i("IN101", "Connected");
                } catch (Exception e2) {
                    Log.e("IN101", "Couldn't establish Bluetooth connection!");
                    try {
                        mmSocket.close();
                    } catch (IOException e3) {
                        Log.e("IN101", "unable to close() "  + " socket during connection failure", e3);
                    }
                    //connectionFailed();
                    return;
                }

                 */
            }
            catch (Exception e){
                Log.i("IN101","其它");
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e("IN101", "Could not close the client socket", e);
            }
        }
    }


}
