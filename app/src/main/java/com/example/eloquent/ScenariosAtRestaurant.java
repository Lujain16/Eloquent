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

public class ScenariosAtRestaurant extends AppCompatActivity {
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
        setContentView(R.layout.activity_scenarios_at_restaurant);
        textArray = new String[]{"What would you like to order?",
                "I would like to have a burger.",
                "Did you want it with cheese?",
                "I don't want cheese on it.",
                "Did you want anything to drink today?",
                "I think I'm going to get a soda today.",
                "What kind of soda can I get you?",
                "A Sprite sounds good.",
                "What else would you like?",
                "Let me get a bag of chips too.",
                "Anything else?",
                "That's all. Thank you."};

        recyclerView = findViewById(R.id.recyclerviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();
        for(int x=0; x<textArray.length-1; x=x+2){
//            System.out.println("-----------------------------textArray[x]"+textArray[x]);
//            System.out.println("-----------------------------textArray[x+1]"+textArray[x+1]);
            Listitem listitem =new Listitem(

                    "Waiter: "+textArray[x],
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
                Intent intent =new Intent(ScenariosAtRestaurant.this, StutteringSeverity.class);
                startActivity(intent);
            }
        });
        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ScenariosAtRestaurant.this, ScenarioAtSchoolInstructions.class);
                startActivity(intent);
            }
        });

    }
}