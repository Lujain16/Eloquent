package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.regex.Pattern;

import static com.example.eloquent.ForgetPassword1.intentCode;
import static com.example.eloquent.ForgetPassword1.intentEmail;
import static com.example.eloquent.Login.intent2;

public class ForgetPassword3 extends AppCompatActivity {
    Button SendButton;
    private static final Pattern password_patern = Pattern.compile("^"+ "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=\\S+$)" + ".{6,}" +"$");
    EditText Password, RePassword;
    String inputPassword, inputRePassword,Email;
    //prev button
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password3);
        SendButton =findViewById(R.id.buttonSend);
        Password =findViewById(R.id.editTextpass);
        RePassword =findViewById(R.id.editTextPass2);
        Email =intentEmail.getStringExtra("EmailForget");

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputPassword = Password.getText().toString();
                inputRePassword = RePassword.getText().toString();

                if (inputPassword.equals("") ||inputRePassword.equals("")){
                    Toast.makeText(ForgetPassword3.this, "The fields must be filled", Toast.LENGTH_LONG).show();

                }else if(!inputPassword.equals(inputRePassword)){
                    Toast.makeText(ForgetPassword3.this, "Password not matched", Toast.LENGTH_LONG).show();
                }
                else if(!password_patern.matcher(inputPassword).matches()){
                    Toast.makeText(ForgetPassword3.this, "Password too weak, It must be at least 6 characters long and contain capital, and small letters.", Toast.LENGTH_LONG).show();
                }else{

                    //---------------------------------------------
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "Email";
                            field[1] = "Password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = Email;
                            data[1] = inputPassword;

                            try {
                                PutData putData = new PutData("http://192.168.100.11/Users/UpdatePassword.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                    }
                                }
                            }catch (Exception E){
                                System.out.println("error");
                            }
                            //End Write and Read data with URL
                        }
                    });
                    //---------------------------------------------
                    Intent intent = new Intent(ForgetPassword3.this, Login.class);
                    startActivity(intent);
                }
            }
        });
        //when users click on previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ForgetPassword3.this, ForgetPassword2.class);
                startActivity(intent);
            }
        });

    }
}