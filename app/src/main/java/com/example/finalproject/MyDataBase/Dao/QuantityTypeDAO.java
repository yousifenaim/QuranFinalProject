package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.finalproject.MyDataBase.Entity.QuantityType;

import java.util.List;

@Dao
public interface QuantityTypeDAO {

    @Insert
    void insertQuantityType(QuantityType... quantityType);

    @Query("select * from QuantityType ")
    LiveData<List<QuantityType>> getAllQuantityType();

    @Query("select name from QuantityType where id=:id")
    String getQuantityTypeName( int id);

}
