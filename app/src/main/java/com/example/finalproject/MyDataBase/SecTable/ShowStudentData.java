package com.example.finalproject.MyDataBase.SecTable;

public class ShowStudentData {

    private double evaluation ;
    private int id ;
    private String image ;
    private String phone ;
    private String centerName ;
    private String circleName ;
    private String branchName ;
    private String studentName ;

    public ShowStudentData() {
    }

    public ShowStudentData(double evaluation, int id, String image, String phone, String centerName, String circleName, String branchName, String studentName) {
        this.evaluation = evaluation;
        this.id = id;
        this.image = image;
        this.phone = phone;
        this.centerName = centerName;
        this.circleName = circleName;
        this.branchName = branchName;
        this.studentName = studentName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
