package com.example.moviebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<User> userArrayList = new ArrayList<>();

    EditText name_update, email_update, sdt_update, date_update;
    RadioButton update_gtNam, update_gtNu;
    Button update_btn;
    String gt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name_update = findViewById(R.id.input_update_hoten);
        email_update = findViewById(R.id.input_update_email);
        sdt_update = findViewById(R.id.input_update_sdt);
        date_update = findViewById(R.id.input_update_ngaysinh);
        update_gtNam = findViewById(R.id.update_gtNam);
        update_gtNu = findViewById(R.id.update_gtNu);
        update_btn = findViewById(R.id.btn_update_info);
        Intent intent = getIntent();
        userArrayList = intent.getParcelableArrayListExtra("userArray");
        name_update.setText(userArrayList.get(0).getFullname());
        email_update.setText(userArrayList.get(0).getEmail());
        sdt_update.setText(userArrayList.get(0).getPhone());
        date_update.setText(userArrayList.get(0).getBirth());
        Log.d("ccc", userArrayList.get(0).getGt());
        if(userArrayList.get(0).getGt().equals("Nam")){
            update_gtNam.setChecked(true);
        }else {
            update_gtNu.setChecked(true);
        }
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        String date  = day + "/" + month + "/" + year;
                        date_update.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        if(update_gtNam.isChecked()){
            gt = "Nam";
        }else {
            gt = "Nữ";
        }
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClient dataClient = APIUtils.getData();
                Call<String> callBack = dataClient.updateUser(userArrayList.get(0).getId(),
                        name_update.getText().toString(),
                        sdt_update.getText().toString(),
                        date_update.getText().toString(),
                        gt, email_update.getText().toString());
                callBack.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}