package com.example.mobiletodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobiletodo.R;
import com.example.mobiletodo.controler.UserControler;
import com.example.mobiletodo.entity.User;

public class CreateProfile extends AppCompatActivity {

    private static Context context;
    private Button createProfileButton;
    private UserControler userControler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        CreateProfile.context = getApplicationContext();
        userControler = new UserControler(context);

        createProfileButton = findViewById(R.id.saveProfileBtn);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });
    }

    private void createProfile() {
        EditText email = findViewById(R.id.createEmailInput);
        EditText password = findViewById(R.id.createPasswordInput);

        if (email.getText().toString().equals("") || password.getText().equals("") || email.getText().toString().equals(null) || password.getText().equals(null)) {
            Toast toast = Toast.makeText(context, "Uzupełnij pola", Toast.LENGTH_LONG);
            toast.show();
        } else {
            String tmp = email.getText().toString();

            User user = new User(tmp.replace(".", ""), password.getText().toString());
            Log.i("TESTU", user.toString());
            userControler.saveUser(user);
            Toast toast = Toast.makeText(context, "Zapisano profil", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, ToDoCalendar.class);
            startActivity(intent);
        }
    }

}