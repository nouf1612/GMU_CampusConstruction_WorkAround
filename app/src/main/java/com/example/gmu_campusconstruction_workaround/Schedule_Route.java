package com.example.gmu_campusconstruction_workaround;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Schedule_Route extends AppCompatActivity {

    private Button MAButton;
    private Spinner spinner_SB;
    private Spinner spinner_DB;
    private Spinner spinner_Days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__route);

        ConfigureMAButton();


        // Layout for main screen
        spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building2);
        spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building2);
        spinner_Days = (Spinner) findViewById(R.id.spinner_Days);

        ArrayAdapter<String> BuildingAdapter = new ArrayAdapter<String>(Schedule_Route.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Buildings));
        BuildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> DayAdapter = new ArrayAdapter<String>(Schedule_Route.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Days));
        DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_SB.setAdapter(BuildingAdapter);
        spinner_DB.setAdapter(BuildingAdapter);
        spinner_Days.setAdapter(DayAdapter);
    }



/**
     * configure the button that will transition
     * back to Main activity
     */

    private void ConfigureMAButton() {
        Button MAButton = (Button) findViewById(R.id.button_GoToMA2);
        MAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
