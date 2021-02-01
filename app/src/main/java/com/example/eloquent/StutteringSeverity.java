package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StutteringSeverity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuttering_severity);

        //close button on StutteringSeverity page
        //when user click on close button  this code will move them to the Home page
        imageView = findViewById(R.id.imageView3close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StutteringSeverity.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}