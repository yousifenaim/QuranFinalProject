package com.example.finalproject.ui.studentSms;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.MyDataBase.Entity.MySms;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.Repository;

import java.util.Calendar;


public class MyIntentService extends IntentService {

    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;

    Repository repository ;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);


        if (sh_ed_validity==3){

            Calendar calendar =Calendar.getInstance();
            String massege =intent.getStringExtra("massege");
            String phone =intent.getStringExtra("phone");
           String newPhone = phone.replace("+972","0");
            repository =new Repository(getApplication());
            MySms mySms =new MySms(massege,sh_ed_user,newPhone,calendar.getTime());
            repository.insertSms(mySms);
        }



    }


}