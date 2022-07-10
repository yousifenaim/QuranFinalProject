package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.MyAdapters.MahfazAdapter;
import com.example.finalproject.MyAdapters.MahfazAdapterItemClick;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Listeners.GetOneCenterById;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;
import com.example.finalproject.databinding.ActivityMahfazBinding;

import java.util.ArrayList;
import java.util.List;

public class MahfazActivity extends AppCompatActivity {

    ActivityMahfazBinding binding ;
    public static final String Mahfaz_REQ_KEY="mafazreq";
    public static final String Mahfaz_ID_Center_REQ_KEY="Mahfaz_ID_Center_REQ_KEY";
    public static final String Mahfaz_ID_Circle_REQ_KEY="Mahfaz_ID_Circle_REQ_KEY";
    public static final String Mahfaz_DATA_REQ_KEY="DATAMAHAFAZ";
    public static final String MAHFAZ_ID_CENTER_SED_TO_CIRCLE_KEY="MAHFAZ_ID_CENTER_SED_TO_CIRCLE_KEY";
    public static final int ADD_MAHFAZ_REQ_CODE =101;
    public static final int EDIT_MAHFAZ_REQ_CODE =102;

    ShowCirclesData circle ;

    MyViewModel myViewModel ;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMahfazBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);

        sh = PreferenceManager.getDefaultSharedPreferences(this);
        sh_edit = sh.edit();

        Intent intent =getIntent();
         circle = (ShowCirclesData) intent.getSerializableExtra(AddMemorizationCircle.ADD_MUAHFEZ_KEY);

        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);

        if (sh_ed_validity==2||sh_ed_validity==3){
            binding.mahfazBtnAdd.setVisibility(View.GONE);
        }


        binding.mahfazBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getBaseContext(),AddMahfazActivity.class);
                intent.putExtra(Mahfaz_REQ_KEY,ADD_MAHFAZ_REQ_CODE);
                intent.putExtra(Mahfaz_ID_Center_REQ_KEY,circle.getIdCenter());
                intent.putExtra(Mahfaz_ID_Circle_REQ_KEY,circle.getId());
                startActivity(intent);
            }
        });

        MahfazAdapter mahfazAdapter =new MahfazAdapter(new ArrayList<>(), myViewModel, this, new MahfazAdapterItemClick() {
            @Override
            public void OnItemClick(Mahfaz mahfaz) {
                Intent intent1 =new Intent(getBaseContext(),AddMahfazActivity.class);
                intent1.putExtra(Mahfaz_DATA_REQ_KEY,mahfaz);
                intent1.putExtra(Mahfaz_REQ_KEY,EDIT_MAHFAZ_REQ_CODE);
                startActivity(intent1);
            }

            @Override
            public void OnCircleClick(Mahfaz mahfaz) {

                Intent intentU =new Intent(getBaseContext(),MemorizationCircle.class);
                intentU.putExtra(MAHFAZ_ID_CENTER_SED_TO_CIRCLE_KEY,mahfaz.getIdCenter());
                startActivity(intentU);

            }
        });

        if (circle.getId()!=-1){

            if (sh_ed_validity==0||sh_ed_validity==1){
                myViewModel.getAllMahfaz(circle.getId()).observe(MahfazActivity.this, new Observer<List<Mahfaz>>() {
                    @Override
                    public void onChanged(List<Mahfaz> mahfazs) {

                        mahfazAdapter.setMahfazs(mahfazs);

                    }
                });
            }
            else if (sh_ed_validity==2){
                myViewModel.getAllMahfaz(circle.getId(),sh_ed_user).observe(this, new Observer<List<Mahfaz>>() {
                    @Override
                    public void onChanged(List<Mahfaz> mahfazs) {
                        mahfazAdapter.setMahfazs(mahfazs);
                    }
                });
            }



        }
        else
            Toast.makeText(this, "Erorrrrrrr", Toast.LENGTH_SHORT).show();




        binding.mahfazRv.setAdapter(mahfazAdapter);
        binding.mahfazRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.mahfazRv.setHasFixedSize(true);








    }
}