package com.example.hangman.API;

import androidx.annotation.NonNull;

import com.example.hangman.Activities.Interfaces.IStatActivity;
import com.example.hangman.Models.UserStatistic;
import com.example.hangman.Models.Word;
import com.squareup.moshi.JsonAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ApiGame extends ApiBase {

    private IStatActivity activity;
    public ApiGame(String apiUrl, IStatActivity activity){
        this.url = apiUrl;
        this.activity = activity;
    }

    public void sendStat(Statistic stat) {
        String postBody = stat.toString();
        Request request = createPostRequest("/stats", postBody);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }

    public void getStats(int userId){
        Request request = createGetRequest(String.format("/stats/%d", userId));
        JsonAdapter<UserStatistic> statsJsonAdapter = moshi.adapter(UserStatistic.class);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    UserStatistic userStats = statsJsonAdapter.fromJson(response.body().source());
                    activity.handler(userStats);
                }
                else{

                }
            }
        });
    }

}
