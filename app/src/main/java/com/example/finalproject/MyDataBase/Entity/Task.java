package com.example.finalproject.MyDataBase.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.finalproject.MyDataBase.DateConverter;

import java.io.Serializable;
import java.util.Date;


@Entity(foreignKeys = {@ForeignKey(entity = Student.class,parentColumns = {"idUser","idCircle"},childColumns = {"idStudent","circleId"},onUpdate = ForeignKey.CASCADE,onDelete = ForeignKey.CASCADE)})
@TypeConverters({DateConverter.class})
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id ;
    @NonNull
    private int idStudent ;
    @NonNull
    private long circleId ;
    @NonNull
    private long centerId ;
    @NonNull
    private String addTaskName ;
    @NonNull
    private String tester ;
    @NonNull
    private Date taskEndDate ;
    @NonNull
    private Date taskAddDate ;
    @NonNull
    private int from ;
    @NonNull
    private int to ;
    @NonNull
    private int idQuantityType;
    @NonNull
    private double assessment ;
    @NonNull
    private String desc ;

    public Task() {
    }


    public Task(int idStudent, long circleId, long centerId, @NonNull String addTaskName, @NonNull String tester, @NonNull Date taskEndDate, @NonNull Date taskAddDate, int from, int to, int idQuantityType, double assessment, @NonNull String desc) {
        this.idStudent = idStudent;
        this.circleId = circleId;
        this.centerId = centerId;
        this.addTaskName = addTaskName;
        this.tester = tester;
        this.taskEndDate = taskEndDate;
        this.taskAddDate = taskAddDate;
        this.from = from;
        this.to = to;
        this.idQuantityType = idQuantityType;
        this.assessment = assessment;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public long getCircleId() {
        return circleId;
    }

    public void setCircleId(long circleId) {
        this.circleId = circleId;
    }

    public long getCenterId() {
        return centerId;
    }

    public void setCenterId(long centerId) {
        this.centerId = centerId;
    }

    @NonNull
    public String getAddTaskName() {
        return addTaskName;
    }

    public void setAddTaskName(@NonNull String addTaskName) {
        this.addTaskName = addTaskName;
    }

    @NonNull
    public String getTester() {
        return tester;
    }

    public void setTester(@NonNull String tester) {
        this.tester = tester;
    }

    @NonNull
    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(@NonNull Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    @NonNull
    public Date getTaskAddDate() {
        return taskAddDate;
    }

    public void setTaskAddDate(@NonNull Date taskAddDate) {
        this.taskAddDate = taskAddDate;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getIdQuantityType() {
        return idQuantityType;
    }

    public void setIdQuantityType(int idQuantityType) {
        this.idQuantityType = idQuantityType;
    }

    public double getAssessment() {
        return assessment;
    }

    public void setAssessment(double assessment) {
        this.assessment = assessment;
    }

    @NonNull
    public String getDesc() {
        return desc;
    }

    public void setDesc(@NonNull String desc) {
        this.desc = desc;
    }
}
