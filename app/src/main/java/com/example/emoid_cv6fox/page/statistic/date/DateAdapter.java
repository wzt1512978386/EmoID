package com.example.emoid_cv6fox.page.statistic.date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.emoid_v2.R;

import java.util.List;

/**
 * @author: lenovo
 * @date: 2020/11/14
 */
public class DateAdapter extends RecyclerView.Adapter<DateHolder> {
    private Context context;
    private List<DateEntity> dateList;
    public DateAdapter(Context context,List<DateEntity> list){
        this.context=context;
        this.dateList=list;
    }
    @NonNull
    @Override
    public DateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(context).inflate(R.layout.item_date,parent,false);
        return new DateHolder(convertView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull DateHolder holder, int position) {
        DateEntity dateEntity=dateList.get(position);
        int date=dateEntity.getDate();
        DateEntity.TYPE type=dateEntity.getType();
        holder.itemView.setTag(R.id.tag_pos,position);
        holder.tvDate.setText(String.valueOf(date));

        //holder.dateLayout.invalidate();
        switch (type){
            case NULL:
            case LASTM:
            case NEXTM:
                holder.tvDate.setAlpha(0.3f);
                break;
            case CURM:
                holder.tvDate.setAlpha(1.0f);
                //holder.frameDateItem.setSelected(false);
                break;
            case SELECTED:
                //holder.frameDateItem.setSelected(true);
                break;
        }
        if(dateEntity.getType()== DateEntity.TYPE.NEXTM||dateEntity.getType()== DateEntity.TYPE.LASTM)
            return;
        dateListener.showDetail(position,holder.frameDateItem);
    }

    @Override
    public int getItemCount() {
        return dateList==null?0:dateList.size();
    }


    //事件接口
    public DateListener dateListener;
    public void setDateListener(DateListener dateListener){
        this.dateListener =dateListener;
    }
}
