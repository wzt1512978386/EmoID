package com.example.emoid_cv6fox.page.statistic.diary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_v2.R;
import com.example.emoid_cv6fox.page.statistic.custom.CustomActivity;
import com.example.emoid_cv6fox.room.Record;
import com.example.emoid_cv6fox.room.RecordDataBase;

import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {
    //外部调用
    public Context context;
    public Intent intent;

    //控件定义
    private LinearLayout globalQuit;
    private ImageButton quit;
    private LinearLayout bt_addDiary;
    private RecyclerView diary;
    private TextView calendar;

    //记录日记的当前日期
    private int curY,curM,curD;

    private List<DiaryEntity> diaryList;
    private DiaryAdapter diaryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        getSupportActionBar().hide();//隐藏ACtion Bar
        context=this;
        intent=getIntent();
        curY=intent.getIntExtra("year",0);
        curM=intent.getIntExtra("month",0);
        curD=intent.getIntExtra("day",0);
        initDiary();
        initEvent();
    }
    private void initEvent(){
        globalQuit=(LinearLayout)findViewById(R.id.linearlayout_statistic_dialog_globalquit);
        quit=(ImageButton)findViewById(R.id.imagebutton_statistic_dialog_quit);
        bt_addDiary =(LinearLayout)findViewById(R.id.linearlayout_statistic_dialog_adddiary);
        //globalQuit.setClickable(false);
        calendar=(TextView)findViewById(R.id.textview_statistic_dialog_calendar);
        calendar.setText(String.format(getString(R.string.dialogCalendar),curY,curM,curD));

        final boolean[] flag = {false};
        for(int i=0;i<globalQuit.getChildCount();i++)
            globalQuit.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    flag[0] =true;
                    return false;
                }
            });
        globalQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag[0]){
                    finish();
                }
                flag[0]=false;
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent=new Intent(context,CustomActivity.class);
                newIntent.putExtra("year",curY);
                newIntent.putExtra("month",curM);
                newIntent.putExtra("day",curD);
                startActivityForResult(newIntent,0);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                Log.i("IN102","add");
            }
        });
    }

    private void initDiary(){
        diaryList =new ArrayList<>();
        diary=findViewById(R.id.recylerview_statistic_dialog_record);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        diary.setLayoutManager(layoutManager);


        /*
        for(int i=0;i<15;i++){
            int x=(int)(Math.random()*100)%4+1;
            list.add(new DiaryEntity(x,x,x));
        }
        */
        updateDiaryList();
        diaryAdapter=new DiaryAdapter(this, diaryList);
        diary.setAdapter(diaryAdapter);
        diaryAdapter.setDiaryClickListener(new DiaryListener() {
            @Override
            public void onClickDelete(int pos) {
                //获取该位置日记的属性
                DiaryEntity diaryEntity= diaryList.get(pos);
                int curT=diaryEntity.type;
                int curH=diaryEntity.hour;
                int curM_=diaryEntity.minute;
                //根据日记属性从数据库中寻找
                List<Record> tempList=RecordDataBase.get().getRecordDao().findByAll(curY,curM,curD,curH,curM_,curT);
                for(int i=0;i<tempList.size();i++)
                    RecordDataBase.get().getRecordDao().delete(tempList.get(i));
                //删除后更新当前日记列表
                updateDiaryList();
                diaryAdapter.notifyDataSetChanged();
                MyApp.pageStatistic.updateCount();
                MyApp.pageStatistic.dateAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        diaryList =null;
    }

    private void updateDiaryList(){
        diaryList.clear();
        List<Record> tempList= RecordDataBase.get().getRecordDao().findByDate(curY,curM,curD);
        for(int i=0;i<tempList.size();i++){
            diaryList.add(new DiaryEntity(tempList.get(i).type,tempList.get(i).hour,tempList.get(i).minute));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateDiaryList();
        diaryAdapter.notifyDataSetChanged();
        MyApp.pageStatistic.updateCount();
        MyApp.pageStatistic.dateAdapter.notifyDataSetChanged();
    }
}