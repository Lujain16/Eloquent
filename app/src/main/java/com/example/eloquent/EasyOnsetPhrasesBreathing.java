package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class EasyOnsetPhrasesBreathing extends AppCompatActivity {
    // start button
    TextView textView;
    // previous button
    ImageView imageView;
    private TextToSpeech textToSpeech;
    private TextView textViewBreathing;
    private ImageView imageViewSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_onset_phrases_breathing);

        //when user click on the Next button this code will move them to the EasyOnsetPhrasesInstructions page
        textView = findViewById(R.id.textViewStart);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------Stop Text To Speech
                if (textToSpeech != null) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                }
                //------------------------------End Stop Text To Speech
                Intent intent =new Intent(EasyOnsetPhrasesBreathing.this, EasyOnsetPhrasesInstructions.class);
                startActivity(intent);
            }
        });

        //when users click on the previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------Stop Text To Speech
                if (textToSpeech != null) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                }
                //------------------------------End Stop Text To Speech
                Intent intent =new Intent(EasyOnsetPhrasesBreathing.this, EasyOnsetSessions.class);
                startActivity(intent);
            }
        });

        // ------------------Start Text To Speech
        textViewBreathing = findViewById(R.id.textViewBreathing);
        imageViewSpeaker = findViewById(R.id.imageViewSpeaker);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.setSpeechRate((float) 0.9);

            }
        });
        imageViewSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null,null);

                textToSpeech.speak(textViewBreathing.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });
        // ------------------End Text To Speech
    }
}