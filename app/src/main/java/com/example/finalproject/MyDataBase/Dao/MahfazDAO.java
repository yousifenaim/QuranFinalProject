package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.SecTable.GetBestStudent;
import com.example.finalproject.MyDataBase.SecTable.ShowOneStudentData;

import java.util.Date;
import java.util.List;

@Dao
public interface MahfazDAO {

    @Insert
    void insertMahfaz(Mahfaz... mahfaz);

    @Update
    void updateMahfaz(Mahfaz... mahfaz);

    @Delete
    void deleteMahfaz(Mahfaz... mahfaz);

    @Query("select * from Mahfaz where idCircle=:id ")
    LiveData<List<Mahfaz>> getAllMahfaz(long id);




    @Query("UPDATE Mahfaz SET name =:name ,phone =:mobile,iamge =:image,branch =:branch ,branch =:branch ,branch =:branch,idCircle=:idCircle,idCenter=:idCenter    WHERE idUser =:idUser ")
    void updateMahfaz( int idUser, long idCenter ,long idCircle , String name, String mobile,String image, int branch);


    @Query("select  * from Mahfaz  where Mahfaz.idCircle=:id and idUser=:userId ")
    LiveData<List<Mahfaz>> getAllMahfaz(long id, int userId);

    @Query("select  * from Mahfaz join Circle on Mahfaz.idCircle=Circle.id join Center on Circle.idCenter=Center.id where Center.idUser=:id  ")
    LiveData<List<Mahfaz>> getAllMahfaz(int id);

    @Query("select  count(idUser) from Mahfaz  ")
    int getNumberOfMahfaz();


    @Query("select  Mahfaz.idUser as id ,avg(Task.assessment)as assement from Mahfaz join Student on Mahfaz.idUser=Student.idMohafez join Task on Student.idUser=Task.idStudent group by Mahfaz.idUser order by assement desc limit 0,1 ")
    LiveData<GetBestStudent> getBestMahfaz();


}
