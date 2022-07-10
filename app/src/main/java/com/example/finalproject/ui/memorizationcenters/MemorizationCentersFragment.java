package com.example.finalproject.ui.memorizationcenters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finalproject.AddMemorizationCenters;
import com.example.finalproject.AdvertisingActivity;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.MapsActivityShow;
import com.example.finalproject.MemorizationCircle;
import com.example.finalproject.MyAdapters.CentersAdapterItemClick;
import com.example.finalproject.MyAdapters.CentersAdapter;
import com.example.finalproject.MyDataBase.Entity.Advertising;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.databinding.MemorizationCentersFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class MemorizationCentersFragment extends Fragment {

    MemorizationCentersFragmentBinding binding;
    static final int ADD_CENTER_REQ_CODE = 101;
    static final int EDIT_CENTER_REQ_CODE = 102;
    public static final String CENTER_Key = "center";
    public static final String MY_CENTER_Key = "mycenter";
    public static final String CENTER_ID_Key = "centeridkey";
    MyViewModel myViewModel;

    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;

//642672726
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = MemorizationCentersFragmentBinding.inflate(getLayoutInflater());

        sh = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);

        if (sh.getInt(LoginActivity.VALIDITY_KEY, -1) != 0) {
            binding.memorizationCentersBtnAdd.setVisibility(View.GONE);

        }


        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);





        CentersAdapter adapter = new CentersAdapter(new ArrayList<>(), myViewModel, getActivity(), new CentersAdapterItemClick() {
            @Override
            public void OnItemClick(ShowCenterData showCenterData) {

                Intent intent111 = new Intent(getActivity(), AdvertisingActivity.class);
                intent111.putExtra(MY_CENTER_Key, showCenterData);
                startActivity(intent111);

//                if (sh_ed_validity == 0) {
//
//                    Intent intent = new Intent(getActivity(), AddMemorizationCenters.class);
//                    intent.putExtra(MY_CENTER_Key, showCenterData);
//                    intent.putExtra(CENTER_Key, EDIT_CENTER_REQ_CODE);
//                    startActivity(intent);
//                }


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
                intent.putExtra(CENTER_ID_Key, showCenterData.getId());
                startActivity(intent);

            }
        });


        binding.memorizationCentersRv.setAdapter(adapter);
        binding.memorizationCentersRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.memorizationCentersRv.setHasFixedSize(true);


        if (sh_ed_validity == 0) {
            myViewModel.getAllCenterss(sh_ed_user).observe(getActivity(), new Observer<List<ShowCenterData>>() {
                @Override
                public void onChanged(List<ShowCenterData> showCenterData) {
                    adapter.setShowCenterData(showCenterData);
                }
            });
        }


        if (sh_ed_validity == 1) {

            myViewModel.getOneCenterData(sh_ed_user).observe(getActivity(), new Observer<List<ShowCenterData>>() {
                @Override
                public void onChanged(List<ShowCenterData> showCenterData) {

                    adapter.setShowCenterData(showCenterData);
                }
            });
        }

        if (sh_ed_validity == 2) {

            myViewModel.getOneCenterDataMahafz(sh_ed_user).observe(getActivity(), new Observer<List<ShowCenterData>>() {
                @Override
                public void onChanged(List<ShowCenterData> showCenterData) {

                    adapter.setShowCenterData(showCenterData);
                }
            });
        }

        if (sh_ed_validity == 3) {

            myViewModel.getOneCenterDataStudent(sh_ed_user).observe(getActivity(), new Observer<List<ShowCenterData>>() {
                @Override
                public void onChanged(List<ShowCenterData> showCenterData) {
                    adapter.setShowCenterData(showCenterData);
                }
            });
        }


            binding.memorizationCentersBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AddMemorizationCenters.class);
                    intent.putExtra(CENTER_Key, ADD_CENTER_REQ_CODE);
                    startActivity(intent);
                }
            });


            // View root = inflater.inflate(R.layout.memorization_centers_fragment, container, false);



        return binding.getRoot();
    }
}
