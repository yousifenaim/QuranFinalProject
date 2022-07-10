package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.Entity.Advertising;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Student;

import java.util.List;

@Dao
public interface AdvertisingDAO {


    @Insert
    void insertAdvertising(Advertising... advertisings);

    @Update
    void updateAdvertising(Advertising... advertisings);

    @Delete
    void deleteAdvertising(Advertising... advertisings);

    @Query("select * from Advertising where idCenter=:id  ")

    LiveData<List<Advertising>> getAllAdvertising(long id);

    @Query("Delete from Advertising where id=:id")
    void deleteAdvertising(long id);


    @Query("UPDATE Advertising SET `desc`=:descr WHERE id =:id ")
    void updateAdvertising(long id ,String descr);

}
