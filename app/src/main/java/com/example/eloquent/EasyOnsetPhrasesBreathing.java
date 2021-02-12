package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EasyOnsetPhrasesBreathing extends AppCompatActivity {
    // start button
    TextView textView;

    // previous button
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_onset_phrases_breathing);

        //cardViewWord button
        //when user click on cardViewWord button  this code will move them to the EasyOnsetBreathing page
        textView = findViewById(R.id.textViewStart);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EasyOnsetPhrasesBreathing.this, EasyOnsetPhrasesInstructions.class);
                startActivity(intent);
            }
        });

        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EasyOnsetPhrasesBreathing.this, EasyOnsetSessions.class);
                startActivity(intent);
            }
        });
    }
}