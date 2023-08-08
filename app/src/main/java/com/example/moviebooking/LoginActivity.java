package com.example.moviebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviebooking.retrofit.APIUtils;
import com.example.moviebooking.retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView sign_up_textview;
    EditText username_input, password_input;
    Button login_btn;

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign_up_textview = findViewById(R.id.sign_up_textview);
        username_input = findViewById(R.id.input_username);
        password_input = findViewById(R.id.input_password);
        login_btn = findViewById(R.id.btn_login);
        String text = "Don't have an account? Register";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sign_up_textview.setText(ss);
        sign_up_textview.setMovementMethod(LinkMovementMethod.getInstance());
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = username_input.getText().toString();
                password = password_input.getText().toString();

                if(username.length()>0 && username.length()>0){
                    DataClient dataClient = APIUtils.getData();
                    Call<List<User>> callBack = dataClient.LoginData(username, password);
                    callBack.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            ArrayList<User> mangUser = (ArrayList<User>) response.body();
                            if(mangUser.size()>0){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("mangUser", mangUser);
                                startActivity(intent);

                            }else {
//                                Toast.makeText(LoginActivity.this, "ko co tai khoan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Log.d("KKK", t.getMessage());
                            Toast.makeText(LoginActivity.this, "Sai tài khoản", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}