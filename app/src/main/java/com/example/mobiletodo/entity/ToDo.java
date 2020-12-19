package com.example.mobiletodo.entity;

public class ToDo {
    int id;
    int userId;
    String date;
    String hour;
    String content;

    public ToDo(int id, int userId, String date, String hour, String content) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.hour = hour;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getUserId() {
        return userId;
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
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
