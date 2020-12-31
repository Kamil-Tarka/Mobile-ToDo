package com.example.mobiletodo.controler;

import android.content.Context;

import com.example.mobiletodo.entity.User;

import java.util.ArrayList;

public class UserControler {
    private final String filename = "user.json";
    Context fileContext;
    JsonHandler jsonHandler;

    public UserControler(Context fileContext) {
        this.fileContext = fileContext;
        this.jsonHandler = new JsonHandler(this.fileContext);
    }

    public boolean checkSavedData() {

        if (jsonHandler.fileExists(filename)) {
            return true;
        }
        return false;
    }

    public boolean saveUser(User user) {
        if (jsonHandler.saveToJSON(user, filename)) {
            return true;
        }
        return false;
    }

    public User createUser( String name, String email, String password){
        return new User(name, email, password);
    }

}
