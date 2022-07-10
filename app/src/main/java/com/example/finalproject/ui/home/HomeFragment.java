package com.example.finalproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.LisDoubleToDouble;
import com.example.finalproject.MyDataBase.Listeners.LisIntToInt;
import com.example.finalproject.MyDataBase.Listeners.ShowStringToString;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.GetBestCircle;
import com.example.finalproject.MyDataBase.SecTable.GetBestStudent;
import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    FragmentHomeBinding binding ;
    MyViewModel myViewModel ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding =FragmentHomeBinding.inflate(getLayoutInflater());
        myViewModel=new ViewModelProvider(getActivity()).get(MyViewModel.class);





        myViewModel.numberOfCircle(new LisIntToInt() {
            @Override
            public void lisIntToInt(int value) {
                binding.homeFraTvNumberOfCircle.setText(value+"");
            }
        });

        myViewModel.numberOfStudent(new LisIntToInt() {
            @Override
            public void lisIntToInt(int value) {
                binding.homeFraTvNumberOfStudent.setText(value+"");

            }
        });

        myViewModel.bestOfStudent().observe(getActivity(), new Observer<GetBestStudent>() {
            @Override
            public void onChanged(GetBestStudent getBestStudent) {

                if (getBestStudent!=null){
                    myViewModel.getUser(getBestStudent.getId()).observe(getActivity(), new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if (user!=null){
                                binding.homeFraTvBestStudent.setText(user.getName()+"");

                            }

                        }
                    });
                }
            }
        });

        myViewModel.getAllAssessment(new LisDoubleToDouble() {
            @Override
            public void LisDoubleToDouble(double value) {
                String formattedString = String.format("%.02f", value);
                binding.homeFraTvAllAsement.setText(formattedString+"%");
            }
        });

        myViewModel.getNumberOfMahfaz(new LisIntToInt() {
            @Override
            public void lisIntToInt(int value) {

                binding.homeFraTvAllMahafez.setText(value+"");

            }
        });

        myViewModel.getBestMahfaz().observe(getActivity(), new Observer<GetBestStudent>() {
            @Override
            public void onChanged(GetBestStudent getBestStudent) {

               if (getBestStudent!=null){
                   myViewModel.getUser(getBestStudent.getId()).observe(getActivity(), new Observer<User>() {
                       @Override
                       public void onChanged(User user) {
                           if (user!=null){
                               binding.homeFraTvBestMahafez.setText(user.getName());

                           }

                       }
                   });
               }


            }
        });

        myViewModel.getBestCircle().observe(getActivity(), new Observer<GetBestCircle>() {
            @Override
            public void onChanged(GetBestCircle getBestCircle) {

                if (getBestCircle!=null){
                    binding.homeFraTvBestCircle.setText(getBestCircle.getName());

                }

            }
        });




        return binding.getRoot();
    }
}