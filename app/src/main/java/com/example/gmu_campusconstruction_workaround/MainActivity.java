package com.example.gmu_campusconstruction_workaround;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConfigureQRButton();
        ConfigureSRButton();
        ConfigureFLButton();



    }



    /**
     * configure the button that will transition
     * to the Quick Route activity
     */
    private void ConfigureQRButton() {
        ImageButton QRButton = findViewById(R.id.button_GoToQR);
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
        ImageButton SRButton = findViewById(R.id.button_GoToSR);
        SRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Schedule_Route.class));
            }
        });
    }

    /**
     * configure the button that will transition
     * to the Favorite List activity
     */
    private void ConfigureFLButton() {
        ImageButton QRButton = findViewById(R.id.button_GoToFL);
        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Favorite_List.class));
            }
        });
    }


}
