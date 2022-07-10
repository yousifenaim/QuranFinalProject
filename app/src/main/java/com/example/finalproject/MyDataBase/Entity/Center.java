package com.example.finalproject.MyDataBase.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,parentColumns = {"idNumber"},childColumns = {"idUser"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Branch.class,parentColumns = {"numberBranch"},childColumns = {"branch"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
public class Center implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id ;
    @NonNull
    private int idUser ;
    @NonNull
    private int numberOfCircle ;
    @NonNull
    private String  name ;
    @NonNull
    private String  image ;
    @NonNull
    private String  address ;
    @NonNull
    private int branch ;
    @NonNull
    private double  longitude ;
    @NonNull
    private double  latitude ;

    public Center() {
    }

    public Center(long id, int idUser, int numberOfCircle, @NonNull String name, @NonNull String image, @NonNull String address, int branch, double longitude, double latitude) {
        this.id = id;
        this.idUser = idUser;
        this.numberOfCircle = numberOfCircle;
        this.name = name;
        this.image = image;
        this.address = address;
        this.branch = branch;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Center(int idUser, int numberOfCircle, @NonNull String name, @NonNull String image, @NonNull String address, int branch, double longitude, double latitude) {
        this.idUser = idUser;
        this.numberOfCircle = numberOfCircle;
        this.name = name;
        this.image = image;
        this.address = address;
        this.branch = branch;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getNumberOfCircle() {
        return numberOfCircle;
    }

    public void setNumberOfCircle(int numberOfCircle) {
        this.numberOfCircle = numberOfCircle;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
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
