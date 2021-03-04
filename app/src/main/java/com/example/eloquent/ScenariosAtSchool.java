package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.Listitem;

public class ScenariosAtSchool extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Listitem> listitems;
    private  RecyclerView.Adapter adapter;
    public String textArray[];
    String place = "At School";

    //Done button
    Button Donebutton;
    //previous button
    ImageView imageView;

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


                PutData putData = new PutData("http://192.168.100.19/Users/ScenarioExercise.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        String result = putData.getResult();
                        System.out.println("resut: " + result);


                        String[] arrOfStr = result.split("/");
                       // Fname.setText(arrOfStr[0]);
                        System.out.println("=============================="+arrOfStr);

                        //'''''''''''''''
                        for(int x=0; x<arrOfStr.length-2; x=x+2){
                            Listitem listitem =new Listitem(
                                    "Student: "+arrOfStr[x],
                                    "You: "+arrOfStr[x+1]
                            );
                            listitems.add(listitem);
                        }

                        //''''''''''''''''

                    }
                }
                //End Write and Read data with URL
            }
        });
        adapter = new MyAdapter(this,listitems);
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
        Donebutton = findViewById(R.id.buttonDone);
        Donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosAtSchool.this, StutteringSeverity.class);
                startActivity(intent);
            }
        });


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