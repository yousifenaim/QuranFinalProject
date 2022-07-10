package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.finalproject.MyAdapters.AdapterStudentAssessment;
import com.example.finalproject.MyAdapters.LisTasks;
import com.example.finalproject.MyDataBase.Entity.Task;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.databinding.ActivityStudentAssessmentBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentAssessmentActivity extends AppCompatActivity {

    ActivityStudentAssessmentBinding binding;
    int studentId;

    public static final String MY_ASSESSMENT_REQ_KEY = "mycirclereq";
    public static final String MY_ASSESSMENT_DATA = "MY_ASSESSMENT_DATA";
    static final int ADD_ASSESSMENT_REQ_CODE = 101;
    static final int EDIT_ASSESSMENT_REQ_CODE = 102;

    Calendar selectedDateFrom;
    Calendar selectedDateTo;

    MyViewModel myViewModel;
    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    AdapterStudentAssessment adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentAssessmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.addStudentAssessmentToolbar);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();

        selectedDateFrom = Calendar.getInstance();
        selectedDateTo = Calendar.getInstance();

        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);
        Intent intent = getIntent();

        studentId = intent.getIntExtra(StudentActivity.STUDENT_ID_REQ_KEY, 0);

        if (sh_ed_validity == 3) {
            binding.studentAssessmentBtnAdd.setVisibility(View.GONE);
        }

        binding.studentAssessmentEdFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                    binding.studentAssessmentEdFrom.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    selectedDateFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    selectedDateFrom.set(Calendar.YEAR, year);
                                    selectedDateFrom.set(Calendar.MONTH, monthOfYear);
                                    selectedDateFrom.clear(Calendar.HOUR_OF_DAY);
                                    selectedDateFrom.clear(Calendar.MINUTE);
                                    selectedDateFrom.clear(Calendar.SECOND);
                                }
                            },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });

        binding.studentAssessmentTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                          @Override
                          public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                              binding.studentAssessmentTo.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                              selectedDateTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                              selectedDateTo.set(Calendar.YEAR, year);
                              selectedDateTo.set(Calendar.MONTH, monthOfYear);
                              selectedDateTo.clear(Calendar.HOUR_OF_DAY);
                              selectedDateTo.clear(Calendar.MINUTE);
                              selectedDateTo.clear(Calendar.SECOND);
                          }
                      },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });






         adapter = new AdapterStudentAssessment(new ArrayList<>(), new LisTasks() {
            @Override
            public void OnItemClick(Task task) {

                if (sh_ed_validity == 0 || sh_ed_validity == 1 || sh_ed_validity == 2) {

                    Intent intent1 = new Intent(getBaseContext(), AddStudentAssessment.class);
                    intent1.putExtra(MY_ASSESSMENT_DATA, task);
                    intent1.putExtra(MY_ASSESSMENT_REQ_KEY, EDIT_ASSESSMENT_REQ_CODE);
                    startActivity(intent1);
                }
            }
        });

        myViewModel.getAllTaskByIdStudent(studentId).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
                binding.studentAssessmentRv.setAdapter(adapter);
                binding.studentAssessmentRv.setHasFixedSize(true);
                binding.studentAssessmentRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            }
        });


        binding.studentAssessmentBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getBaseContext(), AddStudentAssessment.class);
                intent1.putExtra(StudentActivity.STUDENT_ID_REQ_KEY, studentId);
                intent1.putExtra(MY_ASSESSMENT_REQ_KEY, ADD_ASSESSMENT_REQ_CODE);
                startActivity(intent1);
            }
        });


        binding.studentAssessmentShowByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myViewModel.getAllTaskByDate(studentId,selectedDateFrom.getTime(), selectedDateTo.getTime()).observe(StudentAssessmentActivity.this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        adapter.setTasks(tasks);
                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menu_in = getMenuInflater();
        menu_in.inflate(R.menu.assessment_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.item_main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                myViewModel.getAllTaskToSearch(studentId,"%"+query+"%").observe(StudentAssessmentActivity.this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        adapter.setTasks(tasks);
                    }
                });




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myViewModel.getAllTaskToSearch(studentId,"%"+newText+"%").observe(StudentAssessmentActivity.this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        adapter.setTasks(tasks);
                    }
                });

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                myViewModel.getAllTaskByIdStudent(studentId).observe(StudentAssessmentActivity.this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        adapter.setTasks(tasks);

                    }
                });
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);


    }

}