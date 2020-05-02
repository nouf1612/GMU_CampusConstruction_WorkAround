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
    private RoutesDB_Access routesDBA;
    private Spinner spinner_DB;
    private String[] UP, LP, MP;
    private String Building1, Building2;
    private ArrayAdapter<String> DB_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick__route);

        routesDBA = RoutesDB_Access.getInstance(getApplicationContext());
        routesDBA.open();
        ConfigureMAButton();


        // spinners for the start and destination lists
        Spinner spinner_SB = findViewById(R.id.spinner_Start_Building);
        spinner_DB = findViewById(R.id.spinner_Dest_Building);
        ArrayAdapter<String> SB_adapter = new ArrayAdapter<>(Quick_Route.this,
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
                Building1 = User_Choice;
                Building2 = spinner_DB.getSelectedItem().toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ConfigureGRButton();


    }


    /**
     * configure the button that will transition
     * back to Main activity
     */
    private void ConfigureMAButton() {
        Button MAButton = findViewById(R.id.button_GoToMA);
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
        Button GRButton = findViewById((R.id.button_GetRoute));
        GRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewRoute();

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
     * Create an instance of the database
     * Get and view the route from it
     */

    public void viewRoute() {

        Cursor res = routesDBA.getAllData();
        if (res.getCount() == 0) { // check if the database is empty
            //show error message
            showMessage("Error", "Nothing found");
        }
        // get the route
        final String Buidlings = Building1 + ", " + Building2;
        String route = routesDBA.getRoute(Buidlings);
        showMessage("Here! Follow this route.", route);

    }

}
