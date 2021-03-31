package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static com.example.eloquent.Login.intent2;
import static com.example.eloquent.ScenarioAtLibrary.intentAtLibraryResult;
import static com.example.eloquent.ScenariosAtRestaurant.intentAtRestaurantResult;
import static com.example.eloquent.ScenariosAtSchool.intentAtSchoolResult;

public class StutteringSeverityAtLibrary extends AppCompatActivity {
    ImageView imageView;
    TextView textViewResult;
    //--------------
    String Email = intent2.getStringExtra("LoginEmailInfo");
    String severity;
    String UpdatedStutter="";
    String result=" ";
    //-------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuttering_severity_at_library);
        // Show the result from python
        textViewResult = (TextView) findViewById(R.id.textViewSetStuttringResult);
        textViewResult.setText(intentAtLibraryResult.getStringExtra("KeyResulAtLibrary"));
        //-----------
        //-------------------------------------------------------------------performance
        severity = intentAtLibraryResult.getStringExtra("KeyResulAtLibrary");
        //   System.out.println("id===================="+id);
        System.out.println("Email===================="+Email);
        System.out.println("severity===================="+severity);
        //   intentSeverityCount.putExtra("counter",id);
//        startActivity(intentSeverityCount);
        //---------------------------------------------

        //=========************************************************************* Get Severity of the user

        //=========************************************************************* Get Severity of the user

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


                        // String[] arrOfStr = result.split(",", 1);

                        if (resultofseverity.equalsIgnoreCase("New severity")){

                            result = " ";

                        }else{

                            result = resultofseverity +"";
                        }
                        //=========*************************************************************
                        UpdatedStutter = result.concat(","+severity);
                        System.out.println(UpdatedStutter+"()()()()()()()()()()()()");
                        //*************************************************************

                    }
                }
                //End Write and Read data with URL
            }
        });

//((((*************************************************************** set performance



        //close button on StutteringSeverity page
        //when user click on close button  this code will move them to the Home page
        imageView = findViewById(R.id.imageView3close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StutteringSeverityAtLibrary.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}