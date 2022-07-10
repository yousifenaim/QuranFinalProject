package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.MyAdapters.StudentAdapterItemClick;
import com.example.finalproject.MyAdapters.StudentsAdapter;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowStudentData;
import com.example.finalproject.databinding.ActivityStudentBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    ActivityStudentBinding binding  ;
    public static final String STUDENT_REQ_KEY="mafazreq";
    public static final String STUDENT_ID_REQ_KEY="STUDENT_ID_REQ_KEY";
    public static final int ADD_STUDENT_REQ_CODE =101;
    public static final int EDIT_STUDENT_REQ_CODE =102;

    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    MyViewModel myViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);


        sh = PreferenceManager.getDefaultSharedPreferences(this);
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY,0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY,0);

        Intent intent =getIntent();
        long circleID =intent.getLongExtra(AddMemorizationCircle.MY_CIRCLE_DATA_TO_STUDENT_KEY,-1);
        long centerID =intent.getLongExtra(AddMemorizationCircle.MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY,-1);

        if (sh_ed_validity==3){
            binding.studentBtnAdd.setVisibility(View.GONE);
        }



        binding.studentBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent =new Intent(getBaseContext(),AddStudentActivity.class);
                myIntent.putExtra(AddMemorizationCircle.MY_CIRCLE_DATA_TO_STUDENT_KEY,circleID);
                myIntent.putExtra(AddMemorizationCircle.MY_CIRCLE_DATA_CENTER_ID_TO_STUDENT_KEY,centerID);
                myIntent.putExtra(STUDENT_REQ_KEY,ADD_STUDENT_REQ_CODE);
                startActivity(myIntent);


            }
        });



        StudentsAdapter studentsAdapter =new StudentsAdapter(new ArrayList<>(), this, new StudentAdapterItemClick() {
            @Override
            public void OnItemClick(int id) {
                Intent intent01 =new Intent(getBaseContext(),AddStudentActivity.class);
                intent01.putExtra(STUDENT_REQ_KEY,EDIT_STUDENT_REQ_CODE);
                intent01.putExtra(AddMemorizationCircle.MY_CIRCLE_DATA_TO_STUDENT_KEY,circleID);
                intent01.putExtra(STUDENT_ID_REQ_KEY,id);
                startActivity(intent01);
            }

            @Override
            public void OnTaskItemClick(int id) {
                Intent intent1 =new Intent(getBaseContext(),StudentAssessmentActivity.class);
                intent1.putExtra(StudentActivity.STUDENT_ID_REQ_KEY,id);
                startActivity(intent1);
            }
        });


        myViewModel.getAllShowStudentData(circleID).observe(this, new Observer<List<ShowStudentData>>() {
            @Override
            public void onChanged(List<ShowStudentData> showStudentData) {
                studentsAdapter.setStudents(showStudentData);

            }
        });

        binding.studentRv.setAdapter(studentsAdapter);
        binding.studentRv.setHasFixedSize(true);
        binding.studentRv.setLayoutManager(new LinearLayoutManager(this));





    }
}