package com.example.mobiletodo.controler;

import android.content.Context;

import com.example.mobiletodo.entity.ToDo;
import com.example.mobiletodo.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JsonHandler {
    Context fileContext;

    public JsonHandler(Context fileContext) {
        this.fileContext = fileContext;
    }

    public boolean saveToJSON(Object o, String file) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fileOutputStream = fileContext.getApplicationContext().openFileOutput(file, Context.MODE_PRIVATE);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean fileExists(String filename) {
        File file = fileContext.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    public String readJSONStr(String json) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fileInputStream = fileContext.getApplicationContext().openFileInput(json);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String tmp;
            while ((tmp = bufferedReader.readLine()) != null) {
                stringBuilder.append(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public ArrayList<ToDo> readToDosFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = readJSONStr(json);
        ArrayList<ToDo> toDos = null;
        if (jsonStr != null) {
            try {
                toDos = (ArrayList<ToDo>) mapper.readValue(jsonStr, new TypeReference<ArrayList<ToDo>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toDos;
    }

    public User readUserFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = readJSONStr(json);
        User user = null;
        if (jsonStr != null) {
            try {
                user = mapper.readValue(jsonStr, new TypeReference<User>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

}
