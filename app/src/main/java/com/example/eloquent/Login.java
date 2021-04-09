package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import DataBase.UserDBHelper;

public class Login extends AppCompatActivity {
    public static Intent intent2 = new Intent();
    EditText EmailText, passwordText;
    //database
    UserDBHelper dbHelper;
    //prev button
    ImageView imageView;
    //login button
    Button LoginButton;
    TextView ForgetPass;
    String Email, Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //when users click on the previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Forget password
        ForgetPass = findViewById(R.id.textView4);
        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this, ForgetPassword1.class);
                startActivity(intent);
            }
        });

        //EditText
        EmailText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordText = (EditText) findViewById(R.id.editTextTextPassword);
        LoginButton = (Button) findViewById(R.id.buttonLogin2);
        ForgetPass = (TextView) findViewById(R.id.textView4);
        dbHelper = new UserDBHelper(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data input from users
                Email = EmailText.getText().toString();
                Password = passwordText.getText().toString();
                if (Email.equals("")|| Password.equals("")){
                    Toast.makeText(Login.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
                else{
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "Email";
                            field[1] = "Password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = Email;
                            data[1] = Password;

                            PutData putData = new PutData("http://192.168.100.11/Users/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equalsIgnoreCase("Login Success")){
                                        Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                        //intent2 = new Intent(Login.this, HomePage.class);
                                        intent2 = new Intent(getApplicationContext(), HomePage.class);
                                        intent2.putExtra("LoginEmailInfo",Email);
                                        startActivity(intent2);
                                    }else{
                                        Toast.makeText(Login.this, "Email or Password wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                            }
                    });
                }}
        });
    }
}
