package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.DateConverter;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.Task;
import com.example.finalproject.MyDataBase.Entity.User;

import java.util.Date;
import java.util.List;

@TypeConverters({DateConverter.class})
@Dao
public interface TaskDAO {


    @Insert
    void insertTask(Task... tasks);

    @Update
    void updateTask(Task... tasks);

    @Delete
    void deleteTask(Task... tasks);


    @Query("select * from Task where idStudent=:id ")
    LiveData<List<Task>> getAllTaskByIdStudent(int id);


    @Query("UPDATE Task SET `desc`=:descr ,tester =:tester,taskEndDate=:taskEndDate, `from`=:fromm, `to`=:tooo ,idQuantityType=:idQuantityType,assessment=:assessment WHERE id =:id ")
    void updateTask(long id,String descr, String tester, Date taskEndDate, int fromm,int tooo ,int idQuantityType,double assessment  );

    @Query("select * from Task where idStudent =:id  and taskEndDate between :fromm and :too ")
    LiveData<List<Task>> getAllTaskByDate(int id ,Date fromm ,Date too);

    @Query("select * from Task where idStudent =:id and tester LIKE :search ")
    LiveData<List<Task>> getAllTaskToSearch(int id ,String search);


    @Query("Delete from Task where id=:id")
    void deleteTask(long id);

    @Query("select avg(assessment) from Task ")
    double getAllAssessment();
}
