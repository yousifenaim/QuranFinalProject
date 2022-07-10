package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.R;

import java.util.List;

public class CenterManagerAdapter extends BaseAdapter {

    List<User> users ;

    public CenterManagerAdapter(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return users.get(i).getIdNumber();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v =view;
        if (v==null){
            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custm_spiner_item,null,false);
        }
        User user =getItem(i);
        TextView name =v.findViewById(R.id.spinner_tv_item);
        if (user.getValidity()==0){
            name.setText(user.getName());
        }


        return v;
    }
}
