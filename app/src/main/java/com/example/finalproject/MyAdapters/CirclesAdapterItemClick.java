package com.example.finalproject.MyAdapters;

import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;

public interface CirclesAdapterItemClick {

    void OnItemClick(ShowCirclesData circlesData);
    void OnItemLocationClick(ShowCirclesData circlesData);
    void OnItemMahfazClick(ShowCirclesData circlesData);
    void OnItemStudentClick(ShowCirclesData circlesData);
}
