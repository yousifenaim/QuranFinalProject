package com.example.finalproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyDataBase.Entity.Advertising;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardAdvertisingRvBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdvertisingAdapter  extends RecyclerView.Adapter<AdvertisingAdapter.AdvertisingViewHolder> {

    List<Advertising> advertisings ;
    Lisadvertisings lisadvertisings;
    Context context ;

    public AdvertisingAdapter(List<Advertising> advertisings, Lisadvertisings lisadvertisings, Context context) {
        this.advertisings = advertisings;
        this.lisadvertisings = lisadvertisings;
        this.context = context;
    }

    public List<Advertising> getAdvertisings() {
        return advertisings;
    }

    public void setAdvertisings(List<Advertising> advertisings) {
        this.advertisings = advertisings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdvertisingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_advertising_rv,parent,false);
        AdvertisingViewHolder holder =new AdvertisingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisingViewHolder holder, int position) {

        Advertising advertising =advertisings.get(position);
        holder.advertising=advertising;
        holder.binding.custmCardAdvertisingDate.setText(getDateFormat(advertising.getDate()));
        holder.binding.custmCardAdvertisingName.setText(advertising.getUserName());
        holder.binding.custmCardAdvertisingDesc.setText(advertising.getDesc());
        Glide.with(context).load(advertising.getUserImage()).into(holder.binding.custmCardAdvertisingImage);

    }

    @Override
    public int getItemCount() {
        return advertisings.size();
    }

    class AdvertisingViewHolder extends RecyclerView.ViewHolder{

        CustmCardAdvertisingRvBinding binding ;
        Advertising advertising;

        public AdvertisingViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CustmCardAdvertisingRvBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lisadvertisings.OnItemClick(advertising);
                }
            });
        }
    }

    String  getDateFormat(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd : h:mm a", Locale.ENGLISH);
        return simpleDateFormat.format(date) ;
    }

}
