package com.example.eloquent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

import static com.example.eloquent.HomePage.intentIndex;
import static com.example.eloquent.HomePage.intentresultofseverity;
import static com.example.eloquent.Login.intent2;

public class MonitorThePerformance extends AppCompatActivity {
    int SeverityLength = intentIndex.getIntExtra("index",1);
    public  float [] arrSeverityNumber = new float[SeverityLength];
    public  String resultofseverity =intentresultofseverity.getStringExtra("resultofseverity");
    //LineGraph lineGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_the_performance);
        System.out.println("++++++++++SeverityLength +++++++++++= "+SeverityLength);
        System.out.println("++++++++++resultofseverity +++++++++++= "+resultofseverity);

        String[] arrOfStr = resultofseverity.split(",");
        for (int i =1, j=0; i< arrOfStr.length;i++,j++){
            if (arrOfStr[i].equalsIgnoreCase("Not stutter: Your speech is eloquent")) {
                //severity_num = 0.0F;
                arrSeverityNumber[j] =0.0F;
            } else if (arrOfStr[i].equalsIgnoreCase("Low")) {
                //severity_num = 200000.0F;
                arrSeverityNumber[j] = 200000.0F;
            } else if (arrOfStr[i].equalsIgnoreCase("Moderate")) {
                //severity_num = 400000.0F;
                arrSeverityNumber[j] =400000.0F;
            } else if (arrOfStr[i].equalsIgnoreCase("High")) {
                //severity_num = 600000.0F;
                arrSeverityNumber[j] =600000.0F;
            }
            System.out.println("++++++++++arrSeverityNumber["+j+"] +++++++++++= "+arrSeverityNumber[j]);
        }

        ChartEntity firstChartEntity = new ChartEntity(-1, this.arrSeverityNumber);
        LineGraph lineGraph = (LineGraph) findViewById(R.id.lineChart);
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
    }
}
