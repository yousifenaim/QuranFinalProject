package com.example.finalproject.MyDataBase;



import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long milliseconds){

        if (milliseconds !=null){
            return new Date(milliseconds);
        }
        return null ;
    }

    @TypeConverter
    public static Long fromDate(Date date){

        if (date == null) {
            return null;
        }
        return date.getTime();
    }
}
