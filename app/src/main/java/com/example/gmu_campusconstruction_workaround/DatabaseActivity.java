package com.example.gmu_campusconstruction_workaround;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DatabaseActivity extends AppCompatActivity {

    private RoutesDatabase routeDb;
    private EditText editBuildings, editRoute, editID;
    private Button btnAddData;
    private Button btnViewAll;
    private Button btnUpdate;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        routeDb = new RoutesDatabase(this);

        editBuildings = (EditText) findViewById(R.id.editText_Buildings);
        editRoute = (EditText) findViewById(R.id.editText_Route);
        editID = (EditText) findViewById(R.id.editText_ID);
        btnAddData = (Button) findViewById(R.id.button_Add);
        btnViewAll = (Button) findViewById(R.id.button_View);
        btnUpdate = (Button) findViewById(R.id.button_Update);
        AddData();
        viewAll();
        UpdateData();


        ConfigureBB();
    }

    private void ConfigureBB() {
        buttonBack = findViewById(R.id.button_Back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = routeDb.updateData(editID.getText().toString(),
                                editBuildings.getText().toString(),
                                editRoute.getText().toString());
                        if(isUpdate == true) {
                            Toast.makeText(DatabaseActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(DatabaseActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = routeDb.insertData(editBuildings.getText().toString(),
                                editRoute.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(DatabaseActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(DatabaseActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
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

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
