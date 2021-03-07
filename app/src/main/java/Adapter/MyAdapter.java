package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eloquent.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Model.Listitem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private Context context;
    private List<Listitem> lisitems;

    private String outputFile = null;

    //==============
    public MediaRecorder myAudioRecorder;
    MediaPlayer mediaPlayer ;
    public TextToSpeech textToSpeech;
    int i =1;


    //=============

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

        //----------Record
        holder.Record.setOnClickListener(new View.OnClickListener() {
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
                holder.Record.setEnabled(false);
                holder.Stop.setEnabled(true);

                Toast.makeText(context, "Recording started...",Toast.LENGTH_LONG).show();




            }
        });
        //----------End Record
        // Stop Recording
        holder.Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    myAudioRecorder.stop();
                    myAudioRecorder.reset();
                    myAudioRecorder = null;
                }catch (Exception e){
                    //
                }


                holder.Record.setEnabled(true);
                holder.Stop.setEnabled(false);
                holder.Play.setEnabled(true);
                Toast.makeText(context, "Audio Recorder successfully", Toast.LENGTH_LONG).show();
            }
        });


        // Play Recorded Audio
        holder. Play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(context, "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
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

                textToSpeech.speak(holder.Answer.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,null);
            }
        });

        // ------------------End Text To Speech For Answer




    }

    @Override
    public int getItemCount() {
        return lisitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Question;
        private  TextView Answer;
        public ImageButton Record,Stop, Play;
//        //------------------------------Text to Speech

          public ImageView imageViewSpeakerQ;
        public ImageView imageViewSpeakerA;
//        //------------------------------Text to Speech

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Question = itemView.findViewById(R.id.textQuestion);
            Answer = itemView.findViewById(R.id.textViewAnswer);

            //----j
            Record = itemView.findViewById(R.id.imageButton_mic);
            Stop = itemView.findViewById(R.id.imageButton_stop);
            Play = itemView.findViewById(R.id.imageButton_play);

            Record.setEnabled(true);
            Stop.setEnabled(false);
            Play.setEnabled(false);


            outputFile = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Scenario+"+i+".mp3";

            i = i+1;
            //----j
//           // textViewQuestion = Question;
            imageViewSpeakerQ = itemView.findViewById(R.id.imageViewQ);
            imageViewSpeakerA = itemView.findViewById(R.id.imageViewA);
//            //------------------------------Text to Speech


        }




    }
}