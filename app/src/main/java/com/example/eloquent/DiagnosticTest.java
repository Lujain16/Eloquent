package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiagnosticTest extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_test);


        //Done button on diagnostic test page
        //when user click on Done button  this code will move them to the stuttering severity page
        button = findViewById(R.id.buttonDone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DiagnosticTest.this, StutteringSeverity.class);
                startActivity(intent);
            }
        });
    }
}