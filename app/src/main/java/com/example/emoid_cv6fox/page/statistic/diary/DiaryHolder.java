package com.example.emoid_cv6fox.page.statistic.diary;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoid_v2.R;
import com.example.emoid_cv6fox.UI.ViewFace;

/**
 * @author: wzt
 * @date: 2020/12/3
 */
public class DiaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //外部调用
    public View itemView;
    //内部调用
    public DiaryAdapter DA;

    public ViewFace face;
    public TextView faceInfo;
    public TextView faceInfo2;
    public TextView time;
    public ImageButton deleteItem;
    public DiaryHolder(@NonNull View itemView,DiaryAdapter diaryAdapter) {
        super(itemView);
        //绑定
        this.itemView=itemView;
        this.DA=diaryAdapter;
        //获取
        face=(ViewFace)itemView.findViewById(R.id.imageview_statistic_dialog_item_face);
        faceInfo=(TextView)itemView.findViewById(R.id.textview_statistic_dialog_item_faceinfo);
        faceInfo2=(TextView)itemView.findViewById(R.id.textview_statistic_dialog_item_faceinfo2);
        time=(TextView)itemView.findViewById(R.id.textview_statistic_dialog_item_time);
        deleteItem=(ImageButton)itemView.findViewById(R.id.imagebutton_statistic_dialog_item_delete);
        //
        deleteItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(DA.diaryListener !=null){
            if(v!=null&&itemView.getTag(R.id.tag_pos)!=null){
                DA.diaryListener.onClickDelete((Integer)itemView.getTag(R.id.tag_pos));
            }
        }
    }
}
