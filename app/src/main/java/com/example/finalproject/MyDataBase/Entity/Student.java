package com.example.finalproject.MyDataBase.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,parentColumns = {"idNumber"},childColumns = {"idUser"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Mahfaz.class,parentColumns = {"idUser","idCenter"},childColumns = {"idMohafez","idCenter"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)} ,
        primaryKeys = {"idUser", "idCircle"})
public class Student {

    private  int idUser ;
    @NonNull
    private int idMohafez ;
    @NonNull
    private  long idCircle ;
    @NonNull
    private  long idCenter ;
    @NonNull
    private double longitude;
    @NonNull
    private double latitude;
    @NonNull
    private double evaluation;


    public Student() {
    }

    public Student(int idUser, int idMohafez, long idCircle, long idCenter, double longitude, double latitude, double evaluation) {
        this.idUser = idUser;
        this.idMohafez = idMohafez;
        this.idCircle = idCircle;
        this.idCenter = idCenter;
        this.longitude = longitude;
        this.latitude = latitude;
        this.evaluation = evaluation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdMohafez() {
        return idMohafez;
    }

    public void setIdMohafez(int idMohafez) {
        this.idMohafez = idMohafez;
    }

    public long getIdCircle() {
        return idCircle;
    }

    public void setIdCircle(long idCircle) {
        this.idCircle = idCircle;
    }

    public long getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(long idCenter) {
        this.idCenter = idCenter;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }
}
