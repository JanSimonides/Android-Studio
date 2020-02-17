package com.example.myapp6;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.example.myapp6.adapter.RecyclerViewAdapter;
import com.example.myapp6.model.Property;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Property> propertyList;


    String URL = "http://10.0.2.2:8080/";
//   String URL = "https://api.myjson.com/bins/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        propertyList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this,propertyList);
        recyclerView.setAdapter(recyclerViewAdapter);
        getJson("all");

    }

    public void getJson(String point){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("vypis",URL);
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        //vytvorenie requestu
        Call<List<Property>> call = jsonApi.getAllPosts(point);
        //pre vytvorenie na novom threade na pozadi
        call.enqueue(new Callback<List<Property>>() {
            @Override
            public void onResponse(Call<List<Property>> call, Response<List<Property>> response) {
                List<Property> properties;
                if (!response.isSuccessful()){
                    //textView.setText("Coda: "+response.code());
                    Log.i("Vypis","Code: "+response.code());
                }
               // textView.setText("");

                properties = response.body();
                propertyList.addAll(properties);

                recyclerViewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Property>> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Vypis", "Throw", t);
            }
        });
    }



}
