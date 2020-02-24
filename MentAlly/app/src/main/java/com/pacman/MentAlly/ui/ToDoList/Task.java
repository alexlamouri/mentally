package com.pacman.MentAlly.ui.ToDoList;

import java.util.Date;

public class Task {
    private String name; //mandatory
    private String start_date; //optional
    private String end_date; //optional

    public Task(String name, String start_date, String end_date) {
        this.name = name;
        if (start_date != null) {
            this.start_date = start_date;
        }
        if (end_date != null) {
            this.end_date = end_date;
        }
    }

    public String getTaskName() {
        return this.name;
    }

    public String getEnd_date() {
        return this.end_date;
    }

    public String getStart_date() {
        return this.start_date;
    }

    public void setTaskName(String name) {
        this.name = name;
    }

    public void setStart_date(String date) {
        this.start_date = date;
    }

    public void setEnd_date(String date) {
        this.end_date = date;
    }

}
