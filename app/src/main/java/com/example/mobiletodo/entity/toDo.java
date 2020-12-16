package com.example.mobiletodo.entity;

public class toDo {
    int id;
    String date;
    String hour;
    String content;

    public toDo(int id, String date, String hour, String content) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getContent() {
        return content;
    }
}
