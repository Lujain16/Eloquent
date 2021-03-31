package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ForgetPassword1 extends AppCompatActivity {

    //login button
    Button SendButton;
    EditText Emailtext;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);

        SendButton =findViewById(R.id.button);
        Emailtext =findViewById(R.id.editTextTextEmailAddress2);
        Email= Emailtext.getText().toString();

        SendButton.setOnClickListener(new View.OnClickListener() {
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
                        data[0] = Email;


                        PutData putData = new PutData("http://192.168.100.14/Users/forgot.php", "POST", field, data);


                        //End Write and Read data with URL
                    }
                });
                //---------------------------------------------

            }
        });
    }
}