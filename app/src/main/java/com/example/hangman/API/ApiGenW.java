package com.example.hangman.API;

import androidx.annotation.NonNull;

import com.example.hangman.Activities.Interfaces.IGameActivity;
import com.example.hangman.Activities.Interfaces.IStatActivity;
import com.example.hangman.Models.Word;
import com.squareup.moshi.JsonAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ApiGenW extends ApiBase {

    private IGameActivity activity;
    public ApiGenW(String apiUrl, IGameActivity activity){
        this.url = apiUrl;
        this.activity = activity;
    }

    public void getWord(){
        Request request = createGetRequest("/game/word");
        JsonAdapter<Word> statsJsonAdapter = moshi.adapter(Word.class);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Word word = statsJsonAdapter.fromJson(response.body().source());
                    activity.handler(word);
                }
                else{

                }
            }
        });
    }

}
