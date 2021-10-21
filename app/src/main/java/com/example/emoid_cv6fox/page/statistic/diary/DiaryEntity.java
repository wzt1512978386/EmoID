package com.example.emoid_cv6fox.page.statistic.diary;

/**
 * @author: wzt
 * @date: 2020/11/14
 */
public class DiaryEntity {
    public int type;
    public int hour;
    public int minute;
    public DiaryEntity(){
        type=hour=minute=-1;
    }
    public DiaryEntity(int type,int hour,int minute){
        this.type=type;
        this.hour=hour;
        this.minute=minute;
    }

}
