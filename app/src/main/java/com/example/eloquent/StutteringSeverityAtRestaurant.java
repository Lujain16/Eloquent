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
import static com.example.eloquent.ScenariosAtRestaurant.intentAtRestaurantResult;
import static com.example.eloquent.ScenariosAtSchool.intentAtSchoolResult;

public class StutteringSeverityAtRestaurant extends AppCompatActivity {
    ImageView imageView;
    TextView textViewResult;
    String Email = intent2.getStringExtra("LoginEmailInfo");
    String severity;
    String UpdatedStutter="";
    String result=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuttering_severity_at_restaurant);
        // Show the result from  python
        textViewResult = (TextView) findViewById(R.id.textViewSetStuttringResult);
        textViewResult.setText(intentAtRestaurantResult.getStringExtra("KeyResulAtRestaurant"));
        severity = intentAtRestaurantResult.getStringExtra("KeyResulAtRestaurant");

        //Retrieving the results of stuttering severity from the database
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

                        if (resultofseverity.equalsIgnoreCase("New severity")){

                            result = " ";

                        }else{

                            result = resultofseverity +"";
                        }
                        UpdatedStutter = result.concat(","+severity);
                    }
                }
                //End Write and Read data with URL
            }
        });

        //Updating the results of stuttering severity in the database
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

                    }
                }
                //End Write and Read data with URL
            }
        });

        //close button on StutteringSeverity page
        //when user click on close button  this code will move them to the Home page
        imageView = findViewById(R.id.imageView3close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StutteringSeverityAtRestaurant.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}