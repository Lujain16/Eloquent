package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPassword1 extends AppCompatActivity {

    //login button
    Button SendButton;
    EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);

        SendButton =findViewById(R.id.button);
        Email =findViewById(R.id.editTextTextEmailAddress2);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword1.this, ForgetPassword2.class);
                startActivity(intent);
            }
        });
    }
}