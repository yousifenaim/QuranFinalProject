package com.example.finalproject.MyDataBase.Entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Branch {

    @PrimaryKey
    private int numberBranch;

    private String name ;

    public Branch() {
    }

    public Branch(int numberBranch,  String name) {
        this.numberBranch = numberBranch;
        this.name = name;
    }

    public int getNumberBranch() {
        return numberBranch;
    }

    public void setNumberBranch(int numberBranch) {
        this.numberBranch = numberBranch;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }
}
