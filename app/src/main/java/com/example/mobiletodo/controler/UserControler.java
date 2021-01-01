package com.example.mobiletodo.controler;

import android.content.Context;
import android.util.Log;

import com.example.mobiletodo.entity.ToDo;
import com.example.mobiletodo.entity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserControler {

    private DatabaseReference mDatabase;
    private final String filename = "user.json";
    Context fileContext;
    JsonHandler jsonHandler;

    public UserControler(Context fileContext) {
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

    public User readUser() {
        return (User) jsonHandler.readUserFromJSON(filename);
    }

    public boolean saveUser(User user) {
        if (jsonHandler.saveToJSON(user, filename)) {
            Log.i("user", jsonHandler.readUserFromJSON(filename).toString());
            mDatabase.child("Users").child("User_"+user.getEmail()).setValue(user);
            return true;
        }
        return false;
    }

    public boolean removeUser(){
        if(jsonHandler.removeJson(filename)){
            return true;
        }
        return false;
    }

}
