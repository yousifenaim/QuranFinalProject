package com.example.finalproject;

import androidx.activity.result.ActivityResult;
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
import android.util.Log;
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
import com.example.finalproject.MyAdapters.ShowMahfazAdapter;
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.Student;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.LisIntToInt;
import com.example.finalproject.MyDataBase.Listeners.LisLongToLong;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.Listeners.ShowIdUserToCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowIdUserAndPasswordToCheck;
import com.example.finalproject.MyDataBase.SecTable.ShowOneStudentData;
import com.example.finalproject.databinding.ActivityAddStudentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddStudentActivity extends AppCompatActivity {

    ActivityAddStudentBinding binding ;
    FirebaseStorage storage ;
    MyViewModel myViewModel;
    Uri selectedImageUri ;

    BranchAdapter branchAdapter;
    ShowCenterAdapter showCenterAdapter;
    ShowCircleAdapter showCircleAdapter;
    ShowMahfazAdapter showMahfazAdapter ;

    Calendar selectedDate;

    int  hotNumber;
    int numberIdBransh;
    int numberIdMahafz;
    long numberIdCenter;
    long numberIdCircle;

    int idSolve ;
    long circleID102;
    Date date102 ;
    String myImage102 ;
    double longitude =-1 ;
    double latitude =-1;
    int idStudentNumberFromEdit;

    int idBranch102 ;
    long idCircle101 ;
    long idCenter101 ;
    long idCircle102 ;
    long idCenter102 ;

    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    ShowOneStudentData showOneStudentDataEditEdit ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.addStudentToolbar);

        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);
        storage =FirebaseStorage.getInstance();
        selectedDate = Calendar.getInstance();

        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);


        Intent myIntent =getIntent();
         idCircle101 =myIntent.getLongExtra(AddMemorizationCircle.MY_CIRCLE_DATA_TO_STUDENT_KEY,-1);
        idCenter101 =myIntent.getLongExtra(AddMemorizationCircle.MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY,-1);
        hotNumber =myIntent.getIntExtra(StudentActivity.STUDENT_REQ_KEY,0);


        if (hotNumber==101){

            binding.addStudentBtnAssessment.setVisibility(View.GONE);
        }
        else if (hotNumber==102){
            disableFields();
            Intent intent001 =getIntent();
             idStudentNumberFromEdit =intent001.getIntExtra(StudentActivity.STUDENT_ID_REQ_KEY,-1);

            myViewModel.getOneStudentData(idStudentNumberFromEdit).observe(this, new Observer<ShowOneStudentData>() {
                @Override
                public void onChanged(ShowOneStudentData showOneStudentData) {

                    // حطية الشرط علشان لمن بحذف بعطيني خطأ
                    if (showOneStudentData!=null){
                        binding.addStudentEdId.setText(showOneStudentData.getId()+"");
                        binding.addStudentEdName.setText(showOneStudentData.getName());
                        binding.addStudentEdMobile.setText(showOneStudentData.getPhone());
                        binding.addStudentEdRePassword.setText(showOneStudentData.getPassword());
                        binding.addStudentEdPassword.setText(showOneStudentData.getPassword());
                        binding.addStudentBtnDate.setText(getDateFormat(showOneStudentData.getDate()));
                        binding.addStudentEdAddress.setText(showOneStudentData.getAddress());
                        date102 =showOneStudentData.getDate();
                        idBranch102=showOneStudentData.getBranch();
                        myImage102=showOneStudentData.getImage();
                        idSolve=showOneStudentData.getId();
                        showOneStudentDataEditEdit=showOneStudentData;
                        longitude =showOneStudentData.getLongitude() ;
                        latitude =showOneStudentData.getLatitude();
                        Glide.with(getBaseContext()).load(showOneStudentData.getImage()).into(binding.addStudentImgUser);



                    }

                    if (sh_ed_validity==3){
                        binding.addStudentBtnAssessment.setVisibility(View.GONE);
                    }


                    if (showOneStudentData!=null){
                        myViewModel.getOneStudent(showOneStudentData.getId()).observe(AddStudentActivity.this, new Observer<Student>() {
                            @Override
                            public void onChanged(Student student) {

                                if (sh_ed_validity==2){

                                    if (hotNumber==102){
                                        numberIdMahafz= sh_ed_user;
                                        numberIdCenter= showOneStudentDataEditEdit.getCenterId();
                                        numberIdCircle= showOneStudentDataEditEdit.getIdCircle();

                                    }
                                }

                                if (sh_ed_validity==3){

                                    binding.addStudentSpCenter.setVisibility(View.GONE);
                                    binding.addStudentSpCenter.setVisibility(View.GONE);
                                    binding.addStudentSpCircle.setVisibility(View.GONE);

                                    if (hotNumber==102){
                                        numberIdMahafz= student.getIdMohafez();
                                        numberIdCenter= showOneStudentDataEditEdit.getCenterId();
                                        numberIdCircle= showOneStudentDataEditEdit.getIdCircle();

                                    }
                                }

                            }
                        });
                    }



                }
            });


        }






        //map

        ActivityResultLauncher<Intent> startAct = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                longitude = result.getData().getDoubleExtra("longitude" , 0);
                latitude = result.getData().getDoubleExtra("latitude" , 0);

            }
        });


        binding.addStudentCircleImgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 =new Intent(getBaseContext(),MapsActivity.class);
                startAct.launch(intent1);

            }
        });



        ActivityResultLauncher<String> arl =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                selectedImageUri=result;
                binding.addStudentImgUser.setImageURI(selectedImageUri);
            }
        });

        binding.addStudentImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch("image/*");
            }
        });




        //adapters

        //spinner branch code
        branchAdapter =new BranchAdapter(new ArrayList<>());
        binding.addStudentSpBranch.setAdapter(branchAdapter);

        myViewModel.getAllBranch().observe(this, new Observer<List<Branch>>() {
            @Override
            public void onChanged(List<Branch> branches) {
                branchAdapter.setBranches(branches);

                if (hotNumber==102) {
                    int branchId = idBranch102;
                    for (Branch b : branches) {
                        if (b.getNumberBranch() == branchId) {
                            binding.addStudentSpBranch.setSelection(branches.indexOf(b));
                            break;
                        }
                    }
                }


            }
        });

        binding.addStudentSpBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Branch branch =branchAdapter.getItem(i);
                numberIdBransh =branch.getNumberBranch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.addStudentBtnAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 =new Intent(getBaseContext(),StudentAssessmentActivity.class);
                if (hotNumber==102){
                    intent1.putExtra(StudentActivity.STUDENT_ID_REQ_KEY,idStudentNumberFromEdit);
                }else if (hotNumber==101){
                    String a = binding.addStudentEdId.getText().toString();
                    intent1.putExtra(StudentActivity.STUDENT_ID_REQ_KEY,Integer.parseInt(a));
                }
                startActivity(intent1);
            }
        });



        if (sh_ed_validity==2 &&hotNumber==101){
            binding.addStudentSpCenter.setVisibility(View.GONE);
            binding.addStudentSpCenter.setVisibility(View.GONE);
            binding.addStudentSpCircle.setVisibility(View.GONE);

            Toast.makeText(this, ""+sh_ed_user, Toast.LENGTH_SHORT).show();


            numberIdMahafz = sh_ed_user;
            numberIdCenter= idCenter101;
            numberIdCircle= idCircle101;
        }




        //spinner code  center

        showCenterAdapter = new ShowCenterAdapter(new ArrayList<>());
        binding.addStudentSpCenter.setAdapter(showCenterAdapter);


        //spinner code  Circle
        showCircleAdapter = new ShowCircleAdapter(new ArrayList<>());
        binding.addStudentSpCircle.setAdapter(showCircleAdapter);

        //spinner muhafez code
        showMahfazAdapter =new ShowMahfazAdapter(new ArrayList<>());
        binding.addStudentSpMahafez.setAdapter(showMahfazAdapter);


        if (sh_ed_validity == 0) {

            myViewModel.getAllCenters(sh_ed_user).observe(this, new Observer<List<Center>>() {
                @Override
                public void onChanged(List<Center> centers) {
                    showCenterAdapter.setCenters(centers);

                    if (hotNumber==101){
                        long centerId = idCenter101;
                        for(Center b: centers){
                            if(b.getId()==centerId){
                                binding.addStudentSpCenter.setSelection(centers.indexOf(b));
                                break;
                            }
                        }
                    }

                    if (hotNumber==102){
                        long centerId = idCenter102;
                        for(Center b: centers){
                            if(b.getId()==centerId){
                                binding.addStudentSpCenter.setSelection(centers.indexOf(b));
                                break;
                            }
                        }
                    }
                }
            });


            binding.addStudentSpCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Center center = showCenterAdapter.getItem(i);
                    numberIdCenter = center.getId();


                    if (sh_ed_validity == 0) {
                        myViewModel.getAllCircleByIdCenter(numberIdCenter).observe(AddStudentActivity.this, new Observer<List<Circle>>() {
                            @Override
                            public void onChanged(List<Circle> circles) {
                                showCircleAdapter.setCircles(circles);

                                if (hotNumber==101){
                                    long circleId = idCircle101;
                                    for(Circle b: circles){
                                        if(b.getId()==circleId){
                                            binding.addStudentSpCircle.setSelection(circles.indexOf(b));
                                            break;
                                        }
                                    }
                                }

                                if (hotNumber==102){
                                    long circleId = idCircle102;
                                    for(Circle b: circles){
                                        if(b.getId()==circleId){
                                            binding.addStudentSpCircle.setSelection(circles.indexOf(b));
                                            break;
                                        }
                                    }
                                }


                                if (circles.size()==0){
                                    Toast.makeText(AddStudentActivity.this, "يرجى اضافة حلقة لهذا المركز", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                binding.addStudentSpCircle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        Circle circle = showCircleAdapter.getItem(i);
                                        numberIdCircle = circle.getId();
                                        numberIdCenter = circle.getIdCenter();


                                        myViewModel.getAllMahfaz(idCircle101).observe(AddStudentActivity.this, new Observer<List<Mahfaz>>() {
                                            @Override
                                            public void onChanged(List<Mahfaz> mahfazs) {


                                                showMahfazAdapter.setMahfazs(mahfazs);
                                                myViewModel.getOneStudent(idStudentNumberFromEdit).observe(AddStudentActivity.this, new Observer<Student>() {
                                                    @Override
                                                    public void onChanged(Student student) {
                                                       runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               if (hotNumber==102) {
                                                                   if (student!=null){
                                                                       int IdMohafez = student.getIdMohafez();
                                                                       for (Mahfaz b : mahfazs) {
                                                                           if (b.getIdUser() == IdMohafez) {
                                                                               binding.addStudentSpBranch.setSelection(mahfazs.indexOf(b));
                                                                               break;
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

                                        binding.addStudentSpMahafez.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                Mahfaz mahfaz =showMahfazAdapter.getItem(i);
                                                numberIdMahafz=mahfaz.getIdUser();
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });

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

        }
        else if (sh_ed_validity == 1) {


            myViewModel.getAllCircleByIdUser(sh_ed_user).observe(this, new Observer<List<Circle>>() {
                @Override
                public void onChanged(List<Circle> circles) {

                    showCircleAdapter.setCircles(circles);

                    if (hotNumber==101){
                        long circleId = idCircle101;
                        for(Circle b: circles){
                            if(b.getId()==circleId){
                                binding.addStudentSpCircle.setSelection(circles.indexOf(b));
                                break;
                            }
                        }
                    }

                    if (hotNumber==102){
                        long circleId = idCircle102;
                        for(Circle b: circles){
                            if(b.getId()==circleId){
                                binding.addStudentSpCircle.setSelection(circles.indexOf(b));
                                break;
                            }
                        }
                    }

                }
            });

            binding.addStudentSpCircle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Circle circle = showCircleAdapter.getItem(i);
                    numberIdCircle = circle.getId();
                    numberIdCenter = circle.getIdCenter();


                    myViewModel.getAllMahfaz(idCircle101).observe(AddStudentActivity.this, new Observer<List<Mahfaz>>() {
                        @Override
                        public void onChanged(List<Mahfaz> mahfazs) {

                            if (sh_ed_validity==2){

                            }
                            showMahfazAdapter.setMahfazs(mahfazs);
                        }
                    });


                    binding.addStudentSpMahafez.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            Mahfaz mahfaz =showMahfazAdapter.getItem(i);
                            numberIdMahafz=mahfaz.getIdUser();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }








        binding.addStudentBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date102 =null;
                Calendar now =Calendar.getInstance();
                DatePickerDialog datePickerDialog =DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                                    @Override
                                  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                      binding.addStudentBtnDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
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


        binding.addStudentCircleBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =binding.addStudentEdName.getText().toString().trim();
                String address =binding.addStudentEdAddress.getText().toString().trim();
                String id =binding.addStudentEdId.getText().toString().trim();
                String password =binding.addStudentEdPassword.getText().toString();
                String rePassword =binding.addStudentEdRePassword.getText().toString();
                String phone =binding.addStudentEdMobile.getText().toString().trim();



                if (numberIdMahafz==0){
                    Toast.makeText(AddStudentActivity.this, "ادخل المحفظ", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(name)){
                    Toast.makeText(AddStudentActivity.this, "ادخل الاسم كامل", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!name.contains(" ")){
                    Toast.makeText(AddStudentActivity.this, "ادخل الاسم بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)){
                    Toast.makeText(AddStudentActivity.this, "ادخل العنوان", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(id)){
                    Toast.makeText(AddStudentActivity.this, "ادخل  رقم الهوية", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((id.length() != 9||id.contains("-")||id.contains("."))){
                    Toast.makeText(AddStudentActivity.this, "ادخل  رقم الهوية بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(AddStudentActivity.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rePassword)){
                    Toast.makeText(AddStudentActivity.this, "ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(AddStudentActivity.this, "ادخل  رقم الهاتف ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() != 10||phone.contains("-")||phone.contains(".") ){

                    Toast.makeText(AddStudentActivity.this, "ادخل رقم هاتف صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (password.length()<8){

                    Toast.makeText(AddStudentActivity.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rePassword.length()<8){

                    Toast.makeText(AddStudentActivity.this, "كلمة المرور قصيرة ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)){

                    Toast.makeText(AddStudentActivity.this, "كلمة المرور غير متطابقة ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(String.valueOf(numberIdBransh))){

                    Toast.makeText(AddStudentActivity.this, "اختر الفرع", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (numberIdCenter==0){

                    Toast.makeText(AddStudentActivity.this, "اختر المركز", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (numberIdCircle==0){

                    Toast.makeText(AddStudentActivity.this, "اختر الحلقة", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(String.valueOf(numberIdMahafz))){

                    Toast.makeText(AddStudentActivity.this, "اختر المحفظ", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (selectedDate==null){
                    Toast.makeText(AddStudentActivity.this, "الرجاء ادخال تاريخ الميلاد", Toast.LENGTH_SHORT).show();
                    return;
                }




                if (longitude==-1&&latitude==-1){
                    Toast.makeText(AddStudentActivity.this, "ادخل الموقع", Toast.LENGTH_SHORT).show();
                    return;
                }






               myViewModel.getUserToRegisterCheck(Integer.parseInt(id), phone, new ShowIdUserToCheck() {
                    @Override
                    public void ShowIdUserAndPasswordToCheck(ShowIdUserAndPasswordToCheck userAndPasswordToCheck) {


                        if (hotNumber==101){





                            if (selectedImageUri==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddStudentActivity.this, "الرجاء ادخال صورة المستخدم", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                return;
                            }

                            if (userAndPasswordToCheck==null){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.progressBar2.setVisibility(View.VISIBLE);
                                        binding.addStudentCircleBtnSave.setEnabled(false);
                                    }
                                });
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

                                                        myViewModel.numberOfStudentByIdCircle(numberIdCircle, new LisIntToInt() {
                                                            @Override
                                                            public void lisIntToInt(int value) {

                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {

                                                                        myViewModel.getOneCircleById(numberIdCircle).observe(AddStudentActivity.this, new Observer<Circle>() {
                                                                            @Override
                                                                            public void onChanged(Circle circle) {

                                                                                if (circle.getNumberOfStudent()>value){
                                                                                    runOnUiThread(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            User user =new User();
                                                                                            user.setIdNumber(Integer.parseInt(id));
                                                                                            user.setName(name);
                                                                                            user.setAddress(address);
                                                                                            user.setValidity(3);
                                                                                            user.setPhone(phone);
                                                                                            user.setPassword(password);
                                                                                            user.setImage(uri.toString());
                                                                                            user.setBranch(numberIdBransh);
                                                                                            user.setBirthDate(selectedDate.getTime());
                                                                                            myViewModel.insertUserToAddAnouther(user, new LisLongToLong() {
                                                                                                @Override
                                                                                                public void LisLongToLong(long Value) {
                                                                                                    myViewModel.insertStudent(Integer.parseInt(id), numberIdMahafz, numberIdCircle, numberIdCenter, longitude, latitude, 1.1, new LisLongToLong() {
                                                                                                        @Override
                                                                                                        public void LisLongToLong(long Value) {
                                                                                                            runOnUiThread(new Runnable() {
                                                                                                                @Override
                                                                                                                public void run() {
                                                                                                                    binding.progressBar2.setVisibility(View.GONE);
                                                                                                                    binding.addStudentCircleBtnSave.setEnabled(true);

                                                                                                                    Toast.makeText(AddStudentActivity.this, "تم اضافة طالب", Toast.LENGTH_SHORT).show();
                                                                                                                    disableFields();
                                                                                                                }
                                                                                                            });

                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            });



                                                                                        }
                                                                                    });

                                                                                }
                                                                                else {
                                                                                   runOnUiThread(new Runnable() {
                                                                                       @Override
                                                                                       public void run() {
                                                                                           binding.progressBar2.setVisibility(View.GONE);
                                                                                           Toast.makeText(AddStudentActivity.this, "لقد وصلت للحد الاقصى من عدد الطلاب", Toast.LENGTH_SHORT).show();
                                                                                           finish();
                                                                                       }
                                                                                   });
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                });
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
                                              binding.progressBar2.setVisibility(View.GONE);
                                              binding.addStudentCircleBtnSave.setEnabled(true);
                                              Toast.makeText(AddStudentActivity.this, "حدث خظأ في رفع الصورة"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                          }
                                      });
                                        //binding.RegisterBtnRegister.setClickable(true);
                                    }
                                });

                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddStudentActivity.this, "البيانات موجودة في حساب اخر", Toast.LENGTH_SHORT).show();
                                        binding.progressBar2.setVisibility(View.GONE);
                                        binding.addStudentCircleBtnSave.setEnabled(true);
                                    }
                                });
                            }
                        }

                        if (hotNumber==102){


                            if (userAndPasswordToCheck.getPhone().equals(phone) || userAndPasswordToCheck.getId()==Integer.parseInt(id)) {

                                if (selectedImageUri!=null){

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.progressBar2.setVisibility(View.VISIBLE);
                                            binding.addStudentCircleBtnSave.setEnabled(false);

                                        }
                                    });

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


                                                            if (date102!=null){

                                                                myViewModel.updateUserStudent(Integer.parseInt(id), name, phone, address, numberIdBransh, uri.toString(), password, date102, new LisIntToInt() {
                                                                    @Override
                                                                    public void lisIntToInt(int value) {

                                                                        myViewModel.updateStudent(Integer.parseInt(id),numberIdMahafz,numberIdCircle,numberIdCenter,longitude,latitude);

                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                binding.progressBar2.setVisibility(View.GONE);
                                                                                binding.addStudentCircleBtnSave.setEnabled(true);
                                                                                Toast.makeText(AddStudentActivity.this, "تم تحديث البيانات بنجاح", Toast.LENGTH_SHORT).show();
                                                                                finish();

                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                            else if (date102==null){
                                                                myViewModel.updateUserStudent(Integer.parseInt(id), name, phone, address, numberIdBransh, uri.toString(), password, selectedDate.getTime(), new LisIntToInt() {
                                                                    @Override
                                                                    public void lisIntToInt(int value) {
                                                                        myViewModel.updateStudent(Integer.parseInt(id),numberIdMahafz,numberIdCircle,numberIdCenter,longitude,latitude);
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                binding.progressBar2.setVisibility(View.GONE);
                                                                                binding.addStudentCircleBtnSave.setEnabled(true);
                                                                                Toast.makeText(AddStudentActivity.this, "تم تحديث البيانات بنجاح", Toast.LENGTH_SHORT).show();
                                                                                finish();

                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }


                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  binding.addStudentCircleBtnSave.setEnabled(true);
                                                  binding.progressBar2.setVisibility(View.GONE);
                                                  Toast.makeText(AddStudentActivity.this, "حدث خظأ في رفع الصورة"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                              }
                                          });
                                        }
                                    });


                                }
                                else if (selectedImageUri==null){


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.progressBar2.setVisibility(View.VISIBLE);
                                            binding.addStudentCircleBtnSave.setEnabled(false);
                                        }
                                    });

                                    if (date102!=null){

                                        myViewModel.updateUserStudent(Integer.parseInt(id), name, phone, address, numberIdBransh, myImage102, password, date102, new LisIntToInt() {
                                            @Override
                                            public void lisIntToInt(int value) {
                                                myViewModel.updateStudent(Integer.parseInt(id),numberIdMahafz,numberIdCircle,numberIdCenter,longitude,latitude);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        binding.progressBar2.setVisibility(View.GONE);
                                                        binding.addStudentCircleBtnSave.setEnabled(true);
                                                        Toast.makeText(AddStudentActivity.this, "تم تحديث البيانات بنجاح", Toast.LENGTH_SHORT).show();
                                                        finish();

                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else if (date102==null){
                                        myViewModel.updateUserStudent(Integer.parseInt(id), name, phone, address, numberIdBransh, myImage102, password, selectedDate.getTime(), new LisIntToInt() {
                                            @Override
                                            public void lisIntToInt(int value) {
                                               myViewModel.updateStudent(Integer.parseInt(id),numberIdMahafz,numberIdCircle,numberIdCenter,longitude,latitude);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        binding.progressBar2.setVisibility(View.GONE);
                                                        binding.addStudentCircleBtnSave.setEnabled(true);
                                                        Toast.makeText(AddStudentActivity.this, "تم تحديث البيانات بنجاح", Toast.LENGTH_SHORT).show();
                                                        finish();


                                                    }
                                                });
                                            }
                                        });
                                    }

                                }


                            }

                        }


                    }
                });







            }
        });



    }




    private void disableFields (){
        binding.addStudentEdName.setEnabled(false);
        binding.addStudentEdAddress.setEnabled(false);
        binding.addStudentEdMobile.setEnabled(false);
        binding.addStudentSpBranch.setEnabled(false);
        binding.addStudentBtnDate.setEnabled(false);
        binding.addStudentCircleBtnSave.setEnabled(false);
        binding.addStudentEdPassword.setEnabled(false);
        binding.addStudentEdId.setEnabled(false);
        binding.addStudentEdRePassword.setEnabled(false);
        binding.addStudentCircleImgMap.setEnabled(false);
        binding.addStudentSpCircle.setEnabled(false);
        binding.addStudentSpCenter.setEnabled(false);
        binding.addStudentSpMahafez.setEnabled(false);
        binding.addStudentImgUser.setEnabled(false);
    }

    private void enabledFields (){
        binding.addStudentEdName.setEnabled(true);
        binding.addStudentEdAddress.setEnabled(true);
        binding.addStudentEdMobile.setEnabled(true);
        binding.addStudentSpBranch.setEnabled(true);
        binding.addStudentBtnDate.setEnabled(true);
        binding.addStudentEdId.setEnabled(true);
        binding.addStudentCircleBtnSave.setEnabled(true);
        binding.addStudentEdPassword.setEnabled(true);
        binding.addStudentEdRePassword.setEnabled(true);
        binding.addStudentCircleImgMap.setEnabled(true);
        binding.addStudentSpCircle.setEnabled(true);
        binding.addStudentSpCenter.setEnabled(true);
        binding.addStudentSpMahafez.setEnabled(true);
        binding.addStudentImgUser.setEnabled(true);
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
            binding.addStudentEdId.setEnabled(false);



        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()){

            case  R.id.item_details_edit :

                enabledFields ();
                MenuItem delete = binding.addStudentToolbar.getMenu().findItem(R.id.item_details_delete);
                binding.addStudentEdId.setEnabled(false);
                delete.setVisible(false);
                return true;

            case  R.id.item_details_delete :


                myViewModel.deleteUser(idSolve, new LisIntToInt() {
                    @Override
                    public void lisIntToInt(int value) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "تم حذف الطالب بنجاح بنجاج", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });




                return true;
        }

        return false;


    }

    String  getDateFormat(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        return simpleDateFormat.format(date) ;
    }



}