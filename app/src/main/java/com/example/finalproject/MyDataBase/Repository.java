package com.example.finalproject.MyDataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.MyAdapters.UserToUser;
import com.example.finalproject.MyDataBase.Dao.AdvertisingDAO;
import com.example.finalproject.MyDataBase.Dao.BranchDAO;
import com.example.finalproject.MyDataBase.Dao.CenterDAO;
import com.example.finalproject.MyDataBase.Dao.CircleDAO;
import com.example.finalproject.MyDataBase.Dao.MahfazDAO;
import com.example.finalproject.MyDataBase.Dao.MySmsDAO;
import com.example.finalproject.MyDataBase.Dao.QuantityTypeDAO;
import com.example.finalproject.MyDataBase.Dao.StudentDAO;
import com.example.finalproject.MyDataBase.Dao.TaskDAO;
import com.example.finalproject.MyDataBase.Dao.UserDAO;
import com.example.finalproject.MyDataBase.Entity.Advertising;
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.MySms;
import com.example.finalproject.MyDataBase.Entity.QuantityType;
import com.example.finalproject.MyDataBase.Entity.Student;
import com.example.finalproject.MyDataBase.Entity.Task;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.CentersBranch;
import com.example.finalproject.MyDataBase.Listeners.GetNameUserById;
import com.example.finalproject.MyDataBase.Listeners.GetOneCenterById;
import com.example.finalproject.MyDataBase.Listeners.LisDoubleToDouble;
import com.example.finalproject.MyDataBase.Listeners.LisIntToInt;
import com.example.finalproject.MyDataBase.Listeners.LisLongToLong;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.Listeners.ShowIdUserToCheck;
import com.example.finalproject.MyDataBase.Listeners.ShowStringToString;
import com.example.finalproject.MyDataBase.SecTable.GetBestCircle;
import com.example.finalproject.MyDataBase.SecTable.GetBestStudent;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;
import com.example.finalproject.MyDataBase.SecTable.ShowIdUserAndPasswordToCheck;
import com.example.finalproject.MyDataBase.SecTable.ShowOneStudentData;
import com.example.finalproject.MyDataBase.SecTable.ShowStudentData;

import java.util.Date;
import java.util.List;

public class Repository {

   private UserDAO userDAO ;
   private BranchDAO branchDAO ;
   private CenterDAO centerDAO;
    private CircleDAO circleDAO;
    private MahfazDAO mahfazDAO;
    private StudentDAO studentDAO ;
    private QuantityTypeDAO quantityTypeDAO;
    private TaskDAO taskDAO;
    private AdvertisingDAO advertisingDAO ;
    private MySmsDAO mySmsDAO ;
    public  Repository(Application application){

        MyDataBase db =MyDataBase.getDatabase(application);
        userDAO=db.userDAO();
        branchDAO=db.branchDAO();
        centerDAO=db.centerDAO();
        circleDAO=db.circleDAO();
        mahfazDAO=db.mahfazDAO();
        studentDAO=db.studentDAO();
        taskDAO=db.taskDAO();
        mySmsDAO=db.mySmsDAO();
        advertisingDAO=db.advertisingDAO();
        quantityTypeDAO=db.quantityTypeDAO();
    }

    public void insertUser(User... user) {

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.insertUser(user);
            }
        });
    }


    public void insertUserToAddAnouther(User user, LisLongToLong lisLongToLong){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long l =userDAO.insertUserToAddAnouther(user);
                lisLongToLong.LisLongToLong(l);
            }
        });

    }

    public void updateUser(User... user){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateUser(user);
            }
        });
    }

    public void updateUserNoImage(int id , String name, String email, String mobile , String address , int branch , Date birthDate){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateUserNoImage(id,name,email,mobile,address,branch,birthDate);
            }
        });
    }

    public void updateUserWithImage(int id , String name, String email, String mobile , String address , int branch , Date birthDate,String image){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                 userDAO.updateUserWithImage(id,name,email,mobile,address,branch,birthDate,image);

            }
        });
    }


    public void updateUserMohafezWithImage(int id , String name, String mobile , String address , int branch ,String image, LisIntToInt lisIntToInt){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int a =userDAO.updateUserMohafezWithImage(id,name,mobile,address,branch,image);
                lisIntToInt.lisIntToInt(a);
            }
        });
    }

    public void updateUserStudent(int id , String name, String mobile , String address , int branch ,String image,String password,Date date,LisIntToInt lisIntToInt){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
             int a =   userDAO.updateUserStudent(id,name,mobile,address,branch,image,password,date);
                lisIntToInt.lisIntToInt(a);
            }
        });

    }




    public void deleteUser(int id,LisIntToInt lisIntToInt){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                lisIntToInt.lisIntToInt(userDAO.deleteUser(id));
            }
        });
    }

    public void deleteCircle(long id){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {circleDAO.deleteCircle(id);
            }
        });
    }



    public void deleteUser(User... user){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.deleteUser(user);
            }
        });
    }

    public LiveData<User> getUser(int id){

        return userDAO.getUser(id);
    }



    public void getNameUserById(int id, GetNameUserById getNameUserById){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String name =userDAO.getNameUserById(id);
                getNameUserById.getNameUserById(name);
            }
        });
    }

   public void  getUserToRegisterCheck(int id, String phone, String email, RegisterCheck registerCheck){

       MyDataBase.databaseWriteExecutor.execute(new Runnable() {
           @Override
           public void run() {
               User user = userDAO.getUserToRegisterCheck(id,phone,email);
               registerCheck.Item(user);

           }
       });

   }


//    public void ShowIdUserAndPasswordToCheck(int id,String phone, RegisterCheck registerCheck){
//
//        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                User user = userDAO.getUserToRegisterCheck(id,phone);
//                registerCheck.Item(user);
//            }
//        });
//    }


    public void getUserToRegisterCheck(int id, String phone, ShowIdUserToCheck userToCheck){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ShowIdUserAndPasswordToCheck a =   userDAO.getUserToRegisterCheck(id,phone);
                userToCheck.ShowIdUserAndPasswordToCheck(a);
            }
        });

    }

    public void  getLoginUserAndPass(int id, RegisterCheck registerCheck){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                User user = userDAO.getLoginUserAndPass(id);
                registerCheck.Item(user);

            }
        });

    }


   public LiveData<List<User>> getAllUser(int id){


        return userDAO.getAllUser(id);
   }



    //Branch

    public  void insertBranch(Branch branch){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                branchDAO.insertBranch(branch);
            }
        });
    }

    public  LiveData<List<Branch>> getAllBranch(){

        return branchDAO.getAllBranch();
    }


    public void getBranchName(int id, CentersBranch centersBranch){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String name =branchDAO.getBranchName(id);
                centersBranch.Item(name);

            }
        });

    }


    //Centers


    public void insertCenter(Center... center){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                centerDAO.insertCenter(center);
            }
        });
    }

    public void updateCenter(Center... center){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                centerDAO.updateCenter(center);
            }
        });
    }

    public void deleteCenter(Center... center){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                centerDAO.deleteCenter(center);
            }
        });

    }


    public LiveData<List<Center>> getAllCenters(int id){

        return centerDAO.getAllCenters(id);
    }

    public LiveData<List<ShowCenterData>> getAllCenterss(int id){

        return centerDAO.getAllCenterss(id);
    }

    public LiveData<List<ShowCenterData>> getOneCenterDataMahafz(int id){
        return centerDAO.getOneCenterDataMahafz(id);
    }



    public void updateCenter1(long id ,String name,int numberOfCircle,String image ,String address ,int branch,double longitude , double latitude  ){


        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                centerDAO.updateCenter1(id,name,numberOfCircle,image,address,branch,longitude,latitude);
            }
        });

    }

    public LiveData<List<Center>> getCenter(long id){

        return centerDAO.getCenter(id);
    }


    public  void getOneCenterById(long id , GetOneCenterById oneCenterById){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Center center = centerDAO.getOneCenterById(id);
                        oneCenterById.getOneCenterById(center);
            }
        });

    }

    public  void getOneCenterByUserId(int id , GetOneCenterById oneCenterById){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Center center = centerDAO.getOneCenterByUserId(id);
                oneCenterById.getOneCenterById(center);
            }
        });

    }



    //Circle



    public void insertCircle(Circle... circle){

       MyDataBase.databaseWriteExecutor.execute(new Runnable() {
           @Override
           public void run() {
               circleDAO.insertCircle(circle);
           }
       });
    }


    public void updateCircle(Circle... circle){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                circleDAO.updateCircle(circle);
            }
        });
    }


    public void deleteCircle(Circle... circle){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                circleDAO.deleteCircle(circle);
            }
        });

    }

    public  LiveData<List<Circle>> getAllCircle(){

        return circleDAO.getAllCircle();
    }



    public LiveData<List<Circle>> getAllCircleByIdCenter(long idCenter){

        return circleDAO.getAllCircleByIdCenter(idCenter);
    }

    public LiveData<Circle> getOneCircleByIdCenter(long idCenter){

        return  circleDAO.getOneCircleByIdCenter(idCenter);
    }

    public LiveData<List<Circle>> getAllCircleByIdUser(int idMusharaf){

        return circleDAO.getAllCircleByIdUser(idMusharaf);
    }



    public LiveData<List<Circle>> getAllCircleByIdCenterAndIdUser(long idCenter,int idMusharaf){

        return circleDAO.getAllCircleByIdCenterAndIdUser(idCenter,idMusharaf);
    }

    public void updateCircle1(long id ,String name,int numberOfStudent ,String address ,String desc ,double longitude , double latitude  ){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                circleDAO.updateCircle1(id,name,numberOfStudent,address,desc,longitude,latitude);
            }
        });
    }

    public void updateCircleAdmin(long id ,String name,int numberOfStudent ,String address ,String desc ,double longitude , double latitude ,long idCenter ,String image){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                circleDAO.updateCircleAdmin(id,name,numberOfStudent,address,desc,longitude,latitude,idCenter,image);
            }
        });
    }


    //muhafaz

    public void insertMahfaz(Mahfaz... mahfaz){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mahfazDAO.insertMahfaz(mahfaz);
            }
        });
    }


    public  LiveData<List<Mahfaz>> getAllMahfaz(long id){

        return mahfazDAO.getAllMahfaz(id);
    }


    public void updateMahfaz( int idUser, long idCenter ,long idCircle , String name, String mobile,String image, int branch){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mahfazDAO.updateMahfaz(idUser,idCenter,idCircle,name,mobile,image,branch);
            }
        });
    }


    public void insertStudent(Student students){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.insertStudent();
            }
        });
    }


    public  void updateStudent(Student... students){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.updateStudent();

            }
        });
    }


    public void deleteStudent(Student... students){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.deleteStudent();

            }
        });
    }

    public void   insertStudent(int id,int mohafez,long idCircle,long idCenter,double longitude,double latitude, double evaluation,LisLongToLong lisLongToLong){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long a =studentDAO.insertStudent(id,mohafez,idCircle,idCenter,longitude,latitude,evaluation);
                lisLongToLong.LisLongToLong(a);
            }
        });
    }

    public LiveData<List<ShowStudentData>> getAllShowStudentData(long id){

        return studentDAO.getAllShowStudentData(id);
    }

    public  LiveData<ShowOneStudentData> getOneStudentData(int id){

        return studentDAO.getOneStudentData(id);
    }



    public void updateStudent(int id , int idMohafez, long idCircle , long idCenter , double longitude , double latitude){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.updateStudent(id,idMohafez,idCircle,idCenter,longitude,latitude);
            }
        });
    }




    public void insertQuantityType(QuantityType... quantityType){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                quantityTypeDAO.insertQuantityType(quantityType);
            }
        });
    }

    public LiveData<List<QuantityType>> getAllQuantityType(){

        return quantityTypeDAO.getAllQuantityType();
    }

    public void getQuantityTypeName(int id, ShowStringToString showStringToString){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
               String a = quantityTypeDAO.getQuantityTypeName(id);
                showStringToString.ShowStringToString(a);

            }
        });
    }


    public void insertTask(Task... tasks){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDAO.insertTask(tasks);
            }
        });
    }

    public void updateTask(Task... tasks){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDAO.updateTask(tasks);
            }
        });
    }

    public void deleteTask(Task... tasks){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                taskDAO.deleteTask(tasks);
            }
        });
    }

    public LiveData<Student> getOneStudent(int id){

        return studentDAO.getOneStudent(id);
    }



    public LiveData<List<ShowCenterData>> getOneCenterData(int id){
        return centerDAO.getOneCenterData(id);
    }



    public LiveData<List<ShowCirclesData>> getCircleDataToShowAdmin(long id){

        return circleDAO.getCircleDataToShowAdmin(id);
    }


    public  LiveData<List<ShowCirclesData>> getCircleDataToMusharaf(long id,int userId){

        return circleDAO.getCircleDataToMusharaf(id,userId);
    }


    public LiveData<List<ShowCirclesData>> getCircleDataToMahfaz(long id,int userId){

        return circleDAO.getCircleDataToMahfaz(id,userId);
    }

    public LiveData<List<Mahfaz>> getAllMahfaz(long id, int userId){

        return mahfazDAO.getAllMahfaz(id,userId);
    }


    public void deleteCenter(long id,LisIntToInt lisIntToInt){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                lisIntToInt.lisIntToInt(centerDAO.deleteCenter(id));
            }
        });
    }


    public LiveData<List<ShowCenterData>> getOneCenterDataStudent(int id){
        return centerDAO.getOneCenterDataStudent(id);
    }

    public LiveData<List<ShowCirclesData>> getCircleDataToStudent(long id,int userId){
        return circleDAO.getCircleDataToStudent(id,userId);
    }

    public LiveData<List<Task>> getAllTaskByIdStudent(int id){
        return taskDAO.getAllTaskByIdStudent(id);
    }


    public void updateTask(long id,String descr, String tester, Date taskEndDate, int fromm,int tooo ,int idQuantityType,double assessment  ){
         MyDataBase.databaseWriteExecutor.execute(new Runnable() {
             @Override
             public void run() {
                 taskDAO.updateTask(id,descr,tester,taskEndDate,fromm,tooo,idQuantityType,assessment);
             }
         });
    }


    public LiveData<List<Task>> getAllTaskByDate(int id ,Date fromm ,Date too){
        return taskDAO.getAllTaskByDate(id,fromm,too);
    }

    public  void deleteTask(long id){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDAO.deleteTask(id);
            }
        });
    }


    public void insertAdvertising(Advertising... advertisings){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                advertisingDAO.insertAdvertising(advertisings);
            }
        });
    }

    public void updateAdvertising(Advertising... advertisings){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                advertisingDAO.updateAdvertising(advertisings);
            }
        });
    }


    public void deleteAdvertising(Advertising... advertisings){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                advertisingDAO.deleteAdvertising(advertisings);
            }
        });
    }


    public LiveData<List<Advertising>> getAllAdvertising(long id){

        return advertisingDAO.getAllAdvertising(id);
    }



   public void deleteAdvertising(long id){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                advertisingDAO.deleteAdvertising(id);
            }
        });
   }


    public void updateAdvertising(long id ,String descr){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                advertisingDAO.updateAdvertising(id,descr);
            }
        });
    }

    public void numberOfCircle(LisIntToInt lisIntToInt){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
               int a = circleDAO.numberOfCircle();
                lisIntToInt.lisIntToInt(a);
            }
        });
    }

    public void  numberOfCircleByIdCenter(long id,LisIntToInt lisIntToInt){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int a = circleDAO.numberOfCircleByIdCenter(id);
                lisIntToInt.lisIntToInt(a);
            }
        });
    }

    public LiveData<Circle> getOneCircleById(long id){

        return circleDAO.getOneCircleById(id);
    }

    public void  numberOfStudentByIdCircle(long id,LisIntToInt lisIntToInt){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int a = studentDAO.numberOfStudentByIdCircle(id);
                lisIntToInt.lisIntToInt(a);
            }
        });

    }


    public void numberOfStudent(LisIntToInt lisIntToInt){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int a = studentDAO.numberOfStudent();
                lisIntToInt.lisIntToInt(a);
            }
        });
    }

    public LiveData<GetBestStudent> bestOfStudent(){

        return studentDAO.bestOfStudent();
    }

    public  void getAllAssessment(LisDoubleToDouble lisDoubleToDouble){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                double value =taskDAO.getAllAssessment();
                lisDoubleToDouble.LisDoubleToDouble(value);
            }
        });
    }


    public void getNumberOfMahfaz(LisIntToInt lisIntToInt){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                int a =mahfazDAO.getNumberOfMahfaz();
                lisIntToInt.lisIntToInt(a);
            }
        });
    }

    public LiveData<GetBestStudent> getBestMahfaz(){
        return mahfazDAO.getBestMahfaz();
    }

    public LiveData<GetBestCircle> getBestCircle(){

        return circleDAO.getBestCircle();
    }



    public LiveData<GetBestCircle> getAssessmentCenter(long id){

        return centerDAO.getAssessmentCenter(id);
    }


    public LiveData<List<Task>> getAllTaskToSearch(int id,String search){
        return taskDAO.getAllTaskToSearch(id,search);
    }

    public void updateUserChangePass(int id ,String password){

        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.updateUserChangePass(id,password);
            }
        });
    }

    public LiveData<List<ShowCirclesData>> getAllCircleData(int id){

        return circleDAO.getAllCircleData(id);
    }

    public LiveData<List<Mahfaz>> getAllMahfaz(int id){
       return mahfazDAO.getAllMahfaz(id);
    }

    public LiveData<List<ShowStudentData>> getAllStudentData(int id){
        return studentDAO.getAllStudentData(id);
    }

    public LiveData<List<User>>  getAllMushref(){
        return userDAO.getAllMushref();
    }


    public void insertSms(MySms... MySms){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mySmsDAO.insertSms(MySms);
            }
        });
    }


    public void getNameUserByPhone(String phone, UserToUser userToUser){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                userToUser.OnItemClick(userDAO.getNameUserByPhone(phone));
            }
        });
    }
   public LiveData<List<MySms>> getAllSms(int id){
        return mySmsDAO.getAllSms(id);
   }

    public LiveData<Center> getLastCenter(){
        return  centerDAO.getLastCenter();
    }

}
