package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EasyOnsetInstructions extends AppCompatActivity {
    // start button
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_onset_instructions);

        //cardViewWord button
        //when user click on cardViewWord button  this code will move them to the EasyOnsetBreathing page
        textView = findViewById(R.id.textViewStart);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EasyOnsetInstructions.this, EasyOnsetWord.class);
                startActivity(intent);
            }
        });
    }
}