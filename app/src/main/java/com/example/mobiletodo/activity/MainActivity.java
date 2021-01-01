package com.example.mobiletodo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobiletodo.R;
import com.example.mobiletodo.Validator;
import com.example.mobiletodo.controler.UserControler;
import com.example.mobiletodo.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button createProfile;
    private static Context context;
    private DatabaseReference mDatabase;
    private UserControler userControler;
    private Validator validator;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();
        userControler = new UserControler(context);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        validator = new Validator();

        if(userControler.checkSavedData()){
            openToDo();
        }

            loginButton = findViewById(R.id.logInButton);
            createProfile = findViewById(R.id.createProfileButton);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   loginToDO();
                }
            });

            createProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createProfile();
                }
            });


    }

    private void loginToDO() {

        EditText login = findViewById(R.id.emailInput);
        EditText password = findViewById(R.id.passwordInput);

        if (login.getText().toString().equals("") || password.getText().equals("") || login.getText().toString().equals(null) || password.getText().equals(null)) {
            Toast toast = Toast.makeText(context, "Uzupełnij pola", Toast.LENGTH_LONG);
            toast.show();
        } else {

            if(validator.isEmailValid(login.getText().toString())) {

                String email = login.getText().toString().replace(".", "");

                mDatabase.child("Users").child("User_" + email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.getResult().exists()) {
                            User tmp;
                            tmp = task.getResult().getValue(User.class);
                            if (tmp.getEmail().equals(email) && tmp.getPassword().equals(password.getText().toString())) {
                                userControler.saveUser(tmp);
                                openToDo();
                            }
                        } else {
                            Toast toast = Toast.makeText(context, "Podano błędny e-mail lub hasło", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
            else {
                Toast toast = Toast.makeText(context, "Wprowaź poprawny e-mail", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private void openToDo() {
        Intent intent = new Intent(this, ToDoCalendar.class);
        startActivity(intent);
    }

    private void createProfile(){
        Intent intent = new Intent(this, CreateProfile.class);
        startActivity(intent);
    }

}

