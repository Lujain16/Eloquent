package com.example.eloquent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScenariosSessions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenarios_sessions);
        // exercises sessions button
        CardView cardViewAtSchool , cardViewAtRestaurant , cardViewAtLibrary;

        //previous button
        ImageView imageView;

        //bottom navigation par
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intenthome =new Intent(getApplicationContext(),HomePage.class);
                        startActivity(intenthome);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_exercises:
                        Intent intentexercises =new Intent(getApplicationContext(),Exercises.class);
                        startActivity(intentexercises);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_monitor:
                        Intent intentmonitor =new Intent(getApplicationContext(),MonitorThePerformance.class);
                        startActivity(intentmonitor);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_settings:
                        Intent intentSettings =new Intent(getApplicationContext(),Settings.class);
                        startActivity(intentSettings);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_profile:
                        Intent intentProfile =new Intent(getApplicationContext(),Profile.class);
                        startActivity(intentProfile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //when users click on the previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosSessions.this, Exercises.class);
                startActivity(intent);
            }
        });

        //cardViewAtSchool button
        //when user click on cardViewAtSchool button  this code will move them to the ScenarioAtSchoolInstructions page
        cardViewAtSchool = findViewById(R.id.cardAtSchool);
        cardViewAtSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosSessions.this, ScenarioAtSchoolInstructions.class);
                startActivity(intent);
            }
        });

        //cardViewAtRestaurant button
        //when user click on cardViewAtRestaurant button  this code will move them to the ScenarioAtRestaurantInstructions page
        cardViewAtRestaurant = findViewById(R.id.cardAtRestaurant);
        cardViewAtRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosSessions.this, ScenarioAtRestaurantInstructions.class);
                startActivity(intent);
            }
        });

        //cardViewAtLibrary button
        //when user click on cardViewAtLibrary button  this code will move them to the ScenarioAtLibraryInstructions page
        cardViewAtLibrary = findViewById(R.id.cardAtLibrary);
        cardViewAtLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosSessions.this, ScenarioAtLibraryInstructions.class);
                startActivity(intent);
            }
        });
    }
}