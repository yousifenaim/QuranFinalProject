package com.example.finalproject.MyDataBase.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.DateConverter;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.SecTable.ShowIdUserAndPasswordToCheck;

import java.util.Date;
import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface UserDAO {


    @Insert
    void insertUser(User... user);

    @Insert
    long insertUserToAddAnouther(User user);


    @Update
    void updateUser(User... user);

    @Delete
    void deleteUser(User... user);

    @Query("select * from User  where idNumber=:idd")
    LiveData<User> getUser(int idd);

    @Query("select * from User where idNumber=:id ")
    LiveData<List<User>> getAllUser(int id);

    @Query("select * from User  where idNumber=:id")
    User getLoginUserAndPass(int id);


    @Query("select * from User  where idNumber=:id or phone=:phone  or email=:email")
    User getUserToRegisterCheck(int id,String phone,String email);

    @Query("select User.idNumber as id ,User.phone as phone  from User  where idNumber=:id or phone=:phone  ")
    ShowIdUserAndPasswordToCheck getUserToRegisterCheck(int id, String phone);


    @Query("select name from User  where idNumber=:id")
    String getNameUserById(int id);

    @Query("select * from User  where phone=:phone")
    User getNameUserByPhone(String phone);

    @Query("select * from User where validity=1")
    LiveData<List<User>>  getAllMushref();



    @Query("UPDATE User SET name =:name ,email =:email,phone =:mobile,address =:address,branch =:branch , birthDate =:birthDate   WHERE idNumber =:id ")
    void updateUserNoImage(int id , String name, String email, String mobile , String address , int branch , Date birthDate);

    @Query("UPDATE User SET name =:name ,email =:email,phone =:mobile,address =:address,branch =:branch , birthDate =:birthDate , image =:image   WHERE idNumber =:id ")
    void updateUserWithImage(int id , String name, String email, String mobile , String address , int branch , Date birthDate,String image);


    @Query("UPDATE User SET name =:name ,phone =:mobile,address =:address,branch =:branch , image =:image   WHERE idNumber =:id ")
    int updateUserMohafezWithImage(int id , String name, String mobile , String address , int branch ,String image);

    @Query("UPDATE User SET name =:name ,phone =:mobile,address =:address,branch =:branch , image =:image ,password=:password,birthDate=:date   WHERE idNumber =:id ")
    int updateUserStudent(int id , String name, String mobile , String address , int branch ,String image,String password,Date date);


    @Query("Delete from User where idNumber=:id")
    int deleteUser(int id);


    @Query("UPDATE User SET password=:password  WHERE idNumber =:id ")
    void updateUserChangePass(int id ,String password);



}
