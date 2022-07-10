package com.example.finalproject.MyDataBase.SecTable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ShowCenterData  implements Serializable {


    private long id ;
    private int idUser ;
    private int numberOfCircle ;
    private String  name ;
    private String  image ;
    private String  address ;
    private int branch ;
    private double  longitude ;
    private double  latitude ;


    public ShowCenterData() {
    }

    public ShowCenterData(long id, int idUser, int numberOfCircle, String name, String image, String address, int branch, double longitude, double latitude) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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
