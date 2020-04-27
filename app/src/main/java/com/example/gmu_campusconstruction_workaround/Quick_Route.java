package com.example.gmu_campusconstruction_workaround;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Quick_Route extends AppCompatActivity {

    //variables for the route database, list spinners and their adapters,
    // and the get Route and MainActivity buttons
    private RoutesDatabase routeDb;
    private Spinner spinner_SB, spinner_DB;
    private Button GRButton, MAButton, buttonRoute;
    private String[] UP, LP, MP;
    private ArrayAdapter<String> SB_adapter, DB_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick__route);

        routeDb = new RoutesDatabase(this);
        ConfigureMAButton();


        // spinners for the start and destination lists
        spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building);
        spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building);
        SB_adapter = new ArrayAdapter<>(Quick_Route.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Start_Buildings));
        spinner_SB.setAdapter(SB_adapter);


        // Destination spinner conditions
        //Set arrays for comparison
        UP = getResources().getStringArray(R.array.Upper_Part);
        LP = getResources().getStringArray(R.array.Lower_Part);
        MP = getResources().getStringArray(R.array.Middle_Part);



        // set the conditions for the destination spinner
        spinner_SB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String User_Choice = parent.getItemAtPosition(position).toString();
                // figure out what list the users choice belongs to
                if(User_Choice.equals("Johnson Center")){
                    DB_adapter = new ArrayAdapter<>(Quick_Route.this,
                            android.R.layout.simple_spinner_item,
                            getResources().getStringArray((R.array.JC_Dest)));
                    spinner_DB.setAdapter(DB_adapter);
                }
                else{
                    int list_num = ListNum(User_Choice);

                    //set the destination adapter based on the users choice
                    if (list_num == 1) {// belongs in the upper part array
                        DB_adapter = new ArrayAdapter<>(Quick_Route.this,
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray((R.array.UP_Dest)));
                        spinner_DB.setAdapter(DB_adapter);
                    } else if (list_num == 2 || list_num == 3) {
                        DB_adapter = new ArrayAdapter<>(Quick_Route.this,
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray((R.array.Upper_Part)));
                        spinner_DB.setAdapter(DB_adapter);
                    } else {
                        //show error message
                        showMessage("Error", "Nothing found");
                    }}
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ConfigureGRButton();


        buttonRoute = findViewById(R.id.button_GetRoute);
        viewRoute();
        //
    }


    /**
     * configure the button that will transition
     * back to Main activity
     */
    private void ConfigureMAButton() {
        MAButton = findViewById(R.id.button_GoToMA);
        MAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * configure the get route button
     */
    private void ConfigureGRButton() {
        GRButton = findViewById((R.id.button_GetRoute));
        GRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewRoute();
            }
        });
    }

    /**
     * Check if the users choice belongs to List
     *
     * @param UserChoice check if in List
     * @param List       check if the user choice is in it
     * @return true if the users choice is in List, false otherwise
     */
    private boolean CheckList(String UserChoice, String[] List) {
        boolean belongs_to_current_list = false;
        //see if user choice belongs to List
        for (String s : List) {
            if (UserChoice.equals(s)) {
                belongs_to_current_list = true;
                break;
            }
        }

        return belongs_to_current_list;
    }

    /**
     * figure out what list the users choice belongs in
     *
     * @param UserChoice check which list it belongs to
     * @return Lists number
     */
    private int ListNum(String UserChoice) {
        int list_num = 0;
        boolean cond = false;
        // check which list the users choice belongs in
        //upper = 1, middle = 2, lower = 3
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                cond = CheckList(UserChoice, UP);
            } else if (i == 2) {
                cond = CheckList(UserChoice, MP);
            } else {
                cond = CheckList(UserChoice, LP);
            }

            if (cond) {
                list_num = i;
                break;
            }
        }
        return list_num;
    }

    /**
     * @param title
     * @param Message
     */
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    /**
     * NOT DONE NEEDS EDITING
     */

    public void viewRoute() {
        buttonRoute.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Start_Building);
                        String building1 = spinner1.getSelectedItem().toString();
                        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Dest_Building);
                        String building2 = spinner2.getSelectedItem().toString();
                        final String building = building1 + ", " + building2;

                        Cursor res = routeDb.getAllData();
                        if (res.getCount() == 0) {
                            //show error message
                            showMessage("Error", "Nothing found");
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            if (res.getString(1).equals(building)) {
                                buffer.append(res.getString(2) + "\n");
                            }
                        }
                        //show table
                        showMessage("Here! Follow this route.", buffer.toString());
                    }
                }
        );
    }
}