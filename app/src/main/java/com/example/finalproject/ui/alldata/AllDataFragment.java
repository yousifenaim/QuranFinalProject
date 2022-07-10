package com.example.finalproject.ui.alldata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.databinding.AllDataFragmentBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class AllDataFragment extends Fragment {

    String [] tabs ={"المراكز","الحلقات","المحفظين","المشرفين","الطلبة"};
    AllDataFragmentBinding binding ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding =AllDataFragmentBinding.inflate(getLayoutInflater());
        ArrayList<Fragment> fragments =new ArrayList<>();
        fragments.add(ItemFragment.newInstance(tabs[0]));
        fragments.add(ItemFragment.newInstance(tabs[1]));
        fragments.add(ItemFragment.newInstance(tabs[2]));
        fragments.add(ItemFragment.newInstance(tabs[3]));
        fragments.add(ItemFragment.newInstance(tabs[4]));


        PagerAdapter adapter = new PagerAdapter(getActivity(),fragments);
        binding.pager.setAdapter(adapter);


        new TabLayoutMediator(binding.taplayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();


        // View root = inflater.inflate(R.layout.all_data_fragment, container, false);

        return binding.getRoot();
    }
}