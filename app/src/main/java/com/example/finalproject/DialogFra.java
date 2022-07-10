package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.databinding.FragmentDialogBinding;

public class DialogFra extends DialogFragment {




    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    MyViewModel myViewModel ;




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        myViewModel =new ViewModelProvider(getActivity()).get(MyViewModel.class);
        sh = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sh_edit = sh.edit();

        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY,0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY,-1);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        FragmentDialogBinding binding = FragmentDialogBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old =  binding.edOldPass.getText().toString();
                String newPass =   binding.edNewPass.getText().toString();
                String reNewPass =   binding.edReNewPass.getText().toString();




                if (TextUtils.isEmpty(old)&&TextUtils.isEmpty(newPass)&&TextUtils.isEmpty(reNewPass)){

                    Toast.makeText(getActivity(), "ادخل النص بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPass.equals(reNewPass)){
                    Toast.makeText(getActivity(), "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPass.equals(reNewPass)){
                    myViewModel.getUser(sh_ed_user).observe(getActivity(), new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if (user.getPassword().equals(old)){
                                myViewModel.updateUserChangePass(sh_ed_user,newPass);
                                DialogFra.this.dismiss();
                            }


                        }
                    });

                }


            }
        });


        return builder.create();


    }
}
