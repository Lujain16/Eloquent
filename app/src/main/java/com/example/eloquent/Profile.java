package com.example.eloquent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;

import DataBase.UserDBHelper;
import Model.UserInformation;

import static com.example.eloquent.Login.intent2;


public class Profile extends AppCompatActivity {
    TextView textViewInfo, username, emailUs;
    ImageView Picture;
    UserDBHelper dbHelper;

    String UserEmailLogin = intent2.getStringExtra("LoginEmailInfo");

    //-------------------myinfo
    EditText Fname, Lname, Email, BD;
    Button save;
    TextView textView;
    String EditUserFName;
    String EditUserLName;
    String UserEmailForIntent= "";
    //------------------myinfo


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //bottom navigation par
        BottomNavigationView bottomNavigationView;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // set Profile selected
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

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


        //-------------------------------------------------------My information
        String emailLogin = intent2.getStringExtra("LoginEmailInfo");
//we do not need this in mySQL
        Fname = findViewById(R.id.PersonNameFN);
        Lname = findViewById(R.id.PersonNameLN);
        Email = findViewById(R.id.PersonEmail);
        BD = findViewById(R.id.editTextBD);
        save = findViewById(R.id.Savebutton);
//        dbHelper = new UserDBHelper(MyInfo.this);
        textView=findViewById(R.id.textView14);

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


                PutData putData = new PutData("http://192.168.100.14/Users/GetUserInfo.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        String result = putData.getResult();
                        System.out.println("resut: " + result);


                        String[] arrOfStr = result.split(",", 4);
                        Fname.setText(arrOfStr[0]);
                        Lname.setText(arrOfStr[1]);
                        Email.setText(arrOfStr[2]);
                        BD.setText(arrOfStr[3]);

                    }
                }
                //End Write and Read data with URL
            }
        });
        //-------------------------------------------------------
        //user name
        username = findViewById(R.id.textView14);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserFName = Fname.getText().toString();
                EditUserLName = Lname.getText().toString();

                //---------------------------------------------
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[3];
                        field[0] = "Email";
                        field[1] = "FirstName";
                        field[2] = "LastName";

                        //Creating array for data
                        String[] data = new String[3];
                        data[0] = emailLogin;
                        data[1] = EditUserFName;
                        data[2] = EditUserLName;

                        PutData putData = new PutData("http://192.168.100.11/Users/UpdateName.php", "POST", field, data);

                        if (putData.startPut()) {

                            if (putData.onComplete()) {
                                username.setText(EditUserFName+" "+EditUserLName);
                                Toast.makeText(Profile.this, "update successful", Toast.LENGTH_LONG).show();

//                                String result = putData.getResult();
//                                intent2 =new Intent(getApplicationContext(),MainActivity.class);
//                                intent2.putExtra("LoginEmailInfo", emailLogin);
//                                startActivity(intent2);
                            }else{
                                Toast.makeText(Profile.this, "update is failed", Toast.LENGTH_LONG).show();
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
                //-------------------------------------------------------

            }
        });



        //-------------------------------------------------------My information

        //------------------------------------------------------user name
        //username = findViewById(R.id.textView14);



         handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "Email";

                //Creating array for data
                String[] data = new String[1];
                data[0] = UserEmailLogin;


                PutData putData = new PutData("http://192.168.100.11/Users/GetUserInfo.php", "POST", field, data);

                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        String[] arrOfStr = result.split(",", 4);

                        username.setText(arrOfStr[0]+" "+arrOfStr[1]);


                    }
                }

            }
        });
        //------------------------------------------------------user name


        //My information
//        textViewInfo = findViewById(R.id.textViewMyinformation);
//        textViewInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Profile.this,MyInfo.class);
//                startActivity(intent);
//            }
//        });


        //add pic
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//        Picture = findViewById(R.id.imageViewPic);
    }
}