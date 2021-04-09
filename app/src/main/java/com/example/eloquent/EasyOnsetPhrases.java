package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class EasyOnsetPhrases extends AppCompatActivity {
    //Record in wav------------------
    public static Intent intentPhrasesResult ; //intentResult save the Stuttring Severity result from python
    private ImageButton record_bt,play_bt,stop_bt;
    private String outputFile = null;
    private boolean recording_sta = false;
    final static String RecordName = "Eloquent_speaker.wav";
    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
    private static int frequency = 44100;
    private static int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private static int EncodingBitRate = AudioFormat.ENCODING_PCM_16BIT;    //PCM16
    private AudioRecord audioRecord = null;
    MediaPlayer m ;
    private int recBufSize = 0;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    Button DonButton;
    TextView textViewPhrases ;
    // previous button
    ImageView imageView;
    private TextToSpeech textToSpeech;
    private TextView textViewEasyOnset;
    private ImageView imageViewSpeaker;
    String category ="phrase";
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_onset_phrases);

        //-------------------Word list generator
        //===================================================
        textViewPhrases = (TextView)findViewById(R.id.textViewEasyOnsePhrases);
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


                PutData putData = new PutData("http://192.168.100.11/Users/EasyOnSetExercise.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        result = putData.getResult();
                        System.out.println("resut: " + result);
                        textViewPhrases.setText(result);

                    }
                }
                //End Write and Read data with URL
            }
        });

        // ------------------Start Text To Speech
        textViewEasyOnset =textViewPhrases;
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
                //--------------------------------Stop Playing the recording
                if(m != null) {
                    m.release();
                    m = null;
                }
                //--------------------------------End Stop Playing the recording

                textToSpeech.speak(textViewEasyOnset.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });
        // ------------------End Text To Speech

        //--------------------------------------****START Recorder*****--------------------------------------------------
        record_bt = (ImageButton) findViewById(R.id.imageButton_mic);
        play_bt = (ImageButton) findViewById(R.id.imageButton_play);
        stop_bt= (ImageButton) findViewById(R.id.imageButton_stop);

        play_bt.setEnabled(false);
        outputFile = getFilesDir() + "/" + RecordName;

        record_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------Stop Playing the recording
                if(m != null) {
                    m.release();
                    m = null;
                }
                //--------------------------------End Stop Playing the recording
                //------------------------------Stop Text To Speech
                if (textToSpeech != null) {
                    textToSpeech.stop();
                }
                //------------------------------End Stop Text To Speech
                //If not recoding
                if (recording_sta == false) {
                    try {
                        startRecording();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    recording_sta = true;
                    play_bt.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recording_sta = false;
                stopRecording();
                play_bt.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_SHORT).show();
            }
        });


        //play button
        play_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                //------------------------------Stop Text To Speech
                if (textToSpeech != null) {
                    textToSpeech.stop();
                }
                //------------------------------End Stop Text To Speech
                 m = new MediaPlayer();
                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                m.start();  //play record
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

        //when users click on the previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------Stop Playing the recording
                    if(m != null) {
                        m.release();
                        m = null;
                    }
                //--------------------------------End Stop Playing the recording
                //------------------------------Stop Text To Speech
                if (textToSpeech != null) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                }
                //------------------------------End Stop Text To Speech
                Intent intent =new Intent(EasyOnsetPhrases.this, EasyOnsetPhrasesInstructions.class);
                startActivity(intent);
            }
        });
        //----------------------------------*************End Recorder**********------------------------------------------------------

        //----------------------------***********************SEND AUDIO PATH TO PYTHON****************************---------------------
        String voieNotePath = outputFile;
        Log.d("Main", "PATH : " + voieNotePath);
        File file = new File(voieNotePath);
        Log.d("Main", "voice exists : " + file.exists() + ", can read : " + file.canRead());
        //----------------------------***********************END SENT AUDIO PATH TO PYTHON****************************-----------------------

        //----------------------------***********************START PYTHON MODEL****************************-------------------------------------
        DonButton = (Button) findViewById(R.id.buttonDone);
        DonButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //--------------------------------Stop Playing the recording
                if(m != null) {
                    m.release();
                    m = null;
                }
                //--------------------------------End Stop Playing the recording
                //------------------------------Stop Text To Speech
                if (textToSpeech != null) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                }
                //------------------------------End Stop Text To Speech
                if (file.exists() ) {
                    if (! Python.isStarted()) {
                        Python.start(new AndroidPlatform(EasyOnsetPhrases.this));
                    }
                    //When the user clicks the done button message will display.
                    Toast.makeText(getApplicationContext(), "Please wait until display your stuttering severity.", Toast.LENGTH_LONG).show();

                    Python py = Python.getInstance();

                    //create python object
                    PyObject pyobj = py.getModule("myscript");

                    // call the function
                    PyObject obj = pyobj.callAttr("main",voieNotePath);

                    // return text from python to textview
                    String PthythonResult =obj.toString();
                    //Delete the audio file after done.
                    file.delete();

                    //intentResult sent the output from python to Stuttring Severity interface
                    intentPhrasesResult = new Intent(EasyOnsetPhrases.this,StutteringSeverityPhrases.class);
                    intentPhrasesResult.putExtra("KeyResultPhrases",PthythonResult);
                    startActivity(intentPhrasesResult);
                    //---------------------------------

                }else{
                    Toast.makeText(getApplicationContext(), "Please record your voice.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //----------------------------***********************END PYTHON MODEL****************************-------------------------------------
    }

    //----------------------------------------------**************START WAV FORMAT*********************------------------------------------------------------
    // code for recording
    private String getFilename(){
        String filePath = getFilesDir().getPath().toString() + "/"+RecordName;
        File file = new File(filePath);
        return (file.getAbsolutePath() );
    }

    private String getTempFilename(){
        String filePath = getFilesDir().getPath().toString() + "/" + AUDIO_RECORDER_TEMP_FILE;
        File file = new File(filePath);
        return (file.getAbsolutePath() );
    }

    private void startRecording(){
        createAudioRecord();
        audioRecord.startRecording();
        isRecording = true;
        recordingThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        },"AudioRecorder Thread");

        recordingThread.start();
    }

    private void writeAudioDataToFile(){
        byte data[] = new byte[recBufSize];
        String filename = getTempFilename();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int read = 0;
        if(null != os){
            while(isRecording){
                read = audioRecord.read(data, 0, recBufSize);
                if(AudioRecord.ERROR_INVALID_OPERATION != read){
                    try {
                        os.write(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRecording(){
        if(null != audioRecord){
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            recordingThread = null;
        }

        copyWaveFile(getTempFilename(),getFilename());
        deleteTempFile();
    }

    private void deleteTempFile() {
        File file = new File(getTempFilename());
        file.delete();
    }

    private void copyWaveFile(String inFilename,String outFilename){
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = frequency;
        int channels = 1;
        long byteRate = RECORDER_BPP * frequency * channels/8;

        byte[] data = new byte[recBufSize];

        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;

            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);

            while(in.read(data) != -1){
                out.write(data);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteWaveFileHeader(
            FileOutputStream out, long totalAudioLen,
            long totalDataLen, long longSampleRate, int channels,
            long byteRate) throws IOException {

        byte[] header = new byte[44];

        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (1 * 16 / 8);  // block align
        header[33] = 0;
        header[34] = RECORDER_BPP;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }
    public void createAudioRecord(){
        recBufSize = AudioRecord.getMinBufferSize(frequency,
                channelConfiguration, EncodingBitRate);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                channelConfiguration, EncodingBitRate, recBufSize);
        System.out.println("AudioRecord Success");
    }
//----------------------------------------------**************END WAV FORMAT*********************------------------------------------------------------
}