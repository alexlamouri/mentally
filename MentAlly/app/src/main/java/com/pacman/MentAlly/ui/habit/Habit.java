package com.pacman.MentAlly.ui.habit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Observable;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Store information about a habit object
 */
public class Habit extends Observable{

    private String habitId;
    private String habitName;
    private String frequency;
    private Calendar startDate;
    private Calendar endDate;
    private int progress;
    private int maxProgress;

    public Habit(String id, String name, Calendar start, Calendar end, String freq, int progress) {
        this.habitId = id;
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

    public String getHabitId() {
        return this.habitId;
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

    public void incrementProgress() {
        this.progress += 1;
        setChanged();
        notifyObservers(this);
    }

}
