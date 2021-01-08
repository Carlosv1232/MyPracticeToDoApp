package com.example.mytodoapp.Model;

// This is going to be used to define the model of the individual tasks

public class ToDoModel {
    private int id; //id will be used to reference from database
    private int status; //is a boolean variable, But in SQLite, they don't take bool, therefore int
    private String task; //string that will hold task

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
