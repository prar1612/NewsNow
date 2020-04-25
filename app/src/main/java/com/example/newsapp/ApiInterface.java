
package com.example.newsapp;

import com.example.newsapp.Model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<Headlines> getHeadlinesForCategory(
            @Query("category") String category,
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headlines> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}