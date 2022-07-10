package com.example.finalproject.MyDataBase.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(foreignKeys = {@ForeignKey(entity = User.class,parentColumns = {"idNumber"},childColumns = {"idMusharaf"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Center.class,parentColumns = {"id"},childColumns = {"idCenter"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
public class Circle implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private  String name ;
    @NonNull
    private int idMusharaf ;
    @NonNull
    private long idCenter ;
    @NonNull
    private int numberOfStudent ;
    @NonNull
    private String desc;
    @NonNull
    private String address;
    @NonNull
    private String  image;
    @NonNull
    private double  longitude ;
    @NonNull
    private double  latitude ;

    public Circle() {
    }

    public Circle(@NonNull String name, int idMusharaf, long idCenter, int numberOfStudent, @NonNull String desc, @NonNull String address, @NonNull String image, double longitude, double latitude) {
        this.name = name;
        this.idMusharaf = idMusharaf;
        this.idCenter = idCenter;
        this.numberOfStudent = numberOfStudent;
        this.desc = desc;
        this.address = address;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getIdMusharaf() {
        return idMusharaf;
    }

    public void setIdMusharaf(int idMusharaf) {
        this.idMusharaf = idMusharaf;
    }

    public long getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(long idCenter) {
        this.idCenter = idCenter;
    }

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public void setNumberOfStudent(int numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }

    @NonNull
    public String getDesc() {
        return desc;
    }

    public void setDesc(@NonNull String desc) {
        this.desc = desc;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
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
}
