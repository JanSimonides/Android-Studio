package com.example.myapp6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class RegistrationActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText password;
    private Button registration;

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
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registration = findViewById(R.id.registration);

        registration.setEnabled(false);
        username.addTextChangedListener(saveTextWatcher);
        email.addTextChangedListener(saveTextWatcher);
        password.addTextChangedListener(saveTextWatcher);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(username.getText().toString(),password.getText().toString(),email.getText().toString());
                Call<Void> call = jsonApi.createUser(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()){
                            finish();
                            Toast.makeText(getApplicationContext(), "Code" +response.code(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Successful",Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Not successful",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });


    }

    private TextWatcher saveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            registration.setEnabled(!username.getText().toString().isEmpty() &&password.getText().toString().trim().length() >1&&!email.getText().toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
