package com.pacman.MentAlly.ui.ambient;

import java.util.Observable;

public class AudioFile extends Observable {

    private String data;
    private String title;
    private String length;
    private boolean isPlaying;

    public AudioFile(String data, String audioTitle, String audioLength) {
        this.data = data;
        this.title = audioTitle;
        this.length = audioLength;
        this.isPlaying = false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAudioLength(String audioLength) {
        this.length = audioLength;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public String getAudioLength() {
        return length;
    }

    public String getData() {
        return data;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void startPlaying() {
        isPlaying = true;
        setChanged();
        notifyObservers(this);
    }

    public void stopPlaying() {
        isPlaying = false;
        setChanged();
        notifyObservers(this);
    }
}
