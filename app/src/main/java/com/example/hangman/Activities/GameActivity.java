package com.example.hangman.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.daimajia.androidanimations.library.Techniques;
//import com.daimajia.androidanimations.library.YoYo;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangman.API.ApiGame;
import com.example.hangman.API.ApiGenW;
import com.example.hangman.API.Statistic;
import com.example.hangman.Activities.Interfaces.IGameActivity;
import com.example.hangman.Models.Word;
import com.example.hangman.R;
import com.example.hangman.db.Database;
import com.example.hangman.db.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class GameActivity extends AppCompatActivity implements IGameActivity {

    StringBuilder wordToGuess_ = new StringBuilder(), letters = new StringBuilder();
    String wordToGuess, playerInput, allChars = "abcdefghijklmnopqrstuvwxyz";
    int badGuesses = 1;
    private ApiGame apiGame;
    private ApiGenW apiWord;
    TextView categoryTextView;
    TextView wordToGuessTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        apiGame = new ApiGame("http://10.0.2.2:5000", null);
        apiWord = new ApiGenW("http://10.0.2.2:5000", this);

        wordToGuessTextView = findViewById(R.id.wordToGuessTextView);
        TextView lettersTextView = findViewById(R.id.letters);
        TextView won = findViewById(R.id.won);
        TextView lost = findViewById(R.id.lost);
        Button btnEnter = findViewById(R.id.btnEnter);
        EditText input_enterALetter = findViewById(R.id.input_enterALetter);
        ImageView ivHangman = findViewById(R.id.hangman);
        categoryTextView = findViewById(R.id.category);
        Date startTime = Calendar.getInstance().getTime();
        apiWord.getWord();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wrongGuess = true;
                playerInput = input_enterALetter.getText().toString().toLowerCase();
                input_enterALetter.setText("");

                if (allChars.contains(playerInput) && !playerInput.equals("")) {

                    for (int i = 0; i < wordToGuess.length(); i++) {
                        if (wordToGuess.charAt(i) == playerInput.charAt(0) && letters.indexOf(playerInput.toUpperCase()) == -1) {
                            wordToGuess_.setCharAt(i, playerInput.charAt(0));
                            wordToGuessTextView.setText(wordToGuess_);
                            wrongGuess = false;
                        }
                    }
                    //Program will add a letter that player has entered to the textview.
                    if (allChars.contains(playerInput) && letters.indexOf(playerInput.toUpperCase()) == -1) {
                        letters.append(playerInput.toUpperCase()).append(", ");
                        lettersTextView.setText(letters);
                    }

                    if (wrongGuess) {
                        badGuesses += 1;
                        ivHangman.setImageResource(getResources().getIdentifier("hangman" + badGuesses, "drawable", getPackageName()));
                        if (badGuesses == 7) {
                            lost.setVisibility(View.VISIBLE);

                            Database db = Database.getDBinstance(GameActivity.this);
                            User user = db.userDao().getFirst();
                            if (user != null) {
                                System.out.println(user.getId());
                                Date endTime = Calendar.getInstance().getTime();
                                System.out.println(String.format("Start time: %s", timeFormat(startTime)));
                                System.out.println(String.format("End time: %s", timeFormat(endTime)));
                                long durationL = TimeUnit.MILLISECONDS.toSeconds(endTime.getTime() - startTime.getTime());
                                Date duration = new Date(durationL * 1000);
                                System.out.println(String.format("duration: %s", timeFormat(duration)));
                                Statistic stat = new Statistic(user.getId(), false, duration);
                                System.out.println(stat);
                                apiGame.sendStat(stat);
                            }

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                            startActivity(new Intent(GameActivity.this, MainActivity.class));

                                        }
                                    });
                                }
                            }, 3000);
                        }
                    }

                    if (wordToGuess_.indexOf("_") == -1) {
                        won.setVisibility(View.VISIBLE);

                        Database db = Database.getDBinstance(GameActivity.this);
                        User user = db.userDao().getFirst();
                        if (user != null) {
                            System.out.println(user.getId());
                            Date endTime = Calendar.getInstance().getTime();
                            System.out.println(String.format("Start time: %s", timeFormat(startTime)));
                            System.out.println(String.format("End time: %s", timeFormat(endTime)));
                            long durationL = TimeUnit.MILLISECONDS.toSeconds(endTime.getTime() - startTime.getTime());
                            Date duration = new Date(durationL * 1000);
                            System.out.println(String.format("duration: %s", timeFormat(duration)));
                            Statistic stat = new Statistic(user.getId(), true, duration);
                            System.out.println(stat);
                            apiGame.sendStat(stat);
                        }


                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(GameActivity.this, MainActivity.class));
                                    }
                                });
                            }
                        }, 3000);
                    }
                } else {
                    Toast.makeText(GameActivity.this, "This is not a letter", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void handler(Word word){
        if (!word.getWord().equals("")) {
            System.out.println("word get");
            wordToGuess = word.getWord();
            System.out.println(wordToGuess);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (word.getCategory().equals("fruits")) {
                        categoryTextView.setHint("Category: Fruits");
                    } else {
                        categoryTextView.setHint("Category: Vegetables");
                    }
                }
            });


            for (int i = 0; i < wordToGuess.length(); i++)
            {
                wordToGuess_.append("_ ");
                wordToGuessTextView.setText(wordToGuess_);
            }
            char[] wordToGuessArray = wordToGuess.toCharArray();
            StringBuilder temp = new StringBuilder();
            for(char c : wordToGuessArray)
            {
                temp.append(c).append(" ");
            }
            wordToGuess = temp.toString();

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(GameActivity.this, "Don't know word", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String timeFormat(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(time);
    }

}
