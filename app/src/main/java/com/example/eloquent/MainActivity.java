package com.example.eloquent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Button button_createAccount , button_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_createAccount = findViewById(R.id.buttonCreateAccount);
        button_Login = findViewById(R.id.buttonLogin);

        // If the user grants all permission the user can start using the program
        if (checkAndPermissionsRequest()) {
            //when user click on create account this code will move them to the create account page
            button_createAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
                }
            });
            //when user click on login this code will move them to the login page
            button_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Please grant permissions to access the app", Toast.LENGTH_LONG).show();
        }
    }
    // Method checkAndPermissionsRequest Check user permissions
    private  boolean checkAndPermissionsRequest() {
        int PermissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int StoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (PermissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (StoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
