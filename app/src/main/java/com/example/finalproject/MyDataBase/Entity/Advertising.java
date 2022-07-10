package com.example.finalproject.MyDataBase.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.finalproject.MyDataBase.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = Center.class,parentColumns = {"id"},childColumns = {"idCenter"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
@TypeConverters({DateConverter.class})
public class Advertising implements Serializable {

    @PrimaryKey(autoGenerate = true)
     private long id ;
    @NonNull
    private Date date ;
    @NonNull
    private String desc ;
    @NonNull
    private String userName;
    @NonNull
    private String userImage;
    @NonNull
    private long idCenter ;

    public Advertising() {
    }

    public Advertising(@NonNull Date date, @NonNull String desc, @NonNull String userName, @NonNull String userImage, long idCenter) {
        this.date = date;
        this.desc = desc;
        this.userName = userName;
        this.userImage = userImage;
        this.idCenter = idCenter;
    }

    @NonNull
    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(@NonNull String userImage) {
        this.userImage = userImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    @NonNull
    public String getDesc() {
        return desc;
    }

    public void setDesc(@NonNull String desc) {
        this.desc = desc;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public long getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(long idCenter) {
        this.idCenter = idCenter;
    }
}
