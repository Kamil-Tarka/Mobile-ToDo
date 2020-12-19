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

public class MainActivity extends AppCompatActivity {

    ArrayList<ToDo> toDos = new ArrayList<ToDo>();
    LinearLayout linearLayout;
    String calendarDate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linear);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String date = dateFormat.format(calendar.getTime());

        calendarDate=date;
        showToDo(calendarDate);
        CalendarView calendarView = findViewById(R.id.calendarView);
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
        ToDo toDo = new ToDo(1,1,date,hour.getText().toString(),title.getText().toString());
        toDos.add(toDo);

        showToDo(date);
    }

    public void showToDo(String date){
        linearLayout.removeAllViews();
        for(int i=0; i<toDos.size(); i++){
            if(date.equals(toDos.get(i).getDate()) || toDos.get(i).getDate().equals(date)) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(toDos.get(i).getHour() + " " + toDos.get(i).getContent()+" "+toDos.get(i).getDate());
                linearLayout.addView(textView);
            }
        }

    }

}