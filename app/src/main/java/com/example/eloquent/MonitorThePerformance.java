package com.example.eloquent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MonitorThePerformance extends AppCompatActivity {

    LineGraph lineGraph;
    //    private final float[] graph1 = new float[]{0.0F, 400000.0F, 200000.0F, 600000.0F, 0.0F, 400000.0F, 200000.0F, 600000.0F};
    private final float[] graph1 = new float[]{600000.0F, 600000.0F, 400000.0F, 400000.0F, 200000.0F, 200000.0F};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_the_performance);

        ChartEntity firstChartEntity = new ChartEntity(-1, this.graph1);
        lineGraph = (LineGraph) findViewById(R.id.lineChart);
        ArrayList<ChartEntity> list = new ArrayList();
        list.add(firstChartEntity);
        lineGraph.setList(list);


        //bottom navigation par
        BottomNavigationView bottomNavigationView;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set monitor selected
        bottomNavigationView.setSelectedItemId(R.id.nav_monitor);

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


    }

}