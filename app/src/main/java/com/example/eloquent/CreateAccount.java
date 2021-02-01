package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class CreateAccount extends AppCompatActivity {

    EditText dateTxt;
    private int mdate, mmonth, myear;
    //prev button
    ImageView imageView;
    // create account button
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        dateTxt = findViewById(R.id.editTextDate);

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mdate = cal.get(Calendar.DATE);
                mmonth = cal.get(Calendar.MONTH);
                myear = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAccount.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        dateTxt.setText(dayOfMonth+"-"+month+"-"+year);

                    }
                },myear,mmonth,mdate);
                datePickerDialog.show();

            }
        });


        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //create account button
        //when user click on create account button  this code will move them to the diagnostic test page
        button = findViewById(R.id.buttonCreateAccount2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CreateAccount.this, DiagnosticTest.class);
                startActivity(intent);
            }
        });
    }
}