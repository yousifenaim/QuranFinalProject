package com.example.finalproject.MyDataBase.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.Student;
import com.example.finalproject.MyDataBase.SecTable.GetBestStudent;
import com.example.finalproject.MyDataBase.SecTable.ShowOneStudentData;
import com.example.finalproject.MyDataBase.SecTable.ShowStudentData;

import java.util.Date;
import java.util.List;


@Dao
public interface StudentDAO {

    @Query("INSERT INTO Student VALUES(:id,:mohafez,:idCircle,:idCenter,:longitude,:latitude,:evaluation)")
    long  insertStudent(int id,int mohafez,long idCircle,long idCenter,double longitude,double latitude, double evaluation);

    @Insert
    void insertStudent(Student... students);

    @Update
    void updateStudent(Student... students);

    @Delete
    void deleteStudent(Student... students);



    @Query("select Student.idUser as id,Student.evaluation as evaluation , User.image as image , User.phone as phone ,Center.name as centerName,Circle.name as circleName ,Branch.name as branchName,User.name as studentName from Student join User on Student.idUser=User.idNumber join Branch on User.branch=Branch.numberBranch join Mahfaz on Student.idMohafez ==Mahfaz.idUser join Circle on Mahfaz.idCircle=Circle.id join Center on Circle.idCenter=Center.id  where Student.idCircle=:id  group by Student.idUser")
    LiveData<List<ShowStudentData>> getAllShowStudentData(long id);

    @Query("select Student.idUser as id,Student.evaluation as evaluation , User.image as image , User.phone as phone ,Center.name as centerName,Circle.name as circleName ,Branch.name as branchName,User.name as studentName from Student join User on Student.idUser=User.idNumber join Branch on User.branch=Branch.numberBranch join Mahfaz on Student.idMohafez ==Mahfaz.idUser join Circle on Mahfaz.idCircle=Circle.id join Center on Circle.idCenter=Center.id  where Center.idUser=:id  group by Student.idUser")
    LiveData<List<ShowStudentData>> getAllStudentData(int id);


    @Query("select User.branch as Branch ,Student.idCenter as centerId,Student.idUser as id,Student.longitude as longitude , User.image as image ,User.address as address,User.password as password ,User.birthDate as date , User.phone as phone ,Student.latitude as latitude,Student.idCircle as idCircle ,User.name as name from Student join User on Student.idUser=User.idNumber where Student.idUser=:id  ")
    LiveData<ShowOneStudentData> getOneStudentData(int id);


    @Query("UPDATE STUDENT SET idMohafez =:idMohafez ,idCircle =:idCircle,idCenter =:idCenter, longitude=:longitude, longitude=:latitude  WHERE idUser =:id ")
    void updateStudent(int id , int idMohafez, long idCircle , long idCenter , double longitude , double latitude);

    @Query("select * from Student where Student.idUser=:id  ")
    LiveData<Student> getOneStudent(int id);

    @Query("select count(idUser) from Student  ")
    int numberOfStudent();

    @Query("select count(idUser) from Student  where idCircle=:id")
    int numberOfStudentByIdCircle(long id);




    @Query("select  idStudent as id,avg(assessment) as assement from Task group by idStudent order by assement desc limit 0,1 ")
    LiveData<GetBestStudent> bestOfStudent();

}
