package com.example.moviebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView login_textview;
    EditText input_ngaysinh, input_hoten, input_username, input_password, input_confirm_password, input_email, input_sdt;
    RadioGroup input_gioitinh;
    RadioButton gtNam, gtNu;
    Button signup_btn;

    String fullname, phone, gt, birth, username, password, confirm_password, email;

    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login_textview = findViewById(R.id.login_textview);
        input_ngaysinh = findViewById(R.id.input_ngaysinh);
        input_hoten = findViewById(R.id.input_hoten);
        input_username = findViewById(R.id.input_username2);
        input_password = findViewById(R.id.input_password2);
        input_confirm_password = findViewById(R.id.input_confirm_password);
        input_email = findViewById(R.id.input_email);
        input_sdt = findViewById(R.id.input_sdt);
        input_gioitinh = findViewById(R.id.input_gioitinh);
        gtNam = findViewById(R.id.gtNam);
        gtNu = findViewById(R.id.gtNu);
        signup_btn = findViewById(R.id.btn_signup);

        gtNam.setChecked(true);

        String text = "Already have an account? Login";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        login_textview.setText(ss);
        login_textview.setMovementMethod(LinkMovementMethod.getInstance());

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        input_ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month +1;
                        String date  = day + "/" + month + "/" + year;
                        input_ngaysinh.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = input_hoten.getText().toString();
                phone = input_sdt.getText().toString();
                if(gtNam.isChecked()){
                    gt = "Nam";
                }else {
                    gt = "Nữ";
                }
                birth = input_ngaysinh.getText().toString();
                username = input_username.getText().toString();
                password = input_password.getText().toString();
                confirm_password = input_confirm_password.getText().toString();
                email = input_email.getText().toString();
                if(fullname.length()>0 && phone.length() > 0 && gt.length()>0 && birth.length()>0 
                    && username.length() >0 && password.length()>0 && confirm_password.length()>0 && email.length()>0){
                    if(password.equals(confirm_password)){
                        DataClient dataClient = APIUtils.getData();
                        Call<String> callBack = dataClient.SignupData(fullname, phone, birth, gt, username, password, email);
                        callBack.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String result = response.body();
                                Log.d("BBB", result);
                                if(result.equals("Success")){
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else if(result.equals("UsernameAlreadyExists")){
                                    Toast.makeText(RegisterActivity.this, "Tên đăng nhập đã được sử dụng", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }else {
                        Toast.makeText(RegisterActivity.this, "Nhập sai password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}