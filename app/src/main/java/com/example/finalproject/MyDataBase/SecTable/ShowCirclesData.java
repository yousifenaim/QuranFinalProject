package com.example.finalproject.MyDataBase.SecTable;

import androidx.annotation.NonNull;

import com.example.finalproject.MyDataBase.Entity.Circle;

import java.io.Serializable;

public class ShowCirclesData implements Serializable {


    private String centerName ;
    private String userName ;
    private String circleImage ;
    private String circleName ;
    private int branch ;
    private long id;
    private int idMusharaf ;
    private long idCenter ;
    private int numberOfStudent ;
    private String descr;
    private String address;
    private double  longitude ;
    private double  latitude ;


    public ShowCirclesData() {
    }

    public ShowCirclesData(String centerName, String userName, String circleImage, String circleName, int branch, long id, int idMusharaf, long idCenter, int numberOfStudent, String descr, String address, double longitude, double latitude) {
        this.centerName = centerName;
        this.userName = userName;
        this.circleImage = circleImage;
        this.circleName = circleName;
        this.branch = branch;
        this.id = id;
        this.idMusharaf = idMusharaf;
        this.idCenter = idCenter;
        this.numberOfStudent = numberOfStudent;
        this.descr = descr;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCircleImage() {
        return circleImage;
    }

    public void setCircleImage(String circleImage) {
        this.circleImage = circleImage;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
