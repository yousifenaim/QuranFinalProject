package com.example.finalproject.MyDataBase.SecTable;

public class GetBestStudent {
   private int id ;
    private double assement ;

    public GetBestStudent(int id, double assement) {
        this.id = id;
        this.assement = assement;
    }

    public GetBestStudent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAssement() {
        return assement;
    }

    public void setAssement(double assement) {
        this.assement = assement;
    }
}
