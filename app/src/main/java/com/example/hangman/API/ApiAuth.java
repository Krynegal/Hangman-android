package com.example.hangman.API;

import androidx.annotation.NonNull;

import com.example.hangman.Activities.Interfaces.IAuthActivity;
import com.example.hangman.Models.AuthData;
import com.example.hangman.db.User;
import com.squareup.moshi.JsonAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ApiAuth extends ApiBase {

    private IAuthActivity activity;
    public ApiAuth(String url, IAuthActivity activity) {
        super();
        this.url = url;
        this.activity = activity;
    }

    public void login(AuthData authData){
        String body = authData.toString();
        System.out.println(body);
        Request request = createPostRequest("/users/login", body);
        JsonAdapter<User> userJsonAdapter = moshi.adapter(User.class);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.handler(new User(-1, null));;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    User user = userJsonAdapter.fromJson(response.body().source());
                    activity.handler(user);
                }
                else{
                    activity.handler(new User(-1, null));
                }
            }
        });
    }

    public void signUp(AuthData authData){
        String body = authData.toString();
        Request request = createPostRequest("/users/signUp", body);

        JsonAdapter<User> userJsonAdapter = moshi.adapter(User.class);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                activity.handler(new User(-1, null));;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    User user = userJsonAdapter.fromJson(response.body().source());
                    activity.handler(user);
                }
                else{
                    activity.handler(new User(-1, null));
                }
            }
        });
    }

}
