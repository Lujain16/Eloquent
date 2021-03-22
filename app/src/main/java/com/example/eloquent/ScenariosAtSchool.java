package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.Listitem;

public class ScenariosAtSchool extends AppCompatActivity {
    public static Intent intentAtSchoolResult ; //intentResult save the Stuttring Severity result from python
    private RecyclerView recyclerView;
    private List<Listitem> listitems;
    private  RecyclerView.Adapter adapter;
    public String textArray[];
    String place = "At School";

    //Done button
    Button Donebutton;
    //previous button
    ImageView imageView;

    ArrayList<String> AuduioPathArrL = new ArrayList<>(); //jjjjj

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenarios_at_school);
//==================================================================================
        recyclerView = findViewById(R.id.recyclerviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();

        //---------------------------------------------
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "place";

                //Creating array for data
                String[] data = new String[1];
                data[0] = place;


                PutData putData = new PutData("http://192.168.100.22/Users/ScenarioExercise.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        String result = putData.getResult();
                        System.out.println("resut: " + result);


                        String[] arrOfStr = result.split("/");
                        // Fname.setText(arrOfStr[0]);
                        System.out.println("==============================" + arrOfStr);

                        //'''''''''''''''
                        for (int x = 0; x < arrOfStr.length - 2; x = x + 2) {
                            AuduioPathArrL.add("/data/user/0/com.example.eloquent/files/" + x + "Adapter.wav");
                            Listitem listitem = new Listitem(
                                    "Student: " + arrOfStr[x],
                                    "You: " + arrOfStr[x + 1],
                                    "" + x

                            );
                            //add record

                            listitems.add(listitem);
                        }

//                        for (int i=0; i<arrOfStr.length; i++){
//                           // String k =AuduioPathArrL.get(i);
//                            System.out.println("##################################################################################  AuduioPathArrL["+i+"]=  "+AuduioPathArrL.get(i));
//                            System.out.println("##################################################################################  AuduioPathArrL size =  "+AuduioPathArrL.size());
//                        }

                        //''''''''''''''''

                    }
                }
                //End Write and Read data with URL
            }
        });
        adapter = new MyAdapter(this, listitems);
        recyclerView.setAdapter(adapter);
        //-------------------------------------------------------


//        textArray = new String[]{"Could you help me?",
//                "What do you need?",
//                "I can't seem to find my class.",
//                "What building is it in?",
//                "It's in the C building.",
//                "Oh, I know exactly where that is.",
//                "Do you mind telling me where it is?",
//                "Sure, what room number is it?",
//                "It's room number 261.",
//                "I have a class around there right now.",
//                "Could you show me where it is?",
//                "No problem, come on."};
//
//        recyclerView = findViewById(R.id.recyclerviewID);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        listitems = new ArrayList<>();
//        for(int x=0; x<textArray.length-1; x=x+2){
//            System.out.println("-----------------------------textArray[x]"+textArray[x]);
//            System.out.println("-----------------------------textArray[x+1]"+textArray[x+1]);
//            Listitem listitem =new Listitem(
//
//                    "Studen: "+textArray[x],
//                    "You: "+textArray[x+1]
//            );
//            listitems.add(listitem);
//        }
//        adapter = new MyAdapter(this,listitems);
//        recyclerView.setAdapter(adapter);
        //==================================================================================


        //Done button
//        Donebutton = findViewById(R.id.buttonDone);
//        Donebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(ScenariosAtSchool.this, StutteringSeverity.class);
//                startActivity(intent);
//            }
//        });

        //----------------------------***********************SEND AUDIO PATH TO PYTHON****************************---------------------
        String voieNotePath = "/data/user/0/com.example.eloquent/files/0Adapter.wav";
        String voieNotePath2 = "/data/user/0/com.example.eloquent/files/2Adapter.wav";
        Log.d("Main", "PATH : " + voieNotePath);
        File file = new File(voieNotePath);
        File file2 = new File(voieNotePath2);
        Log.d("Main", "voice exists : " + file.exists() + ", can read : " + file.canRead());


        //MediaPlayer mpintro = MediaPlayer.create(ScenariosAtSchool.this, Uri.parse(voieNotePath));
        // mpintro.start();

        //----------------------------***********************END SENT AUDIO PATH TO PYTHON****************************-----------------------

        //----------------------------***********************START PYTHON MODEL****************************-------------------------------------
        Donebutton = (Button) findViewById(R.id.buttonDone);



        Donebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (file.exists() == true && file2.exists()) {

                if (!Python.isStarted()) {
                    Python.start(new AndroidPlatform(ScenariosAtSchool.this));
                }

                Python py = Python.getInstance();

                //create python object
                PyObject pyobj = py.getModule("myscript");

                // call the function
                PyObject obj = pyobj.callAttr("main", voieNotePath);

                // return text from python to textview
                String PthythonResult = obj.toString();
               // System.out.println("||||||||||||||||||||||||||||||||PthythonResult=  " + PthythonResult);

                //intentResult sent the output from python to Stuttring Severity interface
                intentAtSchoolResult = new Intent(ScenariosAtSchool.this,StutteringSeverityAtSchool.class);
                intentAtSchoolResult.putExtra("KeyResulAtSchool",PthythonResult);
                startActivity(intentAtSchoolResult);

            }
                else{

                    Toast.makeText(getApplicationContext(), "Please record your voice on all cards.", Toast.LENGTH_SHORT).show();
                }
            }//on Click
        });


        //----------------------------***********************END PYTHON MODEL****************************-------------------------------------


        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosAtSchool.this, ScenarioAtSchoolInstructions.class);
                startActivity(intent);
            }
        });

    }
}