package com.example.moviebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText currentPassTV, newPassTV, confirmNewPassTV;
    Button changePassBtn;

    ArrayList<User> userArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        currentPassTV = findViewById(R.id.input_oldPassword);
        newPassTV = findViewById(R.id.input_newPassword);
        confirmNewPassTV = findViewById(R.id.input_confirmNewPassword);
        changePassBtn =findViewById(R.id.btn_changePass);
        Intent intent = getIntent();
        userArrayList = intent.getParcelableArrayListExtra("userArray");
        Log.d("bbb", userArrayList.get(0).getPassword());
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPassTV.getText() != null && newPassTV != null && confirmNewPassTV != null){
                    if(currentPassTV.getText().toString().equals(userArrayList.get(0).getPassword()) && newPassTV.getText().toString().equals(confirmNewPassTV.getText().toString())){
                        DataClient dataClient = APIUtils.getData();
                        Call<String> callBack = dataClient.changePass(userArrayList.get(0).getId(), currentPassTV.getText().toString(), newPassTV.getText().toString());
                        callBack.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(ChangePasswordActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(ChangePasswordActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "Nhap sai", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ChangePasswordActivity.this, "Vui long nhap du", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}