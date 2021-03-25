package com.example.eloquent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import DataBase.UserDBHelper;

import static com.example.eloquent.Login.intent2;

public class Settings extends AppCompatActivity {
    TextView DeleteAccount, Logout, emailUs, textViewUseApp;
    UserDBHelper dbHelper;
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
        DeleteAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //---------------------------------------------
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
                        data[0] = emailLogin;
                        PutData putData = new PutData("http://192.168.100.14/Users/DeleteAccount.php", "POST", field, data);

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                //   Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                             //   if(result.equalsIgnoreCase("Account Deleted")){
                                    Toast.makeText(Settings.this, result, Toast.LENGTH_SHORT).show();
                                System.out.println("result: "+ result);
                                    intent2 = new Intent(Settings.this, MainActivity.class);

                                    intent2.putExtra("LoginEmailInfo", emailLogin);
                                    startActivity(intent2);

                               // }else{
                                   // Toast.makeText(Settings.this, "Email or Password wrong", Toast.LENGTH_SHORT).show();

                              //  }
                            }
                        }
                        //End Write and Read data with URL
                    }
                });




               // System.out.println("dbHelper.DeleteAccount(emailLogin);----------------------------------"+dbHelper.DeleteAccount(emailLogin));

//                dbHelper = new UserDBHelper(Settings.this);
//                boolean result = dbHelper.DeleteAccount(emailLogin);
//                System.out.println("result----------------------------------"+result);

//                if (result == true){
//                    Intent intent =new Intent(Settings.this,MainActivity.class);
//                    startActivity(intent);
//                    Toast.makeText(Settings.this, "Delete Account is success", Toast.LENGTH_LONG).show();
//                }
//                else {
//
//                    Toast.makeText(Settings.this, "Delete Account is failed", Toast.LENGTH_LONG).show();
//                }
            }
        });


        //Logout
        Logout = findViewById(R.id.textView29);
        Logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //email us
        emailUs = findViewById(R.id.textView13);
        emailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"EloquentApplication@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, " ");
                intent.putExtra(Intent.EXTRA_TEXT, " ");
                //intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:"));
                if (intent.resolveActivity(getPackageManager())!=null){

                    startActivity(intent);

                }else{
                    Toast.makeText(Settings.this, "error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewUseApp= findViewById(R.id.textViewUseApp);
        textViewUseApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Settings.this, HowToUseApp.class);
                startActivity(intent);

            }
        });

    }//-----------
}