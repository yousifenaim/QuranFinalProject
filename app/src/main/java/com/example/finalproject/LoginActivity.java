package com.example.finalproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.databinding.ActivityLoginBinding;
import com.example.finalproject.ui.studentSms.SmsReceiver;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding ;
    MyViewModel myViewModel ;
    SmsReceiver smsReceiver;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
   public static final String USERNAME_KEY = "username";
    public static final String VALIDITY_KEY = "validity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);

        sh = PreferenceManager.getDefaultSharedPreferences(this);
        sh_edit = sh.edit();




        ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result){

                             smsReceiver =new SmsReceiver();
                            IntentFilter filter = new IntentFilter();
                            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
                            registerReceiver(smsReceiver,filter);
                        }
                    }
                });
        activityResultLauncher.launch(Manifest.permission.RECEIVE_SMS);



        binding.LoginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id =binding.LoginEdId.getText().toString();
                String pass =binding.LoginEdPassword.getText().toString();


                if (TextUtils.isEmpty(id)){

                    Toast.makeText(LoginActivity.this, "ادخل اسم المستخدم", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)){

                    Toast.makeText(LoginActivity.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }


                int myId =Integer.parseInt(id);
                myViewModel.getLoginUserAndPass(Integer.parseInt(id), new RegisterCheck() {
                    @Override
                    public void Item(User user) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (user==null){
                                    Toast.makeText(LoginActivity.this, "خطأ في اسم المستخدم او كلمة المرور", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (user.getIdNumber()==myId&&user.getPassword().equals(pass)){

                                    sh_edit.putInt(USERNAME_KEY,user.getIdNumber());
                                    sh_edit.putInt(VALIDITY_KEY,user.getValidity());
                                    sh_edit.apply();
                                    Intent intent =new Intent(getBaseContext(),MyHomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(LoginActivity.this, "خطأ في اسم المستخدم او كلمة المرور", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                });

            }
        });




        binding.LoginTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}