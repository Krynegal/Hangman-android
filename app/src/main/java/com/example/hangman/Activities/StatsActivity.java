package com.example.hangman.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangman.API.ApiAuth;
import com.example.hangman.API.ApiGame;
import com.example.hangman.Activities.Interfaces.IStatActivity;
import com.example.hangman.Models.UserStatistic;
import com.example.hangman.R;
import com.example.hangman.db.Database;
import com.example.hangman.db.User;

public class StatsActivity extends AppCompatActivity implements IStatActivity {

    TextView wins;
    TextView winRate;
    TextView gamesNum;
    TextView bestTime;
    TextView winStreak;
    TextView bestWinStreak;
    ImageView returnIcon;

    private ApiGame apiGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        wins = findViewById(R.id.wins);
        winRate = findViewById(R.id.winRate);
        gamesNum = findViewById(R.id.gamesNum);
        //bestTime = findViewById(R.id.bestTime);
        winStreak = findViewById(R.id.winStreak);
        bestWinStreak = findViewById(R.id.bestWinStreak);
        returnIcon = findViewById(R.id.returnIcon);
        apiGame = new ApiGame("http://10.0.2.2:5000", this);

        Database db = Database.getDBinstance(StatsActivity.this);
        User user = db.userDao().getFirst();
        if (user != null) {
            System.out.println(user.getId());
            apiGame.getStats(user.getId());
        }

        returnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(StatsActivity.this, MainActivity.class));
            }
        });
    }

    public void handler(UserStatistic userStats){
        if (userStats.getUser_id() != -1) {
            System.out.println("user's stats");
            System.out.println(userStats);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showStat(userStats);
                }
            });

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(StatsActivity.this, "no stats", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void showStat(UserStatistic userStats) {
        wins.setText(Integer.toString(userStats.getWins_num()));
        winRate.setText(String.format("%.2f", userStats.getWin_rate()));
        gamesNum.setText(Integer.toString(userStats.getGames_num()));
        //String bestTimeStr = userStats.getBest_time();
        //bestTime.setText(bestTimeStr.substring(bestTimeStr.length() - 5));
        winStreak.setText(Integer.toString(userStats.getCur_win_streak()));
        bestWinStreak.setText(Integer.toString(userStats.getBest_win_streak()));
    }

}
