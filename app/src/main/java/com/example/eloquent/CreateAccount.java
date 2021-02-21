package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

import DataBase.UserDBHelper;
import Model.UserInformation;

import static com.example.eloquent.Login.intent2;

public class CreateAccount extends AppCompatActivity {

    EditText dateTxt, FnameTxt, LnameTxt, EmailTxt, passwordTxt, RePasswordTxt;

    private int mday, mmonth, myear;
    //prev button
    ImageView imageView;
    // create account button
    Button createAccountButton;
    //Database
    UserDBHelper dbHelper;

    String FirstName,LastName, Email, BirthDate, Password,RePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        dateTxt = findViewById(R.id.editTextDate);

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mday = cal.get(Calendar.DAY_OF_MONTH);
                mmonth = cal.get(Calendar.MONTH);
                myear = cal.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAccount.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        dateTxt.setText(dayOfMonth+"-"+month+"-"+year);

                    }
                },myear,mmonth,mday);
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

                FirstName = FnameTxt.getText().toString();
                LastName = LnameTxt.getText().toString();
                Email = EmailTxt.getText().toString();
                BirthDate = dateTxt.getText().toString();
                Password = passwordTxt.getText().toString();
                RePassword = RePasswordTxt.getText().toString();

                //---------------------------------------------
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[5];
                        field[0] = "FirstName";
                        field[1] = "LastName";
                        field[2] = "Email";
                        field[3] = "BirthDate";
                        field[4] = "Password";
                        //Creating array for data
                        String[] data = new String[5];
                        data[0] = FirstName;
                        data[1] = LastName;
                        data[2] = Email;
                        data[3] = BirthDate;
                        data[4] = Password;

                        PutData putData = new PutData("http://192.168.100.4/Users/CreateAccount.php", "POST", field, data);

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Toast.makeText(CreateAccount.this, result, Toast.LENGTH_SHORT).show();

                                System.out.println("************************************ "+result);
                                if (result.equalsIgnoreCase("Create Account Success")){
                                    System.out.println("Create Account Success");

                                }else if(result.equalsIgnoreCase("Create Account Failed")){

                                    System.out.println("Create Account Failed");

                                }else if(result.equalsIgnoreCase("Error: Database connection")){
                                    System.out.println("Error: Database connection");

                                }else if(result.equalsIgnoreCase("All fields are required")){

                                    System.out.println("All fields are required");
                                }else {

                                    System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                                }

                                //End ProgressBar (Set visibility to GONE)
                                //Log.i("PutData", result);
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
                 //---------------------------------------------








//                try {
//
//
////                    // if there is empty field
////                    if (Fname.equals("") || Lname.equals("") || Email.equals("") || Bdate.equals("") || pass.equals("") || Repass.equals("")) {
////                        Toast.makeText(CreateAccount.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
////                    }
////                     if  (!pass.equals(Repass)) {
////                        Toast.makeText(CreateAccount.this, "Passwords are not matched, please retype them correctly", Toast.LENGTH_SHORT).show();
////                    }
//                    //check exist email
//                    boolean checkEmail = dbHelper.checkEmail(Email);
//                    if (checkEmail == true) {
//                        Toast.makeText(CreateAccount.this, "Email is existing, Please change it", Toast.LENGTH_SHORT).show();
//                    }
//
//                    else {
//                        if(!(Fname.equals("") || Lname.equals("") || Email.equals("") || Bdate.equals("") || pass.equals("") || Repass.equals(""))&&pass.equals(Repass)&&(checkEmail==false)) {
//                            userInformation = new UserInformation(Fname, Lname, Email, Bdate, pass);
//                            boolean insert = dbHelper.AddUser(userInformation);
//                            Toast.makeText(CreateAccount.this, "success= " + insert, Toast.LENGTH_SHORT).show();
//
//                            if (insert == true) {
//                                Toast.makeText(CreateAccount.this, "Your account has been created", Toast.LENGTH_SHORT).show();
//                             //   Intent
//                                        intent2 = new Intent(CreateAccount.this, DiagnosticTest.class);
//                                intent2.putExtra("LoginEmailInfo", Email);
//                                startActivity(intent2);
//                            }
//
//                        }else {
//                            Toast.makeText(CreateAccount.this, "Your account has not!!! been created", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//
//
//                }catch (Exception e){
//                    Toast.makeText(CreateAccount.this, "Your account has not!!! been created", Toast.LENGTH_SHORT).show();
//                }

            }
        });

    }
}
