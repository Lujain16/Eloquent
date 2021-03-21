package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.eloquent.DiagnosticTest.intentResult;
import static com.example.eloquent.EasyOnsetPhrases.intentPhrasesResult;

public class StutteringSeverityPhrases extends AppCompatActivity {

    ImageView imageView;
    TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuttering_severity_phrases);

        // Show the result from EasyOnsetPhrases python
        textViewResult = (TextView) findViewById(R.id.textViewSetStuttringResult);
        textViewResult.setText(intentPhrasesResult.getStringExtra("KeyResultPhrases"));
        //-----------



        //close button on StutteringSeverity page
        //when user click on close button  this code will move them to the Home page
        imageView = findViewById(R.id.imageView3close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(StutteringSeverityPhrases.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}