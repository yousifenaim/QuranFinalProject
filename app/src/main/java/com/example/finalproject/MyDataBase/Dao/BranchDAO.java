package com.example.finalproject.MyDataBase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.finalproject.MyDataBase.Entity.Branch;

import java.util.List;

@Dao
public interface BranchDAO {

    @Insert
    void insertBranch(Branch branch);

    @Query("select * from Branch ")
    LiveData<List<Branch>> getAllBranch();

    @Query("select name from Branch where numberBranch=:id")
    String getBranchName( int id);

}
