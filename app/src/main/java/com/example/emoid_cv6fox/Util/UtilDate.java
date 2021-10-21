package com.example.emoid_cv6fox.Util;

import com.example.emoid_cv6fox.page.statistic.date.DateEntity;

import java.util.Calendar;

public class UtilDate {
    //获取当前年份
    public static int getYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    //获取当前月份
    public static int getMonth(){
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }
    //获取当前日期在该月第几天
    public static int getCurDayOfMonth(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    //获取当前日期在该周第几天
    public static int getCurDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static DateEntity[][] getDayOfMonthFormat(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);//设置时间为每月的第一天
        //设置日历格式数组,6行7列
        DateEntity days[][] = new DateEntity[6][7];
        //设置该月的第一天是周几
        int daysOfFirstWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //设置本月有多少天
        int daysOfMonth = getDaysOfMonth(year, month);
        //设置上个月有多少天
        int daysOfLastMonth = getLastDaysOfMonth(year, month);
        int dayNum = 1;
        int nextDayNum = 1;
        //将日期格式填充数组
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                days[i][j]=new DateEntity(DateEntity.TYPE.NULL,0);
                if (i == 0 && j < daysOfFirstWeek - 1) {
                    days[i][j].setDate(daysOfLastMonth - daysOfFirstWeek + 2 + j);
                    days[i][j].setType(DateEntity.TYPE.LASTM);
                } else if (dayNum <= daysOfMonth) {
                    days[i][j].setDate(dayNum++);
                    days[i][j].setType(DateEntity.TYPE.CURM);
                } else {
                    days[i][j].setType(DateEntity.TYPE.NEXTM);
                    days[i][j].setDate(nextDayNum++);
                }
            }
        }
        return days;
    }

    //根据年月返回该月有多少天
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (isLeap(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }
    //判断是否闰年
    public static boolean isLeap(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }

    //根据传入的年份和月份，判断上一个有多少天
    public static int getLastDaysOfMonth(int year, int month) {
        int lastDaysOfMonth = 0;
        if (month == 1) {
            lastDaysOfMonth = getDaysOfMonth(year - 1, 12);
        } else {
            lastDaysOfMonth = getDaysOfMonth(year, month - 1);
        }
        return lastDaysOfMonth;
    }
}
