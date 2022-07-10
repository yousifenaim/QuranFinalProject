package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.SecTable.GetBestCircle;
import com.example.finalproject.MyDataBase.SecTable.GetBestStudent;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;

import java.util.List;

@Dao
public interface CircleDAO {


    @Insert
    void insertCircle(Circle... circle);

    @Update
    void updateCircle(Circle... circle);

    @Delete
    void deleteCircle(Circle... circle);

    @Query("Delete from Circle where id=:id")
    void deleteCircle(long id);


       @Query("select * from Circle ")
       LiveData<List<Circle>> getAllCircle();


    @Query("select * from Circle where idCenter=:idCenter ")
    LiveData<List<Circle>> getAllCircleByIdCenter(long idCenter);

    @Query("select * from Circle where idCenter=:idCenter ")
    LiveData<Circle> getOneCircleByIdCenter(long idCenter);

    @Query("select * from Circle where id=:id ")
    LiveData<Circle> getOneCircleById(long id);

    @Query("select * from Circle where  idMusharaf=:idMusharaf ")
    LiveData<List<Circle>> getAllCircleByIdUser(int idMusharaf);


    @Query("select * from Circle where idCenter=:idCenter and idMusharaf=:idMusharaf ")
    LiveData<List<Circle>> getAllCircleByIdCenterAndIdUser(long idCenter,int idMusharaf);


    @Query("UPDATE Circle SET name =:name ,numberOfStudent =:numberOfStudent,address =:address,`desc`=:desc,longitude =:longitude ,latitude =:latitude  WHERE id =:id ")
    void updateCircle1(long id ,String name,int numberOfStudent ,String address ,String desc ,double longitude , double latitude  );

    @Query("UPDATE Circle SET name =:name ,numberOfStudent =:numberOfStudent,address =:address,`desc`=:desc,longitude =:longitude ,latitude =:latitude ,idCenter=:idCenter, image=:image WHERE id =:id ")
    void updateCircleAdmin(long id ,String name,int numberOfStudent ,String address ,String desc ,double longitude , double latitude ,long idCenter,String image );


    @Query("select Center.name as centerName , User.name as userName , Circle.image as circleImage ,Circle.name as circleName ,Center.branch as branch , Circle.longitude  as longitude ,Circle.latitude as latitude,Circle.id as id,Circle.idMusharaf as idMusharaf,Circle.idCenter as idCenter,Circle.numberOfStudent as numberOfStudent , Circle.`desc` as descr,Circle.address as address from  Circle join User on Circle.idMusharaf =User.idNumber join Center on Circle.idCenter=Center.id where  Circle.idCenter==:id  ")
    LiveData<List<ShowCirclesData>> getCircleDataToShowAdmin(long id);

    @Query("select Center.name as centerName , User.name as userName , Circle.image as circleImage ,Circle.name as circleName ,Center.branch as branch , Circle.longitude  as longitude ,Circle.latitude as latitude,Circle.id as id,Circle.idMusharaf as idMusharaf,Circle.idCenter as idCenter,Circle.numberOfStudent as numberOfStudent , Circle.`desc` as descr,Circle.address as address from User join Circle on User.idNumber=Circle.idMusharaf join Center on Center.id=Circle.idCenter  where  Circle.idCenter==:id and User.idNumber=:userId ")
    LiveData<List<ShowCirclesData>> getCircleDataToMusharaf(long id,int userId);

    @Query("select Center.name as centerName , User.name as userName , Circle.image as circleImage ,Circle.name as circleName ,Center.branch as branch , Circle.longitude  as longitude ,Circle.latitude as latitude,Circle.id as id,Circle.idMusharaf as idMusharaf,Circle.idCenter as idCenter,Circle.numberOfStudent as numberOfStudent , Circle.`desc` as descr,Circle.address as address from User  join Mahfaz on User.idNumber =Mahfaz.idUser join Circle on Mahfaz.idCircle =Circle.id join Center on Circle.idCenter =Center.id where  Center.id==:id and Mahfaz.idUser=:userId   ")
    LiveData<List<ShowCirclesData>> getCircleDataToMahfaz(long id,int userId);

    @Query("select Center.name as centerName , User.name as userName , Circle.image as circleImage ,Circle.name as circleName ,Center.branch as branch , Circle.longitude  as longitude ,Circle.latitude as latitude,Circle.id as id,Circle.idMusharaf as idMusharaf,Circle.idCenter as idCenter,Circle.numberOfStudent as numberOfStudent , Circle.`desc` as descr,Circle.address as address  from Student join Mahfaz on Student.idMohafez=Mahfaz.idUser join User on User.idNumber=Student.idUser join Circle on Mahfaz.idCircle=Circle.id join Center on Center.id=Circle.idCenter where   Student.idUser=:userId  and Center.id=:id ")
    LiveData<List<ShowCirclesData>> getCircleDataToStudent(long id,int userId);


    @Query("select Center.name as centerName , User.name as userName , Circle.image as circleImage ,Circle.name as circleName ,Center.branch as branch , Circle.longitude  as longitude ,Circle.latitude as latitude,Circle.id as id,Circle.idMusharaf as idMusharaf,Circle.idCenter as idCenter,Circle.numberOfStudent as numberOfStudent , Circle.`desc` as descr,Circle.address as address from  Circle join User on Circle.idMusharaf =User.idNumber join Center on Circle.idCenter=Center.id where  Center.idUser==:id  ")
    LiveData<List<ShowCirclesData>> getAllCircleData(int id);



    @Query("select count(id) from Circle  ")
    int numberOfCircle();

    @Query("select count(id) from Circle where idCenter=:id  ")
    int numberOfCircleByIdCenter(long id);

    @Query("select Circle.name as name ,avg(Task.assessment)as assement from Circle join Mahfaz on Circle.id=Mahfaz.idCircle join Student on Student.idMohafez=Mahfaz.idUser join Task on Student.idUser=Task.idStudent group by Circle.id order by assement desc limit 0,1")
    LiveData<GetBestCircle> getBestCircle();



}
