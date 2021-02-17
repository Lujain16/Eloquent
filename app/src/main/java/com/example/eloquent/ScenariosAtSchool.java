package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.Listitem;

public class ScenariosAtSchool extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Listitem> listitems;
    private  RecyclerView.Adapter adapter;
    public String textArray[];

    //Done button
    Button Donebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenarios_at_school);

        textArray = new String[]{"Could you help me?",
                "What do you need?",
                "I can't seem to find my class.",
                "What building is it in?",
                "It's in the C building.",
                "Oh, I know exactly where that is.",
                "Do you mind telling me where it is?",
                "Sure, what room number is it?",
                "It's room number 261.",
                "I have a class around there right now.",
                "Could you show me where it is?",
                "No problem, come on."};

        recyclerView = findViewById(R.id.recyclerviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();
        for(int x=0; x<textArray.length-1; x=x+2){
            System.out.println("-----------------------------textArray[x]"+textArray[x]);
            System.out.println("-----------------------------textArray[x+1]"+textArray[x+1]);
            Listitem listitem =new Listitem(

                    "Studen: "+textArray[x],
                    "You: "+textArray[x+1]
            );
            listitems.add(listitem);
        }
        adapter = new MyAdapter(this,listitems);
        recyclerView.setAdapter(adapter);

        //Done button
        Donebutton = findViewById(R.id.buttonDone);
        Donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosAtSchool.this, StutteringSeverity.class);
                startActivity(intent);
            }
        });

    }
}