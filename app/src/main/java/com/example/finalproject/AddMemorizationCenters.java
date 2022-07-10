package com.example.finalproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.example.finalproject.MyAdapters.CenterManagerAdapter;
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.LisIntToInt;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.databinding.ActivityAddMemorizationCentersBinding;
import com.example.finalproject.ui.memorizationcenters.MemorizationCentersFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMemorizationCenters extends AppCompatActivity {

    ActivityAddMemorizationCentersBinding binding ;
    int  hotNumber;
    MyViewModel myViewModel;
    CenterManagerAdapter  centerManagerAdapter;
    BranchAdapter branchAdapter;
    Uri selectedImageUri ;
    FirebaseStorage storage ;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    int numberIdBransh ;
    int numberIdUser ;

    double longitude =-1 ;
    double latitude =-1;

    ShowCenterData CenterByEdit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAddMemorizationCentersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.addMemorizationCentersToolbar);
        sh = PreferenceManager.getDefaultSharedPreferences(this);
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY,0);

        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);
        storage =FirebaseStorage.getInstance();



        Intent intent =getIntent();
        hotNumber =intent.getIntExtra(MemorizationCentersFragment.CENTER_Key,-1);
        if (hotNumber==101){
            binding.addMemorizationCentersBtnShowCircle.setVisibility(View.GONE);

        }

        else if (hotNumber==102){

            disableFields();
            CenterByEdit= (ShowCenterData) intent.getSerializableExtra(MemorizationCentersFragment.MY_CENTER_Key);
            Glide.with(getBaseContext()).load(CenterByEdit.getImage()).into(binding.addMemorizationCentersImgAdd);
            binding.addMemorizationCentersEdAddress.setText(CenterByEdit.getAddress());
            binding.addMemorizationCentersEdName.setText(CenterByEdit.getName());
            binding.addMemorizationCentersSpinnerBranch.setSelection(CenterByEdit.getBranch());
            binding.addMemorizationCentersEdNumberCenter.setText(CenterByEdit.getNumberOfCircle()+"");
            longitude =CenterByEdit.getLongitude();
            latitude =CenterByEdit.getLatitude();
            numberIdBransh=CenterByEdit.getBranch();
            numberIdUser =CenterByEdit.getIdUser();
            binding.addMemorizationCentersBtnShowCircle.setVisibility(View.VISIBLE);

        }
        else
            Toast.makeText(getBaseContext(), "Check Net Work", Toast.LENGTH_SHORT).show();



        //adapter
          centerManagerAdapter =new CenterManagerAdapter(new ArrayList<>());
        binding.addMemorizationCentersSpinnerDirectorCenter.setAdapter(centerManagerAdapter);

        myViewModel.getAllUser(sh_ed_user).observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                centerManagerAdapter.setUsers(users);
            }
        });

        binding.addMemorizationCentersSpinnerDirectorCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                User user =centerManagerAdapter.getItem(i);
                numberIdUser=user.getIdNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //spinner branch code
         branchAdapter =new BranchAdapter(new ArrayList<>());
        binding.addMemorizationCentersSpinnerBranch.setAdapter(branchAdapter);

        myViewModel.getAllBranch().observe(this, new Observer<List<Branch>>() {
            @Override
            public void onChanged(List<Branch> branches) {

                branchAdapter.setBranches(branches);

                if (hotNumber==102){
                    int branchId = CenterByEdit.getBranch();
                    for(Branch b: branches){
                        if(b.getNumberBranch()==branchId){
                            binding.addMemorizationCentersSpinnerBranch.setSelection(branches.indexOf(b));
                            break;
                        }
                    }

                }
            }
        });



        binding.addMemorizationCentersSpinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Branch branch =branchAdapter.getItem(i);
                numberIdBransh =branch.getNumberBranch();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        ActivityResultLauncher<String> arl =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                selectedImageUri=result;
                binding.addMemorizationCentersImgAdd.setImageURI(selectedImageUri);
            }
        });

        binding.addMemorizationCentersImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch("image/*");
            }
        });



        ActivityResultLauncher<Intent> startAct = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                longitude = result.getData().getDoubleExtra("longitude" , 0);
                latitude = result.getData().getDoubleExtra("latitude" , 0);

            }
        });


        binding.addMemorizationCentersImgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 =new Intent(getBaseContext(),MapsActivity.class);
                startAct.launch(intent1);

            }
        });

        binding.addMemorizationCentersBtnShowCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CenterByEdit==null){

                    myViewModel.getLastCenter().observe(AddMemorizationCenters.this, new Observer<Center>() {
                        @Override
                        public void onChanged(Center center) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent2 =new Intent(getBaseContext(), MemorizationCircle.class);
                                    intent2.putExtra(MemorizationCentersFragment.CENTER_ID_Key,center.getId());
                                    startActivity(intent2);
                                }
                            });

                        }
                    });

                }
                else{
                Intent intent =new Intent(getBaseContext(), MemorizationCircle.class);
                intent.putExtra(MemorizationCentersFragment.CENTER_ID_Key,CenterByEdit.getId());
                startActivity(intent);
                }
            }
        });




       binding.addMemorizationCentersBtnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {




               String name =binding.addMemorizationCentersEdName.getText().toString();
               String address =binding.addMemorizationCentersEdAddress.getText().toString();
               String number =binding.addMemorizationCentersEdNumberCenter.getText().toString();

               if (hotNumber==101){

                   if (TextUtils.isEmpty(name)){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل اسم المركز", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (TextUtils.isEmpty(address)){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل عنوان المركز", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (TextUtils.isEmpty(number)){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل عدد الحلقات", Toast.LENGTH_SHORT).show();
                       return;
                   }


                   if (longitude==-1&&latitude==-1){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل الموقع", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   if (selectedImageUri==null){
                       Toast.makeText(AddMemorizationCenters.this, "الرجاء ادخال صورة المستخدم", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   binding.addMemorizationCentersProgressBar.setVisibility(View.VISIBLE);
                   binding.addMemorizationCentersBtnSave.setEnabled(false);
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

                                           Center center =new Center(numberIdUser,Integer.parseInt(number),name,uri.toString(),address,numberIdBransh,longitude,latitude);
                                           myViewModel.insertCenter(center);
                                           binding.addMemorizationCentersBtnSave.setEnabled(true);
                                           Toast.makeText(AddMemorizationCenters.this, "تم إضافة مركز بنجاح", Toast.LENGTH_SHORT).show();
                                           binding.addMemorizationCentersBtnShowCircle.setVisibility(View.VISIBLE);
                                           binding.addMemorizationCentersBtnShowCircle.setVisibility(View.VISIBLE);
                                           binding.addMemorizationCentersProgressBar.setVisibility(View.GONE);


                                       }
                                   });
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           binding.addMemorizationCentersBtnSave.setEnabled(true);
                           binding.addMemorizationCentersProgressBar.setVisibility(View.GONE);
                           Toast.makeText(AddMemorizationCenters.this, "حدث خظأ في رفع الصورة"+e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });


               }



               else if (hotNumber==102){


                   ShowCenterData center = (ShowCenterData) intent.getSerializableExtra(MemorizationCentersFragment.MY_CENTER_Key);
                   String imageEdit =center.getImage();
                   double lat = center.getLatitude();
                   double lng = center.getLatitude();

                   if (TextUtils.isEmpty(name)){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل اسم المركز", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (TextUtils.isEmpty(address)){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل عنوان المركز", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (TextUtils.isEmpty(number)){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل عدد الحلقات", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   if (longitude==-1&&latitude==-1){
                       Toast.makeText(AddMemorizationCenters.this, "ادخل الموقع", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   if (selectedImageUri==null){

                       Center center2 =new Center(numberIdUser,Integer.parseInt(number),name,imageEdit,address,numberIdBransh,longitude,latitude);
                       myViewModel.updateCenter1(CenterByEdit.getId(),center2.getName(),center2.getNumberOfCircle(),center2.getImage(),center2.getAddress(),center2.getBranch(),center2.getLongitude(),center2.getLatitude());
                       Toast.makeText(AddMemorizationCenters.this, "تم تحديث المركز بنجاح", Toast.LENGTH_SHORT).show();
                       binding.addMemorizationCentersBtnSave.setEnabled(true);
                       binding.addMemorizationCentersProgressBar.setVisibility(View.GONE);
                   }
                   else if (selectedImageUri!=null){


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

                                               Center center2 =new Center(numberIdUser,Integer.parseInt(number),name,uri.toString(),address,numberIdBransh,longitude,latitude);
                                               myViewModel.updateCenter1(CenterByEdit.getId(),center2.getName(),center2.getNumberOfCircle(),center2.getImage(),center2.getAddress(),center2.getBranch(),center2.getLongitude(),center2.getLatitude());
                                               Toast.makeText(AddMemorizationCenters.this, "تم تحديث المركز بنجاح", Toast.LENGTH_SHORT).show();
                                               binding.addMemorizationCentersBtnSave.setEnabled(true);
                                               binding.addMemorizationCentersBtnShowCircle.setVisibility(View.VISIBLE);
                                               binding.addMemorizationCentersProgressBar.setVisibility(View.GONE);



                                           }
                                       });
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               binding.addMemorizationCentersBtnSave.setEnabled(true);
                               Toast.makeText(AddMemorizationCenters.this, "حدث خظأ في رفع الصورة"+e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });

                   }




               }

               else{
                   Toast.makeText(AddMemorizationCenters.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                   binding.addMemorizationCentersBtnSave.setEnabled(true);
                   binding.addMemorizationCentersProgressBar.setVisibility(View.GONE);
               }



           }
       });



    }

    private void disableFields (){
        binding.addMemorizationCentersEdName.setEnabled(false);
        binding.addMemorizationCentersEdAddress.setEnabled(false);
        binding.addMemorizationCentersEdNumberCenter.setEnabled(false);
        binding.addMemorizationCentersImgAdd.setEnabled(false);
        binding.addMemorizationCentersImgMap.setEnabled(false);
        binding.addMemorizationCentersSpinnerBranch.setEnabled(false);
        binding.addMemorizationCentersSpinnerDirectorCenter.setEnabled(false);
        binding.addMemorizationCentersBtnSave.setVisibility(View.GONE);
    }

    private void enabledFields (){
        binding.addMemorizationCentersEdName.setEnabled(true);
        binding.addMemorizationCentersEdAddress.setEnabled(true);
        binding.addMemorizationCentersEdNumberCenter.setEnabled(true);
        binding.addMemorizationCentersImgAdd.setEnabled(true);
        binding.addMemorizationCentersImgMap.setEnabled(true);
        binding.addMemorizationCentersSpinnerBranch.setEnabled(true);
        binding.addMemorizationCentersSpinnerDirectorCenter.setEnabled(true);
        binding.addMemorizationCentersBtnSave.setVisibility(View.VISIBLE);
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
                MenuItem delete = binding.addMemorizationCentersToolbar.getMenu().findItem(R.id.item_details_delete);
                delete.setVisible(false);
                return true;

            case  R.id.item_details_delete :



                myViewModel.deleteCenter(CenterByEdit.getId(), new LisIntToInt() {
                    @Override
                    public void lisIntToInt(int value) {

                        finish();

                    }
                });




                return true;
        }

        return false;


    }


}