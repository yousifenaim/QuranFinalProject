package com.example.finalproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.CentersBranch;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardMushrefRvBinding;

import java.util.List;

public class MushrefAdapter extends RecyclerView.Adapter<MushrefAdapter.MushrefViewHolder> {

    List<User> users ;
    Context context ;
    MyViewModel myViewModel ;

    public MushrefAdapter(List<User> users, Context context, MyViewModel myViewModel) {
        this.users = users;
        this.context = context;
        this.myViewModel = myViewModel;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MushrefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_mushref_rv,parent,false);
        MushrefViewHolder mushrefViewHolder =new MushrefViewHolder(view);
        return mushrefViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MushrefViewHolder holder, int position) {

        User user =users.get(position);
        holder.binding.custmCardMushrefName.setText(user.getName());
        holder.binding.custmCardMushrefNumber.setText(user.getPhone()+"");
        Glide.with(context).load(user.getImage()).into(holder.binding.custmCardMushrefImage);
        myViewModel.getBranchName(user.getBranch(), new CentersBranch() {
            @Override
            public void Item(String name) {
                holder.binding.custmCardMushrefBranch.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.custmCardMushrefBranch.setText(name);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MushrefViewHolder extends RecyclerView.ViewHolder{

        CustmCardMushrefRvBinding binding ;

        public MushrefViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CustmCardMushrefRvBinding.bind(itemView);
        }
    }
}
