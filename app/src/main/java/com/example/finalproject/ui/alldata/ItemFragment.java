package com.example.finalproject.ui.alldata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalproject.AddMahfazActivity;
import com.example.finalproject.AddMemorizationCircle;
import com.example.finalproject.AddStudentActivity;
import com.example.finalproject.AdvertisingActivity;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.MahfazActivity;
import com.example.finalproject.MapsActivityShow;
import com.example.finalproject.MemorizationCircle;
import com.example.finalproject.MyAdapters.CentersAdapter;
import com.example.finalproject.MyAdapters.CentersAdapterItemClick;
import com.example.finalproject.MyAdapters.CirclesAdapter;
import com.example.finalproject.MyAdapters.CirclesAdapterItemClick;
import com.example.finalproject.MyAdapters.MahfazAdapter;
import com.example.finalproject.MyAdapters.MahfazAdapterItemClick;
import com.example.finalproject.MyAdapters.MushrefAdapter;
import com.example.finalproject.MyAdapters.StudentAdapterItemClick;
import com.example.finalproject.MyAdapters.StudentsAdapter;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;
import com.example.finalproject.MyDataBase.SecTable.ShowStudentData;
import com.example.finalproject.R;
import com.example.finalproject.StudentActivity;
import com.example.finalproject.StudentAssessmentActivity;
import com.example.finalproject.databinding.FragmentItemBinding;
import com.example.finalproject.ui.memorizationcenters.MemorizationCentersFragment;

import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MyViewModel myViewModel ;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;


    private String mParam1;
    private String mParam2;

    public ItemFragment() {
        // Required empty public constructor
    }


    public static ItemFragment newInstance(String param1) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentItemBinding binding =FragmentItemBinding.inflate(getLayoutInflater(),container,false);
        sh = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        if (mParam1.equals("المراكز")){

            CentersAdapter adapter = new CentersAdapter(new ArrayList<>(), myViewModel, getActivity(), new CentersAdapterItemClick() {
                @Override
                public void OnItemClick(ShowCenterData showCenterData) {

                    Intent intent111 = new Intent(getActivity(), AdvertisingActivity.class);
                    intent111.putExtra(MemorizationCentersFragment.MY_CENTER_Key, showCenterData);
                    startActivity(intent111);


                }

                @Override
                public void OnItemLocationClick(double lat, double lng) {

                    Intent intent = new Intent(getActivity(), MapsActivityShow.class);
                    intent.putExtra("lat", String.valueOf(lat));
                    intent.putExtra("lng", String.valueOf(lng));
                    startActivity(intent);
                }

                @Override
                public void OnItemCircleClick(ShowCenterData showCenterData) {

                    Intent intent = new Intent(getActivity(), MemorizationCircle.class);
                    intent.putExtra(MemorizationCentersFragment.CENTER_ID_Key, showCenterData.getId());
                    startActivity(intent);

                }
            });


            binding.rv.setAdapter(adapter);
            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rv.setHasFixedSize(true);



            if (sh_ed_validity == 0) {

                myViewModel.getAllCenterss(sh_ed_user).observe(getActivity(), new Observer<List<ShowCenterData>>() {
                    @Override
                    public void onChanged(List<ShowCenterData> showCenterData) {
                        adapter.setShowCenterData(showCenterData);
                    }
                });
            }


        }
        else if(mParam1.equals("الحلقات")){

            CirclesAdapter adapter =new CirclesAdapter(new ArrayList<>(), myViewModel, requireContext(), new CirclesAdapterItemClick() {
                @Override
                public void OnItemClick(ShowCirclesData circlesData) {

                    if (sh_ed_validity==0||sh_ed_validity==1){

                        Intent intent =new Intent(getActivity(), AddMemorizationCircle.class);
                        intent.putExtra(MemorizationCircle.MY_CIRCLE_DATA_KEY,circlesData);
                        intent.putExtra(MemorizationCircle.MY_CIRCLE_REQ_KEY,MemorizationCircle.EDIT_CENTER_REQ_CODE);
                        startActivity(intent);
                    }
                }

                @Override
                public void OnItemLocationClick(ShowCirclesData circlesData) {

                    Intent intent =new Intent(getActivity(), MapsActivityShow.class);
                    intent.putExtra("lat",String.valueOf(circlesData.getLatitude()));
                    intent.putExtra("lng",String.valueOf(circlesData.getLongitude()));
                    startActivity(intent);
                }

                @Override
                public void OnItemMahfazClick(ShowCirclesData circlesData) {

                    if (sh_ed_validity==0||sh_ed_validity==1||sh_ed_validity==2){

                        Intent myIntent1 =new Intent(getActivity(), MahfazActivity.class);
                        myIntent1.putExtra(AddMemorizationCircle.ADD_MUAHFEZ_KEY,circlesData);
                        startActivity(myIntent1);
                    }

                }

                @Override
                public void OnItemStudentClick(ShowCirclesData circlesData) {

                    Intent intent1 =new Intent(getActivity(), StudentActivity.class);
                    intent1.putExtra(AddMemorizationCircle.MY_CIRCLE_DATA_TO_STUDENT_KEY,circlesData.getId());
                    intent1.putExtra(AddMemorizationCircle.MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY,circlesData.getIdCenter());
                    startActivity(intent1);

                }
            });


            binding.rv.setAdapter(adapter);
            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rv.setHasFixedSize(true);

            if (sh_ed_validity==0){

                myViewModel.getAllCircleData(sh_ed_user).observe(getActivity(), new Observer<List<ShowCirclesData>>() {
                    @Override
                    public void onChanged(List<ShowCirclesData> showCirclesData) {
                        adapter.setShowCirclesData(showCirclesData);


                    }
                });


            }
        }

        else if(mParam1.equals("المحفظين")){
            MahfazAdapter mahfazAdapter =new MahfazAdapter(new ArrayList<>(), myViewModel, getActivity(), new MahfazAdapterItemClick() {
                @Override
                public void OnItemClick(Mahfaz mahfaz) {
                    Intent intent1 =new Intent(getActivity(), AddMahfazActivity.class);
                    intent1.putExtra(MahfazActivity.Mahfaz_DATA_REQ_KEY,mahfaz);
                    intent1.putExtra(MahfazActivity.Mahfaz_REQ_KEY,MahfazActivity.EDIT_MAHFAZ_REQ_CODE);
                    startActivity(intent1);
                }

                @Override
                public void OnCircleClick(Mahfaz mahfaz) {

                    Intent intentU =new Intent(getActivity(),MemorizationCircle.class);
                    intentU.putExtra(MahfazActivity.MAHFAZ_ID_CENTER_SED_TO_CIRCLE_KEY,mahfaz.getIdCenter());
                    startActivity(intentU);

                }
            });

            binding.rv.setAdapter(mahfazAdapter);
            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rv.setHasFixedSize(true);

            if (sh_ed_validity==0){

                myViewModel.getAllMahfaz(sh_ed_user).observe(getActivity(), new Observer<List<Mahfaz>>() {
                    @Override
                    public void onChanged(List<Mahfaz> mahfazs) {

                        mahfazAdapter.setMahfazs(mahfazs);
                    }
                });
            }

        }

        else if(mParam1.equals("المشرفين")){

            MushrefAdapter adapter = new MushrefAdapter(new ArrayList<>(),getActivity(),myViewModel);

            myViewModel.getAllMushref().observe(getActivity(), new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
                    adapter.setUsers(users);
                }
            });

            binding.rv.setAdapter(adapter);
            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rv.setHasFixedSize(true);

        }
        else if(mParam1.equals("الطلبة")){

            StudentsAdapter studentsAdapter =new StudentsAdapter(new ArrayList<>(), getActivity(), new StudentAdapterItemClick() {
                @Override
                public void OnItemClick(int id) {

                }

                @Override
                public void OnTaskItemClick(int id) {
                    Intent intent1 =new Intent(getActivity(), StudentAssessmentActivity.class);
                    intent1.putExtra(StudentActivity.STUDENT_ID_REQ_KEY,id);
                    startActivity(intent1);
                }
            });

            binding.rv.setAdapter(studentsAdapter);
            binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.rv.setHasFixedSize(true);

            myViewModel.getAllStudentData(sh_ed_user).observe(getActivity(), new Observer<List<ShowStudentData>>() {
                @Override
                public void onChanged(List<ShowStudentData> showStudentData) {
                    studentsAdapter.setStudents(showStudentData);
                }
            });
        }



        return binding.getRoot();
    }
}