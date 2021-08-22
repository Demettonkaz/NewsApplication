package com.example.newsexample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Converter {

    public static <T> T stringToObject(String s, Class<T> classOfT) {
        try {
            GsonBuilder builder = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeSpecialFloatingPointValues();
            Gson gson = builder.create();
            return gson.fromJson(s, classOfT);
        } catch (Exception error) {
            return null;
        }
    }

    public static String objectToString(Object o) {
        try {
            GsonBuilder builder = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeSpecialFloatingPointValues();
            Gson gson = builder.create();
            return gson.toJson(o);
        } catch (Exception error) {
            return "";
        }
    }

}
