package com.example.eloquent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.style.TtsSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static com.example.eloquent.Login.intent2;

public class HomePage extends AppCompatActivity {
    public static Intent intentresultofseverity = new Intent();
    public static Intent intentIndex = new Intent();
    int index =0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        String Email = intent2.getStringExtra("LoginEmailInfo");
        //=====================================================================xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        //---------------------------------------performance
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "Email";
                System.out.println("=======Email===== "+Email);
                //Creating array for data
                String[] data = new String[1];
                data[0] = Email;

                PutData putData = new PutData("http://192.168.100.11/Users/getPerformanceSeverity.php", "POST", field, data);
                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        String resultofseverity = putData.getResult();
                        System.out.println("resultofseverity: **************************** " + resultofseverity);

                        String[] arrOfStr = resultofseverity.split(",");
                        for (int i =0; i< arrOfStr.length;i++){
                            index = i;

                            System.out.println("arrOfStr["+i+"]" +arrOfStr[i]);
                        }
                        //intentresultofseverity = new Intent(HomePage.this, MonitorThePerformance.class);
                        intentresultofseverity.putExtra("resultofseverity",resultofseverity);

                        //intentIndex = new Intent(HomePage.this,HomePage.class);
                        System.out.println("=======index===== "+index);
                        intentIndex.putExtra("index",index);
//                        startActivity(intentIndex);
//                        startActivity(intentresultofseverity);
                    }
                }
                //End Write and Read data with URL
            }
        });
        //---------------------------------------performance
        //===========================================================================xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

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
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_exercises:
                        Intent intentexercises =new Intent(getApplicationContext(),Exercises.class);
                        startActivity(intentexercises);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_monitor:
                        Intent intentMonitor =new Intent(getApplicationContext(),MonitorThePerformance.class);
                        startActivity(intentMonitor);
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
                //---------------------------------------performance
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        String[] field = new String[1];
                        field[0] = "Email";

                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = Email;

                        PutData putData = new PutData("http://192.168.100.11/Users/getPerformanceSeverity.php", "POST", field, data);
                        if (putData.startPut()) {
                            System.out.println("resut:1 ");
                            if (putData.onComplete()) {
                                System.out.println("resut:2 ");
                                String resultofseverity = putData.getResult();
                                System.out.println("resut: **************************** " + resultofseverity);

                                String[] arrOfStr = resultofseverity.split(",");
                                for (int i =0; i< arrOfStr.length;i++){
                                    index = i;

                                    System.out.println("arrOfStr["+i+"]" +arrOfStr[i]);
                                }
                                intentresultofseverity = new Intent(HomePage.this, MonitorThePerformance.class);
                                intentresultofseverity.putExtra("resultofseverity",resultofseverity);

                                intentIndex = new Intent(HomePage.this,HomePage.class);
                                intentIndex.putExtra("index",index);
                                startActivity(intentIndex);
                                startActivity(intentresultofseverity);
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
                //---------------------------------------performance
            }
        });

    }

}