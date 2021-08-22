package com.example.newsexample.data.api;

import com.example.newsexample.data.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("?q=football")
    Call<NewsResponse> getNews(@Query("fromDate") String fromDate, @Query("toDate")String toDate, @Query("sortBy")String sortBy, @Query("apiKey")String apiKey);

}
