package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.example.eloquent.Login.intent2;

public class ForgetPassword1 extends AppCompatActivity {
    public static Intent intentCode;
    public static Intent intentEmail;
    Button SendButton;
    EditText Emailtext;
    String Email;
    String Code;
    //prev button
    ImageView imageView;
    TextView wariningMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);

        SendButton =findViewById(R.id.button);
        Emailtext = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        wariningMsg =findViewById(R.id.textView13);
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = Emailtext.getText().toString();
                //Generating random code number
                final int random = new Random().nextInt((9999 - 1000) + 1) + 1000;
                Code = String.valueOf(random);
                //---------------------------------------------
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "Email";
                        field[1] = "Code";

                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = Email;
                        data[1] = Code;
                        if(Email.equals("")) {
                            wariningMsg.setText("Email field is required.");
                        }

                        else {
                            PutData putData = new PutData("http://192.168.100.11/Users/ForgetPassword.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    System.out.println("result-----------= " + result.substring(0,20));
                                    if ("Email does not exist".equalsIgnoreCase(result.substring(0,20))) {
                                        wariningMsg.setText("The email is not registered in the system.");
                                    } else {
                                        intentCode = new Intent(ForgetPassword1.this, ForgetPassword2.class);
                                        intentCode.putExtra("keyCode", Code);
                                        intentEmail = new Intent(ForgetPassword1.this, ForgetPassword1.class);
                                        intentEmail.putExtra("EmailForget", Email);
                                        startActivity(intentEmail);
                                        startActivity(intentCode);
                                    }

                                }

                            }

                        }//new else
                        //End Write and Read data with URL
                    }
                });
                //---------------------------------------------
//                intentCode = new Intent(ForgetPassword1.this, ForgetPassword2.class);
//                intentCode.putExtra("keyCode",Code);
//                intentEmail = new Intent(ForgetPassword1.this, ForgetPassword1.class);
//                intentEmail.putExtra("EmailForget",Email);
//                startActivity(intentEmail);
//                startActivity(intentCode);
            }
        });

        //when users click on previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ForgetPassword1.this, Login.class);
                startActivity(intent);
            }
        });
    }
}