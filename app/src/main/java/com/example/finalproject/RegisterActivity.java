package com.example.finalproject;


import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.databinding.ActivityRegisterBinding;
import com.example.finalproject.MyAdapters.BranchAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding ;
    Calendar selectedDate;
    BranchAdapter spinnerAdapter ;
    MyViewModel myViewModel ;
     int NumberidBransh ;

     Uri selectedImageUri ;
    FirebaseStorage storage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedDate = Calendar.getInstance();
        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);
        storage =FirebaseStorage.getInstance();


        ActivityResultLauncher<String> arl =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                selectedImageUri=result;
                binding.RegisterImgUser.setImageURI(selectedImageUri);
            }
        });

        binding.RegisterImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch("image/*");
            }
        });

        binding.RegisterBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now =Calendar.getInstance();
                DatePickerDialog datePickerDialog =DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        binding.RegisterBtnDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                selectedDate.set(Calendar.YEAR,year);
                                selectedDate.set(Calendar.MONTH,monthOfYear);
                                selectedDate.clear(Calendar.HOUR_OF_DAY);
                                selectedDate.clear(Calendar.MINUTE);
                                selectedDate.clear(Calendar.SECOND);
                    }
                },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });



        //spinner code
        spinnerAdapter =new BranchAdapter(new ArrayList<>());
        binding.RegisterSp.setAdapter(spinnerAdapter);

        myViewModel.getAllBranch().observe(this, new Observer<List<Branch>>() {
            @Override
            public void onChanged(List<Branch> branches) {
                spinnerAdapter.setBranches(branches);
            }
        });

        binding.RegisterSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               Branch branch =spinnerAdapter.getItem(i);
                NumberidBransh =branch.getNumberBranch();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        binding.RegisterBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =binding.RegisterEdName.getText().toString().trim();
                String email =binding.RegisterEdEmail.getText().toString().trim();
                String address =binding.RegisterEdAddress.getText().toString().trim();
                String id =binding.RegisterEdId.getText().toString().trim();
                String password =binding.RegisterEdPassword.getText().toString();
                String rePassword =binding.RegisterEdRePassword.getText().toString();
                String phone =binding.RegisterEdMobile.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this, "ادخل الاسم كامل", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!name.contains(" ")){
                    Toast.makeText(RegisterActivity.this, "ادخل الاسم بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "ادخل البريد الالكتروني", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.contains("@")&&!email.contains(".")){
                    Toast.makeText(RegisterActivity.this, "ادخل بريد الالكتروني صالح", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    Toast.makeText(RegisterActivity.this, "ادخل العنوان", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(id)){
                    Toast.makeText(RegisterActivity.this, "ادخل  رقم الهوية", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((id.length() != 9||id.contains("-")||id.contains("."))){
                    Toast.makeText(RegisterActivity.this, "ادخل  رقم الهوية بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rePassword)){
                    Toast.makeText(RegisterActivity.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(RegisterActivity.this, "ادخل  رقم الهاتف ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() != 10||phone.contains("-")||phone.contains(".") ){

                    Toast.makeText(RegisterActivity.this, "ادخل رقم هاتف صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length()<8){

                    Toast.makeText(RegisterActivity.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rePassword.length()<8){

                    Toast.makeText(RegisterActivity.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)){

                    Toast.makeText(RegisterActivity.this, "كلمة المرور غير متطابقة ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(String.valueOf(NumberidBransh))){

                    Toast.makeText(RegisterActivity.this, "اختر الفرع", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (selectedDate==null){
                    Toast.makeText(RegisterActivity.this, "الرجاء ادخال تاريخ الميلاد", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (selectedImageUri==null){
                    Toast.makeText(RegisterActivity.this, "الرجاء ادخال صورة المستخدم", Toast.LENGTH_SHORT).show();
                    return;
                }

                binding.RegisterBtnRegister.setEnabled(false);
                binding.RegisterBtnProgressBar.setVisibility(View.VISIBLE);

                myViewModel.getUserToRegisterCheck(Integer.parseInt(id),phone,email, new RegisterCheck() {
                    @Override
                    public void Item(User user) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                if (user==null){


                                    Calendar calendar =Calendar.getInstance();
                                    storage.getReference()
                                            .child("Images/"+calendar.getTimeInMillis())
                                            .putFile(selectedImageUri)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {

                                                            User user =new User(Integer.parseInt(id),0,email,password,name,phone,selectedDate.getTime(),address,NumberidBransh,uri.toString());
                                                            myViewModel.insertUser(user);
                                                            Toast.makeText(RegisterActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                                                            binding.RegisterBtnProgressBar.setVisibility(View.GONE);
                                                            binding.RegisterBtnRegister.setEnabled(true);
                                                            finish();

                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            binding.RegisterBtnProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this, "حدث خظأ في رفع الصورة"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            binding.RegisterBtnRegister.setEnabled(true);
                                        }
                                    });

                                }
                                else {
                                    binding.RegisterBtnProgressBar.setVisibility(View.GONE);
                                    binding.RegisterBtnRegister.setClickable(true);
                                    Toast.makeText(RegisterActivity.this, "البيانات المدخلة موجودة في حساب اخر", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });













            }
        });
        



    }
}