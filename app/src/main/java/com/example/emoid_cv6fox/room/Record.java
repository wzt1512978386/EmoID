package com.example.emoid_cv6fox.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: wzt
 * @date: 2020/11/14
 */
@Entity(tableName = "record")
public class Record {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public int type;
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", type=" + type +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
