package com.example.emoid_cv6fox.page.statistic.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoid_v2.R;

import java.util.List;

/**
 * @author: wzt
 * @date: 2020/11/14
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryHolder> {
    private Context context;
    private List<DiaryEntity> diaryList;
    private final String []remarks={"打球","吃饭","约会","跑步","拔牙","考试","游戏","学习","看书","骑车","睡觉","熬夜","电视","游泳","奶茶","电影","分手","快递"};
    public DiaryAdapter(Context context,List<DiaryEntity> list){
        this.context=context;
        this.diaryList=list;
    }
    @NonNull
    @Override
    public DiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView= LayoutInflater.from(context).inflate(R.layout.item_dialog_record,parent,false);
        return new DiaryHolder(convertView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryHolder holder, int position) {
        DiaryEntity diaryEntity=diaryList.get(position);
        //diary实体属性
        int hour=diaryEntity.hour;
        int minute=diaryEntity.minute;
        int type=diaryEntity.type;

        holder.itemView.setTag(R.id.tag_pos,position);
        holder.time.setText(String.format(context.getResources().getString(R.string.HM),hour,minute));
        int randTemp=(int)(Math.random()*remarks.length*100)%remarks.length;
        holder.faceInfo2.setText(remarks[randTemp]);
        switch (type){
            case 1:
                holder.faceInfo.setText("Happy");
                holder.face.setTF(0,0);
                break;
            case 2:
                holder.faceInfo.setText("Neut");
                holder.face.setTF(1,0);
                break;
            case 3:
                holder.faceInfo.setText("Sadn");
                holder.face.setTF(2,0);
                break;
            case 4:
                holder.faceInfo.setText("Anger");
                holder.face.setTF(3,0);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return diaryList==null?0:diaryList.size();
    }

    //事件接口回调
    protected DiaryListener diaryListener;
    public void setDiaryClickListener(DiaryListener diaryListener){
        this.diaryListener =diaryListener;
    }
}
