package com.example.finalproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Listeners.CentersBranch;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardMemorizationCentersRvBinding;

import java.util.List;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.CentersViewHolder> {

    private List<ShowCenterData> showCenterDatas ;
    private MyViewModel myViewModel ;
    private Context context ;
    CentersAdapterItemClick centerItemClick;


    public CentersAdapter(List<ShowCenterData> showCenterData, MyViewModel myViewModel, Context context, CentersAdapterItemClick centerItemClick) {
        this.showCenterDatas = showCenterData;
        this.myViewModel = myViewModel;
        this.context = context;
        this.centerItemClick = centerItemClick;
    }

    public List<ShowCenterData> getShowCenterData() {
        return showCenterDatas;
    }

    public void setShowCenterData(List<ShowCenterData> showCenterData) {
        this.showCenterDatas = showCenterData;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CentersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_memorization_centers_rv,parent,false);
        CentersViewHolder holder =new CentersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CentersViewHolder holder, int position) {

        ShowCenterData showCenterData = showCenterDatas.get(position);
        holder.showCenterData=showCenterDatas.get(position);
        holder.lat =showCenterData.getLatitude();
        holder.lng=showCenterData.getLongitude();
        myViewModel.getBranchName(showCenterData.getBranch(), new CentersBranch() {
            @Override
            public void Item(String name) {
                holder.binding.custmCardMemorizationCentersTvBranch.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.custmCardMemorizationCentersTvBranch.setText(name);
                    }
                });
            }
        });
        holder.binding.custmCardMemorizationCentersTvName.setText(showCenterData.getName());
        holder.binding.custmCardMemorizationCentersTvAddress.setText(showCenterData.getAddress());
        Glide.with(context).load(showCenterData.getImage()).into(holder.binding.custmCardMemorizationCentersImage);
    }

    @Override
    public int getItemCount() {
        return showCenterDatas.size();
    }

    class CentersViewHolder extends RecyclerView.ViewHolder{

       CustmCardMemorizationCentersRvBinding binding ;
        double lat ;
        double lng;
        ShowCenterData showCenterData;


        public CentersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CustmCardMemorizationCentersRvBinding.bind(itemView);

            binding.custmCardMemorizationCentersTvCircles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    centerItemClick.OnItemCircleClick(showCenterData);
                }
            });

            binding.custmCardMemorizationCentersLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    centerItemClick.OnItemLocationClick(lat,lng);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    centerItemClick.OnItemClick(showCenterData);
                }
            });
        }
    }}
