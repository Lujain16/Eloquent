package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eloquent.R;
import com.google.android.material.transition.Hold;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import Model.Listitem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private Context context;
    private List<Listitem> lisitems;

    //Record in wav------------------
    public static Intent intentWordResult ; //intentResult save the Stuttring Severity result from python

    private boolean recording_sta = false;
    public String RecordName = null;
    public String RecordNameAndPostion =null;

    String RecordNameArray[];
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

    public static int position;
    public TextToSpeech textToSpeech;
    int i =1;

    public MyAdapter(Context context, List listitem) {
        this.context = context;
        this.lisitems = listitem;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Listitem item = lisitems.get(position);
        holder.Question.setText(item.getQuestion());
        holder.Answer.setText(item.getAnswer());
        holder.RecordPostion = item.getRecordPostion();

        ArrayList<String> AuduioPathArrL = new ArrayList<>();

        //----------Record
        holder.play_bt.setEnabled(false);
        RecordNameAndPostion = holder.RecordPostion;
        Listitem Adapterlistitem = lisitems.get(position);
        holder.record_bt.setOnClickListener(new View.OnClickListener() {

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
                    // textToSpeech.shutdown();
                }
                //------------------------------End Stop Text To Speech
                //If not recoding
                if (recording_sta == false) {
                    try {
                        if(Adapterlistitem.getRecordPostion().equals(holder.RecordPostion)){
                            RecordName= holder.RecordPostion+"Adapter.wav";
                            holder.outputFile = context.getFilesDir() + "/" +RecordName;
                            AuduioPathArrL.add(holder.outputFile);
                        }
                        startRecording();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    recording_sta = true;
                    holder.play_bt.setEnabled(false);
                    Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //----------End Record
        // Stop Recording
        holder.stop_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recording_sta = false;
                stopRecording();
                holder.play_bt.setEnabled(true);
                Toast.makeText(context, "Audio recorded successfully", Toast.LENGTH_SHORT).show();
            }
        });

        //play button
        holder.play_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                //------------------------------Stop Text To Speech
                if (textToSpeech != null) {
                    textToSpeech.stop();
                }
                //------------------------------End Stop Text To Speech
                 m = new MediaPlayer();
                try {
                    m.reset();
                    m.setDataSource(holder.outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();  //play record
                Toast.makeText(context, "Playing audio", Toast.LENGTH_LONG).show();
            }
        });

        // ------------------Start Text To Speech For Question
        textToSpeech=new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.setSpeechRate((float) 0.9);
            }
        });
        holder.imageViewSpeakerQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------Stop Playing the recording
                if(m != null) {
                    m.release();
                    m = null;
                }
                //--------------------------------End Stop Playing the recording
                textToSpeech.speak(holder.Question.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });
        // ------------------End Text To Speech For Question

        // ------------------Start Text To Speech For Answer
        textToSpeech=new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.setSpeechRate((float) 0.9);

            }
        });
        holder.imageViewSpeakerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------Stop Playing the recording
                if(m != null) {
                    m.release();
                    m = null;
                }
                //--------------------------------End Stop Playing the recording

                textToSpeech.speak(holder.Answer.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });
        // ------------------End Text To Speech For Answer
    }

    @Override
    public int getItemCount() {
        return lisitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView Question;
        private  TextView Answer;
        public ImageButton Record,Stop, Play;
        public ImageButton record_bt,play_bt,stop_bt;
        public String outputFile = null;
        public String RecordPostion;
        public String voiceLocation = Environment.getExternalStorageDirectory().getPath() +"Voice Recorder/";
        public ImageView imageViewSpeakerQ;
        public ImageView imageViewSpeakerA;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Question = itemView.findViewById(R.id.textQuestion);
            Answer = itemView.findViewById(R.id.textViewAnswer);
            record_bt = itemView.findViewById(R.id.imageButton_mic);
            stop_bt = itemView.findViewById(R.id.imageButton_stop);
            play_bt = itemView.findViewById(R.id.imageButton_play);
            imageViewSpeakerQ = itemView.findViewById(R.id.imageViewQ);
            imageViewSpeakerA = itemView.findViewById(R.id.imageViewA);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
        }
    }

    //----------------------------------------------**************START WAV FORMAT*********************------------------------------------------------------
    //code for recording
    private String getFilename(){
        String filePath = context.getFilesDir().getPath().toString() + "/"+RecordName;
        File file = new File(filePath);
        return (file.getAbsolutePath() );
    }

    private String getTempFilename(){
        String filePath = context.getFilesDir().getPath().toString() + "/" + AUDIO_RECORDER_TEMP_FILE;
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