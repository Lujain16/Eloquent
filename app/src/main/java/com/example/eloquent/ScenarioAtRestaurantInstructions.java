package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ScenarioAtRestaurantInstructions extends AppCompatActivity {
    // start button
    TextView textViewStart;

    //previous button
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_at_restaurant_instructions);
        //start button
        //when user click on start button  this code will move them to the Scenarios page
        textViewStart = findViewById(R.id.textViewStart);
        textViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenarioAtRestaurantInstructions.this, ScenariosAtRestaurant.class);
                startActivity(intent);
            }
        });

        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenarioAtRestaurantInstructions.this, ScenariosSessions.class);
                startActivity(intent);
            }
        });


    }
}