package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.eloquent.EasyOnsetWord.intentWordResult;
import static com.example.eloquent.ScenariosAtSchool.intentAtSchoolResult;

public class StutteringSeverityAtSchool extends AppCompatActivity {
    ImageView imageView;
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuttering_severity_at_school);
        // Show the result from  python
        textViewResult = (TextView) findViewById(R.id.textViewSetStuttringResult);
        textViewResult.setText(intentAtSchoolResult.getStringExtra("KeyResulAtSchool"));
        //-----------



        //close button on StutteringSeverity page
        //when user click on close button  this code will move them to the Home page
        imageView = findViewById(R.id.imageView3close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StutteringSeverityAtSchool.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}