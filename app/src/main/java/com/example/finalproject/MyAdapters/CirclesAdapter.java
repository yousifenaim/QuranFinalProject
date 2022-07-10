package com.example.finalproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.Listeners.CentersBranch;
import com.example.finalproject.MyDataBase.Listeners.GetNameUserById;
import com.example.finalproject.MyDataBase.Listeners.GetOneCenterById;
import com.example.finalproject.MyDataBase.Listeners.RegisterCheck;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCirclesData;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardMemorizationCircleRvBinding;

import java.util.List;

public class CirclesAdapter extends RecyclerView.Adapter<CirclesAdapter.CirclesViewHolder> {

    private List<ShowCirclesData> showCirclesData ;
    private MyViewModel myViewModel ;
    private Context context ;
    private CirclesAdapterItemClick circlesAdapterItemClick;

    public CirclesAdapter(List<ShowCirclesData> showCirclesData, MyViewModel myViewModel, Context context, CirclesAdapterItemClick circlesAdapterItemClick) {
        this.showCirclesData = showCirclesData;
        this.myViewModel = myViewModel;
        this.context = context;
        this.circlesAdapterItemClick = circlesAdapterItemClick;
    }


    public List<ShowCirclesData> getShowCirclesData() {
        return showCirclesData;
    }

    public void setShowCirclesData(List<ShowCirclesData> showCirclesData) {
        this.showCirclesData = showCirclesData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CirclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_memorization_circle_rv,parent,false);
       CirclesViewHolder  holder =new CirclesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CirclesViewHolder holder, int position) {


        holder.showCirclesData =showCirclesData.get(position);
        holder.binding.custmCardMemorizationCircleTvName.setText(showCirclesData.get(position).getCircleName());
        holder.binding.custmCardMemorizationCircleTvMoshrafName.setText(showCirclesData.get(position).getUserName());
        holder.binding.custmCardMemorizationCircleTvCenterName.setText(showCirclesData.get(position).getCenterName());

        myViewModel.getBranchName(showCirclesData.get(position).getBranch(), new CentersBranch() {
                    @Override
                    public void Item(String name) {
                        holder.binding.custmCardMemorizationCircleTvBranch.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.binding.custmCardMemorizationCircleTvBranch.setText(name);
                            }
                        });
                    }
                });


      Glide.with(context).load(showCirclesData.get(position).getCircleImage()).into(holder.binding.custmCardMemorizationCircleImage);

//        Circle circle =circles.get(position);
//        holder.circle=circle;
//        holder.binding.custmCardMemorizationCircleTvName.setText(circle.getName());
//        myViewModel.getNameUserById(circle.getIdMusharaf(), new GetNameUserById() {
//            @Override
//            public void getNameUserById(String name) {
//                holder.binding.custmCardMemorizationCircleTvMoshrafName.setText(name);
//            }
//        });
//
//
//        myViewModel.getOneCenterById(circle.getIdCenter(), new GetOneCenterById() {
//            @Override
//            public void getOneCenterById(Center center) {
//
//                holder.binding.custmCardMemorizationCircleTvCenterName.setText(center.getName());
//
//
//                myViewModel.getBranchName(center.getBranch(), new CentersBranch() {
//                    @Override
//                    public void Item(String name) {
//                        holder.binding.custmCardMemorizationCircleTvBranch.setText(name);
//                    }
//                });
//            }
//        });
//
//
//        Glide.with(context).load(circle.getImage()).into(holder.binding.custmCardMemorizationCircleImage);
    }

    @Override
    public int getItemCount() {
        return showCirclesData.size();
    }

    class  CirclesViewHolder extends RecyclerView.ViewHolder{

        CustmCardMemorizationCircleRvBinding binding ;
        ShowCirclesData showCirclesData;

        public CirclesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =CustmCardMemorizationCircleRvBinding.bind(itemView);

            binding.custmCardMemorizationCircleLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    circlesAdapterItemClick.OnItemLocationClick(showCirclesData);
                }
            });

            binding.custmCardMemorizationCircleTvMahfazList1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    circlesAdapterItemClick.OnItemMahfazClick(showCirclesData);
                }
            });

            binding.custmCardMemorizationCircleTvStudentList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    circlesAdapterItemClick.OnItemStudentClick(showCirclesData);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    circlesAdapterItemClick.OnItemClick(showCirclesData);
                }
            });
        }
    }
}
