package com.example.gmu_campusconstruction_workaround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Schedule_Route extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__route);

        //ConfigureMAButton();


        // Layout for main screen
        Spinner Spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building);
        Spinner Spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building);
        ArrayAdapter<String> SB_adapter;
    }



/*    *//**
     * configure the button that will transition
     * back to Main activity
     *//*
    private void ConfigureMAButton() {
        Button MAButton = (Button) findViewById(R.id.button_GoToMA);
        MAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }*/
}
