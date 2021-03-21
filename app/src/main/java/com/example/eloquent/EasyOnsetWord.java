package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import Model.Listitem;

public class EasyOnsetWord extends AppCompatActivity {
    private ImageButton Record,Stop, Play;
    private MediaRecorder myAudioRecorder;
    MediaPlayer mediaPlayer ;
    private String outputFile = null;
    Button button;
    ImageView imageView; // previous button
    TextView textViewWord ;
    Random rand = new Random();

    private TextToSpeech textToSpeech;
    private TextView textViewEasyOnset;
    private ImageView imageViewSpeaker;
    String category ="word";
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_onset_word);
        Record = (ImageButton) findViewById(R.id.imageButton_mic);
        Stop = (ImageButton) findViewById(R.id.imageButton_stop);
        Play = (ImageButton) findViewById(R.id.imageButton_play);

        Record.setEnabled(true);
        Stop.setEnabled(false);
        Play.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Word_Recording.mp3";

        //-------------------Word list generator
        //===================================================
        textViewWord = (TextView)findViewById(R.id.textViewEasyOnsetWord);
        //---------------------------------------------
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "category";

                //Creating array for data
                String[] data = new String[1];
                data[0] = category;


                PutData putData = new PutData("http://192.168.100.22/Users/EasyOnSetExercise.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        result = putData.getResult();
                        System.out.println("resut: " + result);
                        textViewWord.setText(result);

                    }
                }
                //End Write and Read data with URL
            }
        });
        //===================================================

//        textViewWord = (TextView)findViewById(R.id.textViewEasyOnsetWord);
//        String[] Word_List = {"After",
//                " Also",
//                "Always",
//                "Enough",
//                "Easy",
//                "Email",
//                "Either",
//                "Exit",
//                "Expert",
//                "Inside",
//                "Inspect",
//                "Include",
//                "Island",
//                "Oven",
//                "Open",
//                "Offer",
//                "Outdoor",
//                "Under",
//                "Update",
//                "Unless",
//                "hole",
//                "heart",
//                "whom",
//                "home",
//                "harm",
//                "horse",
//                "hard"
//        };
//
//        int Word_List_Length = Word_List.length-1 ;
//        int random_Number_Word = rand.nextInt(Word_List_Length-0);
//        textViewWord.setText(Word_List[random_Number_Word]);
//

        //-------------------End Word list generator

        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    myAudioRecorder.setAudioChannels(1);
                    myAudioRecorder.setOutputFile(outputFile);
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();

                } catch (IllegalStateException ise){
                    //..
                }catch (IOException ioe){
                    //...
                }
                Record.setEnabled(false);
                Stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started...",Toast.LENGTH_LONG).show();
            }
        });

        // Stop Recording
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    myAudioRecorder.stop();
                    myAudioRecorder.reset();
                    myAudioRecorder = null;
                }catch (Exception e){
                    //
                }


                Record.setEnabled(true);
                Stop.setEnabled(false);
                Play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();
            }
        });


        // Play Recorded Audio
        Play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
            }
        });

        button = findViewById(R.id.buttonDone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------Stop Playing the recording
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                //--------------------------------End Stop Playing the recording
                Intent intent =new Intent(EasyOnsetWord.this, StutteringSeverity.class);// change StutteringSeverity.class ****
                startActivity(intent);
            }
        });

        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------Stop Playing the recording
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                //--------------------------------End Stop Playing the recording
                Intent intent =new Intent(EasyOnsetWord.this, EasyOnsetInstructions.class);
                startActivity(intent);
            }
        });

        // ------------------Start Text To Speech
       // textViewEasyOnset = findViewById(R.id.textViewEasyOnsetWord);
        textViewEasyOnset = textViewWord;
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

                textToSpeech.speak(textViewEasyOnset.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });

        // ------------------End Text To Speech


    }


    }
