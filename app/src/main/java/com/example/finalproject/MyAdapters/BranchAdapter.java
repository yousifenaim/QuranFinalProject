package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.R;

import java.util.List;

public class BranchAdapter  extends BaseAdapter {

    List<Branch> branches ;

    public BranchAdapter(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return branches.size();
    }

    @Override
    public Branch getItem(int i) {
        return branches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return branches.get(i).getNumberBranch();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =view;
        if (v==null){

            v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custm_branch,null,false);
        }

        TextView textView = v.findViewById(R.id.custm_branch_spiner_item);
        Branch branch =getItem(i);
        textView.setText(branch.getName());
        return v;
    }
}
