package com.example.gmu_campusconstruction_workaround;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RoutesDatabase routeDb;
    private Button buttonDatabase;
    private Button buttonRoute;
    private Button btnViewTest;

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

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_Building1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_Building2);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myAdapter);
        spinner2.setAdapter(myAdapter);

        buttonRoute = (Button) findViewById(R.id.button_Route);
        viewRoute();
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

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
