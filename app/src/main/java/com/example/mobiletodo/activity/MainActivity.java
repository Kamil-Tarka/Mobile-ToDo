package com.example.mobiletodo.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobiletodo.R;


public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button createProfile;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.logInButton);
        createProfile = findViewById(R.id.createProfileButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openToDo();
            }
        });

        createProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });

    }

    public void openToDo() {
        Intent intent = new Intent(this, ToDoCalendar.class);
        startActivity(intent);
    }

    public void createProfile(){
        Intent intent = new Intent(this, CreateProfile.class);
        startActivity(intent);
    }

}

