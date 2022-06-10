package com.example.hangman.Models;

public class UserStatistic {

    private int user_id;
    private int games_num;
    private float win_rate;
    private int wins_num;
    private String best_time;
    private int cur_win_streak;
    private int best_win_streak;

    public UserStatistic(int userId, int gamesNum, float winRate, int winStreak, int winsNum, int bestWinStreak, String bestTime) {
        this.user_id = userId;
        this.games_num = gamesNum;
        this.win_rate = winRate;
        this.cur_win_streak = winStreak;
        this.wins_num = winsNum;
        this.best_win_streak = bestWinStreak;
        this.best_time = bestTime;
    }

    @Override
    public String toString() {
        return String.format("{" +
                "\"userId\":%d, " +
                "\"gamesNum\":%d, " +
                "\"winRate\":%f, " +
                "\"winsNum\":%d, " +
                "\"bestTime\":\"%s\", " +
                "\"winStreak\":%d, " +
                "\"bestWinStreak\":%d " +
                "}", user_id, games_num, win_rate, wins_num, best_time, cur_win_streak, best_win_streak);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getWin_rate() {
        return win_rate;
    }

    public int getGames_num() { return games_num; }

    public void setGames_num(int games_num) {
        this.games_num = games_num;
    }

    public void setWin_rate(float win_rate) {
        this.win_rate = win_rate;
    }

    public String getBest_time() {
        return best_time;
    }

    public void setBest_time(String best_time) {
        this.best_time = best_time;
    }

    public int getWins_num() {
        return wins_num;
    }

    public void setWins_num(int wins_num) {
        this.wins_num = wins_num;
    }

    public int getCur_win_streak() {
        return cur_win_streak;
    }

    public void setCur_win_streak(int cur_win_streak) {
        this.cur_win_streak = cur_win_streak;
    }

    public int getBest_win_streak() {
        return best_win_streak;
    }

    public void setBest_win_streak(int best_win_streak) {
        this.best_win_streak = best_win_streak;
    }
}
