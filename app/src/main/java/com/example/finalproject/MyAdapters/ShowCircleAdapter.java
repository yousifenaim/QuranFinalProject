package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.R;

import java.util.List;

public class ShowCircleAdapter extends BaseAdapter {

    List<Circle> circles ;

    public ShowCircleAdapter(List<Circle> circles) {
        this.circles = circles;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public void setCircles(List<Circle> circles) {
        this.circles = circles;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return circles.size();
    }

    @Override
    public Circle getItem(int i) {
        return circles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return circles.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v =view;
        if (v==null){

            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custm_branch,null,false);
        }

        TextView textView = v.findViewById(R.id.custm_branch_spiner_item);
        Circle circle =getItem(i);
        textView.setText(circle.getName());
        return v;
    }
}
