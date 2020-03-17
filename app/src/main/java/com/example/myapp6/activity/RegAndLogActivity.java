package com.example.myapp6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp6.JsonApi;
import com.example.myapp6.R;
import com.example.myapp6.model.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegAndLogActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login;
    private Button regButton;

    //String URL = "http://10.0.2.2:8080/";
    String URL = "http://192.168.1.10:8080/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //vytvorenie requestu
    JsonApi jsonApi = retrofit.create(JsonApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_and_log);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.logButton);
        regButton = findViewById(R.id.regButton);
        username.addTextChangedListener(saveTextWatcher);
        password.addTextChangedListener(saveTextWatcher);

        login.setEnabled(!username.getText().toString().isEmpty() &&password.getText().toString().trim().length() >5);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(username.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                Call<Void> call = jsonApi.login(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Code" +response.code(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),PreActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Not successful sign up. Click na Registration" ,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(reg);
            }
        });
    }

    private TextWatcher saveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            login.setEnabled(!username.getText().toString().isEmpty() &&password.getText().toString().trim().length() >1);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
