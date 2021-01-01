package com.example.mobiletodo.entity;

public class ToDo {
    int id;
    String userEmail;
    String date;
    String hour;
    String content;

    public ToDo(){}

    public ToDo(Object value){

    }

    public ToDo(int id, String userEmail, String date, String hour, String content) {
        this.id = id;
        this.userEmail = userEmail;
        this.date = date;
        this.hour = hour;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
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

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
