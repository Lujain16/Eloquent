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

import static com.example.eloquent.EasyOnsetPhrases.intentPhrasesResult;
import static com.example.eloquent.EasyOnsetWord.intentWordResult;
import static com.example.eloquent.Login.intent2;



public class StutteringSeverityPhrases extends AppCompatActivity {
    //public static Intent intentSeverityCount;
    //--------------
//    int SeverityCount =Integer. parseInt(intentSeverityCount.getStringExtra("counter"));
//    String intent = intentSeverityCount.getStringExtra("counter");
//    String id = String.valueOf(SeverityCount);
    //--------------
    String Email = intent2.getStringExtra("LoginEmailInfo");
    String severity;
    String UpdatedStutter="";
    String result=" ";
    //-------------

    ImageView imageView;
    TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuttering_severity_phrases);

        // Show the result from python
        textViewResult = (TextView) findViewById(R.id.textViewSetStuttringResult);
        textViewResult.setText(intentPhrasesResult.getStringExtra("KeyResultPhrases"));

        //-----------
        //-------------------------------------------------------------------performance
        //=========************************************************************* Get Severity of the user

        //=========************************************************************* Get Severity of the user

        //=========************************************************************* Get Severity of the user
        severity = intentPhrasesResult.getStringExtra("KeyResultPhrases");
        //   System.out.println("id===================="+id);
        System.out.println("Email===================="+Email);
        System.out.println("severity===================="+severity);

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

                PutData putData = new PutData("http://192.168.100.11/Users/getPerformanceSeverity.php", "POST", field, data);

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

        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                //  field[0] = "id";
                field[0] = "Email";
                field[1] = "severity";

                //Creating array for data
                String[] data = new String[2];
                //  data[0] = id;
                data[0] = Email;
                data[1] = UpdatedStutter;

                PutData putData = new PutData("http://192.168.100.11/Users/UpdateSeverity.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        result = putData.getResult();
                        System.out.println("resut:--------------- " + result);


                        //   String[] arrOfStr = result.split(",", 2);
//                        arrId = arrOfStr[1];
//                        arrSeverity= arrOfStr[2];



                    }
                }
                //End Write and Read data with URL
            }
        });
        //-------------------------------------------------------performance

        //=========************************************************************* END

        //=========************************************************************* END

        //=========************************************************************* END
        //-------------------------------------------------------performance



        //close button on StutteringSeverity page
        //when user click on close button  this code will move them to the Home page
        imageView = findViewById(R.id.imageView3close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(StutteringSeverityPhrases.this, HomePage.class);
//                startActivity(intent);
                Intent intent = new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent);
            }
        });
    }
}