package com.example.emoid_cv6fox.page.statistic.date;

/**
 * @author: lenovo
 * @date: 2020/11/14
 */
public class DateEntity {
    public enum TYPE{LASTM,NEXTM,CURM,SELECTED,NULL}//last_month、next_month、current_month
    private TYPE type;
    private int date;//日期
    public DateEntity(){
        this.type=TYPE.NULL;
        this.date=-1;
    }
    public DateEntity(TYPE type,int date){
        this.type=type;
        this.date=date;
    }
    public int getDate() {
        return date;
    }
    public TYPE getType() {
        return type;
    }
    public void setDate(int date) {
        this.date = date;
    }
    public void setType(TYPE type) {
        this.type = type;
    }
}
