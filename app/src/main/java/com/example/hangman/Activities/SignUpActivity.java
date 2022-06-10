package com.example.hangman.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangman.API.ApiAuth;
import com.example.hangman.Activities.Interfaces.IAuthActivity;
import com.example.hangman.Models.AuthData;
import com.example.hangman.db.Database;
import com.example.hangman.db.User;
import com.example.hangman.R;

import java.util.List;

public class SignUpActivity extends AppCompatActivity implements IAuthActivity {

    ApiAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_view);
        EditText login = findViewById(R.id.login);
        EditText pass = findViewById(R.id.password);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        ImageView returnIcon = findViewById(R.id.returnIcon);
        auth = new ApiAuth("http://10.0.2.2:5000", this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Enter login and password", Toast.LENGTH_SHORT).show();
                } else {
                    AuthData authData = new AuthData(login.getText().toString(), pass.getText().toString());
                    auth.signUp(authData);
                }
            }
        });

        returnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    public void handler(User user) {
        if (user.getId() != -1) {
            System.out.println("user signuped");

            Database db = Database.getDBinstance(SignUpActivity.this);
            db.userDao().insert(user);

            startActivity(new Intent(SignUpActivity.this, GameActivity.class));
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignUpActivity.this, "User is already exists", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
