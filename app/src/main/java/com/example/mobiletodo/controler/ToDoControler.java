package com.example.mobiletodo.controler;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;

import com.example.mobiletodo.activity.ToDoCalendar;
import com.example.mobiletodo.entity.ToDo;
import com.example.mobiletodo.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class ToDoControler {

    private DatabaseReference mDatabase;
    private final String filename = "todo.json";
    Context fileContext;
    JsonHandler jsonHandler;

    public ToDoControler(Context fileContext) {
        this.fileContext = fileContext;
        this.jsonHandler = new JsonHandler(this.fileContext);
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public boolean checkSavedData() {

        if (jsonHandler.fileExists(filename)) {
            return true;
        }
        return false;
    }

    public ArrayList<ToDo> updateToDos() {
        return (ArrayList<ToDo>) jsonHandler.readToDosFromJSON(filename);
    }

    public boolean saveToDos(ArrayList<ToDo> toDos, User user) {
        if (jsonHandler.saveToJSON(toDos, filename)) {
            mDatabase.child("ToDos").child("User_"+user.getEmail()).setValue(toDos);
            return true;
        }
        return false;
    }

    public boolean removeToDos(){
        if(jsonHandler.removeJson(filename)){
            return true;
        }
        return false;
    }

}
