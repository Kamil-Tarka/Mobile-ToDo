package com.example.mobiletodo.controler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.mobiletodo.NetworkManager;
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
    private NetworkManager networkManager;
    public ToDoControler(Context fileContext) {
        this.fileContext = fileContext;
        this.jsonHandler = new JsonHandler(this.fileContext);
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.networkManager = new NetworkManager(fileContext);
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
            if(networkManager.checkConnection()){
                mDatabase.child("ToDos").child("User_"+user.getEmail()).setValue(toDos);
            }
            else{
                Toast toast = Toast.makeText(fileContext, "Z powodu braku dostępu do sieci, dane zostały zapisane lokalnie. Aplikacja przeprowadzi synchronizację przy najbliższej okazji.", Toast.LENGTH_LONG);
                toast.show();
            }

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

    public void syncData(ArrayList<ToDo> toDos, User user){
        if(networkManager.checkConnection()){
            mDatabase.child("ToDos").child("User_"+user.getEmail()).setValue(toDos);
        }
    }

}
