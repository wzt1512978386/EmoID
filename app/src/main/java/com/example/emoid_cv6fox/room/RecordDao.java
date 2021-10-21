package com.example.emoid_cv6fox.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author: wzt
 * @date: 2020/11/14
 */
@Dao
public interface RecordDao {
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Record... records);

   @Delete
    void delete(Record... records);

   @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Record... records);

   @Query("select * from record")
    List<Record> findAll();

   @Query("select * from record where year=:y and month=:m and day=:d")
    List<Record> findByDate(int y, int m, int d);

    @Query("select * from record where year=:y and month=:m")
    List<Record> findByMonth(int y, int m);

   @Query("select * from record where id in (:ids)")
    List<Record> findByIds(int[] ids);

    @Query("select * from record where year=:y and month=:m and day=:d and hour=:h and minute=:m_ and type=:t")
    List<Record> findByAll(int y, int m, int d,int h,int m_,int t);
}
