package com.example.emoid_cv6fox.page.statistic;

import android.content.Intent;
import android.view.View;

import com.example.emoid_cv6fox.Config.ConfigView;
import com.example.emoid_v2.R;
import com.example.emoid_cv6fox.UI.Statistic.FrameDateItem;
import com.example.emoid_cv6fox.Util.UtilDate;
import com.example.emoid_cv6fox.page.statistic.date.DateListener;
import com.example.emoid_cv6fox.page.statistic.diary.DiaryActivity;
import com.example.emoid_cv6fox.room.Record;
import com.example.emoid_cv6fox.room.RecordDataBase;

import java.util.List;

/**
 * @author: wzt
 * @date: 2020/12/3
 */
public class EventsStatistic {
    private PageStatistic PS;//调用
    public EventsStatistic(PageStatistic pageStatistic){this.PS=pageStatistic;}
    public void setEvents(){onClick();}
    public void onClick(){

        PS.bt_nextM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PS.curM++;
                if(PS.curM>12){
                    PS.curM-=12;
                    PS.curY++;
                }
                PS.YM.setText(String.format(PS.getString(R.string.calendar),PS.curM,PS.curY));
                PS.updateDateList();
                PS.updateCount();
                PS.dateAdapter.notifyDataSetChanged();
            }
        });
        PS.bt_lastM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PS.curM--;
                if(PS.curM<1){
                    PS.curM+=12;
                    PS.curY--;
                }
                PS.YM.setText(String.format(PS.getString(R.string.calendar),PS.curM,PS.curY));
                //YM.setText(curY+"/"+curM);
                PS.updateDateList();
                PS.updateCount();
                PS.dateAdapter.notifyDataSetChanged();
            }
        });

        //日期适配器事件
        PS.dateAdapter.setDateListener(new DateListener() {
            @Override
            public void onClick(int pos) {
                //计算显示范围的日期
                int dayEnd=PS.dateList.get(pos).getDate();
                int dayStart=dayEnd- ConfigView.LChart.DRAW_DAY_NUM +1;
                int curM_=PS.curM;
                if (dayStart<1){
                    if(PS.curM-1<1) {
                        dayStart += UtilDate.getDaysOfMonth(PS.curY - 1, 12);
                        curM_=12;
                    }
                    else {
                        dayStart += UtilDate.getDaysOfMonth(PS.curY, PS.curM - 1);
                        curM_=PS.curM-1;
                    }
                }
                /*
                for(int i=0;i<PS.dateList.size();i++) {
                    if (PS.dateList.get(i).getType() == DateEntity.TYPE.SELECTED)
                        PS.dateList.get(i).setType(DateEntity.TYPE.CURM);
                }
                 */
                /*
                for(int i=0;i<ConfigView.LChart.DRAW_DAY_NUM;i++){
                    int tempPos=Math.max(pos-i,0);
                    if(PS.dateList.get(tempPos).getType()== DateEntity.TYPE.CURM)
                        PS.dateList.get(tempPos).setType(DateEntity.TYPE.SELECTED);
                }*/

                PS.viewLineChart.setShowDetail(ConfigView.LChart.DRAW_DAY_NUM,PS.curY,PS.curM,PS.dateList.get(pos).getDate());
                PS.showTimeStart.setText(String.format("%1$02d·%2$02d",curM_,dayStart));
                PS.showTimeEnd.setText(String.format("%1$02d·%2$02d",PS.curM,dayEnd));

                PS.dateAdapter.notifyItemChanged(pos);




            }

            @Override
            public void onLongClick(int pos) {
                onClick(pos);
                //if(PS.dateList.get(pos).getType()== DateEntity.TYPE.CURM)
                  //  PS.dateList.get(pos).setType(DateEntity.TYPE.SELECTED);
                //else if(PS.dateList.get(pos).getType()== DateEntity.TYPE.SELECTED)
                    //PS.dateList.get(pos).setType(DateEntity.TYPE.CURM);

                Intent intent=new Intent(PS.context, DiaryActivity.class);
                intent.putExtra("year",PS.curY);
                intent.putExtra("month",PS.curM);
                intent.putExtra("day",PS.dateList.get(pos).getDate());
                PS.startActivity(intent);
                PS.activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }

            @Override
            public void showDetail(int pos, FrameDateItem frameDateItem) {
                int tempD=PS.dateList.get(pos).getDate();
                List<Record> tempList= RecordDataBase.get().getRecordDao().findByDate(PS.curY,PS.curM,tempD);
                int []count={0,0,0,0};
                for(int i=0;i<tempList.size();i++){
                    count[tempList.get(i).type-1]++;
                }
                frameDateItem.startShow(count);
            }
        });
    }
}
