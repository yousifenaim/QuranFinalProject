package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyAdapters.AdvertisingAdapter;
import com.example.finalproject.MyAdapters.Lisadvertisings;
import com.example.finalproject.MyDataBase.Entity.Advertising;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Listeners.GetOneCenterById;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.GetBestCircle;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.databinding.ActivityAdvertisingBinding;
import com.example.finalproject.ui.memorizationcenters.MemorizationCentersFragment;

import java.util.ArrayList;
import java.util.List;


public class AdvertisingActivity extends AppCompatActivity {

    ActivityAdvertisingBinding binding ;
    MyViewModel myViewModel ;
    static final int ADD_ADVERTISING_REQ_CODE =101;
    static final int EDIT_ADVERTISING_REQ_CODE =102;
    public static final String ADVERTISING_REQ_KEY="ADVERTISING_REQ_KEY";
    public static final String ADVERTISING_DATA_KEY="ADVERTISING_DATA_KEY";

    ShowCenterData showCenterData;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAdvertisingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myViewModel=new ViewModelProvider(this).get(MyViewModel.class);

        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);

        if (sh_ed_validity==0){
            binding.advertisingEdit.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
         showCenterData = (ShowCenterData) intent.getSerializableExtra(MemorizationCentersFragment.MY_CENTER_Key);



        binding.advertisingTvCenterName.setText(showCenterData.getName());
        Glide.with(this).load(showCenterData.getImage()).into(binding.advertisingImageCenter);
        myViewModel.getAssessmentCenter(showCenterData.getId()).observe(this, new Observer<GetBestCircle>() {
            @Override
            public void onChanged(GetBestCircle getBestCircle) {
                if (TextUtils.isEmpty(getBestCircle.getAssement())){
                    binding.advertisingTvCenterAssemtent.setText("0"+"%");
                }
                else
                binding.advertisingTvCenterAssemtent.setText(getBestCircle.getAssement()+"%");
            }
        });

        binding.advertisingBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(getBaseContext(),AddAdvertising.class);
                intent1.putExtra(MemorizationCentersFragment.MY_CENTER_Key,showCenterData);
                intent1.putExtra(ADVERTISING_REQ_KEY,ADD_ADVERTISING_REQ_CODE);
                startActivity(intent1);
            }
        });

        binding.advertisingEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (sh_ed_validity == 0) {

                    Intent intent = new Intent(getBaseContext(), AddMemorizationCenters.class);
                    intent.putExtra(MemorizationCentersFragment.MY_CENTER_Key, showCenterData);
                    intent.putExtra(MemorizationCentersFragment.CENTER_Key, 102);
                    startActivity(intent);
                }

            }
        });


        AdvertisingAdapter advertisingAdapter =new AdvertisingAdapter(new ArrayList<>(), new Lisadvertisings() {
            @Override
            public void OnItemClick(Advertising advertising) {
                Intent intent1 =new Intent(getBaseContext(),AddAdvertising.class);
                intent1.putExtra(ADVERTISING_DATA_KEY,advertising);
                intent1.putExtra(ADVERTISING_REQ_KEY,EDIT_ADVERTISING_REQ_CODE);
                startActivity(intent1);

            }
        },this);


        myViewModel.getAllAdvertising(showCenterData.getId()).observe(this, new Observer<List<Advertising>>() {
            @Override
            public void onChanged(List<Advertising> advertisings) {
                advertisingAdapter.setAdvertisings(advertisings);
            }
        });

        binding.advertisingRv.setAdapter(advertisingAdapter);
        binding.advertisingRv.setLayoutManager(new LinearLayoutManager(this));
        binding.advertisingRv.setHasFixedSize(true);



    }

    @Override
    protected void onResume() {
        super.onResume();
        myViewModel.getOneCenterById(showCenterData.getId(), new GetOneCenterById() {
            @Override
            public void getOneCenterById(Center center) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (center==null){
                            Toast.makeText(AdvertisingActivity.this, "تم حذف المركز", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }
}