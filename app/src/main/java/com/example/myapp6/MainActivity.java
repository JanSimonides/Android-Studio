package com.example.myapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp6.model.Property;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button buttonGetJson;
    private Button buttonReset;
    //String URL = "http://10.0.2.2:8080/";
   String URL = "https://api.myjson.com/bins/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGetJson = findViewById(R.id.buttonGetJson);
        buttonReset = findViewById(R.id.buttonReset);
        textView = findViewById(R.id.textView);

        buttonGetJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJson();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
            }
        });

    }

    public void getJson(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("vypis","URL");
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        //vytvorenie requestu
        Call<List<Property>> call = jsonApi.getPosts();
        //pre vytvorenie na novom threade na pozadi
        call.enqueue(new Callback<List<Property>>() {
            @Override
            public void onResponse(Call<List<Property>> call, Response<List<Property>> response) {
                if (!response.isSuccessful()){
                    textView.setText("Coda: "+response.code());
                    Log.i("Vypis","Coda: "+response.code());
                    return;
                }
                List<Property> properties = response.body();
                for (Property p :properties){
                    String name =  p.getPropertyName();
                    textView.append(name+ '\n');
                    Log.i("vypis","Name: " + name);

                }
            }
            @Override
            public void onFailure(Call<List<Property>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
