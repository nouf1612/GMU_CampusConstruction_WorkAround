package com.example.gmu_campusconstruction_workaround;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Favorite_List extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        ConfigureMAButton();

    }



     /**
     * configure the button that will transition
     * back to Main activity
     **/
    private void ConfigureMAButton() {
        Button MAButton = (Button) findViewById(R.id.button_GoToMA);
        MAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
