package com.example.hangman.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity implements IAuthActivity {

    private ApiAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        EditText login = findViewById(R.id.login);
        EditText pass = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.logInButton);
        Button btnCreate = findViewById(R.id.btnCreate);
        ImageView returnIcon = findViewById(R.id.returnIcon);
        auth = new ApiAuth("http://10.0.2.2:5000", this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter login and password", Toast.LENGTH_SHORT).show();
                } else {
                    AuthData authData = new AuthData(login.getText().toString(), pass.getText().toString());
                    auth.login(authData);
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        returnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    public void handler(User user){
        if (user.getId() != -1) {

            Database db = Database.getDBinstance(LoginActivity.this);
            db.userDao().insert(user);

            System.out.println("user get");
            System.out.println(user.getId());
            startActivity(new Intent(LoginActivity.this, GameActivity.class));
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "Wrong password or login", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
