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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PropertyAddActivity extends AppCompatActivity {
    private EditText  name;
    private EditText  room;
    private EditText  price;
    private EditText  inDate;
    private EditText  outDate;
    private EditText  charState;
    private EditText  intType;
    private Button saveButton;

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
        setContentView(R.layout.activity_property_add);

        name = findViewById(R.id.name);
        room = findViewById(R.id.room);
        price = findViewById(R.id.price);
        inDate = findViewById(R.id.inDate);
        outDate = findViewById(R.id.outDate);
        charState = findViewById(R.id.charState);
        intType = findViewById(R.id.intType);
        saveButton = findViewById(R.id.saveButton);

        name.addTextChangedListener(saveTextWatcher);
        room.addTextChangedListener(saveTextWatcher);
        price.addTextChangedListener(saveTextWatcher);
        inDate.addTextChangedListener(saveTextWatcher);
        outDate.addTextChangedListener(saveTextWatcher);
        charState.addTextChangedListener(saveTextWatcher);
        intType.addTextChangedListener(saveTextWatcher);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pName = name.getText().toString();
                String pRoom = room.getText().toString();
                Float pPrice = Float.parseFloat(price.getText().toString());
                String pInDate = inDate.getText().toString();
                String pOutDate = outDate.getText().toString();

                PropertyDTO propertyDTO = new PropertyDTO(pName,pRoom,pPrice,pInDate,pOutDate,charState.getText().charAt(0),Integer.parseInt(intType.getText().toString()));

                Call<Void> call = jsonApi.saveProperty(propertyDTO);
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
                        Toast.makeText(getApplicationContext(), "Property saved",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Property not saved" ,Toast.LENGTH_LONG).show();
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
                saveButton.setEnabled(!name.getText().toString().isEmpty() && !price.getText().toString().isEmpty()
                        && !charState.getText().toString().isEmpty() && !intType.getText().toString().isEmpty() && !room.getText().toString().isEmpty());

                saveButton.setEnabled(isInDateValid(inDate.getText().toString()) && isOutDateValid(outDate.getText().toString(),inDate.getText().toString()));
        }
        @Override
        public void afterTextChanged(Editable s) {
            saveButton.setEnabled(isInDateValid(inDate.getText().toString()) && isOutDateValid(outDate.getText().toString(),inDate.getText().toString()));
        }
    };

    private boolean isInDateValid (String date){
        boolean result;
        if (date.trim().length() ==8) {
            try {
                LocalDate localDate = LocalDate.parse(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
                result = true;
            } catch (DateTimeParseException e) {
                int messagePosition = e.getMessage().indexOf("Invalid");
                String message = e.getMessage().substring(messagePosition, e.getMessage().length());
                inDate.setError(message);
                result = false;
            }
        }
        else {
            result = false;
        }
        return result;
    }

    private boolean isOutDateValid (String date,String inDate){
        boolean result;
        if (date.trim().length() == 8) {
            try {
                LocalDate localDate = LocalDate.parse(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));

                if (isInDateValid(inDate)){
                    LocalDate prevDate = LocalDate.parse(inDate.substring(0, 4) + "-" + inDate.substring(4, 6) + "-" + inDate.substring(6, 8));
                    if (localDate.compareTo(prevDate) < 0){
                        //ak je datum vyradenie skor ako datum zaradenia
                        result = false;
                        outDate.setError("Out Date is earlier than in date");
                    }
                    else{
                        //ak je datum zaradenia skor ako datum vyradenia SPRAVNE
                        result = true;
                    }
                } else {
                    //ak vstupny datum nie je validny
                    result = false;
                }
            } catch (DateTimeParseException e) {
                int messagePosition = e.getMessage().indexOf("Invalid");
                String message = e.getMessage().substring(messagePosition, e.getMessage().length());
                outDate.setError(message);
                //ak je datum v zlom formate a neda sa parsovat
                result = false;
            }
        }
        else if (date.isEmpty()){
            //ak je prazdny moze sa zaevidovat IBA ak je prazdny alebo 8 cisel
            result = true;
        }else {
            //v pripade poctu cisel 1-7
            result = false;
            outDate.setError("Out date must be empty or in right format");
        }
        return result;
    }

}
