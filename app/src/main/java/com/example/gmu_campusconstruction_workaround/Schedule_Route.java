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

public class Schedule_Route extends AppCompatActivity {
    private RoutesDatabase routeDb;
    private SundayDatabase sundayDb;
    private MondayDatabase mondayDb;
    private TuesdayDatabase tuesdayDb;
    private WednesdayDatabase wednesdayDb;
    private ThursdayDatabase thursdayDb;
    private FridayDatabase fridayDb;
    private SaturdayDatabase saturdayDb;
    private Button btnAddDay;
    private Button btnViewDay;
    private Button btnDelete;
    private Button btnClear;
    private ArrayAdapter<String> DB_adapter;
    private Spinner spinner_DB;
    private String[] UP,LP,MP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__route);

        //Day Databases
        sundayDb = new SundayDatabase(this);
        mondayDb = new MondayDatabase(this);
        tuesdayDb = new TuesdayDatabase(this);
        wednesdayDb = new WednesdayDatabase(this);
        thursdayDb = new ThursdayDatabase(this);
        fridayDb = new FridayDatabase(this);
        saturdayDb = new SaturdayDatabase(this);
        routeDb = new RoutesDatabase(this);

        // Layout for main screen
        Spinner spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building2);
        spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building2);
        Spinner spinner_Days = (Spinner) findViewById(R.id.spinner_Days);
        ArrayAdapter<String> SB_adapter = new ArrayAdapter<String>(Schedule_Route.this,
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
                    DB_adapter = new ArrayAdapter<>(Schedule_Route.this,
                            android.R.layout.simple_spinner_item,
                            getResources().getStringArray((R.array.JC_Dest)));
                    spinner_DB.setAdapter(DB_adapter);
                }
                else{
                    int list_num = ListNum(User_Choice);

                    //set the destination adapter based on the users choice
                    if (list_num == 1) {// belongs in the upper part array
                        DB_adapter = new ArrayAdapter<>(Schedule_Route.this,
                                android.R.layout.simple_spinner_item,
                                getResources().getStringArray((R.array.UP_Dest)));
                        spinner_DB.setAdapter(DB_adapter);
                    } else if (list_num == 2 || list_num == 3) {
                        DB_adapter = new ArrayAdapter<>(Schedule_Route.this,
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


        ArrayAdapter<String> DAYS_adapter = new ArrayAdapter<String>(Schedule_Route.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Days));
        spinner_Days.setAdapter(DAYS_adapter);

        btnAddDay = (Button) findViewById(R.id.button_AddDay);
        btnViewDay = (Button) findViewById(R.id.button_ViewDay);
        btnDelete = (Button) findViewById(R.id.button_DeleteDay);
        btnClear = (Button) findViewById(R.id.button_ClearDay);

        //Calling methods
        ConfigureMAButton();
        AddData();
        DeleteData();
        viewDay();
        ClearData();
    }



    /**
     * configure the button that will transition
     * back to Main activity
     */
    private void ConfigureMAButton() {
        Button MAButton = (Button) findViewById(R.id.button_Back);
        MAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * configure the button that will delete
     * a route from the specified day selected from the spinners
     */
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Start_Building2);
                        String b1 = spinner1.getSelectedItem().toString();
                        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Dest_Building2);
                        String b2 = spinner2.getSelectedItem().toString();
                        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_Days);
                        String day = spinner3.getSelectedItem().toString();
                        final String building = b1 + ", " + b2;

                        //Look for the right day and remove the route based on the two buildings.
                        if (day.equals("Sunday")){
                            boolean isDelete = sundayDb.deleteBuilding(building);
                            if(isDelete == true) {
                                Toast.makeText(Schedule_Route.this, "Route Deleted", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Schedule_Route.this, "Route not Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(day.equals("Monday")){
                            boolean isDelete = mondayDb.deleteBuilding(building);
                            if(isDelete == true) {
                                Toast.makeText(Schedule_Route.this, "Route Deleted", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Schedule_Route.this, "Route not Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(day.equals("Tuesday")){
                            boolean isDelete = tuesdayDb.deleteBuilding(building);
                            if(isDelete == true) {
                                Toast.makeText(Schedule_Route.this, "Route Deleted", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Schedule_Route.this, "Route not Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(day.equals("Wednesday")){
                            boolean isDelete = wednesdayDb.deleteBuilding(building);
                            if(isDelete == true) {
                                Toast.makeText(Schedule_Route.this, "Route Deleted", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Schedule_Route.this, "Route not Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(day.equals("Thursday")){
                            boolean isDelete = thursdayDb.deleteBuilding(building);
                            if(isDelete == true) {
                                Toast.makeText(Schedule_Route.this, "Route Deleted", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Schedule_Route.this, "Route not Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(day.equals("Friday")){
                            boolean isDelete = fridayDb.deleteBuilding(building);
                            if(isDelete == true) {
                                Toast.makeText(Schedule_Route.this, "Route Deleted", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Schedule_Route.this, "Route not Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(day.equals("Saturday")){
                            boolean isDelete = saturdayDb.deleteBuilding(building);
                            if(isDelete == true) {
                                Toast.makeText(Schedule_Route.this, "Route Deleted", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Schedule_Route.this, "Route not Deleted", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(Schedule_Route.this, "Invalid Day", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    /**
     * configure the button that will clear
     * all of the routes from the specified day
     */
    public void ClearData() {
        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_Days);
                        String day = spinner3.getSelectedItem().toString();
                        Cursor res = getCursor(day);

                        //Look for the right day and remove all of the routes.
                        if (day.equals("Sunday")){
                            if(res.getCount() == 0) {
                                //show error message
                                showMessage("Error", "No schedule in " + day + ".");
                                return;
                            }
                            while(res.moveToNext()){
                                sundayDb.deleteID(res.getString(0));
                            }
                            //show table
                            showMessage("Clear Day", day + " has been cleared.");
                        }
                        else if (day.equals("Monday")){
                            if(res.getCount() == 0) {
                                //show error message
                                showMessage("Error", "No schedule in " + day + ".");
                                return;
                            }
                            while(res.moveToNext()){
                                mondayDb.deleteID(res.getString(0));
                            }
                            //show table
                            showMessage("Clear Day", day + " has been cleared.");
                        }
                        else if (day.equals("Tuesday")){
                            if(res.getCount() == 0) {
                                //show error message
                                showMessage("Error", "No schedule in " + day + ".");
                                return;
                            }
                            while(res.moveToNext()){
                                tuesdayDb.deleteID(res.getString(0));
                            }
                            //show table
                            showMessage("Clear Day", day + " has been cleared.");
                        }
                        else if (day.equals("Wednesday")){
                            if(res.getCount() == 0) {
                                //show error message
                                showMessage("Error", "No schedule in " + day + ".");
                                return;
                            }
                            while(res.moveToNext()){
                                wednesdayDb.deleteID(res.getString(0));
                            }
                            //show table
                            showMessage("Clear Day", day + " has been cleared.");
                        }
                        else if (day.equals("Thursday")){
                            if(res.getCount() == 0) {
                                //show error message
                                showMessage("Error", "No schedule in " + day + ".");
                                return;
                            }
                            while(res.moveToNext()){
                                thursdayDb.deleteID(res.getString(0));
                            }
                            //show table
                            showMessage("Clear Day", day + " has been cleared.");
                        }
                        else if (day.equals("Friday")){
                            if(res.getCount() == 0) {
                                //show error message
                                showMessage("Error", "No schedule in " + day + ".");
                                return;
                            }
                            while(res.moveToNext()){
                                fridayDb.deleteID(res.getString(0));
                            }
                            //show table
                            showMessage("Clear Day", day + " has been cleared.");
                        }
                        else if (day.equals("Saturday")){
                            if(res.getCount() == 0) {
                                //show error message
                                showMessage("Error", "No schedule in " + day + ".");
                                return;
                            }
                            while(res.moveToNext()){
                                saturdayDb.deleteID(res.getString(0));
                            }
                            //show table
                            showMessage("Clear Day", day + " has been cleared.");
                        }
                        else {
                            showMessage("Error", "Invalid Day");
                        }
                    }
                }
        );
    }

    /**
     * configure the button that will add
     * a the buildings and route to the specified day
     */
    public void AddData() {
        btnAddDay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Start_Building2);
                        String b1 = spinner1.getSelectedItem().toString();
                        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Dest_Building2);
                        String b2 = spinner2.getSelectedItem().toString();
                        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_Days);
                        String day = spinner3.getSelectedItem().toString();
                        final String building = b1 + ", " + b2;
                        final String route = locateRoute(building);

                        //Look for the right day, and add the buildings and route.
                        if (day.equals("Sunday")) {
                            boolean isInserted = sundayDb.insertData(building, route);
                            if (isInserted == true)
                                Toast.makeText(Schedule_Route.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Schedule_Route.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                        else if (day.equals("Monday")) {
                            boolean isInserted = mondayDb.insertData(building, route);
                            if (isInserted == true)
                                Toast.makeText(Schedule_Route.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Schedule_Route.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                        else if (day.equals("Tuesday")) {
                            boolean isInserted = tuesdayDb.insertData(building, route);
                            if (isInserted == true)
                                Toast.makeText(Schedule_Route.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Schedule_Route.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                        else if (day.equals("Wednesday")) {
                            boolean isInserted = wednesdayDb.insertData(building, route);
                            if (isInserted == true)
                                Toast.makeText(Schedule_Route.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Schedule_Route.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                        else if (day.equals("Thursday")) {
                            boolean isInserted = thursdayDb.insertData(building, route);
                            if (isInserted == true)
                                Toast.makeText(Schedule_Route.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Schedule_Route.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                        else if (day.equals("Friday")) {
                            boolean isInserted = fridayDb.insertData(building, route);
                            if (isInserted == true)
                                Toast.makeText(Schedule_Route.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Schedule_Route.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                        else if (day.equals("Saturday")) {
                            boolean isInserted = saturdayDb.insertData(building, route);
                            if (isInserted == true)
                                Toast.makeText(Schedule_Route.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Schedule_Route.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(Schedule_Route.this, "Invalid Day", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    /**
     * a helper method that will locate
     * the route specified by the two buildings
     * @param building String of the two buildings in the format "building1, building2"
     */
    public String locateRoute(String building) {
        Cursor res = routeDb.getAllData();
        if (res.getCount() == 0) {
            //show error message
            showMessage("Error", "Nothing found");
        }
        //traverse through the routeDb database to find the route of the specified buildings
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            if (res.getString(1).equals(building)) {
                buffer.append(res.getString(2) + "\n");
            }
        }
        //Return the route
        return buffer.toString();
    }

    public void viewDay() {
        btnViewDay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Days);
                        String day = spinner1.getSelectedItem().toString();
                        Cursor res = getCursor(day);
                        if(res.getCount() == 0) {
                            //show error message
                            showMessage("Error", "No route on " + day);
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            //buffer.append("Id: "+ res.getString(0) + "\n");
                            buffer.append("Buildings: "+ res.getString(1) + "\n\n");
                            buffer.append("Route: "+ res.getString(2) + "\n\n\n");
                        }
                        //show table
                        showMessage(day, buffer.toString());
                    }
                }
        );
    }

    public Cursor getCursor(String day){
        Cursor res = null;
        if (day.equals("Sunday")) {
            res = sundayDb.getAllData();
        }
        else if (day.equals("Monday")){
            res = mondayDb.getAllData();
        }
        else if (day.equals("Tuesday")){
            res = tuesdayDb.getAllData();
        }
        else if (day.equals("Wednesday")){
            res = wednesdayDb.getAllData();
        }
        else if (day.equals("Thursday")){
            res = thursdayDb.getAllData();
        }
        else if (day.equals("Friday")){
            res = fridayDb.getAllData();
        }
        else if (day.equals("Saturday")){
            res = saturdayDb.getAllData();
        }
        else {
            showMessage("Invalid Day", "Invalid Day");
        }
        return res;
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
}
