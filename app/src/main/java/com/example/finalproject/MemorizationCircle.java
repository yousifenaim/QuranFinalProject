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

import com.example.finalproject.MyAdapters.CirclesAdapter;
import com.example.finalproject.MyAdapters.CirclesAdapterItemClick;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Listeners.GetOneCenterById;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;
import com.example.finalproject.databinding.ActivityMemorizationCircleBinding;
import com.example.finalproject.ui.memorizationcenters.MemorizationCentersFragment;

import java.util.ArrayList;
import java.util.List;

public class MemorizationCircle extends AppCompatActivity {

    ActivityMemorizationCircleBinding binding ;
    MyViewModel myViewModel ;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    public static final String MY_CIRCLE_DATA_KEY="mycircledata";
    public static final String MY_CIRCLE_REQ_KEY="mycirclereq";
    public static final int ADD_CENTER_REQ_CODE =101;
    public static final int EDIT_CENTER_REQ_CODE =102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemorizationCircleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);

        sh = PreferenceManager.getDefaultSharedPreferences(this);
        sh_edit = sh.edit();

        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY,0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY,0);
        Intent intent =getIntent();
        long id = intent.getLongExtra(MemorizationCentersFragment.CENTER_ID_Key,-1);
        long requestIdFromMahafezCard =getIntent().getLongExtra(MahfazActivity.MAHFAZ_ID_CENTER_SED_TO_CIRCLE_KEY,-1);


        if (sh_ed_validity==2||sh_ed_validity==3){
            binding.memorizationCircleBtnAdd.setVisibility(View.GONE);
        }
        binding.memorizationCircleBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getBaseContext(),AddMemorizationCircle.class);
                intent.putExtra(MY_CIRCLE_REQ_KEY,ADD_CENTER_REQ_CODE);
                intent.putExtra(MemorizationCentersFragment.CENTER_ID_Key,id);
                startActivity(intent);
            }
        });









        CirclesAdapter adapter =new CirclesAdapter(new ArrayList<>(), myViewModel, this, new CirclesAdapterItemClick() {
            @Override
            public void OnItemClick(ShowCirclesData circlesData) {

                if (sh_ed_validity==0||sh_ed_validity==1){

                    Intent intent =new Intent(MemorizationCircle.this, AddMemorizationCircle.class);
                  //  Circle circle =new Circle(circlesData.getCircleName(),circlesData.getIdMusharaf(),circlesData.getIdCenter(),circlesData.getNumberOfStudent(),circlesData.getDescr(),circlesData.getAddress(),circlesData.getCircleImage(),circlesData.getLongitude(),circlesData.getLatitude());
                    intent.putExtra(MY_CIRCLE_DATA_KEY,circlesData);
                    intent.putExtra(MY_CIRCLE_REQ_KEY,EDIT_CENTER_REQ_CODE);
                    startActivity(intent);
                }
            }

            @Override
            public void OnItemLocationClick(ShowCirclesData circlesData) {

                         Intent intent =new Intent(MemorizationCircle.this, MapsActivityShow.class);
                         intent.putExtra("lat",String.valueOf(circlesData.getLatitude()));
                         intent.putExtra("lng",String.valueOf(circlesData.getLongitude()));
                         startActivity(intent);
            }

            @Override
            public void OnItemMahfazClick(ShowCirclesData circlesData) {

                if (sh_ed_validity==0||sh_ed_validity==1||sh_ed_validity==2){

                    Intent myIntent1 =new Intent(getBaseContext(),MahfazActivity.class);
                    myIntent1.putExtra(AddMemorizationCircle.ADD_MUAHFEZ_KEY,circlesData);
                    startActivity(myIntent1);
                }

            }

            @Override
            public void OnItemStudentClick(ShowCirclesData circlesData) {

                Intent intent1 =new Intent(getBaseContext(),StudentActivity.class);
                intent1.putExtra(AddMemorizationCircle.MY_CIRCLE_DATA_TO_STUDENT_KEY,circlesData.getId());
                intent1.putExtra(AddMemorizationCircle.MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY,circlesData.getIdCenter());
                startActivity(intent1);

            }
        });

        if (sh_ed_validity==0){


            if (id!=-1){
                myViewModel.getCircleDataToShowAdmin(id).observe(this, new Observer<List<ShowCirclesData>>() {
                    @Override
                    public void onChanged(List<ShowCirclesData> showCirclesData) {

                        adapter.setShowCirclesData(showCirclesData);
                    }
                });
            }
            else if (id==-1){

                myViewModel.getCircleDataToShowAdmin(requestIdFromMahafezCard).observe(this, new Observer<List<ShowCirclesData>>() {
                    @Override
                    public void onChanged(List<ShowCirclesData> showCirclesData) {

                        adapter.setShowCirclesData(showCirclesData);
                    }
                });
            }





        }
        else if (sh_ed_validity==1){

            if (id!=-1){
                myViewModel.getCircleDataToMusharaf(id,sh_ed_user).observe(this, new Observer<List<ShowCirclesData>>() {
                    @Override
                    public void onChanged(List<ShowCirclesData> showCirclesData) {
                        adapter.setShowCirclesData(showCirclesData);

                    }
                });
            }
            else if (id==-1){
                myViewModel.getCircleDataToMusharaf(requestIdFromMahafezCard,sh_ed_user).observe(this, new Observer<List<ShowCirclesData>>() {
                    @Override
                    public void onChanged(List<ShowCirclesData> showCirclesData) {
                        adapter.setShowCirclesData(showCirclesData);

                    }
                });
            }


        }
        else if (sh_ed_validity==2){


         if (id!=-1){

             myViewModel.getCircleDataToMahfaz(id,sh_ed_user).observe(this, new Observer<List<ShowCirclesData>>() {
                 @Override
                 public void onChanged(List<ShowCirclesData> showCirclesData) {
                     if (showCirclesData.size()==0){
                         Toast.makeText(getBaseContext(), id+"hhhh"+sh_ed_user, Toast.LENGTH_SHORT).show();

                     }
                     adapter.setShowCirclesData(showCirclesData);
                 }
             });
         }


            else if (id==-1){
                //الايدي من عند المحفظ
//                Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();

                myViewModel.getCircleDataToMahfaz(requestIdFromMahafezCard,sh_ed_user).observe(this, new Observer<List<ShowCirclesData>>() {
                    @Override
                    public void onChanged(List<ShowCirclesData> showCirclesData) {
                        adapter.setShowCirclesData(showCirclesData);


                    }
                });
            }



        }

        else if (sh_ed_validity==3){

                    myViewModel.getCircleDataToStudent(id,sh_ed_user).observe(this, new Observer<List<ShowCirclesData>>() {
                            @Override
                           public void onChanged(List<ShowCirclesData> showCirclesData) {

                                adapter.setShowCirclesData(showCirclesData);

                            }
                         });

        }




        binding.memorizationCircleRv.setAdapter(adapter);
        binding.memorizationCircleRv.setLayoutManager(new LinearLayoutManager(this));
        binding.memorizationCircleRv.setHasFixedSize(true);



    }


}