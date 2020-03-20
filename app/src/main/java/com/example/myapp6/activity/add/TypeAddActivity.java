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
import com.example.myapp6.model.entity.Type;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypeAddActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText intType;
    private EditText description;

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
        setContentView(R.layout.activity_type_add);

        intType  = findViewById(R.id.intType);
        description  = findViewById(R.id.description);
        saveButton = findViewById(R.id.saveButton);

        intType.addTextChangedListener(saveTypeWatcher);
        description.addTextChangedListener(saveTypeWatcher);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type type = new Type(Integer.parseInt(intType.getText().toString()),description.getText().toString());

                Call<Void> call = jsonApi.saveType(type);
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
                        Toast.makeText(getApplicationContext(), "Type saved",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Type not saved"+t,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
    private TextWatcher saveTypeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveButton.setEnabled(!intType.getText().toString().isEmpty() && !description.getText().toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
