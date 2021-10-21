package com.example.emoid_cv6fox.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.emoid_cv6fox.App.MyApp;

/**
 * @author: wzt
 * @date: 2020/11/14
 */
@Database(entities = {Record.class}, version = 1)
public abstract class RecordDataBase extends RoomDatabase {
    private static final RecordDataBase dataBase;
    static {
        dataBase = Room.databaseBuilder(MyApp.get(), RecordDataBase.class, "wztDataBase")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
    }
    public abstract RecordDao getRecordDao();

    public static RecordDataBase get() {
        return dataBase;
    }
}
