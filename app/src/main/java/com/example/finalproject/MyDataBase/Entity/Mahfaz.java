package com.example.finalproject.MyDataBase.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,parentColumns = {"idNumber"},childColumns = {"idUser"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
                       @ForeignKey(entity = Center.class,parentColumns = {"id"},childColumns = {"idCenter"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE),
                        @ForeignKey(entity = Circle.class,parentColumns = {"id"},childColumns = {"idCircle"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)
            }
            ,primaryKeys = {"idCenter", "idUser"},
        indices = {@Index(value = {"idUser"}, unique = true)})
public class Mahfaz implements Serializable {

    @NonNull

    private  int idUser ;
    @NonNull
    private  long idCenter ;
    @NonNull
    private  long idCircle ;
    @NonNull
    private String name ;

    @NonNull
    private String iamge ;
    @NonNull
    private String phone ;
    @NonNull
    private int branch ;

    public Mahfaz() {
    }

    public Mahfaz(int idUser, long idCenter, long idCircle, @NonNull String name, @NonNull String iamge, @NonNull String phone, int branch) {
        this.idUser = idUser;
        this.idCenter = idCenter;
        this.idCircle = idCircle;
        this.name = name;
        this.iamge = iamge;
        this.phone = phone;
        this.branch = branch;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    @NonNull
    public String getIamge() {
        return iamge;
    }

    public void setIamge(@NonNull String iamge) {
        this.iamge = iamge;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public long getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(long idCenter) {
        this.idCenter = idCenter;
    }

    public long getIdCircle() {
        return idCircle;
    }

    public void setIdCircle(long idCircle) {
        this.idCircle = idCircle;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
