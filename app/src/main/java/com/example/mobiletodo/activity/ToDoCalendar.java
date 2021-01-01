package com.example.mobiletodo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobiletodo.R;
import com.example.mobiletodo.controler.JsonHandler;
import com.example.mobiletodo.controler.ToDoControler;
import com.example.mobiletodo.controler.UserControler;
import com.example.mobiletodo.entity.ToDo;
import com.example.mobiletodo.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToDoCalendar extends AppCompatActivity {

    ArrayList<ToDo> toDos = new ArrayList<ToDo>();
    LinearLayout linearLayout;
    private String calendarDate;
    private static Context context;
    private ToDoControler toDoControler;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private DatabaseReference mDatabase;
    private User user;
    private UserControler userControler;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_calendar);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setContentView(R.layout.activity_to_do_calendar);
        ToDoCalendar.context = getApplicationContext();

        toDoControler = new ToDoControler(context);
        userControler = new UserControler(context);

        CalendarView calendarView = findViewById(R.id.calendarView);
        linearLayout = findViewById(R.id.linear);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar date = Calendar.getInstance();
        calendarDate = simpleDateFormat.format(date.getTime());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(userControler.checkSavedData()) {
            user = userControler.readUser();
            Log.i("user", user.toString());

            if (toDoControler.checkSavedData()) {
                toDos = toDoControler.updateToDos();
                showToDo(calendarDate);
            } else {
                mDatabase.child("ToDos").child("User_" + user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                            ToDo toDo;
                            toDo = dataSnapshot.getValue(ToDo.class);
                            toDos.add(toDo);
                        }
                        showToDo(calendarDate);
                    }
                });
            }
        }
        else {
            Intent intent = new Intent(this, CreateProfile.class);
            startActivity(intent);
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (Integer.toString(dayOfMonth).length() < 2 && Integer.toString(month).length() < 2) {
                    calendarDate = "0" + dayOfMonth + "-0" + (month + 1) + "-" + year;
                } else if (Integer.toString(dayOfMonth).length() < 2) {
                    calendarDate = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;
                } else if (Integer.toString(month).length() < 2) {
                    calendarDate = dayOfMonth + "-0" + (month + 1) + "-" + year;
                } else {
                    calendarDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                }
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

        if(toDos.size()!=0) {
            toDos.add(new ToDo(toDos.get(toDos.size() - 1).getId()+1, "xD", date, hour.getText().toString() + ":" + minute.getText().toString(), content.getText().toString()));
            toDoControler.saveToDos(toDos, user);
        }
        else {
            toDos.add(new ToDo(1, "xD", date, hour.getText().toString() + ":" + minute.getText().toString(), content.getText().toString()));
            toDoControler.saveToDos(toDos, user);
        }
        showToDo(date);
    }

    public void showToDo(String date) {
        linearLayout.removeAllViews();
        Log.i("TODOS", toDos.toString());
        for (ToDo toDo : toDos) {
            if (toDo.getDate().equals(date)) {
                LinearLayout linearLayout2 = new LinearLayout(ToDoCalendar.this);
                linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout2.setPadding(0, 10, 0, 10);
                TextView textView = new TextView(ToDoCalendar.this);
                textView.setTextSize(18);
                textView.setText(toDo.getHour() + " " + toDo.getContent());
                linearLayout2.addView(textView);
                TextView button = new TextView(ToDoCalendar.this);
                button.setLayoutParams(new RelativeLayout.LayoutParams(100, 150));
                button.setText("X");
                button.setTextSize(18);
                button.setTextColor(Color.RED);

                textView.setWidth(displayMetrics.widthPixels - 180);
                final int index = toDos.indexOf(toDo);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(index);
                    }
                });
                linearLayout2.addView(button);
                linearLayout.addView(linearLayout2);
            }
        }
    }

    public void remove(int id) {
        toDos.remove(toDos.get(id));
        toDoControler.saveToDos(toDos, user);
        showToDo(calendarDate);
    }

}