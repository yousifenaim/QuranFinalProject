package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.R;

import java.util.List;

public class ShowMahfazAdapter extends BaseAdapter {

    List<Mahfaz> mahfazs ;

    public ShowMahfazAdapter(List<Mahfaz> mahfazs) {
        this.mahfazs = mahfazs;
    }

    public List<Mahfaz> getMahfazs() {
        return mahfazs;
    }

    public void setMahfazs(List<Mahfaz> mahfazs) {
        this.mahfazs = mahfazs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mahfazs.size();
    }

    @Override
    public Mahfaz getItem(int i) {
        return mahfazs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mahfazs.get(i).getIdUser();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v =view;
        if (v==null){

            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custm_branch,null,false);
        }

        TextView textView = v.findViewById(R.id.custm_branch_spiner_item);
        Mahfaz mahfaz =getItem(i);
        textView.setText(mahfaz.getName());
        return v;
    }
}
