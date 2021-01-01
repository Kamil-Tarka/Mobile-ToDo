package com.example.mobiletodo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mobiletodo.R;
import com.example.mobiletodo.controler.ToDoControler;
import com.example.mobiletodo.controler.UserControler;

public class Options extends AppCompatActivity {
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        Options.context = getApplicationContext();

        LinearLayout toCalendar = findViewById(R.id.toCalendar);
        LinearLayout about = findViewById(R.id.about);
        LinearLayout logout = findViewById(R.id.logOut);

        toCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Options.this);
                builder.setTitle("O aplikacji");
                builder.setMessage("Jest to projekt realizowany w ramach przedmiotu \"Wstęp do systemów mobilnych\"");
                builder.setCancelable(true);
                /*builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Options.this.finish();
                    }
                });*/
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

    }

    public void goToCalendar(){
        Intent intent = new Intent(this, ToDoCalendar.class);
        startActivity(intent);

    }

    public void logOut(){
        UserControler userControler = new UserControler(context);
        ToDoControler toDoControler = new ToDoControler(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(Options.this);
        builder.setTitle("Czy na pewno?");
        builder.setMessage("Jeśli się wylogujesz twój lokalny profil zostanie usunięty wraz z lokalną kopią danych.");
        builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userControler.removeUser();
                        toDoControler.removeToDos();
                        toMain();
                    }
                });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void toMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}