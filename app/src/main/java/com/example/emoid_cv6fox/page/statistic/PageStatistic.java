package com.example.emoid_cv6fox.page.statistic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emoid_cv6fox.App.MyApp;

import com.example.emoid_cv6fox.UI.Statistic.ViewLineChart;
import com.example.emoid_cv6fox.page.statistic.date.DateAdapter;
import com.example.emoid_cv6fox.page.statistic.date.DateEntity;
import com.example.emoid_cv6fox.room.Record;
import com.example.emoid_cv6fox.room.RecordDataBase;
import com.example.emoid_cv6fox.Util.UtilDate;
import com.example.emoid_v2.R;

import java.util.ArrayList;
import java.util.List;

public class PageStatistic extends Fragment {
    //外部调用
    public View root;
    public Activity activity;
    public Context context;

    //日历
    private RecyclerView calendar;
    protected TextView YM;//日历标题显示年月
    protected ImageButton bt_nextM, bt_lastM;//控制上下月
    protected TextView []countText;//记录该月某种情绪的总个数
    private int []countSum;
    final int []countID={R.id.textview_statistic_calendar_count1,//记录四个情绪个数的id定义
            R.id.textview_statistic_calendar_count2,
            R.id.textview_statistic_calendar_count3,
            R.id.textview_statistic_calendar_count4};

    protected List<DateEntity> dateList;//存放日历的日期实体列表
    public DateAdapter dateAdapter;//日期适配器
    protected int curY,curM;//日历当前年月

    //滑动框架
    private ScrollView scrollView;


    //折线图
    protected TextView showTimeStart, showTimeEnd;
    protected ViewLineChart viewLineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.page_statistic, container, false);//设置对应布局
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyApp.pageStatistic=this;//绑定
        this.activity=getActivity();
        this.context=getContext();
        initCalendar();
        initLineChart();
        new EventsStatistic(this).setEvents();
        //initScroll();//暂时不用
    }

    private void initScroll(){
        //scrollView = (ScrollView)root.findViewById(R.id.scrollview_statistic);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 判断是在顶部、底部
                        if ( scrollView.getScrollY() <= 0 ) {
                            break;
                        }else if ( scrollView.getChildAt(0) .getMeasuredHeight() <=
                                scrollView.getHeight() + scrollView.getScrollY()) {
                            break;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initLineChart(){
        //获取控件
         viewLineChart=(ViewLineChart)root.findViewById(R.id.graphicview_statistic);
        showTimeStart =(TextView)root.findViewById(R.id.textview_statistic_graph_showtime1);
        showTimeEnd =(TextView)root.findViewById(R.id.textview_statistic_graph_showtime2);
    }
    private void initCalendar(){
        //获取控件
        calendar=(RecyclerView)root.findViewById(R.id.recylerview_statistic_calendar);
        YM=(TextView)root.findViewById(R.id.textview_statistic_calendar);
        bt_nextM =(ImageButton)root.findViewById(R.id.imagebutton_statistic_nextmonth);
        bt_lastM =(ImageButton)root.findViewById(R.id.imagebutton_statistic_lastmonth);

        countText=new TextView[4];//显示统计天数
        for(int i=0;i<4;i++)
            countText[i]=root.findViewById(countID[i]);
        countSum=new int[4];//记录统计天数

        //日期
        dateList=new ArrayList<>();
        curM= UtilDate.getMonth();
        curY= UtilDate.getYear();
        YM.setText(String.format(getString(R.string.calendar),curM,curY));

        updateDateList();//更新DateList
        updateCount();//更新统计

        //日期适配器
        dateAdapter=new DateAdapter(context,dateList);//日期适配器
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,7){//定义网格布局
            @Override
            public boolean canScrollVertically() { return false; }//不能滑动
        };
        calendar.setAdapter(dateAdapter);
        calendar.setLayoutManager(gridLayoutManager);



    }
    //更新日历的dateList
    public void updateDateList(){
        Log.i("IN101",String.valueOf(curY+"年  "+curM+"月"));
        DateEntity [][]days= UtilDate.getDayOfMonthFormat(curY,curM);//根据年月获取日期数组
        dateList.clear();
        for(int i=0;i<4;i++)
            countSum[i]=0;

        for(int i=0;i<days.length;i++){
            //排除最后一行可能全是下一月的情况
            if(i==days.length-1&&days[i][0].getType()== DateEntity.TYPE.NEXTM)
                break;
            for(int j=0;j<days[0].length;j++) {
                dateList.add(days[i][j]);
            }
        }
    }
    //更新统计
    public void updateCount(){
        for(int i=0;i<4;i++)
            countSum[i]=0;
        List<Record> tempRecord=RecordDataBase.get().getRecordDao().findByMonth(curY,curM);
        for(int i=0;i<tempRecord.size();i++)
            countSum[tempRecord.get(i).type-1]++;
        for(int i=0;i<4;i++)
            countText[i].setText(String.valueOf(countSum[i]));
    }

}
