package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.finalproject.MyAdapters.BranchAdapter;
import com.example.finalproject.MyAdapters.ShowQuantityTypeAdapter;
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.QuantityType;
import com.example.finalproject.MyDataBase.Entity.Student;
import com.example.finalproject.MyDataBase.Entity.Task;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.databinding.ActivityAddStudentAssessmentBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddStudentAssessment extends AppCompatActivity {

    ActivityAddStudentAssessmentBinding binding ;

    MyViewModel myViewModel;
    ShowQuantityTypeAdapter showQuantityTypeAdapter ;
    Calendar selectedDate;


    int studentId ;
    int numberIdQuantityType;
    long circleId ;
    long centerId ;
    String userName ;

    int hotNumber ;
    int idQuantityType;
    long idTask ;


    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddStudentAssessmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);
        setSupportActionBar(binding.addStudentAssessmentToolbar);



        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);
        selectedDate = Calendar.getInstance();


        Intent intent =getIntent();
        studentId =intent.getIntExtra(StudentActivity.STUDENT_ID_REQ_KEY,0);
        hotNumber =intent.getIntExtra(StudentAssessmentActivity.MY_ASSESSMENT_REQ_KEY,0);


        if (hotNumber==101){

        }
        else if (hotNumber==102){
            disableFields();
            Intent myIntent = getIntent();

            Task myTask = (Task) myIntent.getSerializableExtra(StudentAssessmentActivity.MY_ASSESSMENT_DATA);
            binding.addStudentAssessmentEdFrom.setText(myTask.getFrom()+"");
            binding.addStudentAssessmentTo.setText(myTask.getTo()+"");
            binding.addStudentAssessmentEdDesc.setText(myTask.getDesc());
            binding.addStudentAssessmentName.setText(myTask.getTester());
            binding.addStudentAssessmentEdAssessment.setText(myTask.getAssessment()+"");
            binding.addStudentAssessmentEdDate.setText(getDateFormat(myTask.getTaskEndDate()));
            idTask=myTask.getId();
            circleId=myTask.getCircleId();
            centerId=myTask.getCenterId();
            idQuantityType =myTask.getIdQuantityType();


        }


        myViewModel.getUser(sh_ed_user).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userName=user.getName();
            }
        });
        if (hotNumber==101){

            myViewModel.getOneStudent(studentId).observe(this, new Observer<Student>() {
                @Override
                public void onChanged(Student student) {



                    circleId=student.getIdCircle();
                    centerId=student.getIdCenter();
                }
            });


        }




        //spinner QuType code
        showQuantityTypeAdapter =new ShowQuantityTypeAdapter(new ArrayList<>());
        binding.addStudentAssessmentSp.setAdapter(showQuantityTypeAdapter);

        myViewModel.getAllQuantityType().observe(this, new Observer<List<QuantityType>>() {
            @Override
            public void onChanged(List<QuantityType> quantityTypes) {
                showQuantityTypeAdapter.setQuantityTypes(quantityTypes);

                if (hotNumber==102){
                    int idQuantity = idQuantityType;
                    for(QuantityType b: quantityTypes){
                        if(b.getId()==idQuantity){
                            binding.addStudentAssessmentSp.setSelection(quantityTypes.indexOf(b));
                            break;
                        }
                    }

                }
            }
        });

        binding.addStudentAssessmentSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                QuantityType quantityType =showQuantityTypeAdapter.getItem(i);
                numberIdQuantityType=quantityType.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        binding.addStudentAssessmentEdDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now =Calendar.getInstance();
                DatePickerDialog datePickerDialog =DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                      @Override
                                      public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                          binding.addStudentAssessmentEdDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                                          selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                          selectedDate.set(Calendar.YEAR,year);
                                          selectedDate.set(Calendar.MONTH,monthOfYear);
                                          selectedDate.clear(Calendar.HOUR_OF_DAY);
                                          selectedDate.clear(Calendar.MINUTE);
                                          selectedDate.clear(Calendar.SECOND);
                                      }
                                  },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });




        binding.addStudentAssessmentBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromText = binding.addStudentAssessmentEdFrom.getText().toString();
                String toText = binding.addStudentAssessmentTo.getText().toString();
                String name = binding.addStudentAssessmentName.getText().toString();
                String assessment = binding.addStudentAssessmentEdAssessment.getText().toString();
                String desc = binding.addStudentAssessmentEdDesc.getText().toString();



                if (TextUtils.isEmpty(name)){
                    Toast.makeText(AddStudentAssessment.this, "ادخل الاسم", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (toText.contains("-")||fromText.contains("-")){
                    Toast.makeText(AddStudentAssessment.this, "ادخل القيم بشكل صحيح", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(desc)){
                    Toast.makeText(AddStudentAssessment.this, "ادخل الوصف", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(assessment)){
                    Toast.makeText(AddStudentAssessment.this, "ادخل التقييم", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(toText)){
                    Toast.makeText(AddStudentAssessment.this, "ادخل القيمة من", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(fromText)){
                    Toast.makeText(AddStudentAssessment.this, "ادخل القيمة الى", Toast.LENGTH_SHORT).show();
                    return;
                }

                int fromNumber =Integer.parseInt(fromText);
                int toNumber =Integer.parseInt(toText);
                double assessmentNumber =Double.parseDouble(assessment);

                if (assessmentNumber>100){
                    Toast.makeText(AddStudentAssessment.this, "ادخل تقييم اقل من 100", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (toNumber<fromNumber){
                    Toast.makeText(AddStudentAssessment.this, "تأكد من القيم ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (toNumber<=0&&fromNumber<=0){
                    Toast.makeText(AddStudentAssessment.this, "تأكد من القيم ", Toast.LENGTH_SHORT).show();
                    return;
                }


                binding.progressBar.setVisibility(View.VISIBLE);


                if (hotNumber==101){
                    if (studentId==0){
                        Toast.makeText(AddStudentAssessment.this, "الطالب غير موجود", Toast.LENGTH_SHORT).show();
                    }
                    Calendar calendar =Calendar.getInstance();

                    Task task =new Task(0,circleId,centerId,userName,name,selectedDate.getTime(),calendar.getTime(),fromNumber,toNumber,numberIdQuantityType,assessmentNumber,desc);
                    myViewModel.insertTask(task);
                    binding.progressBar.setVisibility(View.GONE);

                }
                else if (hotNumber==102){
                    myViewModel.updateTask(idTask,desc,name,selectedDate.getTime(),fromNumber,toNumber,numberIdQuantityType,assessmentNumber);
                    Toast.makeText(AddStudentAssessment.this, "Update Success", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);

                }


            }
        });
    }



    private void disableFields (){
        binding.addStudentAssessmentEdDate.setEnabled(false);
        binding.addStudentAssessmentSp.setEnabled(false);
        binding.addStudentAssessmentEdDesc.setEnabled(false);
        binding.addStudentAssessmentEdAssessment.setEnabled(false);
        binding.addStudentAssessmentName.setEnabled(false);
        binding.addStudentAssessmentTo.setEnabled(false);
        binding.addStudentAssessmentEdFrom.setEnabled(false);
        binding.addStudentAssessmentBtnSave.setEnabled(false);

    }

    private void enabledFields (){
        binding.addStudentAssessmentEdDate.setEnabled(true);
        binding.addStudentAssessmentSp.setEnabled(true);
        binding.addStudentAssessmentEdDesc.setEnabled(true);
        binding.addStudentAssessmentEdAssessment.setEnabled(true);
        binding.addStudentAssessmentName.setEnabled(true);
        binding.addStudentAssessmentTo.setEnabled(true);
        binding.addStudentAssessmentEdFrom.setEnabled(true);
        binding.addStudentAssessmentBtnSave.setEnabled(true);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menu_in = getMenuInflater();
        menu_in.inflate(R.menu.details_menu, menu);

        MenuItem edit = menu.findItem(R.id.item_details_edit);
        MenuItem delete = menu.findItem(R.id.item_details_delete);
        if (hotNumber==101){

            edit.setVisible(false);
            delete.setVisible(false);

        }
        else if (hotNumber==102){

            edit.setVisible(true);
            delete.setVisible(true);



        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()){

            case  R.id.item_details_edit :

                enabledFields ();
                MenuItem delete = binding.addStudentAssessmentToolbar.getMenu().findItem(R.id.item_details_delete);
                delete.setVisible(false);
                return true;

            case  R.id.item_details_delete :


                myViewModel.deleteTask(idTask);
                Toast.makeText(this, "تم حذف المهمة بنجاح ", Toast.LENGTH_SHORT).show();
                finish();



                return true;
        }

        return false;


    }


    String  getDateFormat(Date date){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd : h:mm a", Locale.ENGLISH);
        return simpleDateFormat.format(date) ;
    }

}