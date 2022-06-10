package com.example.hangman.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.moshi.Moshi;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class ApiBase {

    protected OkHttpClient client;
    protected String url;
    protected Moshi moshi;

    public  ApiBase(){
        moshi = new Moshi.Builder().build();
        client = new OkHttpClient();
    }

    protected Request createPostRequest(String query, String  body){
        return new Request
                .Builder()
                .url(url + query)
                .post(RequestBody.create(body,
                        MediaType.parse("application/json; charset=utf-8")))
                .build();
    }

    protected Request createGetRequest(String query){
        return new Request
                .Builder()
                .url(url + query)
                .get()
                .build();
    }
}
