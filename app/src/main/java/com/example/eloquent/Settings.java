package com.example.eloquent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import DataBase.UserDBHelper;

import static com.example.eloquent.Login.intent2;

public class Settings extends AppCompatActivity {
    TextView DeleteAccount;
    UserDBHelper dbHelper;
    //error////////////////////////////////////////
    String emailLogin = intent2.getStringExtra("LoginEmailInfo");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //bottom navigation par
        BottomNavigationView bottomNavigationView;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set Settings selected
        bottomNavigationView.setSelectedItemId(R.id.nav_settings);

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

        //Delete Account
        System.out.println("emailLogin----------------------------------"+emailLogin);
        DeleteAccount = findViewById(R.id.textViewDeletAccount);
        DeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // System.out.println("dbHelper.DeleteAccount(emailLogin);----------------------------------"+dbHelper.DeleteAccount(emailLogin));

                dbHelper = new UserDBHelper(Settings.this);
                boolean result = dbHelper.DeleteAccount(emailLogin);
                System.out.println("result----------------------------------"+result);


                if (result == true){
                    Intent intent =new Intent(Settings.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Settings.this, "Delete Account is success", Toast.LENGTH_LONG).show();
                }
                else {

                    Toast.makeText(Settings.this, "Delete Account is failed", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}