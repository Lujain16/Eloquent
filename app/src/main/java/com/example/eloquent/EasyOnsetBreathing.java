package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EasyOnsetBreathing extends AppCompatActivity {
    // start button
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_onset_breathing);


        //cardViewWord button
        //when user click on cardViewWord button  this code will move them to the EasyOnsetBreathing page
        textView = findViewById(R.id.textViewStart);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EasyOnsetBreathing.this, EasyOnsetInstructions.class);
                startActivity(intent);
            }
        });
    }
}