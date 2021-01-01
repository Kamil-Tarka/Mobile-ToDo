package com.example.mobiletodo.controler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.mobiletodo.NetworkManager;
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
    private NetworkManager networkManager;

    public UserControler(Context fileContext) {
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

    public User readUser() {
        return (User) jsonHandler.readUserFromJSON(filename);
    }

    public boolean saveUser(User user) {
        if (jsonHandler.saveToJSON(user, filename)) {
            if(networkManager.checkConnection()){
                mDatabase.child("Users").child("User_"+user.getEmail()).setValue(user);
            }
            else{
                Toast toast = Toast.makeText(fileContext, "Z powodu braku dostępu do sieci, dane zostały zapisane lokalnie. Aplikacja przeprowadzi synchronizację przy najbliższej okazji.", Toast.LENGTH_LONG);
                toast.show();
            }
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

    public void syncData(User user){
        if(networkManager.checkConnection()){
            mDatabase.child("Users").child("User_"+user.getEmail()).setValue(user);
        }
    }

}
