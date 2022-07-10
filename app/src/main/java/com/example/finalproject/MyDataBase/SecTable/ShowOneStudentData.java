package com.example.finalproject.MyDataBase.SecTable;


import androidx.room.TypeConverters;

import com.example.finalproject.MyDataBase.DateConverter;

import java.util.Date;
@TypeConverters({DateConverter.class})
public class ShowOneStudentData {

    private  int id ;
    private long idCircle;
    private long centerId;
    private int Branch;
    private String name ;
    private String phone ;
    private String image ;
    private Date date ;
    private String password ;
    private String address ;
    private double longitude ;
    private double latitude ;

    public ShowOneStudentData() {
    }

    public ShowOneStudentData(int id, long idCircle, long centerId, int branch, String name, String phone, String image, Date date, String password, String address, double longitude, double latitude) {
        this.id = id;
        this.idCircle = idCircle;
        this.centerId = centerId;
        Branch = branch;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.date = date;
        this.password = password;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIdCircle() {
        return idCircle;
    }

    public void setIdCircle(long idCircle) {
        this.idCircle = idCircle;
    }

    public long getCenterId() {
        return centerId;
    }

    public void setCenterId(long centerId) {
        this.centerId = centerId;
    }

    public int getBranch() {
        return Branch;
    }

    public void setBranch(int branch) {
        Branch = branch;
    }
}
