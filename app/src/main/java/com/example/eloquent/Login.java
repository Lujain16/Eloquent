package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Login extends AppCompatActivity {
    //prev button
    ImageView imageView;
    //login button
    Button button;
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

        button = findViewById(R.id.buttonLogin2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this, HomePage.class);
                startActivity(intent);
            }
        });

    }
}