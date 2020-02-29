package com.pacman.MentAlly.ui.habit;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Store information about a habit object
 */
public class Habit {

    private String habitName;
    private String frequency;
    private Calendar startDate;
    private Calendar endDate;
    private int progress;
    private int maxProgress;

    public Habit(String name, Calendar start, Calendar end, String freq, int progress) {
        this.habitName = name;
        this.frequency = freq;
        this.startDate = start;
        this.endDate = end;
        this.progress = progress;
        this.setMaxProgress();
    }

    private void setMaxProgress() {
        this.maxProgress = 0;
        int daysBetween = (int) ChronoUnit.DAYS.between(this.startDate.toInstant(), this.endDate.toInstant());
        if (this.frequency.equalsIgnoreCase("daily")) {
            this.maxProgress = daysBetween;
        } else if (this.frequency.equalsIgnoreCase("weekly")) {
            this.maxProgress = daysBetween / 7;
        } else {
            this.maxProgress = daysBetween / 28;
        }
    }

    public String getHabitName() {
        return this.habitName;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void incrementProgress(int diff) {
        this.progress += diff;
    }

}
