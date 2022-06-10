package com.example.hangman.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users LIMIT 1")
    User getFirst();

    @Query("SELECT * FROM users WHERE id = :id")
    User getById(long id);

    @Query("DELETE FROM users")
    void delete();

    @Insert
    void insert(User user);


}
