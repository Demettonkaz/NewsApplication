package com.example.newsexample.view.main.viewmodel;

import android.content.Context;

import com.example.newsexample.data.model.Article;
import com.example.newsexample.data.model.NewsResponse;
import com.example.newsexample.data.api.ApiClient;
import com.example.newsexample.data.api.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel {

    private static String API_KEY = "ae68088e70d04639b4950bdc9d546924";
    private static String SORT_BY = "publishedAt";

    private Context context;
    private ApiInterface apiInterface;
    private List<Article> newsList = new ArrayList<>();
    private List<Article> articlesList = new ArrayList<>();


    public MainViewModel(Context context) {
        this.context = context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void clearList() {
        newsList.clear();
        articlesList.clear();
    }

    public List<Article> getNewsList() {
        if (articlesList.size()!=0)
            return articlesList;
        else
            return null;
    }


    public void fetchNews(String fromDate, String toDate) {
        Call<NewsResponse> call = apiInterface.getNews(fromDate, toDate, SORT_BY, API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                NewsResponse responseBody = response.body();
                if (responseBody != null) {
                    newsList = responseBody.getArticles();
                    articlesList.addAll(newsList);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
