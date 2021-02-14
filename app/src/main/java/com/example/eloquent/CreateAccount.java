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
import android.widget.Toast;

import java.util.Calendar;

import DataBase.UserDBHelper;
import Model.UserInformation;

import static com.example.eloquent.Login.intent2;

public class CreateAccount extends AppCompatActivity {

    EditText dateTxt, FnameTxt, LnameTxt, EmailTxt, passwordTxt, RePasswordTxt;

    private int mdate, mmonth, myear;
    //prev button
    ImageView imageView;
    // create account button
    Button createAccountButton;
    //Database
    UserDBHelper dbHelper;


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
                        mmonth = month+1;
                        dateTxt.setText(mdate+"-"+mmonth+"-"+myear);

                    }
                },myear,mmonth,mdate);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();

            }
        });


        //prev
        //when user click on previous button this code will move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent
               intent2 =new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent2);
            }
        });



        //database////////////////////////
        //DATABASE------------
        dateTxt = (EditText) findViewById(R.id.editTextDate);
        FnameTxt = (EditText) findViewById(R.id.editTextTextPersonName);
        LnameTxt = (EditText) findViewById(R.id.editTextTextPersonName2);
        EmailTxt = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordTxt = (EditText) findViewById(R.id.editTextTextPassword);
        RePasswordTxt = (EditText) findViewById(R.id.editTextTextPassword2);
        createAccountButton = (Button) findViewById(R.id.buttonCreateAccount2);
        dbHelper = new UserDBHelper(this);

        //if click in create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation userInformation;

                String Fname = FnameTxt.getText().toString();
                String Lname = LnameTxt.getText().toString();
                String Email = EmailTxt.getText().toString();
                String Bdate = dateTxt.getText().toString();
                String pass = passwordTxt.getText().toString();
                String Repass = RePasswordTxt.getText().toString();

                try {


//                    // if there is empty field
//                    if (Fname.equals("") || Lname.equals("") || Email.equals("") || Bdate.equals("") || pass.equals("") || Repass.equals("")) {
//                        Toast.makeText(CreateAccount.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
//                    }
//                     if  (!pass.equals(Repass)) {
//                        Toast.makeText(CreateAccount.this, "Passwords are not matched, please retype them correctly", Toast.LENGTH_SHORT).show();
//                    }
                    //check exist email
                    boolean checkEmail = dbHelper.checkEmail(Email);
                    if (checkEmail == true) {
                        Toast.makeText(CreateAccount.this, "Email is existing, Please change it", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if(!(Fname.equals("") || Lname.equals("") || Email.equals("") || Bdate.equals("") || pass.equals("") || Repass.equals(""))&&pass.equals(Repass)&&(checkEmail==false)) {
                            userInformation = new UserInformation(Fname, Lname, Email, Bdate, pass);
                            boolean insert = dbHelper.AddUser(userInformation);
                            Toast.makeText(CreateAccount.this, "success= " + insert, Toast.LENGTH_SHORT).show();

                            if (insert == true) {
                                Toast.makeText(CreateAccount.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                             //   Intent
                                        intent2 = new Intent(CreateAccount.this, DiagnosticTest.class);
                                intent2.putExtra("LoginEmailInfo", Email);
                                startActivity(intent2);
                            }

                        }else {
                            Toast.makeText(CreateAccount.this, "Your account has not!!! been created", Toast.LENGTH_SHORT).show();

                        }
                    }



                }catch (Exception e){
                    Toast.makeText(CreateAccount.this, "Your account has not!!! been created", Toast.LENGTH_SHORT).show();


                }

            }
        });

    }
}
