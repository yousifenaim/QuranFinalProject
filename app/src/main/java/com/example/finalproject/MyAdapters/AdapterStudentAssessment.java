package com.example.finalproject.MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.MyDataBase.Entity.Task;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardStudentAssessmentRvBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterStudentAssessment extends RecyclerView.Adapter<AdapterStudentAssessment.StudentAssessmentViewHolder> {

    List<Task> tasks ;
    LisTasks lisTasks ;

    public AdapterStudentAssessment(List<Task> tasks, LisTasks lisTasks) {
        this.tasks = tasks;
        this.lisTasks = lisTasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentAssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_student_assessment_rv,parent,false);
        StudentAssessmentViewHolder holder =new StudentAssessmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAssessmentViewHolder holder, int position) {

        Task task =tasks.get(position);
        holder.task=task;
        holder.binding.custmCardStudentAssessmentTvName.setText(task.getTester());
        holder.binding.custmCardStudentAssessmentTvDesc.setText(task.getDesc());
        if (task.getAssessment()==0){
            holder.binding.custmCardStudentAssessmentTvAssessment.setText("لم تكتمل");
        }
        else{
            holder.binding.custmCardStudentAssessmentTvAssessment.setText(task.getAssessment()+"%");
        }

        holder.binding.custmCardStudentAssessmentTvDate.setText(getDateFormat(task.getTaskAddDate()));

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class StudentAssessmentViewHolder extends RecyclerView.ViewHolder{

        CustmCardStudentAssessmentRvBinding binding ;
        Task task ;
        public StudentAssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CustmCardStudentAssessmentRvBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lisTasks.OnItemClick(task);
                }
            });

        }
    }



    String  getDateFormat(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd : h:mm a", Locale.ENGLISH);
        return simpleDateFormat.format(date) ;
    }
}
