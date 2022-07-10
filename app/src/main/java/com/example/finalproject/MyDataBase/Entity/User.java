package com.example.finalproject.MyDataBase.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.finalproject.MyDataBase.DateConverter;

import java.io.Serializable;
import java.util.Date;



@Entity(foreignKeys = {@ForeignKey(entity = Branch.class,parentColumns = {"numberBranch"},childColumns = {"branch"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
@TypeConverters({DateConverter.class})
public class User implements Serializable {
    @PrimaryKey
    private int idNumber ;
    @NonNull
    private int validity ;

    private  String email ;
    @NonNull
    private String password ;
    @NonNull
    private String name ;
    @NonNull
    private String phone ;

    private Date birthDate;
    @NonNull
    private  String address ;
    @NonNull
    private int branch ;
    @NonNull
    private String image ;

    public User() {
    }

    public User(int idNumber, int validity, String email, @NonNull String password, @NonNull String name, @NonNull String phone, Date birthDate, @NonNull String address, int branch, @NonNull String image) {
        this.idNumber = idNumber;
        this.validity = validity;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
        this.branch = branch;
        this.image = image;
    }

    public User(int idNumber, int validity, @NonNull String password, @NonNull String name, @NonNull String phone, Date birthDate, @NonNull String address, int branch, @NonNull String image) {
        this.idNumber = idNumber;
        this.validity = validity;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
        this.branch = branch;
        this.image = image;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(@NonNull String image) {
        this.image = image;
    }
}
