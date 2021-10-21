package com.example.emoid_cv6fox.page.statistic.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.emoid_v2.R;
import com.example.emoid_cv6fox.room.Record;
import com.example.emoid_cv6fox.room.RecordDataBase;

public class CustomActivity extends AppCompatActivity {
    private EditText type,hour,minute;
    private Button bt_add;
    private int t,h,m;
    private int curY,curM,curD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        getSupportActionBar().hide();

        //获取上个活动带来的信息
        Intent intent = getIntent();
        curY= intent.getIntExtra("year",0);
        curM= intent.getIntExtra("month",0);
        curD= intent.getIntExtra("day",0);

        initEvent();
    }
    private void initEvent(){
        //获取控件
        type=(EditText)findViewById(R.id.edittext_test_type);
        hour=(EditText)findViewById(R.id.edittext_test_hour);
        minute=(EditText)findViewById(R.id.edittext_test_minute);
        bt_add =(Button)findViewById(R.id.button_test_add);

        //随机生成
        int type_rand=((int)(Math.random()*40)%4+1);
        int hour_rand=((int)(Math.random()*240)%24+1);
        int minute_rand=((int)(Math.random()*600)%60+1);
        //随机初始化
        type.setText(String.valueOf(type_rand));
        hour.setText(String.valueOf(hour_rand));
        minute.setText(String.valueOf(minute_rand));

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=type.getText().toString().trim();
                String s2=hour.getText().toString().trim();
                String s3=minute.getText().toString().trim();
                if (!s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty()) {
                    t = Integer.parseInt(s1);
                    t=(t-1+4)%4+1;//-----
                    h = Integer.parseInt(s2);
                    m = Integer.parseInt(s3);
                    Log.i("IN102","加入记录为 type:"+t+" hour:"+h+"  minute:"+m);
                    Record record=new Record();
                    record.year=curY;
                    record.month=curM;
                    record.day=curD;
                    record.hour=h;
                    record.minute=m;
                    record.type=t;
                    RecordDataBase.get().getRecordDao().insert(record);
                    finish();
                }
                else
                    Log.i("IN102","加入失败");


            }
        });
    }
}