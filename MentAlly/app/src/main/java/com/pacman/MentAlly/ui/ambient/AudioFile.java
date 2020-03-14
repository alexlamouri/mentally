package com.pacman.MentAlly.ui.ambient;

public class AudioFile {

    private String data;
    private String title;
    private String length;

    public AudioFile(String data, String audioTitle) {
        this.data = data;
        this.title = audioTitle;
    }

    public AudioFile(String data, String audioTitle, String audioLength) {
        this.data = data;
        this.title = audioTitle;
        this.length = audioLength;
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
}
