package com.example.mobiletodo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobiletodo.R;
import com.example.mobiletodo.controler.JsonHandler;
import com.example.mobiletodo.controler.ToDoControler;
import com.example.mobiletodo.entity.ToDo;

import java.util.ArrayList;

public class ToDoCalendar extends AppCompatActivity {

    ArrayList<ToDo> toDos = new ArrayList<ToDo>();
    LinearLayout linearLayout;
    String calendarDate;
    private static Context context;
    JsonHandler jsonHandler;
    ToDoControler toDoControler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_calendar);

            setContentView(R.layout.activity_to_do_calendar);
            ToDoCalendar.context = getApplicationContext();
            toDoControler = new ToDoControler(context);
            jsonHandler = new JsonHandler(context);

            CalendarView calendarView = findViewById(R.id.calendarView);
            linearLayout = findViewById(R.id.linear);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar date = Calendar.getInstance();
            calendarDate = simpleDateFormat.format(date.getTime());

            if (toDoControler.checkSavedData()) {
                toDos = toDoControler.updateToDos();
                showToDo(calendarDate);
            }

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    calendarDate = dayOfMonth + "-" + (month + 1) + "-" + year;
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

        public void addToDo(String date) {
            EditText hour = findViewById(R.id.hourInput);
            EditText minute = findViewById(R.id.minuteInput);
            EditText content = findViewById(R.id.titleInput);

            toDos.add(toDoControler.createToDo(toDos.get(toDos.size()-1).getId()+1, date, hour.getText().toString() + ":" + minute.getText().toString(), content.getText().toString()));
            toDoControler.saveToDos(toDos);
            showToDo(date);
        }

        public void showToDo(String date) {
            linearLayout.removeAllViews();
            for (ToDo toDo : toDos) {
                if (toDo.getDate().equals(date)) {
                    LinearLayout linearLayout2 = new LinearLayout(ToDoCalendar.this);
                    linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                    TextView textView = new TextView(ToDoCalendar.this);
                    textView.setTextSize(18);
                    textView.setText(toDo.getHour() + " " + toDo.getContent());
                    linearLayout2.addView(textView);
                    TextView textView1 = new TextView(ToDoCalendar.this);
                    textView1.setTextSize(18);
                    textView1.setText(" ID: "+toDo.getId());
                    linearLayout2.addView(textView1);
                    linearLayout.addView(linearLayout2);
                }
            }
        }

    }