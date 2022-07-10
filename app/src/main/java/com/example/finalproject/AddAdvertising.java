package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.finalproject.MyDataBase.Entity.Advertising;
import com.example.finalproject.MyDataBase.Entity.User;
import com.example.finalproject.MyDataBase.MyViewModel;
import com.example.finalproject.MyDataBase.SecTable.ShowCenterData;
import com.example.finalproject.databinding.ActivityAddAdvertisingBinding;
import com.example.finalproject.ui.memorizationcenters.MemorizationCentersFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class AddAdvertising extends AppCompatActivity {

    ActivityAddAdvertisingBinding binding ;
    Calendar selectedDate;

    MyViewModel myViewModel ;

    SharedPreferences sh;
    SharedPreferences.Editor sh_edit;
    Advertising advertising ;

    int hotNumber ;
    String name ,image ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddAdvertisingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.addAdvertisingToolbar);
        myViewModel =new ViewModelProvider(this).get(MyViewModel.class);


        sh = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sh_edit = sh.edit();
        int sh_ed_user = sh.getInt(LoginActivity.USERNAME_KEY, 0);
        int sh_ed_validity = sh.getInt(LoginActivity.VALIDITY_KEY, -1);
        Intent intent =getIntent();
        ShowCenterData showCenterData = (ShowCenterData) intent.getSerializableExtra(MemorizationCentersFragment.MY_CENTER_Key);
        hotNumber = intent.getIntExtra(AdvertisingActivity.ADVERTISING_REQ_KEY,0);

        if (hotNumber==102){
            disableFields();
            Intent intent1 =getIntent();
             advertising = (Advertising) intent1.getSerializableExtra(AdvertisingActivity.ADVERTISING_DATA_KEY);
            binding.addAdvertisingEdText.setText(advertising.getDesc());
        }

        myViewModel.getUser(sh_ed_user).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                name=user.getName();
                image=user.getImage();
            }
        });



        binding.addAdvertisingBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedDate = Calendar.getInstance();
                String desc =binding.addAdvertisingEdText.getText().toString();

                if (TextUtils.isEmpty(desc)){
                    Toast.makeText(AddAdvertising.this, "ادخل الوصف", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hotNumber==101){
                    Advertising advertising =new Advertising(selectedDate.getTime(),desc,name,image,showCenterData.getId());

                    myViewModel.insertAdvertising(advertising);
                    Toast.makeText(AddAdvertising.this, "تم النشر", Toast.LENGTH_SHORT).show();

                }

                else if (hotNumber==102){

                    myViewModel.updateAdvertising(advertising.getId(),desc);
                    Toast.makeText(AddAdvertising.this, "تم التحديث ينجاح", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
    private void disableFields() {
        binding.addAdvertisingEdText.setEnabled(false);
        binding.addAdvertisingBtnSave.setEnabled(false);

    }

    private void enabledFields() {
        binding.addAdvertisingEdText.setEnabled(true);
        binding.addAdvertisingBtnSave.setEnabled(true);



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
                MenuItem delete = binding.addAdvertisingToolbar.getMenu().findItem(R.id.item_details_delete);
                delete.setVisible(false);
                return true;

            case  R.id.item_details_delete :


                myViewModel.deleteAdvertising(advertising.getId());

                Toast.makeText(this, "تم حذف  المنشور  بنجاج", Toast.LENGTH_SHORT).show();
                finish();



                return true;
        }

        return false;


    }

}