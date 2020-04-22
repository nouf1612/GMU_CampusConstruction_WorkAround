package com.example.gmu_campusconstruction_workaround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Quick_Route extends AppCompatActivity {

    //variables for the route database, list spinners and their adapters,
    // and the get Route and MainActivity buttons
    private RoutesDatabase routeDb;
    private Spinner spinner_SB, spinner_DB;
    private Button GRButton,MAButton, buttonRoute;
    private String[] UP, LP, MP;
    private String Building_1,Building_2;
    private ArrayAdapter<String> SB_adapter,DB_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick__route);

        routeDb = new RoutesDatabase(this);
        ConfigureMAButton();


        // spinners for the start and destination lists
        spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building);
        spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building);
        SB_adapter = new ArrayAdapter<String>(Quick_Route.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Start_Buildings));
        spinner_SB.setAdapter(SB_adapter);


        // Destination spinner conditions
        //Set arrays for comparison
        UP = getResources().getStringArray(R.array.Upper_Part);
        LP = getResources().getStringArray(R.array.Lower_Part);
        MP = getResources().getStringArray(R.array.Middle_Part);



        spinner_SB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String User_Choice = parent.getItemAtPosition(position).toString();
                // figure out what list the users choice belongs to
                int list_num = ListNum(User_Choice);

                //set the destination adapter based on the users choice
                if (list_num==1) {// belongs in the upper part array
                    DB_adapter = new ArrayAdapter<String>(Quick_Route.this,
                            android.R.layout.simple_spinner_item,
                            getResources().getStringArray((R.array.UP_Dest)));
                    spinner_DB.setAdapter(DB_adapter);   }
                else if (list_num==2 || list_num==3) {
                    DB_adapter = new ArrayAdapter<String>(Quick_Route.this,
                            android.R.layout.simple_spinner_item,
                            getResources().getStringArray((R.array.Upper_Part)));
                    spinner_DB.setAdapter(DB_adapter);
                }
                else {
                    //show error message
                    showMessage("Error", "Nothing found");
                    }
                Building_1 = User_Choice;
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // get the users destination choice
        spinner_DB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Building_2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ConfigureGRButton();

        //KEVIN EDITS
        buttonRoute = (Button) findViewById(R.id.button_GetRoute);
        viewRoute();
        //
    }


    /**
     * configure the button that will transition
     * back to Main activity
     */
    private void ConfigureMAButton() {
        MAButton = (Button) findViewById(R.id.button_GoToMA);
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
    private void ConfigureGRButton(){
        GRButton = (Button) findViewById((R.id.button_GetRoute));
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
        for (int i = 0; i < List.length; i++) {
            if (UserChoice.equals(List[i])) {
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
     *
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
     * @param Building_1
     * @param Building_2
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
                        if(res.getCount() == 0) {
                            //show error message
                            showMessage("Error", "Nothing found");
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {
                            if(res.getString(1).equals(building)) {
                                buffer.append(res.getString(2) + "\n");
                            }
                        }
                        //show table
                        showMessage("Here! Follow this route.", buffer.toString());
                    }
                }
        );
    }

/*    public void viewRoute(String Building_1, String Building_2) {
        Cursor res = routeDb.getAllData();
        String route = "none";
        if(res.getCount() == 0) {
                //show error message
                showMessage("Error", "Nothing found");
            }
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()) {
                if(res.getString(1).equals(building)) {
                    buffer.append(res.getString(2) + "\n");
                }
            }
            //show table
            showMessage("Here! Follow this route.", buffer.toString());
        }*/



}

    /* Old Layout by kevin

    private RoutesDatabase routeDb;
    private Button buttonDatabase;
    private Button buttonRoute;
    private Button btnViewTest;
    // private Button buttonQR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        routeDb = new RoutesDatabase(this);

        buttonDatabase = (Button) findViewById(R.id.button_Database);
        btnViewTest = (Button) findViewById(R.id.button_ViewTest);

        viewAll();

        buttonDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatabaseConfig();
            }
        });
        //Testing layout start
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Building1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Building2);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myAdapter);
        spinner2.setAdapter(myAdapter);

        buttonRoute = (Button) findViewById(R.id.button_Route);
        viewRoute();
        //Testing Layout end
        //Quick Route Layout
        Spinner Spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building);
        Spinner Spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building);
        ArrayAdapter<String> SB_adapter =
    }

    public void openDatabaseConfig() {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewRoute() {
        buttonRoute.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Building1);
                        String building1 = spinner1.getSelectedItem().toString();
                        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Building2);
                        String building2 = spinner2.getSelectedItem().toString();
                        final String building = building1 + ", " + building2;

                        Cursor res = routeDb.getAllData();
                        String route = "none";
                        if(res.getCount() == 0) {
                            //show error message
                            showMessage("Error", "Nothing found");
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()) {
                            if(res.getString(1).equals(building)) {
                                buffer.append(res.getString(2) + "\n");
                            }
                        }
                        //show table
                        showMessage("Here! Follow this route.", buffer.toString());
                    }
                }
        );
    }

    public void viewAll() {
        btnViewTest.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = routeDb.getAllData();
                        if(res.getCount() == 0) {
                            //show error message
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Id: "+ res.getString(0) + "\n");
                            buffer.append("Buildings: "+ res.getString(1) + "\n");
                            buffer.append("Route: "+ res.getString(2) + "\n\n");
                        }
                        //show table
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }


}*/
