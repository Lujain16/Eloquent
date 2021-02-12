package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import DataBase.UserDBHelper;

public class Login extends AppCompatActivity {
    //textview
    EditText EmailText, passwordText;
    //database
    UserDBHelper dbHelper;
    //prev button
    ImageView imageView;
    //login button
    Button LoginButton;
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

                String Email = EmailText.getText().toString();
                String pass = passwordText.getText().toString();

                if (Email.equals("")||pass.equals("")) {
                    Toast.makeText(Login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkEmail = dbHelper.checkEmail(Email);
                    if (checkEmail==true) {
                        Toast.makeText(Login.this, "email exist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, HomePage.class);
                        startActivity(intent);
                    }
                    Boolean checkEmailAndPass = dbHelper.checkEmailAndPassword(Email, pass);
                    if (checkEmailAndPass==true){
                        Toast.makeText(Login.this, "login successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomePage.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "invaled login", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }
}