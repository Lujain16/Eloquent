package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiagnosticTest extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private ImageButton Record,Stop, Play;
    private MediaRecorder myAudioRecorder;
    MediaPlayer mediaPlayer ;
    private String outputFile = null;

    Button Donebutton;
    private TextToSpeech textToSpeech;
    private TextView textViewDT;
    private ImageView imageViewSpeaker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_test);

        Record = (ImageButton) findViewById(R.id.imageButton_mic);
        Stop = (ImageButton) findViewById(R.id.imageButton_stop);
        Play = (ImageButton) findViewById(R.id.imageButton_play);

        Record.setEnabled(true);
        Stop.setEnabled(false);
        Play.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/DT_Recording.mp3";

        // If the user grants all permission the user can start using the program
        if(checkAndPermissionsRequest()) {
            //permissions  granted. now user able to Record , Stop And play

            // Start Recording
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
                        //myAdioRecorder.release();
                        myAudioRecorder = null;
                    }catch (Exception e){}


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


        }

        // ------------------Start Text To Speech
        textViewDT = findViewById(R.id.textViewDT);
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

                textToSpeech.speak(textViewDT.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });

        // ------------------End Text To Speech

        //Done button on diagnostic test page
        //when user click on Done button  this code will move them to the stuttering severity page
        Donebutton = findViewById(R.id.buttonDone);
        Donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DiagnosticTest.this, StutteringSeverity.class);
                startActivity(intent);
            }
        });
    }

    // Method checkAndPermissionsRequest Check user permissions
    private  boolean checkAndPermissionsRequest() {
        int PermissionRecordAudio = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        int StoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (PermissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
            // Toast.makeText(getApplicationContext(), "PermissionRecordAudio ...",Toast.LENGTH_LONG).show();
        }
        if (StoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // Toast.makeText(getApplicationContext(), "StoragePermission ...",Toast.LENGTH_LONG).show();
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            // Toast.makeText(getApplicationContext(), "!listPermissionsNeeded ...",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;


    }
    //---------------------------------------------------------------
}