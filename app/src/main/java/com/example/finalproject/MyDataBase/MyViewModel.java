package com.example.finalproject.MyDataBase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finalproject.MyAdapters.UserToUser;
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

public class MyViewModel extends AndroidViewModel {

    Repository repository ;
    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }

    public void insertUser(User... user) {
        repository.insertUser(user);
    }

    public void insertUserToAddAnouther(User user, LisLongToLong lisLongToLong){

       repository.insertUserToAddAnouther(user,lisLongToLong);

    }


    public void updateUser(User... user){
       repository.updateUser(user);
    }

    public void deleteUser(User... user){
       repository.deleteUser(user);
    }

    public void updateUserNoImage(int id , String name, String email, String mobile , String address , int branch , Date birthDate){

        repository.updateUserNoImage(id,name,email,mobile,address,branch,birthDate);
    }

    public void updateUserWithImage(int id , String name, String email, String mobile , String address , int branch , Date birthDate,String image){

        repository.updateUserWithImage(id,name,email,mobile,address,branch,birthDate,image);
    }


    public void updateUserMohafezWithImage(int id , String name, String mobile , String address , int branch , String image, LisIntToInt lisIntToInt){

        repository.updateUserMohafezWithImage(id,name,mobile,address,branch,image,lisIntToInt);


    }


    public void updateUserStudent(int id , String name, String mobile , String address , int branch ,String image,String password,Date date,LisIntToInt lisIntToInt){

        repository.updateUserStudent(id,name,mobile,address,branch,image,password,date,lisIntToInt);

    }




    public void deleteUser(int id,LisIntToInt lisIntToInt){
        repository.deleteUser(id,lisIntToInt);
    }



    public LiveData<User> getUser(int id){

        return repository.getUser(id);
    }

    public void deleteCircle(long id){

        repository.deleteCircle(id);
    }


    public void  getUserToRegisterCheck(int id, String phone, String email, RegisterCheck registerCheck) {

        repository.getUserToRegisterCheck(id,phone,email, registerCheck);
    }

//    public void getUserToRegisterCheck(int id,String phone, RegisterCheck registerCheck){
//
//        repository.getUserToRegisterCheck(id,phone,registerCheck);
//    }


    public void getUserToRegisterCheck(int id, String phone, ShowIdUserToCheck userToCheck){

        repository.getUserToRegisterCheck(id,phone,userToCheck);

    }



    public void  getLoginUserAndPass(int id, RegisterCheck registerCheck){

        repository.getLoginUserAndPass(id, registerCheck);

    }

    public LiveData<List<User>> getAllUser(int id){


        return repository.getAllUser(id);
    }

    public void getNameUserById(int id, GetNameUserById getNameUserById){

        repository.getNameUserById(id,getNameUserById);
    }

    //Branch

    public  void insertBranch(Branch branch){

        repository.insertBranch(branch);
    }

    public  LiveData<List<Branch>> getAllBranch(){

        return repository.getAllBranch();
    }

    public void getBranchName(int id, CentersBranch centersBranch){

        repository.getBranchName(id,centersBranch);
    }


    public void insertCenter(Center... center){

       repository.insertCenter(center);
    }

    public void updateCenter(Center... center){

        repository.updateCenter(center);
    }

    public void deleteCenter(Center... center){
       repository.deleteCenter(center);

    }


    public LiveData<List<Center>> getAllCenters(int id){

        return repository.getAllCenters(id);
    }

    public LiveData<List<ShowCenterData>> getAllCenterss(int id){

        return repository.getAllCenterss(id);
    }

    public LiveData<List<ShowCenterData>> getOneCenterDataMahafz(int id){
        return repository.getOneCenterDataMahafz(id);
    }


    public  void getOneCenterById(long id , GetOneCenterById oneCenterById){

        repository.getOneCenterById(id ,oneCenterById);

    }

    public  void getOneCenterByUserId(int id, GetOneCenterById oneCenterById){

        repository.getOneCenterByUserId(id ,oneCenterById);

    }

    public LiveData<List<Circle>> getAllCircleByIdUser(int idMusharaf){

        return repository.getAllCircleByIdUser(idMusharaf);
    }




    public  void updateCenter1(long id ,String name,int numberOfCircle,String image ,String address ,int branch,double longitude , double latitude  ){

   repository.updateCenter1(id,name,numberOfCircle,image,address,branch,longitude,latitude);

    }

    public LiveData<List<Center>> getCenter(long id){

        return repository.getCenter(id);
    }



    public void insertCircle(Circle... circle){

        repository.insertCircle(circle);
    }


    public void updateCircle(Circle... circle){

        repository.updateCircle(circle);
    }


    public void deleteCircle(Circle... circle){

        repository.deleteCircle(circle);

    }

    public  LiveData<List<Circle>> getAllCircle(){

        return repository.getAllCircle();
    }


    public LiveData<List<Circle>> getAllCircleByIdCenter(long idCenter){

        return repository.getAllCircleByIdCenter(idCenter);
    }




    public LiveData<List<Circle>> getAllCircleByIdCenterAndIdUser(long idCenter,int idMusharaf){

        return repository.getAllCircleByIdCenterAndIdUser(idCenter,idMusharaf);
    }

    public void updateCircle1(long id ,String name,int numberOfStudent ,String address,String desc ,double longitude , double latitude  ){

        repository.updateCircle1(id,name,numberOfStudent,address,desc,longitude,latitude);
    }


    public void updateCircleAdmin(long id, String name, int numberOfStudent, String address, String desc, double longitude, double latitude, long idCenter,String image){

       repository.updateCircleAdmin(id,name,numberOfStudent,address,desc,longitude,latitude,idCenter,image);
    }


    public LiveData<Circle> getOneCircleByIdCenter(long idCenter){

        return  repository.getOneCircleByIdCenter(idCenter);
    }



    //muhafaz

    public void insertMahfaz(Mahfaz... mahfaz){

        repository.insertMahfaz(mahfaz);
    }


    public  LiveData<List<Mahfaz>> getAllMahfaz(long id){

        return repository.getAllMahfaz(id);
    }


    public void updateMahfaz( int idUser, long idCenter ,long idCircle , String name, String mobile,String image, int branch){

        repository.updateMahfaz(idUser,idCenter,idCircle,name,mobile,image,branch);
    }



    public void insertStudent(Student  students){

        repository.insertStudent(students);
    }


    public  void updateStudent(Student... students){

        repository.updateStudent();
    }


    public void deleteStudent(Student... students){

        repository.deleteStudent();
    }


    public void   insertStudent(int id,int mohafez,long idCircle,long idCenter,double longitude,double latitude, double evaluation,LisLongToLong lisLongToLong){

        repository.insertStudent(id,mohafez,idCircle,idCenter,longitude,latitude,evaluation,lisLongToLong);
    }

    public LiveData<List<ShowStudentData>> getAllShowStudentData(long id){

        return repository.getAllShowStudentData(id);
    }


    public LiveData<ShowOneStudentData> getOneStudentData(int id){

        return repository.getOneStudentData(id);
    }


    public void updateStudent(int id , int idMohafez, long idCircle , long idCenter , double longitude , double latitude){

        repository.updateStudent(id,idMohafez,idCircle,idCenter,longitude,latitude);

    }


    public void insertQuantityType(QuantityType... quantityType){
        repository.insertQuantityType(quantityType);
    }

    public LiveData<List<QuantityType>> getAllQuantityType(){

        return repository.getAllQuantityType();
    }

    public void getQuantityTypeName(int id, ShowStringToString showStringToString){

        repository.getQuantityTypeName(id,showStringToString);
    }


    public void insertTask(Task... tasks){
        repository.insertTask(tasks);

    }

    public void updateTask(Task... tasks){
        repository.updateTask(tasks);

    }

    public void deleteTask(Task... tasks){
        repository.deleteTask(tasks);

    }

    public LiveData<Student> getOneStudent(int id){

        return repository.getOneStudent(id);
    }


    public LiveData<List<ShowCenterData>> getOneCenterData(int id){
        return repository.getOneCenterData(id);
    }


    public LiveData<List<ShowCirclesData>> getCircleDataToShowAdmin(long id){

        return repository.getCircleDataToShowAdmin(id);
    }


    public LiveData<List<ShowCirclesData>> getCircleDataToMusharaf(long id,int userId){

        return repository.getCircleDataToMusharaf(id,userId);
    }


    public LiveData<List<ShowCirclesData>> getCircleDataToMahfaz(long id,int userId){

        return repository.getCircleDataToMahfaz(id,userId);
    }

    public LiveData<List<Mahfaz>> getAllMahfaz(long id, int userId){

        return repository.getAllMahfaz(id,userId);
    }


    public void deleteCenter(long id,LisIntToInt lisIntToInt){
        repository.deleteCenter(id,lisIntToInt);
    }


    public LiveData<List<ShowCenterData>> getOneCenterDataStudent(int id){
        return repository.getOneCenterDataStudent(id);
    }

    public LiveData<List<ShowCirclesData>> getCircleDataToStudent(long id,int userId){
        return repository.getCircleDataToStudent(id,userId);
    }


    public LiveData<List<Task>> getAllTaskByIdStudent(int id){
        return repository.getAllTaskByIdStudent(id);
    }



    public void updateTask(long id,String descr, String tester, Date taskEndDate, int fromm,int tooo ,int idQuantityType,double assessment  ){

        repository.updateTask(id,descr,tester,taskEndDate,fromm,tooo,idQuantityType,assessment);
    }

    public LiveData<List<Task>> getAllTaskByDate(int id ,Date fromm ,Date too){
        return repository.getAllTaskByDate(id,fromm,too);
    }

    public void deleteTask(long id){
        repository.deleteTask(id);

    }


    public void insertAdvertising(Advertising... advertisings){
        repository.insertAdvertising(advertisings);
    }

    public void updateAdvertising(Advertising... advertisings){
        repository.updateAdvertising(advertisings);
    }


    public void deleteAdvertising(Advertising... advertisings){
        repository.deleteAdvertising(advertisings);
    }

    public LiveData<List<Advertising>> getAllAdvertising(long id){

        return repository.getAllAdvertising(id);
    }


    public void deleteAdvertising(long id){

        repository.deleteAdvertising(id);
    }


    public void updateAdvertising(long id ,String descr){
        repository.updateAdvertising(id,descr);
    }


    public void numberOfCircle(LisIntToInt lisIntToInt){
        repository.numberOfCircle(lisIntToInt);
    }

    public void  numberOfCircleByIdCenter(long id,LisIntToInt lisIntToInt){
        repository.numberOfCircleByIdCenter(id,lisIntToInt);
    }


    public void  numberOfStudentByIdCircle(long id,LisIntToInt lisIntToInt){
        repository.numberOfStudentByIdCircle(id,lisIntToInt);

    }

    public LiveData<Circle> getOneCircleById(long id){

        return repository.getOneCircleById(id);
    }


    public void numberOfStudent(LisIntToInt lisIntToInt){
        repository.numberOfStudent(lisIntToInt);
    }


    public LiveData<GetBestStudent> bestOfStudent(){

        return repository.bestOfStudent();
    }

    public  void getAllAssessment(LisDoubleToDouble lisDoubleToDouble){
        repository.getAllAssessment(lisDoubleToDouble);
    }


    public void getNumberOfMahfaz(LisIntToInt lisIntToInt) {

        repository.getNumberOfMahfaz(lisIntToInt);

    }

    public LiveData<GetBestStudent> getBestMahfaz(){
        return repository.getBestMahfaz();
    }

    public LiveData<GetBestCircle> getBestCircle(){

        return repository.getBestCircle();
    }


    public LiveData<GetBestCircle> getAssessmentCenter(long id){

        return repository.getAssessmentCenter(id);
    }

    public LiveData<List<Task>> getAllTaskToSearch(int id,String search){
        return repository.getAllTaskToSearch(id,search);
    }

    public void updateUserChangePass(int id ,String password){

        repository.updateUserChangePass(id,password);

    }

    public LiveData<List<ShowCirclesData>> getAllCircleData(int id){

        return repository.getAllCircleData(id);
    }

    public LiveData<List<Mahfaz>> getAllMahfaz(int id){
        return repository.getAllMahfaz(id);
    }

    public LiveData<List<ShowStudentData>> getAllStudentData(int id){
        return repository.getAllStudentData(id);
    }

    public LiveData<List<User>>  getAllMushref(){
        return repository.getAllMushref();
    }

    public void getNameUserByPhone(String phone, UserToUser userToUser){
        repository.getNameUserByPhone(phone,userToUser);
    }
    public LiveData<List<MySms>> getAllSms(int id){
        return repository.getAllSms(id);
    }

    public LiveData<Center> getLastCenter(){
        return  repository.getLastCenter();
    }


}
