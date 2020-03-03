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
import com.example.myapp6.model.PropertyDTO;
import com.example.myapp6.model.entity.Property;
import com.example.myapp6.model.entity.State;
import com.example.myapp6.model.entity.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PropertyAddActivity extends AppCompatActivity {
    private EditText  propertyId;
    private EditText  name;
    private EditText  room;
    private EditText  price;
    private EditText  inDate;
    private EditText  outDate;
    private EditText  charState;
    private EditText  intType;
    private Button saveButton;

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
        setContentView(R.layout.activity_property_add);

        propertyId = findViewById(R.id.propertyId);
        name = findViewById(R.id.name);
        room = findViewById(R.id.room);
        price = findViewById(R.id.price);
        inDate = findViewById(R.id.inDate);
        outDate = findViewById(R.id.outDate);
        charState = findViewById(R.id.charState);
        intType = findViewById(R.id.intType);
        saveButton = findViewById(R.id.saveButton);

        propertyId.addTextChangedListener(saveTextWatcher);
        name.addTextChangedListener(saveTextWatcher);
        room.addTextChangedListener(saveTextWatcher);
        price.addTextChangedListener(saveTextWatcher);
        inDate.addTextChangedListener(saveTextWatcher);
        charState.addTextChangedListener(saveTextWatcher);
        intType.addTextChangedListener(saveTextWatcher);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pId = Integer.parseInt(propertyId.getText().toString());
                String pName = name.getText().toString();
                String pRoom = room.getText().toString();
                Float pPrice = Float.parseFloat(price.getText().toString());
                String pInDate = inDate.getText().toString();
                String pOutDate = outDate.getText().toString();

                PropertyDTO propertyDTO = new PropertyDTO(pId,pName,pRoom,pPrice,pInDate,pOutDate,charState.getText().charAt(0),Integer.parseInt(intType.getText().toString()));

                Call<Void> call = jsonApi.saveProperty(propertyDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Code" +response.code(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Property saved",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Property not saved" ,Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
        });
    }


    private TextWatcher saveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(!propertyId.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && !price.getText().toString().isEmpty() &&
                        !inDate.getText().toString().isEmpty() && !charState.getText().toString().isEmpty() && !intType.getText().toString().isEmpty() && !room.getText().toString().isEmpty() && inDate.length()==8);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
