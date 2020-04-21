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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConfigureQRButton();

    }

    /**
     * configure the button that will transition
     * to the Quick Route activity
     */
    private void ConfigureQRButton() {
        Button QRButton = (Button) findViewById(R.id.button_GoToQR);
        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Quick_Route.class));
            }
        });
    }

    /**
     * configure the button that will transition
     * to the Schedule Route activity
     */
    private void ConfigureSRButton() {
        Button SRButton = (Button) findViewById(R.id.button_GoToSR);
        SRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Quick_Route.class));
            }
        });
    }
}
