package com.example.newsexample.view.detail.viewmodel;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DetailViewModel {

    private Context context;

    public DetailViewModel(Context context){
        this.context = context;
    }

    public Calendar dateFormatter(String publishedDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            calendar.setTime(sdf.parse(publishedDate));
        } catch (ParseException e) {
            System.out.println("Something went wrong.");
        }
        return calendar;
    }
}
