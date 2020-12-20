package com.example.mobiletodo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobiletodo.entity.ToDo;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<ToDo> toDos = new ArrayList<ToDo>();
    LinearLayout linearLayout;
    String calendarDate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        linearLayout = findViewById(R.id.linear);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ToDo tmp = new ToDo(1, 1, "20-11-2020","13:13","xD");
        toDos.add(tmp);

        showToDo("20-11-2020");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendarDate = dayOfMonth + "-" + month + "-" + year;
                showToDo(calendarDate);
            }
        });

        Button button = findViewById(R.id.buttonToDo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDo(calendarDate);
            }
        });

    }

    public void addToDo(String date){
        EditText hour = findViewById(R.id.hourInput);
        EditText title = findViewById(R.id.titleInput);
        ToDo toDo = new ToDo(2,2,date,hour.getText().toString(),title.getText().toString());
        toDos.add(toDo);

        showToDo(date);
    }

    public void showToDo(String date){
        linearLayout.removeAllViews();
        for(ToDo toDo: toDos){
            if(toDo.getDate().equals(date)) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(toDo.getHour() + " " + toDo.getContent()+" "+toDo.getDate());
                linearLayout.addView(textView);
            }
        }

    }

}