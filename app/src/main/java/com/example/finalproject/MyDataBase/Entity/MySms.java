package com.example.finalproject.MyDataBase.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.finalproject.MyDataBase.DateConverter;

import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,parentColumns = {"idNumber"},childColumns = {"studentId"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
@TypeConverters({DateConverter.class})
public class MySms {

    @PrimaryKey(autoGenerate = true)
    private long id ;
    private String message ;
    @NonNull
    private int studentId;
    @NonNull
    private String phone ;
    @NonNull
    private Date date ;

    public MySms() {
    }

    public MySms(String message, int studentId, @NonNull String phone, @NonNull Date date) {
        this.message = message;
        this.studentId = studentId;
        this.phone = phone;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }
}
