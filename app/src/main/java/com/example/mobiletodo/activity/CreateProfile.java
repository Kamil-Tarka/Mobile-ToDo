package com.example.mobiletodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobiletodo.R;
import com.example.mobiletodo.entity.User;

public class CreateProfile extends AppCompatActivity {

    private Button createProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        createProfileButton = findViewById(R.id.createProfileButton);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });
    }

    private void createProfile(){
        
    }

}