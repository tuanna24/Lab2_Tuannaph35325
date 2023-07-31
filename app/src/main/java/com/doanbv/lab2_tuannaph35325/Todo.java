package com.doanbv.lab2_tuannaph35325;

public class Todo {

    private String Title, Content, Date, Type;
    private int Status, ID;

    public Todo( int ID,String title, String content, String date, String type, int status) {
        Title = title;
        Content = content;
        Date = date;
        Type = type;
        Status = status;
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
