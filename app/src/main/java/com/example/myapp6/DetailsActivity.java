package com.example.myapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    private TextView detailsName, detailsRoom, detailsPrice,detailsState,detailsType;
    private Button buttonDelete, buttonBack, save;
    String URL = "http://10.0.2.2:8080/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //vytvorenie requestu
    JsonApi jsonApi = retrofit.create(JsonApi.class);
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsName = findViewById(R.id.detailsName);
        detailsRoom = findViewById(R.id.detailsRoom);
        detailsPrice = findViewById(R.id.detailsPrice);
        detailsState = findViewById(R.id.detailsState);
        detailsType = findViewById(R.id.detailsType);

        buttonDelete = findViewById(R.id.delete);
        buttonBack = findViewById(R.id.back);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailsName.setText(bundle.getString("name"));
            detailsRoom.setText(bundle.getString("room"));
            detailsPrice.setText(bundle.getString("price"));
            detailsType.setText(bundle.getString("type"));
            detailsState.setText(bundle.getString("state"));
            id = bundle.getInt("id");

        }

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Code", "ID: " +id);
                deleteProperty(id);
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void deleteProperty (int id){
        Call<Void> call = jsonApi.deleteProperty(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Code", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e( "onFailure: ",t.getMessage());

            }
        });
    }
}
