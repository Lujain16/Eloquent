 package com.example.eloquent;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eloquent.R;

import java.util.ArrayList;
import java.util.List;

import DataBase.UserDBHelper;
import Model.UserInformation;

import static com.example.eloquent.Login.intent2;


 public class MyInfo extends AppCompatActivity {
    EditText Fname, Lname, Email, BD;
    Button save;
    TextView textView;
    UserDBHelper dbHelper;
    String EditUserFName;
    String EditUserLName;
String UserEmailForIntent= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        String emailLogin = intent2.getStringExtra("LoginEmailInfo");

        Fname = findViewById(R.id.PersonNameFN);
        Lname = findViewById(R.id.PersonNameLN);
        Email = findViewById(R.id.PersonEmail);
        BD = findViewById(R.id.editTextBD);
        save = findViewById(R.id.Savebutton);
        dbHelper = new UserDBHelper(MyInfo.this);
        textView=findViewById(R.id.textView14);


        try{
            List<UserInformation> Usersinfo = dbHelper.getuserinfo(emailLogin);

            String UserFName = Usersinfo.get(0).getFName();
            String UserLName = Usersinfo.get(0).getLName();
            String UserEmail = Usersinfo.get(0).getEmail();
            String UserBdate = Usersinfo.get(0).getBirthDate();
            UserEmailForIntent =UserEmail;
            EditUserFName = UserFName;
            EditUserLName = UserLName;

            Fname.setText(UserFName);
            Lname.setText(UserLName);
            Email.setText(UserEmail);
            BD.setText(UserBdate);

        }catch (Exception e){
            Toast.makeText(MyInfo.this, "error", Toast.LENGTH_LONG).show();

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserFName = Fname.getText().toString();
                Fname.setText(EditUserFName);
                EditUserLName = Lname.getText().toString();
                Lname.setText(EditUserLName);
                boolean result = dbHelper.EditName(emailLogin, EditUserFName,EditUserLName);


                if (result){
                  //  Intent intentProfile
                            intent2 =new Intent(getApplicationContext(),Profile.class);
                    intent2.putExtra("LoginEmailInfo", UserEmailForIntent);
                    startActivity(intent2);
                }else {
                    Toast.makeText(MyInfo.this, "update is failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

