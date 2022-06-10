package com.example.hangman.API;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Statistic {
    private boolean won;
    private int userId;
    private Date duration;


    public Statistic(int userId, boolean won, Date duration) {
        this.userId = userId;
        this.won = won;
        this.duration = duration;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("{\"user_id\":%d, \"won\":%b, \"duration\":\"%s\"}", userId, won, duration.getTime());
    }

}
