package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.SecTable.GetBestCircle;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.MyDataBase.SecTable.ShowOneStudentData;

import java.util.List;

@Dao
public interface CenterDAO {

    @Insert
    void insertCenter(Center... center);

    @Update
    void updateCenter(Center... center);

    @Delete
    void deleteCenter(Center... center);

    @Query("Delete from Center where id=:id")
    int deleteCenter(long id);

    @Query("select * from Center where idUser=:id ")
    LiveData<List<Center>> getAllCenters(int id);

    @Query("select * from Center where idUser=:id ")
    LiveData<List<ShowCenterData>> getAllCenterss(int id);


    @Query("select * from Center where id=:id ")
    LiveData<List<Center>> getCenter(long id);

    @Query("select * from Center where id=:id ")
    Center getOneCenterById(long id);



    @Query("select * from Center where idUser=:id ")
    Center getOneCenterByUserId(int id);

    @Query("select Center.id as id,Center.idUser as idUser,Center.numberOfCircle as numberOfCircle,Center.name as name,Center.image as image,Center.address as address,Center.branch as branch,Center.longitude as longitude,Center.latitude as latitude from Circle join center on Circle.idCenter=Center.id  where  Circle.idMusharaf=:id   ")
    LiveData<List<ShowCenterData>> getOneCenterData(int id);


    @Query("select Center.id as id,Center.idUser as idUser,Center.numberOfCircle as numberOfCircle,Center.name as name,Center.image as image,Center.address as address,Center.branch as branch,Center.longitude as longitude,Center.latitude as latitude from Mahfaz join Circle on Mahfaz.idCircle=Circle.id join Center on Circle.idCenter =Center.id where  Mahfaz.idUser=:id ")
    LiveData<List<ShowCenterData>> getOneCenterDataMahafz(int id);


    @Query("select Center.id as id,Center.idUser as idUser,Center.numberOfCircle as numberOfCircle,Center.name as name,Center.image as image,Center.address as address,Center.branch as branch,Center.longitude as longitude,Center.latitude as latitude from Student join  Mahfaz on Student.idMohafez=Mahfaz.idUser join Circle on Mahfaz.idCircle=Circle.id join Center on Circle.idCenter =Center.id where  Student.idUser=:id  ")
    LiveData<List<ShowCenterData>> getOneCenterDataStudent(int id);

    @Query("select Center.* from  User  join Mahfaz on User.idNumber =Mahfaz.idUser join Circle on Mahfaz.idCircle =Circle.id join Center on Circle.idCenter =Center.id  join Student on Mahfaz.idUser=Student.idMohafez where  Mahfaz.idUser=:id or Circle.idMusharaf=:id   ")
    LiveData<List<ShowCenterData>> getOneCenterDat55a(int id);


    @Query("select * from Center  order by Center.id DESC limit 0,1")
    LiveData<Center> getLastCenter();




    @Query("UPDATE Center SET name =:name ,numberOfCircle =:numberOfCircle,image =:image,address =:address,branch =:branch ,longitude =:longitude ,latitude =:latitude  WHERE id =:id ")
    void updateCenter1(long id ,String name,int numberOfCircle,String image ,String address ,int branch,double longitude , double latitude  );


    @Query("select Center.name as name ,avg(Task.assessment)as assement from Center join Circle on Center.id=Circle.idCenter join Mahfaz on Circle.id=Mahfaz.idCircle join Student on Student.idMohafez=Mahfaz.idUser join Task on Student.idUser=Task.idStudent where Center.id =:id")
    LiveData<GetBestCircle> getAssessmentCenter(long id);
}
