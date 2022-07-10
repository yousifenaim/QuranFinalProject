package com.example.finalproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.CentersBranch;
import com.example.finalproject.MyDataBase.Listeners.GetOneCenterById;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardMahfazActivityRvBinding;
import com.example.finalproject.databinding.CustmCardMemorizationCentersRvBinding;

import java.util.List;

public class MahfazAdapter extends RecyclerView.Adapter<MahfazAdapter.MyMahfazViewHolder> {

    private List<Mahfaz> mahfazs ;
    private MyViewModel myViewModel ;
    private Context context ;
    private MahfazAdapterItemClick mahfazAdapterItemClick ;

    public MahfazAdapter(List<Mahfaz> mahfazs, MyViewModel myViewModel, Context context, MahfazAdapterItemClick mahfazAdapterItemClick) {
        this.mahfazs = mahfazs;
        this.myViewModel = myViewModel;
        this.context = context;
        this.mahfazAdapterItemClick = mahfazAdapterItemClick;
    }

    public List<Mahfaz> getMahfazs() {
        return mahfazs;
    }

    public void setMahfazs(List<Mahfaz> mahfazs) {
        this.mahfazs = mahfazs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyMahfazViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_mahfaz_activity_rv,parent,false);
        MyMahfazViewHolder holder =new MyMahfazViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyMahfazViewHolder holder, int position) {

        Mahfaz mahfaz =mahfazs.get(position);
        holder.mahfaz =mahfaz;
        holder.binding.custmCardMahfazActivityName.setText(mahfaz.getName());
        holder.binding.custmCardMahfazActivityNumber.setText(mahfaz.getPhone());
        myViewModel.getBranchName(mahfaz.getBranch(), new CentersBranch() {
            @Override
            public void Item(String name) {

                 holder.binding.custmCardMahfazActivityBranch.post(new Runnable() {
                     @Override
                     public void run() {
                         holder.binding.custmCardMahfazActivityBranch.post(new Runnable() {
                             @Override
                             public void run() {
                                 holder.binding.custmCardMahfazActivityBranch.setText(name);
                             }
                         });
                     }
                 });


            }
        });
        Glide.with(context).load(mahfaz.getIamge()).into(holder.binding.custmCardMahfazActivityImage);


    }

    @Override
    public int getItemCount() {
        return mahfazs.size();
    }

    class MyMahfazViewHolder extends RecyclerView.ViewHolder{

        CustmCardMahfazActivityRvBinding  binding ;
        Mahfaz mahfaz ;

        public MyMahfazViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =CustmCardMahfazActivityRvBinding.bind(itemView);

            binding.custmCardMahfazActivityCircles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mahfazAdapterItemClick.OnCircleClick(mahfaz);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mahfazAdapterItemClick.OnItemClick(mahfaz);
                }
            });
        }
    }
}
