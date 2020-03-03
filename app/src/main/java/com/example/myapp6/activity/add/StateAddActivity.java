package com.example.myapp6.activity.add;

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
import com.example.myapp6.model.entity.State;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StateAddActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText charState;
    private EditText description;

    String URL = "http://10.0.2.2:8080/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //vytvorenie requestu
    JsonApi jsonApi = retrofit.create(JsonApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_add);
        charState  = findViewById(R.id.charState);
        description  = findViewById(R.id.description);
        saveButton = findViewById(R.id.saveButton);

        charState.addTextChangedListener(saveStateWatcher);
        description.addTextChangedListener(saveStateWatcher);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State state = new State(charState.getText().charAt(0),description.getText().toString());
                Call<Void> call = jsonApi.saveState(state);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Code" +response.code(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "State saved",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "State not saved: "+t,Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
        });

    }
    private TextWatcher saveStateWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveButton.setEnabled(!charState.getText().toString().isEmpty() && !description.getText().toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
