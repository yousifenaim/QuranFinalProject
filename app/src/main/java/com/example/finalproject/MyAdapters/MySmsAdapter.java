package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.MyDataBase.Entity.MySms;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardSmsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MySmsAdapter extends RecyclerView.Adapter<MySmsAdapter.SmsViewHolder> {

    List<MySms> mySmsss ;
    MyViewModel myViewModel ;


    public MySmsAdapter(List<MySms> mySmsss, MyViewModel myViewModel) {
        this.mySmsss = mySmsss;
        this.myViewModel = myViewModel;

    }

    public List<MySms> getMySmsss() {
        return mySmsss;
    }

    public void setMySmsss(List<MySms> mySmsss) {
        this.mySmsss = mySmsss;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_sms,parent,false);
        SmsViewHolder smsViewHolder = new SmsViewHolder(view);
        return smsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {

        MySms mySms =mySmsss.get(position);
        holder.binding.custmCardSmsTvMessage.setText(mySms.getMessage());
        holder.binding.custmCardSmsTvDate.setText(getDateFormat(mySms.getDate()));
        holder.binding.custmCardSmsTvNumber.setText(mySms.getPhone());
        myViewModel.getNameUserByPhone(mySms.getPhone(), new UserToUser() {
            @Override
            public void OnItemClick(User user) {

                if (user!=null){
                    holder.binding.custmCardSmsTvName.post(new Runnable() {
                        @Override
                        public void run() {

                            holder.binding.custmCardSmsTvName.setText(user.getName());
                            if (user.getValidity()==1){
                                holder.binding.custmCardSmsTvValy.setText("مشرف");
                            }
                            else if (user.getValidity()==2){
                                holder.binding.custmCardSmsTvValy.setText("محفظ");
                            }


                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mySmsss.size();
    }

    class SmsViewHolder extends RecyclerView.ViewHolder{

        CustmCardSmsBinding binding ;
        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =CustmCardSmsBinding.bind(itemView);

        }
    }


    String  getDateFormat(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd : h:mm a", Locale.ENGLISH);
        return simpleDateFormat.format(date) ;
    }
}
