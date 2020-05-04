package com.example.gmu_campusconstruction_workaround;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Favorite_List extends AppCompatActivity {

    private RoutesDB_Access routesDBA;
    private FavoritesDatabase favoritesDb;
    private Button button_deleteFav,button_addFav,button_View,button_clear;
    private String[] UP, LP, MP;
    private ArrayAdapter<String>  DB_adapter;
    private Spinner spinner_DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        //Database initialized
        routesDBA = RoutesDB_Access.getInstance(getApplicationContext());
        routesDBA.open();
        favoritesDb = new FavoritesDatabase(this);

        // Layout for main screen
        Spinner spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building);
        spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building);
        ArrayAdapter<String> SB_adapter = new ArrayAdapter<String>(Favorite_List.this,
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
                    DB_adapter = new ArrayAdapter<>(Favorite_List.this,
                            android.R.layout.simple_spinner_item,
                            getResources().getStringArray((R.array.JC_Dest)));
                    spinner_DB.setAdapter(DB_adapter);
                }
                else{
                    int list_num = ListNum(User_Choice);

                    //set the destination adapter based on the users choice
                    if (list_num == 1) {// belongs in the upper part array
                        DB_adapter = new ArrayAdapter<>(Favorite_List.this,
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray((R.array.UP_Dest)));
                        spinner_DB.setAdapter(DB_adapter);
                    } else if (list_num == 2 || list_num == 3) {
                        DB_adapter = new ArrayAdapter<>(Favorite_List.this,
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

        // Initializing button variables
        button_addFav = (Button) findViewById(R.id.button_addFav);
        button_deleteFav = (Button) findViewById(R.id.button_deleteFav);
        button_View = (Button) findViewById(R.id.button_View);
        button_clear = (Button) findViewById(R.id.button_clear);

        //Calls methods in class
        ConfigureMAButton();
        InsertData();
        RemoveData();
        ViewAll();
        ClearAll();
    }

    /**
     * configure the button that will transition
     * back to Main activity
     */
    private void ConfigureMAButton() {
        Button MAButton = (Button) findViewById(R.id.button_GoToMA);
        MAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Inserts data from spinners into database
     * once button is pressed
     */
    private void InsertData() {
        button_addFav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Start_Building);
                        String b1 = spinner1.getSelectedItem().toString();
                        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Dest_Building);
                        String b2 = spinner2.getSelectedItem().toString();
                        final String building = b1 + ", " + b2;
                        final String route = locateRoute(building);
                        Cursor res = favoritesDb.readAllData();
                        if (res.getCount() < 20) {
                            boolean isInserted = favoritesDb.insertRoute(building, route);
                            if (isInserted == true)
                                Toast.makeText(Favorite_List.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Favorite_List.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Favorite_List.this, "Favorites list is full", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    /**
     * Removes the route from the favorites database
     * and prompts a message of confirmation
     * once button is pressed
     */
    public void RemoveData() {
        button_deleteFav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Start_Building);
                        String b1 = spinner1.getSelectedItem().toString();
                        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Dest_Building);
                        String b2 = spinner2.getSelectedItem().toString();
                        final String building = b1 + ", " + b2;
                        boolean isInserted = favoritesDb.deleteBuilding(building);
                        if (isInserted == true)
                            Toast.makeText(Favorite_List.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Favorite_List.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /**
     * Views all routes in the database
     * once button is pressed
     * Error message if database is empty
     */
    public void ViewAll(){
        button_View.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = favoritesDb.readAllData();
                        if (res.getCount() == 0) {
                            //show error message
                            showMessage("No Routes", "Favorites list is empty");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            //buffer.append("Id: "+ res.getString(0) + "\n");
                            buffer.append("Buildings: " + res.getString(1) + "\n\n");
                            buffer.append("Route: " + res.getString(2) + "\n\n\n");
                        }
                        //show table
                        showMessage("Favorites", buffer.toString());
                    }
                }
        );
    }

    /**
     * Clears all routes from the database
     * once button is pressed
     * Error if database is already empty
     */
    public void ClearAll() {
        button_clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = favoritesDb.readAllData();
                        if (res.getCount() == 0) {
                            //show error message
                            showMessage("Error", "Favorites is already empty");
                            return;
                        }
                        while (res.moveToNext()) {
                            favoritesDb.deleteID(res.getString(0));
                        }
                        //show table
                        showMessage("Clear List","Favorites has been cleared.");
                    }
                }
        );
    }

    /**
     * Locates route from main database to be added
     * once button is pressed
     */
    public String locateRoute(String building) {
        Cursor res = routesDBA.getAllData();
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
        //Return the route
        return buffer.toString();
    }

    /**
     *
     * @param title
     * @param Message
     **/
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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
}
