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
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static com.example.eloquent.Login.intent2;

public class EasyOnsetSessions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_onset_sessions);
        // exercises sessions button
        CardView cardViewWord , cardViewPhrases;
        // previous button
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

        //when users click on the previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EasyOnsetSessions.this, Exercises.class);
                startActivity(intent);
            }
        });

        //cardViewWord button
        //when user click on cardViewWord button  this code will move them to the EasyOnsetBreathing page
        cardViewWord = findViewById(R.id.cardeasyonsetWord);
        cardViewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EasyOnsetSessions.this, EasyOnsetBreathing.class);
                startActivity(intent);
            }
        });

        //cardViewPhrases button
        //when user click on cardViewPhrases button  this code will move them to the EasyOnsetPhrasesBreathing page
        cardViewPhrases = findViewById(R.id.cardeasyonsetPhrases);
        cardViewPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EasyOnsetSessions.this, EasyOnsetPhrasesBreathing.class);
                startActivity(intent);
            }
        });
    }
}