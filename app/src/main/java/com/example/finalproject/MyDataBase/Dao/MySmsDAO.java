package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.MySms;

import java.util.List;

@Dao
public interface MySmsDAO {


    @Insert
    void insertSms(MySms... MySms);


    @Query("select * from MySms  where studentId=:id ")
    LiveData<List<MySms>> getAllSms(int id);

}
