package com.example.eloquent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //exercises button
        CardView cardViewExercises, cardViewMonitor;


        //bottom navigation par
        BottomNavigationView bottomNavigationView;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set home selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intenthome =new Intent(getApplicationContext(),HomePage.class);
                        startActivity(intenthome);
                        //startActivities(new Intent(getApplicationContext(),Exercises.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_exercises:
                        Intent intentexercises =new Intent(getApplicationContext(),Exercises.class);
                        startActivity(intentexercises);
                        //startActivities(new Intent(getApplicationContext(),Exercises.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_monitor:
                        Intent intentmonitor =new Intent(getApplicationContext(),MonitorThePerformance.class);
                        startActivity(intentmonitor);
                        //startActivities(new Intent(getApplicationContext(),Exercises.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_settings:
                        Intent intentSettings =new Intent(getApplicationContext(),Settings.class);
                        startActivity(intentSettings);
                        //startActivities(new Intent(getApplicationContext(),Exercises.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_profile:
                        Intent intentProfile =new Intent(getApplicationContext(),Profile.class);
                        startActivity(intentProfile);
                        //startActivities(new Intent(getApplicationContext(),Exercises.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        //exercises button
        //when user click on exercises button  this code will move them to the exercises page
        cardViewExercises = findViewById(R.id.cardExercises);
        cardViewExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomePage.this, Exercises.class);
                startActivity(intent);
            }
        });

        //monitor button
        //when user click on monitor button  this code will move them to the MonitorThePerformance page
        cardViewMonitor = findViewById(R.id.cardMonitor);
        cardViewMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomePage.this, MonitorThePerformance.class);
                startActivity(intent);
            }
        });


    }



}