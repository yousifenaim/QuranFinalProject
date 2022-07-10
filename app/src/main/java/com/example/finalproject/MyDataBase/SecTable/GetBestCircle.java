package com.example.finalproject.MyDataBase.SecTable;

public class GetBestCircle {

    private String name ;
    private String assement;

    public GetBestCircle() {
    }


    public GetBestCircle(String name, String assement) {
        this.name = name;
        this.assement = assement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssement() {
        return assement;
    }

    public void setAssement(String assement) {
        this.assement = assement;
    }
}
