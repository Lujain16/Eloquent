package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;
import java.util.regex.Pattern;

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

    //required of the password to contain:
    // at least one number, at least one small letter, at least one capital letter, not contain space, contain at least 6 characters long.
    private static final Pattern password_patern = Pattern.compile("^"+ "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=\\S+$)" + ".{6,}" +"$");
    String FirstName,LastName, Email, BirthDate, Password,RePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Calendar to choose the date of birth
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

        //when users click on previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent2 =new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intent2);
            }
        });

        //EditText
        dateTxt = (EditText) findViewById(R.id.editTextDate);
        FnameTxt = (EditText) findViewById(R.id.editTextTextPersonName);
        LnameTxt = (EditText) findViewById(R.id.editTextTextPersonName2);
        EmailTxt = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordTxt = (EditText) findViewById(R.id.editTextTextPassword);
        RePasswordTxt = (EditText) findViewById(R.id.editTextTextPassword2);
        createAccountButton = (Button) findViewById(R.id.buttonCreateAccount2);

        //if click in create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data input from users
                FirstName = FnameTxt.getText().toString();
                LastName = LnameTxt.getText().toString();
                Email = EmailTxt.getText().toString();
                BirthDate = dateTxt.getText().toString();
                Password = passwordTxt.getText().toString();
                RePassword = RePasswordTxt.getText().toString();

                if (FirstName.equals("") || LastName.equals("") || Email.equals("") || BirthDate.equals("") || Password.equals("") ||RePassword.equals("")){
                    Toast.makeText(CreateAccount.this, "All fields must be filled", Toast.LENGTH_LONG).show();

                }
                else if(!Password.equals(RePassword)){
                    Toast.makeText(CreateAccount.this, "Password not matched", Toast.LENGTH_LONG).show();

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    Toast.makeText(CreateAccount.this, "Please Enter a valid Email", Toast.LENGTH_LONG).show();
                }
                else if(!password_patern.matcher(Password).matches()){
                    Toast.makeText(CreateAccount.this, "Password too weak, It must be at least 6 characters long and contain capital, and small letters.", Toast.LENGTH_LONG).show();
                }

                else{

                //If all fields are filled correct, add the user to the database
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

                        PutData putData = new PutData("http://192.168.100.11/Users/CreateAccount.php", "POST", field, data);

                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                Toast.makeText(CreateAccount.this, result, Toast.LENGTH_SHORT).show();
                                intent2 = new Intent(CreateAccount.this, appInstructions.class);
                                intent2.putExtra("LoginEmailInfo",Email);
                                startActivity(intent2);
                            }
                        }

                    }
                });

            }}
        });

    }
}
