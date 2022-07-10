package com.example.finalproject.ui.settings;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.finalproject.DialogFra;
import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentSettingsBinding;
import com.example.finalproject.ui.studentSms.SmsReceiver;


import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class SettingsFragment extends Fragment  {


    FragmentSettingsBinding binding ;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSettingsBinding.inflate(getLayoutInflater());

        sh = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY,0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY,-1);



        binding.settingsFragmentTvSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getActivity(), "تم التنشيط", Toast.LENGTH_SHORT).show();
            }
        });




        binding.settingsFragmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currentLang = Locale.getDefault().getLanguage();
                if (i ==1){
                    setLocale("ar");
                }
                else if (i==2){
                    setLocale("en");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.settingsFragmentTvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        binding.settingsFragmentTvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFra dialog = new DialogFra();

                dialog.show(requireActivity().getSupportFragmentManager(),"");
            }
        });

        return binding.getRoot();
    }


    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(requireContext(), LoginActivity.class);
        getActivity().finish();
        startActivity(refresh);
    }
}