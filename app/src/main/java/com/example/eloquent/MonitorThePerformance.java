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

import static com.example.eloquent.Login.intent2;

public class MonitorThePerformance extends AppCompatActivity {

    String Email = intent2.getStringExtra("LoginEmailInfo");
    String[] arrId;
    String [] arrSeverity;

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

        //---------------------------------------performance
        //---------------------------------------------
        //=========************************************************************* Get Severity of the user

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "Email";

                //Creating array for data
                String[] data = new String[1];
                data[0] = Email;

                PutData putData = new PutData("http://192.168.100.14/Users/getPerformanceSeverity.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        String resultofseverity = putData.getResult();
                        System.out.println("resut: **************************** " + resultofseverity);

                        String[] arrOfStr = resultofseverity.split(",");
                        for (int i =0; i< arrOfStr.length;i++){

                            System.out.println("arrOfStr["+i+"]" +arrOfStr[i]);
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });
        //-------------------------------------------------------



    }

}