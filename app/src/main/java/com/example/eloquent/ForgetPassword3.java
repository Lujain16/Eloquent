package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPassword3 extends AppCompatActivity {
    Button SendButton;
    EditText Password, RePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password3);
        SendButton =findViewById(R.id.button);
        Password =findViewById(R.id.editTextpass);
        RePassword =findViewById(R.id.editTextPass2);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword3.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}