package com.example.emoid_cv6fox.page.connect.Device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.emoid_v2.R;

import java.util.List;

public class DeviceAdpter extends RecyclerView.Adapter<DeviceHolder> {
    private Context context;
    //设备列表
    private List<DeviceEntity> deviceList;

    public DeviceAdpter(Context context,List<DeviceEntity> list){
        this.context=context;
        this.deviceList=list;
    }



    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView=LayoutInflater.from(context).inflate(R.layout.item_device,parent,false);
        return new DeviceHolder(convertView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {
        DeviceEntity device=deviceList.get(position);
        String name=device.name;
        holder.itemView.setTag(R.id.tag_pos,position);
        holder.itemView.setTag(R.id.tag_dev,device.dev);
        holder.deviceName.setText(name);
        holder.deviceType.setImageResource(R.drawable.item_device_1);
        holder.deviceSignal.setImageResource(R.drawable.item_device_signal_3);
    }

    @Override
    public int getItemCount() {
        return deviceList==null?0:deviceList.size();
    }




    public DeviceListener deviceListener;
    public void setDeviceListener(DeviceListener deviceListener){
        this.deviceListener=deviceListener;
    }

}
