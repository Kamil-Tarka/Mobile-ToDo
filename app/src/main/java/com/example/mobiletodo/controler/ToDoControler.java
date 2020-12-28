package com.example.mobiletodo.controler;

import android.content.Context;

import com.example.mobiletodo.entity.ToDo;

import java.util.ArrayList;

public class ToDoControler {

    private final String filename = "todo.json";
    Context fileContext;
    JsonHandler jsonHandler;

    public ToDoControler(Context fileContext) {
        this.fileContext = fileContext;
        this.jsonHandler = new JsonHandler(this.fileContext);
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

    public boolean saveToDos(ArrayList<ToDo> toDos) {
        if (jsonHandler.saveToJSON(toDos, filename)) {
            return true;
        }
        return false;
    }

    public ToDo createToDo(int id, String date, String hour, String content) {
        return new ToDo(id, 2, date, hour, content);
    }

}
