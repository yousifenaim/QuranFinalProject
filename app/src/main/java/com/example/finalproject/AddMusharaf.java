package com.example.finalproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyAdapters.BranchAdapter;
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.LisIntToInt;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.databinding.ActivityAddMusharafBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMusharaf extends AppCompatActivity {

    ActivityAddMusharafBinding binding ;
    public static final String MUSHARAF_KEY="Musharaf";
    int hotNumber ;
    Calendar selectedDate;
    BranchAdapter spinnerAdapter ;
    MyViewModel myViewModel;
    int NumberidBransh ;
    int idMoshref;
    int idBranch;
    Uri selectedImageUri ;
    FirebaseStorage storage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddMusharafBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.addMusharafToolbar);
        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);
        selectedDate = Calendar.getInstance();
        storage =FirebaseStorage.getInstance();



        Intent intent =getIntent();
        hotNumber =intent.getIntExtra(AddMemorizationCircle.MY_MUSHARAF_REQ_KEY,-1);

        if (hotNumber==101){

        }
        else if (hotNumber==102){
            disableFields();
             idMoshref =intent.getIntExtra(AddMemorizationCircle.MY_MUSHARAF_DATA_KEY,0);

            myViewModel.getLoginUserAndPass(idMoshref, new RegisterCheck() {
                @Override
                public void Item(User user) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getBaseContext()).load(user.getImage()).into(binding.addMusharafImgUser);
                            binding.addMusharafEdName.setText(user.getName());
                            binding.addMusharafEdEmail.setText(user.getEmail());
                            binding.addMusharafEdMobile.setText(user.getPhone());
                            binding.addMusharafEdAddress.setText(user.getAddress());
                            binding.addMusharafEdId.setText(user.getIdNumber()+"");
                            binding.addMusharafEdPassword.setText(user.getPassword());
                            binding.addMusharafEdRePassword.setText(user.getPassword());
                            idBranch=user.getBranch();

                        }
                    });

                }
            });
        }



        ActivityResultLauncher<String> arl =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                selectedImageUri=result;
                binding.addMusharafImgUser.setImageURI(selectedImageUri);
            }
        });

        binding.addMusharafImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch("image/*");
            }
        });




        binding.addMusharafBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now =Calendar.getInstance();
                DatePickerDialog datePickerDialog =DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                                    @Override
                       public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                           binding.addMusharafBtnDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
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
        binding.addMusharafSp.setAdapter(spinnerAdapter);

        myViewModel.getAllBranch().observe(this, new Observer<List<Branch>>() {
            @Override
            public void onChanged(List<Branch> branches) {
                spinnerAdapter.setBranches(branches);


                if (hotNumber==102) {
                    int branchId = idBranch;
                    for (Branch b : branches) {
                        if (b.getNumberBranch() == branchId) {
                            binding.addMusharafSp.setSelection(branches.indexOf(b));
                            break;
                        }
                    }
                }
            }
        });

        binding.addMusharafSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Branch branch =spinnerAdapter.getItem(i);
                NumberidBransh =branch.getNumberBranch();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.addMusharafBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String name =binding.addMusharafEdName.getText().toString().trim();
                String email =binding.addMusharafEdEmail.getText().toString().trim();
                String address =binding.addMusharafEdAddress.getText().toString().trim();
                String id =binding.addMusharafEdId.getText().toString().trim();
                String password =binding.addMusharafEdPassword.getText().toString();
                String rePassword =binding.addMusharafEdRePassword.getText().toString();
                String phone =binding.addMusharafEdMobile.getText().toString().trim();



                if (TextUtils.isEmpty(name)){
                    Toast.makeText(AddMusharaf.this, "ادخل الاسم كامل", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!name.contains(" ")){
                    Toast.makeText(AddMusharaf.this, "ادخل الاسم بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(AddMusharaf.this, "ادخل البريد الالكتروني", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.contains("@")&&!email.contains(".")){
                    Toast.makeText(AddMusharaf.this, "ادخل بريد الالكتروني صالح", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    Toast.makeText(AddMusharaf.this, "ادخل العنوان", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(id)){
                    Toast.makeText(AddMusharaf.this, "ادخل  رقم الهوية", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((id.length() != 9||id.contains("-")||id.contains("."))){
                    Toast.makeText(AddMusharaf.this, "ادخل  رقم الهوية بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(AddMusharaf.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rePassword)){
                    Toast.makeText(AddMusharaf.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(AddMusharaf.this, "ادخل  رقم الهاتف ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() != 10||phone.contains("-")||phone.contains(".") ){

                    Toast.makeText(AddMusharaf.this, "ادخل رقم هاتف صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length()<8){

                    Toast.makeText(AddMusharaf.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rePassword.length()<8){

                    Toast.makeText(AddMusharaf.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)){

                    Toast.makeText(AddMusharaf.this, "كلمة المرور غير متطابقة ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(String.valueOf(NumberidBransh))){

                    Toast.makeText(AddMusharaf.this, "اختر الفرع", Toast.LENGTH_SHORT).show();
                    return;
                }




                myViewModel.getUserToRegisterCheck(Integer.parseInt(id),phone,email, new RegisterCheck() {
                    @Override
                    public void Item(User user) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                if (user == null ) {


                                    binding.addMusharafBtnProgressBar.setVisibility(View.VISIBLE);
                                    binding.addMusharafBtnRegister.setEnabled(false);
                                    if (hotNumber == 101) {
                                        if (selectedImageUri == null) {
                                            Toast.makeText(AddMusharaf.this, "الرجاء ادخال صورة المستخدم", Toast.LENGTH_SHORT).show();
                                            binding.addMusharafBtnProgressBar.setVisibility(View.GONE);
                                            return;
                                        }

                                        Calendar calendar = Calendar.getInstance();
                                        storage.getReference()
                                                .child("Images/" + calendar.getTimeInMillis())
                                                .putFile(selectedImageUri)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {

                                                                User user = new User(Integer.parseInt(id), 1, email, password, name, phone, selectedDate.getTime(), address, NumberidBransh, uri.toString());
                                                                myViewModel.insertUser(user);
                                                                Intent intent = new Intent();
                                                                intent.putExtra(MUSHARAF_KEY, user);
                                                                setResult(RESULT_OK, intent);
                                                                binding.addMusharafBtnRegister.setEnabled(true);
                                                                binding.addMusharafBtnProgressBar.setVisibility(View.GONE);
                                                                finish();
                                                                Toast.makeText(AddMusharaf.this, "تمت اضافة مشرف بنجاح", Toast.LENGTH_SHORT).show();


                                                            }
                                                        });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                binding.addMusharafBtnProgressBar.setVisibility(View.GONE);
                                                binding.addMusharafBtnRegister.setEnabled(true);
                                                Toast.makeText(AddMusharaf.this, "حدث خظأ في رفع الصورة" , Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                } else {
                                    binding.addMusharafBtnProgressBar.setVisibility(View.GONE);
                                    binding.addMusharafBtnRegister.setEnabled(true);
                                    if (hotNumber==101){
                                        Toast.makeText(AddMusharaf.this, "البيانات المدخلة موجودة في حساب اخر", Toast.LENGTH_SHORT).show();

                                    }
                                }



                                binding.addMusharafBtnProgressBar.setVisibility(View.VISIBLE);
                                binding.addMusharafBtnRegister.setEnabled(false);
                                if (user != null) {


                                if (user.getPhone().equals(phone) || user.getEmail().equals(email) || user.getIdNumber() == Integer.parseInt(id)) {


                                    if (hotNumber == 102) {

                                        if (selectedImageUri != null) {


                                            Calendar calendar = Calendar.getInstance();
                                            storage.getReference()
                                                    .child("Images/" + calendar.getTimeInMillis())
                                                    .putFile(selectedImageUri)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {

                                                                    myViewModel.updateUserWithImage(idMoshref, name, email, phone, address, NumberidBransh, selectedDate.getTime(), uri.toString());
                                                                    binding.addMusharafBtnProgressBar.setVisibility(View.GONE);
                                                                    binding.addMusharafBtnRegister.setEnabled(true);
                                                                    Toast.makeText(AddMusharaf.this, "تمت تحديث المشرف بنجاح", Toast.LENGTH_SHORT).show();


                                                                }
                                                            });
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    binding.addMusharafBtnProgressBar.setVisibility(View.GONE);
                                                    binding.addMusharafBtnRegister.setEnabled(true);
                                                    Toast.makeText(AddMusharaf.this, "حدث خظأ في رفع الصورة" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                        } else if (selectedImageUri == null) {

                                            myViewModel.updateUserNoImage(idMoshref, name, email, phone, address, NumberidBransh, selectedDate.getTime());
                                            binding.addMusharafBtnProgressBar.setVisibility(View.GONE);
                                            binding.addMusharafBtnRegister.setEnabled(true);
                                            Toast.makeText(AddMusharaf.this, "تمت تحديث المشرف بنجاح", Toast.LENGTH_SHORT).show();


                                        }

                                    }
                                }

                            }



                            }
                        });

                    }
                });


            }
        });





    }




    private void disableFields (){
        binding.addMusharafEdName.setEnabled(false);
        binding.addMusharafEdAddress.setEnabled(false);
        binding.addMusharafEdMobile.setEnabled(false);
        binding.addMusharafBtnDate.setEnabled(false);
        binding.addMusharafSp.setEnabled(false);
        binding.addMusharafEdEmail.setEnabled(false);
        binding.addMusharafEdId.setEnabled(false);
        binding.addMusharafEdPassword.setEnabled(false);
        binding.addMusharafEdRePassword.setEnabled(false);
        binding.addMusharafImgUser.setEnabled(false);
        binding.addMusharafBtnRegister.setEnabled(false);
    }

    private void enabledFields (){
        binding.addMusharafEdName.setEnabled(true);
        binding.addMusharafEdAddress.setEnabled(true);
        binding.addMusharafEdMobile.setEnabled(true);
        binding.addMusharafBtnDate.setEnabled(true);
        binding.addMusharafSp.setEnabled(true);
        binding.addMusharafEdEmail.setEnabled(true);
        binding.addMusharafEdId.setEnabled(true);
        binding.addMusharafEdPassword.setEnabled(true);
        binding.addMusharafEdRePassword.setEnabled(true);
        binding.addMusharafImgUser.setEnabled(true);
        binding.addMusharafBtnRegister.setEnabled(true);
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menu_in = getMenuInflater();
        menu_in.inflate(R.menu.details_menu, menu);

        MenuItem edit = menu.findItem(R.id.item_details_edit);
        MenuItem delete = menu.findItem(R.id.item_details_delete);
        if (hotNumber==101){

            edit.setVisible(false);
            delete.setVisible(false);

        }
        else if (hotNumber==102){

            edit.setVisible(true);
            delete.setVisible(true);
            binding.addMusharafEdId.setEnabled(false);



        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()){

            case  R.id.item_details_edit :

                enabledFields ();
                MenuItem delete = binding.addMusharafToolbar.getMenu().findItem(R.id.item_details_delete);
                binding.addMusharafEdId.setEnabled(false);
                delete.setVisible(false);
                return true;

            case  R.id.item_details_delete :


                myViewModel.deleteUser(idMoshref, new LisIntToInt() {
                    @Override
                    public void lisIntToInt(int value) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent =new Intent(getBaseContext(),MyHomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), "تم حذف المشرف بنجاح بنجاج", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });




                return true;
        }

        return false;


    }


}