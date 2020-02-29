package com.pacman.MentAlly.ui.habit;

import java.util.Calendar;

/**
 * Store information about a habit object
 */
public class Habit {

    private String habitName;
    private String endDate;
    private int progress;

    public Habit(String name, String date, int progress) {
        this.habitName = name;
        this.endDate = date;
        this.progress = progress;
    }

    public String getHabitName() {
        return this.habitName;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public int getProgress() {
        return this.progress;
    }

    public void incrementProgress(int diff) {
        this.progress += diff;
    }

}
