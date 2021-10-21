package com.example.emoid_cv6fox.page.statistic.date;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.emoid_cv6fox.UI.Statistic.FrameDateItem;
import com.example.emoid_v2.R;

/**
 * @author: wzt
 * @date: 2020/12/3
 */
public class DateHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnLongClickListener{
    //外部调用
    public View itemView;
    //内部调用
    private DateAdapter DA;

    public TextView tvDate;
    public FrameDateItem frameDateItem;
    public DateHolder(@NonNull View itemView,DateAdapter dateAdapter) {
        super(itemView);
        //绑定
        this.itemView=itemView;
        this.DA=dateAdapter;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        //获取
        tvDate=(TextView)itemView.findViewById(R.id.textview_statistic_dateitem);
        frameDateItem =(FrameDateItem)itemView.findViewById(R.id.framedateitem_statistic);
    }

    @Override
    public void onClick(View v) {
        if(DA.dateListener !=null){
            if(v!=null&&v.getTag(R.id.tag_pos)!=null){
                DA.dateListener.onClick((Integer)v.getTag(R.id.tag_pos));
            }
        }

    }

    @Override
    public boolean onLongClick(View v) {
        if(DA.dateListener !=null){
            if(v!=null&&v.getTag(R.id.tag_pos)!=null){
                DA.dateListener.onLongClick((Integer)v.getTag(R.id.tag_pos));
            }
        }
        return false;
    }
}
