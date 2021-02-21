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
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import DataBase.UserDBHelper;

public class Login extends AppCompatActivity {
    public static Intent intent2;
    //textview
    EditText EmailText, passwordText;
    //database
    UserDBHelper dbHelper;
    //prev button
    ImageView imageView;
    //login button
    Button LoginButton;

    String Email, Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //database
        EmailText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordText = (EditText) findViewById(R.id.editTextTextPassword);
        LoginButton = (Button) findViewById(R.id.buttonLogin2);
        dbHelper = new UserDBHelper(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = EmailText.getText().toString();
                Password = passwordText.getText().toString();

                if (Email.equals("")|| Password.equals("")){
                    Toast.makeText(Login.this, "All fields must be filled and  passwords must be correct.", Toast.LENGTH_SHORT).show();

                }
                else{

                    //---------------------------------------------
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

                            PutData putData = new PutData("http://192.168.100.4/Users/CreateAccount.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();

                                    intent2 = new Intent(Login.this, HomePage.class);
                                    startActivity(intent2);

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                    //---------------------------------------------
                }}
        });

    }
}

//    if (Email.equals("")||pass.equals("")) {
//            Toast.makeText(Login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
//            } else {
//            boolean checkEmail = dbHelper.checkEmail(Email);
//            if (checkEmail==true) {
//            Toast.makeText(Login.this, "email exist", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(Login.this, HomePage.class);
//        startActivity(intent);
//        }
//        boolean checkEmailAndPass = dbHelper.checkEmailAndPassword(Email, pass);
//        if (checkEmailAndPass==true){
//        Toast.makeText(Login.this, "login successfull", Toast.LENGTH_SHORT).show();
//
//        intent2 = new Intent(getApplicationContext(),HomePage.class);
//        intent2.putExtra("LoginEmailInfo",Email);
//        startActivity(intent2);
//        }else{
//        Toast.makeText(Login.this, "invaled login", Toast.LENGTH_SHORT).show();
//
//        }
//        }