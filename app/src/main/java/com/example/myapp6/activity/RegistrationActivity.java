package com.example.myapp6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp6.JsonApi;
import com.example.myapp6.R;
import com.example.myapp6.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText controlPassword;
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
        controlPassword = findViewById(R.id.controlPassword);
        registration = findViewById(R.id.registration);

        registration.setEnabled(false);
        username.addTextChangedListener(saveTextWatcher);
        email.addTextChangedListener(saveTextWatcher);
        password.addTextChangedListener(saveTextWatcher);
        controlPassword.addTextChangedListener(saveTextWatcher);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(username.getText().toString(),password.getText().toString(),email.getText().toString());
                Call<Void> call = jsonApi.createUser(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()){
                            try {
                                JSONObject responseObject = new JSONObject(response.errorBody().string());
                                String message = responseObject.getString("message");
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Registration was successful" +response.message() ,Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Not Registration was not successful",Toast.LENGTH_LONG).show();
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
            registration.setEnabled(isPasswordValid(password.getText().toString()) &&
                    isEmailValid(email.getText().toString()) &&
                    isControlPasswordValid(password.getText().toString(),controlPassword.getText().toString()));


            if (!isPasswordValid(password.getText().toString())){
                password.setError("Password must be at least 6 characters Length");
            }
            if(!isEmailValid(email.getText().toString())){
                email.setError("Invalid email address");
            }
            if(!isControlPasswordValid(password.getText().toString(),controlPassword.getText().toString())){
                controlPassword.setError("The password and confirmation password do not match");
            }
            if (!isUsernameValid(username.getText().toString())){
                username.setError("Username must be at least 3 characters Length");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean isUsernameValid(String username) {
        return username != null && username.trim().length() > 2;
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.trim().isEmpty();
        }
    }

    private boolean isPasswordValid (String password) {
        return password != null && password.trim().length() > 5;
    }
    private boolean isControlPasswordValid (String password, String cPassword){
        return  password.trim().equals(cPassword.trim());
    }

}
