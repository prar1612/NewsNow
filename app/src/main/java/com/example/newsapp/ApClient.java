package com.example.newsapp;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApClient {
    private static final String BASE_URL="https://newsapi.org/v2/";
    private static ApClient apiClient;
    private static Retrofit retrofit;

    private ApClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static synchronized ApClient getInstance(){
        if(apiClient == null){
            apiClient = new ApClient();
        }
        return apiClient;
    }

    public ApiInterface getApi(){

        return retrofit.create(ApiInterface.class);
    }
}
