package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.QuantityType;
import com.example.finalproject.R;

import java.util.List;

public class ShowQuantityTypeAdapter extends BaseAdapter {
    List<QuantityType> quantityTypes;

    public ShowQuantityTypeAdapter(List<QuantityType> quantityTypes) {
        this.quantityTypes = quantityTypes;
    }

    public List<QuantityType> getQuantityTypes() {
        return quantityTypes;
    }

    public void setQuantityTypes(List<QuantityType> quantityTypes) {
        this.quantityTypes = quantityTypes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return quantityTypes.size();
    }

    @Override
    public QuantityType getItem(int i) {
        return quantityTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return quantityTypes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =view;
        if (v==null){

            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custm_branch,null,false);
        }

        TextView textView = v.findViewById(R.id.custm_branch_spiner_item);
        QuantityType quantityType =getItem(i);
        textView.setText(quantityType.getName());
        return v;
    }
}
