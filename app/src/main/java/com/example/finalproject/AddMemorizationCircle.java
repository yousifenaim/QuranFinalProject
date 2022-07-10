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
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.GetOneCenterById;
import com.example.finalproject.MyDataBase.Listeners.LisIntToInt;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;
import com.example.finalproject.databinding.ActivityAddMemorizationCircleBinding;
import com.example.finalproject.ui.memorizationcenters.MemorizationCentersFragment;

import java.util.ArrayList;
import java.util.List;

public class AddMemorizationCircle extends AppCompatActivity {

    ActivityAddMemorizationCircleBinding binding ;
    int hotNumber ;
    int userIdNumber ;
    int addCircleSuccess =-1 ;
    double longitude =-1 ;
    double latitude =-1;
    ShowCenterAdapter spinnerAdapter ;
    public static final String MY_MUSHARAF_DATA_KEY="mycircledata";
    public static final String MY_CIRCLE_DATA_TO_STUDENT_KEY="MY_CIRCLE_DATA_TO_STUDENT_KEY";
    public static final String MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY="MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY";
    public static final String MY_MUSHARAF_REQ_KEY="mycirclereq";
    public static final String ADD_MUAHFEZ_KEY="addmuhafez";
    static final int ADD_MUSHARAF_REQ_CODE =101;
    static final int EDIT_MUSHARAF_REQ_CODE =102;
    MyViewModel myViewModel ;
    long NumberidCenter;
   //Long id;
    User user;
    String centerImage ;
    ShowCirclesData CircleData102 ;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAddMemorizationCircleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.addMemorizationCircleToolbar);
        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();


        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);


        Intent intent =getIntent();
        hotNumber =intent.getIntExtra(MemorizationCircle.MY_CIRCLE_REQ_KEY,-1);
         long idCenter = intent.getLongExtra(MemorizationCentersFragment.CENTER_ID_Key,-1);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY,-1);

        if (hotNumber==101){

            binding.addMemorizationCircleBtnAddMusharaf.setText(R.string.Add_Musharaf);
        }
        else if (hotNumber==102){
            binding.addMemorizationCircleBtnAddMusharaf.setText(R.string.edit_mushref);
            if (sh_ed_validity==1){
                binding.addMemorizationCircleBtnAddMusharaf.setVisibility(View.GONE);
            }

            CircleData102 = (ShowCirclesData) intent.getSerializableExtra(MemorizationCircle.MY_CIRCLE_DATA_KEY);
            disableFields();
            binding.addMemorizationCircleEdDesc.setText(CircleData102.getDescr());
            binding.addMemorizationCircleEdName.setText(CircleData102.getCircleName());
            binding.addMemorizationCircleEdAddress.setText(CircleData102.getAddress());
            binding.addMemorizationCircleEdNumberStudent.setText(CircleData102.getNumberOfStudent()+"");
            Glide.with(getBaseContext()).load(CircleData102.getCircleImage()).into(binding.addMemorizationCircleImg);
            binding.addMemorizationCircleBtnShowMahfaz.setVisibility(View.VISIBLE);
            binding.addMemorizationCircleBtnShowStudent.setVisibility(View.VISIBLE);

             longitude =CircleData102.getLongitude() ;
             latitude =CircleData102.getLatitude();

        }

        else
            Toast.makeText(getBaseContext(), "Check Net Work", Toast.LENGTH_SHORT).show();

        binding.addMemorizationCircleBtnShowMahfaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent1 =new Intent(getBaseContext(),MahfazActivity.class);
                myIntent1.putExtra(ADD_MUAHFEZ_KEY,CircleData102);
                startActivity(myIntent1);
            }
        });

        binding.addMemorizationCircleBtnShowStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 =new Intent(getBaseContext(),StudentActivity.class);
                intent1.putExtra(MY_CIRCLE_DATA_TO_STUDENT_KEY,CircleData102.getId());
                intent1.putExtra(MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY,CircleData102.getIdCenter());
                startActivity(intent1);
            }
        });





        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode()==RESULT_OK&&result.getData()!=null){
                            user = (User) result.getData().getSerializableExtra(AddMusharaf.MUSHARAF_KEY);
                            binding.addMemorizationCircleBtnAddMusharaf.setEnabled(false);


                        }

                    }
                });


        binding.addMemorizationCircleBtnAddMusharaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hotNumber==101){
                    Intent intent =new Intent(getBaseContext(),AddMusharaf.class);
                    intent.putExtra(MY_MUSHARAF_REQ_KEY,ADD_MUSHARAF_REQ_CODE);
                    launcher.launch(intent);
                }

                else if (hotNumber==102){
                    Intent intent =new Intent(getBaseContext(),AddMusharaf.class);
                    intent.putExtra(MY_MUSHARAF_REQ_KEY,EDIT_MUSHARAF_REQ_CODE);
                    intent.putExtra(MY_MUSHARAF_DATA_KEY,CircleData102.getIdMusharaf());
                    launcher.launch(intent);
                }

            }
        });




        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY,0);

        //spinner code
        spinnerAdapter =new ShowCenterAdapter(new ArrayList<>());
        binding.addMemorizationCircleSpinneCenter.setAdapter(spinnerAdapter);

        myViewModel.getAllCenters(sh_ed_user).observe(this, new Observer<List<Center>>() {
            @Override
            public void onChanged(List<Center> centers) {
                spinnerAdapter.setCenters(centers);

                if (hotNumber==101){
                    long centerId = idCenter;
                    for(Center b: centers){
                        if(b.getId()==centerId){
                            binding.addMemorizationCircleSpinneCenter.setSelection(centers.indexOf(b));
                            break;
                        }
                    }
            }

                if (hotNumber==102){
                    long centerId = CircleData102.getIdCenter();
                    for(Center b: centers){
                        if(b.getId()==centerId){
                            binding.addMemorizationCircleSpinneCenter.setSelection(centers.indexOf(b));
                            break;
                        }
                    }
                }
            }
        });


        binding.addMemorizationCircleSpinneCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Center center =spinnerAdapter.getItem(i);
                NumberidCenter =center.getId();
                centerImage =center.getImage();
                Glide.with(getBaseContext()).load(center.getImage()).into(binding.addMemorizationCircleImg);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        ActivityResultLauncher<Intent> startAct = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                longitude = result.getData().getDoubleExtra("longitude" , 0);
                latitude = result.getData().getDoubleExtra("latitude" , 0);

            }
        });



        binding.addMemorizationCircleImgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 =new Intent(getBaseContext(),MapsActivity.class);
                startAct.launch(intent1);
            }
        });





        binding.addMemorizationCircleBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =binding.addMemorizationCircleEdName.getText().toString().trim();
                String address =binding.addMemorizationCircleEdAddress.getText().toString().trim();
                String numberOfStudent =binding.addMemorizationCircleEdNumberStudent.getText().toString().trim();
                String desc =binding.addMemorizationCircleEdDesc.getText().toString();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(AddMemorizationCircle.this, "ادخل الاسم بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!name.contains(" ")){
                    Toast.makeText(AddMemorizationCircle.this, "ادخل الاسم كامل", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)){
                    Toast.makeText(AddMemorizationCircle.this, "ادخل العنوان", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(numberOfStudent)){
                    Toast.makeText(AddMemorizationCircle.this, "ادخل عدد الطلاب المسموح بهم", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(desc)){
                    Toast.makeText(AddMemorizationCircle.this, "ادخل الوصف", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (longitude==-1&&latitude==-1){
                    Toast.makeText(AddMemorizationCircle.this, "ادخل الموقع", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (hotNumber==101){

                    if (TextUtils.isEmpty(centerImage)){
                        Toast.makeText(AddMemorizationCircle.this, "ادخل الصورة", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (user==null){
                        Toast.makeText(AddMemorizationCircle.this, "قم بإضافة مشرف", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    myViewModel.getOneCenterById(NumberidCenter, new GetOneCenterById() {
                        @Override
                        public void getOneCenterById(Center center) {


                            myViewModel.numberOfCircleByIdCenter(idCenter, new LisIntToInt() {
                                @Override
                                public void lisIntToInt(int value) {

                                    if (center.getNumberOfCircle()>value){


                                        Circle circle = new Circle(name,user.getIdNumber(),NumberidCenter,Integer.parseInt(numberOfStudent),desc,address,centerImage,longitude,latitude);
                                        myViewModel.insertCircle(circle);


                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AddMemorizationCircle.this, "تمت إضافة الحلقة بنجاح", Toast.LENGTH_SHORT).show();
                                                user=null;
                                            }
                                        });

                                    }
                                    else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AddMemorizationCircle.this, "لقد وصلت للحد الاقصى من الحلقات", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }
                            });

                        }
                    });



                }


                else if (hotNumber==102){

                    if (sh_ed_validity==0){
                        myViewModel.updateCircleAdmin(CircleData102.getId(),name,Integer.parseInt(numberOfStudent),address,desc,longitude,latitude,NumberidCenter,centerImage);
                        Toast.makeText(AddMemorizationCircle.this, "تم تحديث الحلقة بنجاح ", Toast.LENGTH_SHORT).show();

                    }
                    else if (sh_ed_validity==1){

                        myViewModel.updateCircle1(CircleData102.getId(),name,Integer.parseInt(numberOfStudent),address,desc,longitude,latitude);
                        Toast.makeText(AddMemorizationCircle.this, "تم تحديث الحلقة بنجاح ", Toast.LENGTH_SHORT).show();

                    }

                }




            }
        });
    }


    private void disableFields (){
        binding.addMemorizationCircleEdName.setEnabled(false);
        binding.addMemorizationCircleEdAddress.setEnabled(false);
        binding.addMemorizationCircleEdNumberStudent.setEnabled(false);
        binding.addMemorizationCircleImg.setEnabled(false);
        binding.addMemorizationCircleImgMap.setEnabled(false);
        binding.addMemorizationCircleEdDesc.setEnabled(false);
        binding.addMemorizationCircleBtnAddMusharaf.setEnabled(false);
        binding.addMemorizationCircleSpinneCenter.setEnabled(false);
        binding.addMemorizationCircleBtnSave.setVisibility(View.GONE);

    }

    private void enabledFields (){
        binding.addMemorizationCircleEdName.setEnabled(true);
        binding.addMemorizationCircleEdAddress.setEnabled(true);
        binding.addMemorizationCircleEdNumberStudent.setEnabled(true);
        binding.addMemorizationCircleImg.setEnabled(true);
        binding.addMemorizationCircleImgMap.setEnabled(true);
        binding.addMemorizationCircleEdDesc.setEnabled(true);
        binding.addMemorizationCircleBtnAddMusharaf.setEnabled(true);
        binding.addMemorizationCircleSpinneCenter.setEnabled(true);
        binding.addMemorizationCircleBtnSave.setVisibility(View.GONE);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menu_in = getMenuInflater();
        menu_in.inflate(R.menu.details_menu, menu);

        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();

        MenuItem edit = menu.findItem(R.id.item_details_edit);
        MenuItem delete = menu.findItem(R.id.item_details_delete);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY,-1);
        if (hotNumber==101){


            edit.setVisible(false);
            delete.setVisible(false);

        }
        else if (hotNumber==102){

            if (sh_ed_validity==0){
                edit.setVisible(true);
                delete.setVisible(true);
            }
            else if (sh_ed_validity==1){
                edit.setVisible(true);
                delete.setVisible(false);
            }



        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY,-1);




        switch (item.getItemId()){

            case  R.id.item_details_edit :

                enabledFields ();
                if (sh_ed_validity==0){
                    binding.addMemorizationCircleBtnAddMusharaf.setVisibility(View.VISIBLE);

                }
                else {
                    binding.addMemorizationCircleBtnAddMusharaf.setVisibility(View.GONE);

                }
                binding.addMemorizationCircleBtnSave.setVisibility(View.VISIBLE);
                MenuItem delete = binding.addMemorizationCircleToolbar.getMenu().findItem(R.id.item_details_delete);
                delete.setVisible(false);
                return true;

            case  R.id.item_details_delete :

                myViewModel.deleteUser(CircleData102.getIdMusharaf(), new LisIntToInt() {
                    @Override
                    public void lisIntToInt(int value) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent =new Intent(getBaseContext(),MyHomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), "تم حذف الحلقة بنجاج", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                    }
                });




                return true;
        }

        return false;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (user!=null){

            myViewModel.deleteUser(user.getIdNumber(), new LisIntToInt() {
                @Override
                public void lisIntToInt(int value) {

                }
            });
            user=null;

        }
    }
}