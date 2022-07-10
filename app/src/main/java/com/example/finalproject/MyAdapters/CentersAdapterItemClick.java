package com.example.finalproject.MyAdapters;

import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;

public interface CentersAdapterItemClick {

    void OnItemClick(ShowCenterData showCenterData);
    void OnItemLocationClick(double lat ,double lng);
    void OnItemCircleClick(ShowCenterData showCenterData);
}
