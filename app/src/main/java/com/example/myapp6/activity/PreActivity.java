package com.example.myapp6.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapp6.R;
import com.example.myapp6.activity.add.PropertyAddActivity;
import com.example.myapp6.activity.add.StateAddActivity;
import com.example.myapp6.activity.add.TypeAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FloatingActionButton buttonShow;
    private FloatingActionButton buttonAdd;
    private String selectShow;
    private String selectAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);
        buttonShow = findViewById(R.id.buttonShow);
        buttonAdd = findViewById(R.id.buttonAdd);

        Spinner spinnerShow = findViewById(R.id.spinnerShow);
        ArrayAdapter<CharSequence> adapterShow = ArrayAdapter.createFromResource(this,R.array.states,android.R.layout.simple_spinner_item);
        adapterShow.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShow.setAdapter(adapterShow);
        spinnerShow.setOnItemSelectedListener(this);

        Spinner spinnerAdd = findViewById(R.id.spinnerAdd);
        ArrayAdapter<CharSequence> adapterAdd = ArrayAdapter.createFromResource(this,R.array.objects,android.R.layout.simple_spinner_item);
        adapterAdd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdd.setAdapter(adapterAdd);
        spinnerAdd.setOnItemSelectedListener(this);


        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShow = new Intent(PreActivity.this,MainActivity.class);
                intentShow.putExtra("select",selectShow);
                startActivity(intentShow);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent();
                if (selectAdd.equals("property")){
                 intentAdd = new Intent(PreActivity.this, PropertyAddActivity.class);
                }
                if (selectAdd.equals("state")){
                 intentAdd = new Intent(PreActivity.this, StateAddActivity.class);
                }
                if (selectAdd.equals("type")){
                 intentAdd = new Intent(PreActivity.this, TypeAddActivity.class);
                }
                startActivity(intentAdd);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerAdd){
            selectAdd = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(),selectAdd,Toast.LENGTH_SHORT).show();
        }
        if (parent.getId() == R.id.spinnerShow){
            selectShow = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(),selectShow,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
