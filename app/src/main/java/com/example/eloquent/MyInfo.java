 package com.example.eloquent;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eloquent.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

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
//we do not need this in mySQL
        Fname = findViewById(R.id.PersonNameFN);
        Lname = findViewById(R.id.PersonNameLN);
        Email = findViewById(R.id.PersonEmail);
        BD = findViewById(R.id.editTextBD);
        save = findViewById(R.id.Savebutton);
//        dbHelper = new UserDBHelper(MyInfo.this);
 textView=findViewById(R.id.textView14);
//

//        try{
//            List<UserInformation> Usersinfo = dbHelper.getuserinfo(emailLogin);
//
//            String UserFName = Usersinfo.get(0).getFName();
//            String UserLName = Usersinfo.get(0).getLName();
//            String UserEmail = Usersinfo.get(0).getEmail();
//            String UserBdate = Usersinfo.get(0).getBirthDate();
//            UserEmailForIntent =UserEmail;
//            EditUserFName = UserFName;
//            EditUserLName = UserLName;
//
//            Fname.setText(UserFName);
//            Lname.setText(UserLName);
//            Email.setText(UserEmail);
//            BD.setText(UserBdate);
//
//        }catch (Exception e){
//            Toast.makeText(MyInfo.this, "error", Toast.LENGTH_LONG).show();
//
//        }


        //---------------------------------------------
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "Email";

                //Creating array for data
                String[] data = new String[1];
                data[0] = emailLogin;


                PutData putData = new PutData("http://192.168.100.19/Users/GetUserInfo.php", "POST", field, data);

                if (putData.startPut()) {
                    System.out.println("resut:1 ");
                    if (putData.onComplete()) {
                        System.out.println("resut:2 ");
                        String result = putData.getResult();
                        System.out.println("resut: " + result);


                        String[] arrOfStr = result.split(",", 4);
                        Fname.setText(arrOfStr[0]);
            Lname.setText(arrOfStr[1]);
            Email.setText(arrOfStr[2]);
            BD.setText(arrOfStr[3]);
//                        for (String a : arrOfStr)
//                            System.out.println(a);

                        //   Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
//                        if(result.equalsIgnoreCase("Login Success")){
//                            Toast.makeText(MyInfo.this, result, Toast.LENGTH_SHORT).show();
//
//                   //         intent2 = new Intent(MyInfo.this, HomePage.class);
//                     //       startActivity(intent2);
//
//                        }else{
//                            Toast.makeText(MyInfo.this, "Email or Password wrong", Toast.LENGTH_SHORT).show();
//
//                        }


                    }
                }
                //End Write and Read data with URL
            }
        });
        //-------------------------------------------------------

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserFName = Fname.getText().toString();
                EditUserLName = Lname.getText().toString();

                //---------------------------------------------
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[3];
                        field[0] = "Email";
                        field[1] = "FirstName";
                        field[2] = "LastName";

                        //Creating array for data
                        String[] data = new String[3];
                        data[0] = emailLogin;
                        data[1] = EditUserFName;
                        data[2] = EditUserLName;

                        PutData putData = new PutData("http://192.168.100.19/Users/UpdateName.php", "POST", field, data);

                        if (putData.startPut()) {

                            if (putData.onComplete()) {

                                String result = putData.getResult();


                                intent2 =new Intent(getApplicationContext(),Profile.class);
                    intent2.putExtra("LoginEmailInfo", emailLogin);
                    startActivity(intent2);
//                        for (String a : arrOfStr)
//                            System.out.println(a);

                                //   Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
//                        if(result.equalsIgnoreCase("Login Success")){
//                            Toast.makeText(MyInfo.this, result, Toast.LENGTH_SHORT).show();
//
//                   //         intent2 = new Intent(MyInfo.this, HomePage.class);
//                     //       startActivity(intent2);
//
//                        }else{
//                            Toast.makeText(MyInfo.this, "Email or Password wrong", Toast.LENGTH_SHORT).show();
//
//                        }


                            }else{
                                Toast.makeText(MyInfo.this, "update is failed", Toast.LENGTH_LONG).show();
//

                            }
                        }
                        //End Write and Read data with URL
                    }
                });
                //-------------------------------------------------------
                //boolean result =true;

                // = dbHelper.EditName(emailLogin, EditUserFName,EditUserLName);



//                if (result){
//                  //  Intent intentProfile
//                            intent2 =new Intent(getApplicationContext(),Profile.class);
//                    intent2.putExtra("LoginEmailInfo", UserEmailForIntent);
//                    startActivity(intent2);
//                }else {
//                    Toast.makeText(MyInfo.this, "update is failed", Toast.LENGTH_LONG).show();
//                }
            }
        });

    }
}

