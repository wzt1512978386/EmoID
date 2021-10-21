package com.example.emoid_cv6fox.page.statistic.date;

import com.example.emoid_cv6fox.UI.Statistic.FrameDateItem;

/**
 * @author: wzt
 * @date: 2020/12/3
 */
public interface DateListener {
    public void onClick(int pos);
    public void onLongClick(int pos);
    public void showDetail(int pos, FrameDateItem frameDateItem);
}
