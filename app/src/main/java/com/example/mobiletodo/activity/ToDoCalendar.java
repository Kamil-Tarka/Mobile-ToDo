package com.example.mobiletodo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobiletodo.NetworkManager;
import com.example.mobiletodo.R;
import com.example.mobiletodo.controler.ToDoControler;
import com.example.mobiletodo.controler.UserControler;
import com.example.mobiletodo.entity.ToDo;
import com.example.mobiletodo.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private NetworkManager networkManager;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_calendar);
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setContentView(R.layout.activity_to_do_calendar);
        ToDoCalendar.context = getApplicationContext();

        networkManager = new NetworkManager(context);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

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
                for(ToDo toDo: toDos) {
                    if (!toDo.getUserEmail().equals(user.getEmail())) {
                        Log.i("todoTest", toDos.get(toDos.size()-1).toString());
                        toDos.clear();
                        toDoControler.removeToDos();
                    }
                }
                showToDo(calendarDate);
            }
            else if(networkManager.checkConnection()){
                mDatabase.child("ToDos").child("User_" + user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                            ToDo toDo;
                            toDo = dataSnapshot.getValue(ToDo.class);

                            toDos.add(toDo);

                        }
                        showToDo(calendarDate);
                        toDoControler.saveToDos(toDos, user);
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
                toDoControler.syncData(toDos, user);
                userControler.syncData(user);
            }
        });

        toDoControler.syncData(toDos, user);
        userControler.syncData(user);

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
            toDos.add(new ToDo(toDos.get(toDos.size() - 1).getId()+1, user.getEmail(), date, hour.getText().toString() + ":" + minute.getText().toString(), content.getText().toString()));
            toDoControler.saveToDos(toDos, user);
        }
        else {
            toDos.add(new ToDo(1, user.getEmail(), date, hour.getText().toString() + ":" + minute.getText().toString(), content.getText().toString()));
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
                textView.setTextColor(Color.BLACK);
                linearLayout2.addView(textView);
                TextView del = new TextView(ToDoCalendar.this);
                del.setLayoutParams(new RelativeLayout.LayoutParams(100, 150));
                del.setText("X");
                del.setTextSize(18);
                del.setTextColor(Color.RED);

                textView.setWidth(displayMetrics.widthPixels - 180);
                final int index = toDos.indexOf(toDo);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(index);
                    }
                });
                linearLayout2.addView(del);
                linearLayout.addView(linearLayout2);
            }
        }
    }

    public void remove(int id) {
        toDos.remove(toDos.get(id));
        toDoControler.saveToDos(toDos, user);
        showToDo(calendarDate);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}