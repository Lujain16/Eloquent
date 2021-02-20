package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.Listitem;

public class ScenarioAtLibrary extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Listitem> listitems;
    private  RecyclerView.Adapter adapter;
    public String textArray[];

    //Done button
    Button Donebutton;
    //previous button
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario_at_library);
        textArray = new String[]{"What can I do for you?",
                "I was looking for a book, but I couldn't find it.",
                "Did you check our database to see if it was on the shelf?",
                "I already did.",
                "Is it on the shelf?",
                "I didn't see it.",
                "Apparently somebody took that book out of the library.",
                "Will you ever get another copy?",
                "We will definitely be getting another.",
                "Could you please reserve it for me?",
                "That won't be a problem.",
                "Thanks. I really appreciate that."};

        recyclerView = findViewById(R.id.recyclerviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();
        for(int x=0; x<textArray.length-1; x=x+2){
//            System.out.println("-----------------------------textArray[x]"+textArray[x]);
//            System.out.println("-----------------------------textArray[x+1]"+textArray[x+1]);
            Listitem listitem =new Listitem(

                    "Librarian: "+textArray[x],
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
                Intent intent =new Intent(ScenarioAtLibrary.this, StutteringSeverity.class);
                startActivity(intent);
            }
        });
        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenarioAtLibrary.this, ScenarioAtLibraryInstructions.class);
                startActivity(intent);
            }
        });

    }
}