package com.example.hangman.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {User.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public abstract UserDao userDao();

    public static Database getDBinstance(Context context) {
        if (INSTANCE==null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "database.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
