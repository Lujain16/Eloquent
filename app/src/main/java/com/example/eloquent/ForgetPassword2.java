package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.eloquent.ForgetPassword1.intentCode;
import static com.example.eloquent.Login.intent2;
//import static com.example.eloquent.ForgetPassword1.intentcode;

public class ForgetPassword2 extends AppCompatActivity {
    Button SendButton;
    EditText code;
    TextView wariningMsg;
    String inputCode;
    String verficationCode;
    //prev button
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);
        SendButton =findViewById(R.id.SendButton);
        code =findViewById(R.id.editTextcode);
        wariningMsg =findViewById(R.id.textView12);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCode = code.getText().toString();
                verficationCode = intentCode.getStringExtra("keyCode");

                if(inputCode.equals(verficationCode)){
                    intentCode = new Intent(ForgetPassword2.this, ForgetPassword3.class);
                    startActivity(intentCode);
                }else{
                    wariningMsg.setText("The verification code is not correct.");
                }
            }
        });
        //when users click on previous button, move them to the previous page
        imageView = findViewById(R.id.imageView3Previous);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ForgetPassword2.this, ForgetPassword1.class);
                startActivity(intent);
            }
        });

    }

}