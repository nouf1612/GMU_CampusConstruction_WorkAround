package com.example.gmu_campusconstruction_workaround;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Favorite_List extends AppCompatActivity {

    private RoutesDatabase routesDb;
    private FavoritesDatabase favoritesDb;
    private Button button_deleteFav,button_addFav,button_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        //Database initialized
        routesDb = new RoutesDatabase(this);
        favoritesDb = new FavoritesDatabase(this);

        // Layout for main screen
        Spinner spinner_SB = (Spinner) findViewById(R.id.spinner_Start_Building);
        Spinner spinner_DB = (Spinner) findViewById(R.id.spinner_Dest_Building);
        ArrayAdapter<String> SB_adapter = new ArrayAdapter<String>(Favorite_List.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Start_Buildings));
        spinner_SB.setAdapter(SB_adapter);
        ArrayAdapter<String> DB_adapter = new ArrayAdapter<String>(Favorite_List.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.Start_Buildings));
        spinner_DB.setAdapter(DB_adapter);

        button_addFav = (Button) findViewById(R.id.button_addFav);
        button_deleteFav = (Button) findViewById(R.id.button_deleteFav);
        button_View = (Button) findViewById(R.id.button_View);

        //Calls methods in class
        ConfigureMAButton();
        InsertData();
        RemoveData();
        ViewAll();
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
     *
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
                        boolean isInserted = favoritesDb.insertRoute(building, route);
                        if (isInserted == true)
                            Toast.makeText(Favorite_List.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Favorite_List.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


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

    public void ViewAll(){
        button_View.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = favoritesDb.readAllData();
                        if (res.getCount() == 0) {
                            //show error message
                            showMessage("Error", "No route");
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

    public String locateRoute(String building) {
        Cursor res = routesDb.getAllData();
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
}
