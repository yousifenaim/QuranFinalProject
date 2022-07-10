package com.example.finalproject.ui.studentSms;

import android.Manifest;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.MyAdapters.MySmsAdapter;
import com.example.finalproject.MyDataBase.Entity.MySms;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentStudentSmsBinding;

import java.util.ArrayList;
import java.util.List;


public class StudentSmsFragment extends Fragment {

    MyViewModel myViewModel ;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    public StudentSmsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentStudentSmsBinding binding =FragmentStudentSmsBinding.inflate(getLayoutInflater(),container,false);
        myViewModel=new ViewModelProvider(getActivity()).get(MyViewModel.class);

        sh = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);

        MySmsAdapter adapter =new MySmsAdapter(new ArrayList<>(),myViewModel);

        myViewModel.getAllSms(sh_ed_user).observe(requireActivity(), new Observer<List<MySms>>() {
            @Override
            public void onChanged(List<MySms> mySms) {
                adapter.setMySmsss(mySms);
            }
        });

        binding.rv.setAdapter(adapter);
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return binding.getRoot();
    }
}