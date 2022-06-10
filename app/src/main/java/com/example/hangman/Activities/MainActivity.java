package com.example.hangman.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hangman.R;
import com.example.hangman.db.Database;
import com.example.hangman.db.User;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPlay = findViewById(R.id.play);
        Button btnAuth = findViewById(R.id.authorization);
        ImageView statIcon = findViewById(R.id.statIcon);

        Database db = Database.getDBinstance(MainActivity.this);
        if (db.userDao().getAll().size() != 0) {
            User user = db.userDao().getFirst();
            System.out.println(user.getId());
            Toast.makeText(MainActivity.this, String.format("You are authorized as %s", user.getLogin()), Toast.LENGTH_SHORT).show();
        }
//        else {
//            Toast.makeText(MainActivity.this, "DB is empty", Toast.LENGTH_SHORT).show();
//        }

        List<User> l = db.userDao().getAll();
        System.out.println("что есть в базе данных:");
        System.out.println(l);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.userDao().getAll().size() != 0) {
                    // clear the table
                    db.userDao().delete();
                    List<User> l = db.userDao().getAll();
                    System.out.println("что есть в базе данных:");
                    System.out.println(l);
                    //Toast.makeText(MainActivity.this, "Table is clear now", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        statIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
            }
        });
    }

}