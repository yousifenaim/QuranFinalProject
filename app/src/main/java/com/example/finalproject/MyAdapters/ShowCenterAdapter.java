package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.R;

import java.util.List;

public class ShowCenterAdapter extends BaseAdapter {

    List<Center> centers ;

    public ShowCenterAdapter(List<Center> centers) {
        this.centers = centers;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return centers.size();
    }

    @Override
    public Center getItem(int i) {
        return centers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return centers.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v =view;
        if (v==null){

            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custm_branch,null,false);
        }

        TextView textView = v.findViewById(R.id.custm_branch_spiner_item);
        Center center =getItem(i);
        textView.setText(center.getName());
        return v;
    }
}
