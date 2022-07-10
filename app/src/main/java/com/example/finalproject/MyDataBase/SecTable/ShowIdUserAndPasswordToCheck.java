package com.example.finalproject.MyDataBase.SecTable;

public class ShowIdUserAndPasswordToCheck {

    private int id ;
    private String phone ;

    public ShowIdUserAndPasswordToCheck(int id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public ShowIdUserAndPasswordToCheck() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
