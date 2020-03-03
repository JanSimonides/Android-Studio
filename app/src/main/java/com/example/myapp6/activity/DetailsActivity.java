package com.example.myapp6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;;
import android.widget.TextView;

import com.example.myapp6.JsonApi;
import com.example.myapp6.MyAlert;
import com.example.myapp6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Setter
@Getter
public class DetailsActivity extends AppCompatActivity {
    private TextView detailsName, detailsRoom, detailsPrice,detailsState,detailsType,detailsInDate,detailsOutDate;
    private FloatingActionButton buttonDelete, save;

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
        detailsInDate = findViewById(R.id.detailsInDate);
        detailsOutDate = findViewById(R.id.detailsOutDate);

        buttonDelete = findViewById(R.id.delete);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailsName.setText(bundle.getString("name"));
            detailsRoom.setText(bundle.getString("room"));
            detailsPrice.setText(String.valueOf(bundle.getFloat("price"))+" €");
            detailsType.setText(bundle.getString("type"));
            detailsState.setText(bundle.getString("state"));
            detailsInDate.setText(bundle.getString("InDate"));
            String propertyOutDate = bundle.getString("OutDate");

            if (propertyOutDate != null) {
                detailsOutDate.setText(propertyOutDate);
            }
            else {
                detailsOutDate.setText("Not defined");
            }
            id = bundle.getInt("id");


        }

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            MyAlert deleteAlert = new MyAlert(DetailsActivity.this, "Delete: " + detailsName.getText(), "Do yo want delete?") {
                @Override
                public void onClickPositiveBtn() {
                    deleteProperty(id);
                    finish();
                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                    main.putExtra("deletedProperty", detailsName.getText());
                    //main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                }
                @Override
                public void onClickNegativeBtn() {
                }
            };
            deleteAlert.show();
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
                Log.e( "onFailure: ", Objects.requireNonNull(t.getMessage()));

            }
        });
    }

}
