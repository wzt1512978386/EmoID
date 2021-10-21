package com.example.emoid_cv6fox.page.connect.Device;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoid_v2.R;


/**
 * @author: wzt
 * @date: 2020/12/2
 */
public  class DeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //外部调用
    public View itemView;
    private DeviceAdpter DA;
    //控件
    public TextView deviceName;
    public ImageView deviceType,deviceSignal;
    public DeviceHolder(@NonNull View itemView,DeviceAdpter deviceAdpter) {
        super(itemView);
        this.itemView=itemView;
        this.DA=deviceAdpter;
        deviceName=(TextView) itemView.findViewById(R.id.textview_connect_item_device_name);
        deviceType=(ImageView) itemView.findViewById(R.id.imageview_connect_item_device_type);
        deviceSignal=(ImageView)itemView.findViewById(R.id.imageview_connect_item_device_signal);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(DA.deviceListener !=null){
            if(v!=null&&v.getTag(R.id.tag_pos)!=null){
                    DA.deviceListener.onDeviceClick((BluetoothDevice) v.getTag(R.id.tag_dev));
            }
        }
    }
}
