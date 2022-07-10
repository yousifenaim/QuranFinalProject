package com.example.finalproject.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.Student;
import com.example.finalproject.MyDataBase.SecTable.ShowStudentData;
import com.example.finalproject.R;
import com.example.finalproject.databinding.CustmCardStudentActivityRvBinding;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudenViewHolder> {

    List<ShowStudentData> students ;
    Context context ;
    StudentAdapterItemClick studentAdapterItemClick ;

    public StudentsAdapter(List<ShowStudentData> students, Context context,StudentAdapterItemClick studentAdapterItemClick) {
        this.students = students;
        this.context = context;
        this.studentAdapterItemClick = studentAdapterItemClick;


    }

    public List<ShowStudentData> getStudents() {
        return students;
    }

    public void setStudents(List<ShowStudentData> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custm_card_student_activity_rv,parent,false);
        StudenViewHolder holder =new StudenViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudenViewHolder holder, int position) {

        ShowStudentData data =students.get(position);
        holder.studentId=data.getId();
        holder.binding.custmCardStudentActivityName.setText(data.getStudentName());
        holder.binding.custmCardStudentActivityBranch.setText(data.getBranchName());
        holder.binding.custmCardStudentActivityCenter.setText(data.getCenterName());
        holder.binding.custmCardStudentActivityCircle.setText(data.getCircleName());
        holder.binding.custmCardStudentActivityNumber.setText(data.getPhone());
        holder.binding.custmCardStudentActivityStudentAssessment.setText(data.getEvaluation()+"");
        Glide.with(context).load(data.getImage()).into(holder.binding.custmCardStudentActivityImage);



    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudenViewHolder extends RecyclerView.ViewHolder{

        CustmCardStudentActivityRvBinding binding ;
        int studentId ;


        public StudenViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =CustmCardStudentActivityRvBinding.bind(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    studentAdapterItemClick.OnItemClick(studentId);
                }
            });

            binding.custmCardStudentActivityTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    studentAdapterItemClick.OnTaskItemClick(studentId);
                }
            });


        }
    }
}
