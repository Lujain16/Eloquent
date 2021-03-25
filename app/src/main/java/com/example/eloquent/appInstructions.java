package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class appInstructions extends AppCompatActivity {
    ImageView imageView; // X icon
    Button clkPlayVideo ;
    VideoView videoView ;
    MediaController mediaC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_instructions);

        clkPlayVideo =(Button)findViewById(R.id.buttonVedio);
        videoView =(VideoView)findViewById(R.id.videoView);
        mediaC =new MediaController(appInstructions.this);


        clkPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "android.resource://com.example.eloquent/"+R.raw.eloquent_video1;
                Uri u = Uri.parse(path);
                videoView.setVideoURI(u);
                videoView.setScaleX(2);
                videoView.setScaleY(2);

                mediaC.setAnchorView(videoView);
                videoView.setMediaController(mediaC);

                videoView.start();

            }
        });


        //close button on StutteringSeverity page
        //when user click on close button  this code will move them to the Home page
        imageView = findViewById(R.id.imageView3close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(appInstructions.this, DiagnosticTest.class);
                startActivity(intent);
            }
        });
    }
}