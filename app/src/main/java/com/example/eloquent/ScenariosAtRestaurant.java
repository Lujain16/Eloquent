package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class ScenariosAtRestaurant extends AppCompatActivity {
    public static Intent intentAtRestaurantResult ; //intentResult save the Stuttring Severity result from python
    private RecyclerView recyclerView;
    private List<Listitem> listitems;
    private  RecyclerView.Adapter adapter;
    public String textArray[];
    //Done button
    Button Donebutton;
    //previous button
    ImageView imageView;
    String place = "At Restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenarios_at_restaurant);
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

                PutData putData = new PutData("http://192.168.100.11/Users/ScenarioExercise.php", "POST", field, data);
                if (putData.startPut()) {
                    System.out.println("result:1 ");
                    if (putData.onComplete()) {
                        System.out.println("result:2 ");
                        String result = putData.getResult();
                        System.out.println("result: " + result);

                        String[] arrOfStr = result.split("/");
                        //Filling the card with questions
                        for(int x=0; x<arrOfStr.length-2; x=x+2){
                            Listitem listitem =new Listitem(
                                    "Waiter: "+arrOfStr[x],
                                    "You: "+arrOfStr[x+1],
                                    ""+x
                            );
                            listitems.add(listitem);
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });
        adapter = new MyAdapter(this,listitems);
        recyclerView.setAdapter(adapter);

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
                    //When the user clicks the done button message will display.
                    Toast.makeText(getApplicationContext(), "Please wait until display your stuttering severity.", Toast.LENGTH_SHORT).show();

                    if (!Python.isStarted()) {
                        Python.start(new AndroidPlatform(ScenariosAtRestaurant.this));
                    }

                    Python py = Python.getInstance();

                    //create python object
                    PyObject pyobj = py.getModule("myscript");

                    // call the function
                    PyObject obj = pyobj.callAttr("main", voieNotePath);

                    // return text from python to textview
                    String PthythonResult = obj.toString();
                    //Delete the audio file after done.
                    file.delete();

                    //intentResult sent the output from python to Stuttring Severity interface
                    intentAtRestaurantResult = new Intent(ScenariosAtRestaurant.this,StutteringSeverityAtRestaurant.class);
                    intentAtRestaurantResult.putExtra("KeyResulAtRestaurant",PthythonResult);
                    startActivity(intentAtRestaurantResult);

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please record your voice on at least two cards.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //----------------------------***********************END PYTHON MODEL****************************-------------------------------------

        //when users click on the previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosAtRestaurant.this, ScenarioAtRestaurantInstructions.class);
                startActivity(intent);
            }
        });
    }
}