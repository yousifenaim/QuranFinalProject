package com.example.finalproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.finalproject.MyAdapters.ShowCenterAdapter;
import com.example.finalproject.MyAdapters.ShowCircleAdapter;
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.LisIntToInt;
import com.example.finalproject.MyDataBase.Listeners.LisLongToLong;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.Listeners.ShowIdUserToCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowIdUserAndPasswordToCheck;
import com.example.finalproject.databinding.ActivityAddMahfazBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMahfazActivity extends AppCompatActivity {

    ActivityAddMahfazBinding binding;
    MyViewModel myViewModel;
    FirebaseStorage storage;

    BranchAdapter branchAdapter;
    ShowCenterAdapter showCenterAdapter;
    ShowCircleAdapter showCircleAdapter;

    int numberIdBransh;
    long numberIdCenter;
    long numberIdCircle;

    long numberIdCenter101;
    long numberIdCircle101;

    int hotNumber ;

    User myUser ;

    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMahfazBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.AddMahfazToolbar);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        storage = FirebaseStorage.getInstance();

         numberIdBransh =-1;
         numberIdCenter =-1;
         numberIdCircle =-1;

        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);


          Intent intent =getIntent();
          hotNumber =intent.getIntExtra(MahfazActivity.Mahfaz_REQ_KEY,0);

          Mahfaz mahfaz = (Mahfaz) intent.getSerializableExtra(MahfazActivity.Mahfaz_DATA_REQ_KEY);



          if (hotNumber==101){
              numberIdCenter101 =intent.getLongExtra(MahfazActivity.Mahfaz_ID_Center_REQ_KEY,0);
              numberIdCircle101 =intent.getLongExtra(MahfazActivity.Mahfaz_ID_Circle_REQ_KEY,0);

          }
          else if (hotNumber==102){
              disableFields();
              myViewModel.getUser(mahfaz.getIdUser()).observe(this, new Observer<User>() {
                  @Override
                  public void onChanged(User user) {
                      // حطية الشرط علشان لمن بحذف بعطيني خطأ
                      if (user!=null){
                          binding.AddMahfazEdName.setText(user.getName());
                          binding.AddMahfazEdPassword.setText(user.getPassword());
                          binding.AddMahfazEdRePassword.setText(user.getPassword());
                          binding.AddMahfazEdMobile.setText(user.getPhone());
                          binding.AddMahfazEdAddress.setText(user.getAddress());
                          binding.addMahfazEdId.setText(user.getIdNumber()+"");
                          myUser=user;
                      }

                  }
              });
              Glide.with(this).load(mahfaz.getIamge()).into(binding.AddMahfazImgUser);
              binding.AddMahfazSpBranch.setSelection(mahfaz.getBranch());
          }
          else if (hotNumber==0)
              Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show();



        //adapters =>>>>>>

        //spinner code branch
        branchAdapter = new BranchAdapter(new ArrayList<>());
        binding.AddMahfazSpBranch.setAdapter(branchAdapter);

        myViewModel.getAllBranch().observe(this, new Observer<List<Branch>>() {
            @Override
            public void onChanged(List<Branch> branches) {
                branchAdapter.setBranches(branches);


                if (hotNumber==102) {
                    int branchId = mahfaz.getBranch();
                    for (Branch b : branches) {
                        if (b.getNumberBranch() == branchId) {
                            binding.AddMahfazSpBranch.setSelection(branches.indexOf(b));
                            break;
                        }
                    }
                }
            }
        });

        binding.AddMahfazSpBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Branch branch = branchAdapter.getItem(i);
                numberIdBransh = branch.getNumberBranch();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //spinner code  center

        showCenterAdapter = new ShowCenterAdapter(new ArrayList<>());
        binding.AddMahfazSpCenter.setAdapter(showCenterAdapter);


        //spinner code  Circle
        showCircleAdapter = new ShowCircleAdapter(new ArrayList<>());
        binding.AddMahfazSpCircle.setAdapter(showCircleAdapter);


        if (sh_ed_validity == 0) {

            myViewModel.getAllCenters(sh_ed_user).observe(this, new Observer<List<Center>>() {
                @Override
                public void onChanged(List<Center> centers) {
                    showCenterAdapter.setCenters(centers);

                    if (hotNumber==101){
                        long centerId = numberIdCenter101;
                        for(Center b: centers){
                            if(b.getId()==centerId){
                                binding.AddMahfazSpCenter.setSelection(centers.indexOf(b));
                                break;
                            }
                        }
                    }

                    if (hotNumber==102){
                        long centerId = mahfaz.getIdCenter();
                        for(Center b: centers){
                            if(b.getId()==centerId){
                                binding.AddMahfazSpCenter.setSelection(centers.indexOf(b));
                                break;
                            }
                        }
                    }
                }
            });


            binding.AddMahfazSpCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Center center = showCenterAdapter.getItem(i);


                    numberIdCenter = center.getId();


                    if (sh_ed_validity == 0) {

                        myViewModel.getAllCircleByIdCenter(numberIdCenter).observe(AddMahfazActivity.this, new Observer<List<Circle>>() {
                            @Override
                            public void onChanged(List<Circle> circles) {
                                showCircleAdapter.setCircles(circles);
                                if (hotNumber==101){
                                    long circleId = numberIdCircle101;
                                    for(Circle b: circles){
                                        if(b.getId()==circleId){
                                            binding.AddMahfazSpCircle.setSelection(circles.indexOf(b));
                                            break;
                                        }
                                    }
                                }

                                if (hotNumber==102){
                                    long circleId = mahfaz.getIdCircle();
                                    for(Circle b: circles){
                                        if(b.getId()==circleId){
                                            binding.AddMahfazSpCircle.setSelection(circles.indexOf(b));
                                            break;
                                        }
                                    }
                                }

                                if (circles.size()==0){
                                    Toast.makeText(AddMahfazActivity.this, "يرجى اضافة حلقة لهذا المركز", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                binding.AddMahfazSpCircle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        Circle circle = showCircleAdapter.getItem(i);
                                        numberIdCircle = circle.getId();
                                        numberIdCenter = circle.getIdCenter();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }
                        });
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else if (sh_ed_validity == 1) {


            myViewModel.getAllCircleByIdUser(sh_ed_user).observe(this, new Observer<List<Circle>>() {
                @Override
                public void onChanged(List<Circle> circles) {

                    showCircleAdapter.setCircles(circles);

                    if (hotNumber==101){
                        long circleId = numberIdCircle101;
                        for(Circle b: circles){
                            if(b.getId()==circleId){
                                binding.AddMahfazSpCircle.setSelection(circles.indexOf(b));
                                break;
                            }
                        }
                    }

                    if (hotNumber==102){
                        long circleId = mahfaz.getIdCircle();
                        for(Circle b: circles){
                            if(b.getId()==circleId){
                                binding.AddMahfazSpCircle.setSelection(circles.indexOf(b));
                                break;
                            }
                        }
                    }
                }
            });

            binding.AddMahfazSpCircle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Circle circle = showCircleAdapter.getItem(i);
                    numberIdCircle = circle.getId();
                    numberIdCenter = circle.getIdCenter();
                    Toast.makeText(AddMahfazActivity.this, "the numberIdCircle is :"+numberIdCircle+"the numberIdCenter is :"+numberIdCenter, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }



        //adapters ^^^^^^^^^^^


        ActivityResultLauncher<String> arl = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                selectedImageUri = result;
                binding.AddMahfazImgUser.setImageURI(selectedImageUri);
            }
        });


        binding.AddMahfazImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch("image/*");
            }
        });


        binding.AddMahfazBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = binding.AddMahfazEdName.getText().toString().trim();
                String address = binding.AddMahfazEdAddress.getText().toString().trim();
                String id = binding.addMahfazEdId.getText().toString().trim();
                String password = binding.AddMahfazEdPassword.getText().toString();
                String rePassword = binding.AddMahfazEdRePassword.getText().toString();
                String phone = binding.AddMahfazEdMobile.getText().toString().trim();


                if (numberIdCircle==-1) {
                    Toast.makeText(AddMahfazActivity.this, "numberIdCircle", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (numberIdCenter==-1) {
                    Toast.makeText(AddMahfazActivity.this, "numberIdCenternumberIdCenternumberIdCenter", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل الاسم بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!name.contains(" ")) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل الاسم كامل", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل العنوان", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل  رقم الهوية", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((id.length() != 9 || id.contains("-") || id.contains("."))) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل  رقم الهوية بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rePassword)) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(AddMahfazActivity.this, "ادخل  رقم الهاتف ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() != 10 || phone.contains("-") || phone.contains(".")) {

                    Toast.makeText(AddMahfazActivity.this, "ادخل رقم هاتف صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length() < 8) {

                    Toast.makeText(AddMahfazActivity.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rePassword.length() < 8) {

                    Toast.makeText(AddMahfazActivity.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)) {

                    Toast.makeText(AddMahfazActivity.this, "كلمة المرور غير متطابقة ", Toast.LENGTH_SHORT).show();
                    return;
                }

                myViewModel.getUserToRegisterCheck(Integer.parseInt(id), phone, new ShowIdUserToCheck() {
                    @Override
                    public void ShowIdUserAndPasswordToCheck(ShowIdUserAndPasswordToCheck userAndPasswordToCheck) {

                        if (hotNumber==101){
                            if (userAndPasswordToCheck!=null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddMahfazActivity.this, "البيانات موجودة في حساب اخر", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                return;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.AddMahfazBtnSave.setEnabled(false);
                                    binding.progressBar.setVisibility( View.VISIBLE);

                                }
                            });
                            if (selectedImageUri == null) {
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(AddMahfazActivity.this, "الرجاء ادخال صورة المستخدم", Toast.LENGTH_SHORT).show();
                                       binding.AddMahfazBtnSave.setEnabled(true);
                                       binding.progressBar.setVisibility( View.GONE);
                                   }
                               });

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

                                                    User user =new User();
                                                    user.setImage(uri.toString());
                                                    user.setName(name);
                                                    user.setBranch(numberIdBransh);
                                                    user.setAddress(address);
                                                    user.setIdNumber(Integer.parseInt(id));
                                                    user.setPassword(password);
                                                    user.setPhone(phone);
                                                    user.setValidity(2);
                                                    Mahfaz mahfaz =new Mahfaz(Integer.parseInt(id),numberIdCenter,numberIdCircle,name,uri.toString(),phone,numberIdBransh);
                                                    myViewModel.insertUserToAddAnouther(user, new LisLongToLong() {
                                                        @Override
                                                        public void LisLongToLong(long value) {

                                                            myViewModel.insertMahfaz(mahfaz);
                                                        }
                                                    });

                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            binding.AddMahfazBtnSave.setEnabled(true);
                                                            binding.progressBar.setVisibility( View.GONE);
                                                            Toast.makeText(AddMahfazActivity.this, "تمت اضافة محفظ بنجاح", Toast.LENGTH_SHORT).show();


                                                        }
                                                    });


                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                  runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          binding.AddMahfazBtnSave.setEnabled(true);
                                          binding.progressBar.setVisibility(View.GONE);
                                          Toast.makeText(AddMahfazActivity.this, "حدث خظأ في رفع الصورة" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                      }
                                  });

                                }
                            });

                        }
                        else  if (hotNumber ==102){

                            if (userAndPasswordToCheck==null){
                                return;
                            }
                            if (userAndPasswordToCheck.getId()==Integer.parseInt(id )||userAndPasswordToCheck.getPhone().equals(phone)){


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.AddMahfazBtnSave.setEnabled(false);
                                        binding.progressBar.setVisibility( View.VISIBLE);
                                    }
                                });


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


                                                            myViewModel.updateUserMohafezWithImage(Integer.parseInt(id), name, phone, address, numberIdBransh, uri.toString(), new LisIntToInt() {
                                                                @Override
                                                                public void lisIntToInt(int value) {
                                                                    myViewModel.updateMahfaz(Integer.parseInt(id),numberIdCenter,numberIdCircle,name,phone,uri.toString(),numberIdBransh);

                                                                }
                                                            });


                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    binding.AddMahfazBtnSave.setEnabled(true);
                                                                    binding.progressBar.setVisibility( View.GONE);
                                                                    Toast.makeText(AddMahfazActivity.this, "تم تحديث المحفظ بنجاح", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });



                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    binding.AddMahfazBtnSave.setEnabled(true);
                                                    binding.progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(AddMahfazActivity.this, "حدث خظأ في رفع الصورة" + e.getMessage(), Toast.LENGTH_SHORT).show();


                                                }
                                            });
                                        }
                                    });



                                }
                                else if (selectedImageUri == null){


                                    myViewModel.updateUserMohafezWithImage(Integer.parseInt(id), name, phone, address, numberIdBransh, myUser.getImage(), new LisIntToInt() {
                                        @Override
                                        public void lisIntToInt(int value) {
                                            myViewModel.updateMahfaz(Integer.parseInt(id),numberIdCenter,numberIdCircle,name,phone,myUser.getImage(),numberIdBransh);

                                        }
                                    });
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.progressBar.setVisibility( View.GONE);
                                            binding.AddMahfazBtnSave.setEnabled(true);
                                            Toast.makeText(AddMahfazActivity.this, "تم تحديث المحفظ بنجاح", Toast.LENGTH_SHORT).show();

                                        }
                                    });



                                }



                            }


                        }
                    }
                });






                        /////







            }
        });


    }


    private void disableFields() {
        binding.AddMahfazEdName.setEnabled(false);
        binding.AddMahfazEdAddress.setEnabled(false);
        binding.AddMahfazEdMobile.setEnabled(false);
        binding.AddMahfazSpBranch.setEnabled(false);
        binding.AddMahfazSpCenter.setEnabled(false);
        binding.addMahfazEdId.setEnabled(false);
        binding.AddMahfazEdRePassword.setEnabled(false);
        binding.AddMahfazEdPassword.setEnabled(false);
        binding.AddMahfazImgUser.setEnabled(false);
        binding.AddMahfazSpCircle.setEnabled(false);
        binding.AddMahfazBtnSave.setEnabled(false);
    }

    private void enabledFields() {
        binding.AddMahfazEdName.setEnabled(true);
        binding.AddMahfazEdAddress.setEnabled(true);
        binding.AddMahfazEdMobile.setEnabled(true);
        binding.AddMahfazSpBranch.setEnabled(true);
        binding.AddMahfazSpCenter.setEnabled(true);
        binding.addMahfazEdId.setEnabled(true);
        binding.AddMahfazEdRePassword.setEnabled(true);
        binding.AddMahfazEdPassword.setEnabled(true);
        binding.AddMahfazImgUser.setEnabled(true);
        binding.AddMahfazSpCircle.setEnabled(true);
        binding.AddMahfazBtnSave.setEnabled(true);


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




        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()){

            case  R.id.item_details_edit :

                enabledFields ();
                binding.addMahfazEdId.setEnabled(false);
                MenuItem delete = binding.AddMahfazToolbar.getMenu().findItem(R.id.item_details_delete);
                delete.setVisible(false);
                return true;

            case  R.id.item_details_delete :


                myViewModel.deleteUser(myUser.getIdNumber(), new LisIntToInt() {
                    @Override
                    public void lisIntToInt(int value) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "تم حذف  المحفظ بنجاج", Toast.LENGTH_SHORT).show();
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