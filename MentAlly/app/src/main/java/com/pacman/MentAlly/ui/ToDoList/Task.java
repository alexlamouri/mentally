package com.pacman.MentAlly.ui.ToDoList;

import java.util.Date;

public class Task {
    private String name; //mandatory
    private Date start_date; //optional
    private Date end_date; //optional

    public Task(String name, Date start_date, Date end_date) {
        this.name = name;
        if (start_date != null) {
            this.start_date = start_date;
        }
        if (end_date != null) {
            this.end_date = end_date;
        }
    }

    public String getName() {
        return this.name;
    }

    public Date getEnd_date() {
        return this.end_date;
    }

    public Date getStart_date() {
        return this.start_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart_date(Date date) {
        this.start_date = date;
    }

    public void setEnd_date(Date date) {
        this.end_date = date;
    }

}
